// Liam Major 30223023
// André Beaulieu, UCID 30174544
// Nezla Annaisha
// Sheikh Falah Sheikh Hasan - 30175335

package managers;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.screen.ITouchScreen;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import driver.MainScreenForm;
import enums.PaymentType;
import enums.ScanType;
import enums.SessionStatus;
import managers.interfaces.*;
import utils.Pair;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * This class is meant to contain everything that is hardware related, this acts
 * as the main interface to the hardware and the underlying order management and
 * payment management systems.
 *
 * This delegates all functionality (with some exceptions) to the other manager
 * classes.
 */
public class SystemManager implements IScreen, ISystemManager, IPaymentManager, IOrderManager, IAttendantManager {

	// hardware references
	protected ISelfCheckoutStation machine;

	// object references

	// object ownership
	protected PaymentManager pm;
	protected OrderManager om;
	protected AttendantManager am;
	protected MainScreenForm msf;

	// vars
	protected SessionStatus state;
	protected CardIssuer issuer;
	protected Map<String, List<Pair<Long, Double>>> records;
	protected boolean disabledRequest = false;
	protected List<ISystemManagerNotify> observers = new ArrayList<>();

	/**
	 * This object is responsible for the needs of the customer. This is how the
	 * customer is supposed to interact with the system.
	 *
	 * @param issuer   a card issuer
	 * @param leniency the scale leniency
	 * @throws IllegalArgumentException when either argument is null
	 */
	public SystemManager(CardIssuer issuer, BigDecimal leniency) {
		// checking arguments
		if (issuer == null) {
			throw new IllegalArgumentException("the card issuer cannot be null");
		}

		if (leniency == null) {
			throw new IllegalArgumentException("the leniency cannot be null");
		}

		// init vars
		this.issuer = issuer; // a reference to the bank
		this.records = new HashMap<>();

		// creating the managers
		this.pm = new PaymentManager(this, issuer);
		this.om = new OrderManager(this, leniency);
		this.am = new AttendantManager(this);

		// creating the GUI
		msf = new MainScreenForm(this);

		// setting the initial state
		setState(SessionStatus.NORMAL);
	}

	@Override
	public JPanel getPanel() {
		return msf.getPanel();
	}

	@Override
	public JFrame getFrame() {
		throw new UnsupportedOperationException("This object does not have a JFrame");
	}

	@Override
	public void configure(ITouchScreen touchScreen) {
		msf.configure(touchScreen);
	}

	@Override
	public void configure(ISelfCheckoutStation machine) {
		// saving a reference
		this.machine = machine;

		// configuring the jframe
		configure(machine.getScreen());

		// configuring the managers
		this.pm.configure(this.machine);
		this.om.configure(this.machine);
		this.am.configure(this.machine);
	}

	/**
	 * This calculates the outstanding balance the customer must pay for their
	 * order.
	 *
	 * @return the amount of money still owed by the customer
	 */
	public BigDecimal getRemainingBalance() {
		return this.getTotalPrice().subtract(this.getCustomerPayment());
	}

	@Override
	public void removeItemFromOrder(Pair<Item, Boolean> pair) {
		if (isPaid() || isDisabled()) {
			throw new IllegalStateException("cannot remove item from state paid or disabled");
		}

		// not restricting this function, this is used to resolve discrepancies
		this.om.removeItemFromOrder(pair);

		// notifying that an item was removed
		notifyItemRemoved(pair.getKey());
	}

	@Override
	public void insertCoin(Coin coin) {
		// not performing action if session is blocked
		if (!isUnblocked())
			throw new IllegalStateException("cannot insert coin when in a non-normal state");

		try {
            // trying to insert a coin into the system
            this.pm.insertCoin(coin);

            // publishing the event
            notifyPaymentAdded(coin.getValue());
		} catch (DisabledException e) {
			blockSession();
			notifyAttendant("Device Powered Off");
		} catch (CashOverloadException e) {
			// Should never happen
			blockSession();
			notifyAttendant("Machine cannot accept coins");
		} finally {
			checkPaid();
		}
	}

	@Override
	public void insertBanknote(Banknote banknote) {
		// not performing action if session is blocked
		if (!isUnblocked())
			throw new IllegalStateException("cannot insert banknote when in a non-normal state");

		try {
            // trying to insert a banknote into the system
            this.pm.insertBanknote(banknote);

            // publishing the event
            notifyPaymentAdded(banknote.getDenomination());
		} catch (DisabledException e) {
			blockSession();
			notifyAttendant("Device Powered Off");
		} catch (CashOverloadException e) {
			// Should never happen
			blockSession();
			notifyAttendant("Machine cannot accept banknotes");
		} finally {
			checkPaid();
		}
	}

	@Override
	public BigDecimal getTotalPrice() throws NullPointerSimulationException {
		return this.om.getTotalPrice();
	}

	@Override
	public BigDecimal getCustomerPayment() {
		return this.pm.getCustomerPayment();
	}

	@Override
	public CardData getMembershipData() {
		return pm.getMembershipData();
	}

	@Override
	public void swipeCard(Card card) {
		// not performing action if session is blocked
		if (!isUnblocked())
			throw new IllegalStateException("cannot swipe card when PAID");

		try {
			this.pm.swipeCard(card);
		} catch (IOException e) {
			notifyInvalidCardRead(card);
		} finally {
			checkPaid();
		}
	}
	@Override
	public void insertCard(Card card, String pin) {
		if (!isUnblocked()) {throw new IllegalStateException("cannot insert card when PAID");}

		try{
			// checking if the card can actually be inserted
			if (!card.hasChip) {
				throw new RuntimeException("Cannot insert a card without a chip.");
			}

			// inserting the card
			this.pm.insertCard(card, pin);
		} catch (IOException e) {
			notifyInvalidCardRead(card);
		} finally {
			checkPaid();
		}
	}

	@Override
	public void tapCard(Card card) {
		// not performing action if session is blocked
		if (!isUnblocked()) {throw new IllegalStateException("cannot tap card when PAID");}

		try{
			// checking if the card can actually be inserted
			if (!card.isTapEnabled) {
				throw new RuntimeException("Cannot tap a card that isn't tap enabled.");
			}

			// inserting the card
			this.pm.tapCard(card);
		} catch (IOException e) {
			notifyInvalidCardRead(card);
		} finally {
			checkPaid();
		}

	}
	
	@Override
	public boolean tenderChange() {
		if (!isPaid())
			throw new IllegalStateException("cannot tender change when not in a PAID state");

		// trying to dispense change
		try {
			return this.pm.tenderChange();
		} catch (NoCashAvailableException e) {
			notifyAttendant("Not enough cash available in the machine. Blocking the session.");
			return false;
		}
	}

	@Override
	public BigDecimal getExpectedMass() {
		return this.om.getExpectedMass();
	}

	@Override
	public List<Pair<Item, Boolean>> getItems() throws NullPointerSimulationException {
		return this.om.getItems();
	}

	@Override
	public void onAttendantOverride() {
		if (isPaid() || isDisabled()) {
			throw new IllegalStateException("attendant cannot override from state PAID");
		}

		this.om.onAttendantOverride();
	}

	@Override
	public void doNotBagRequest(boolean bagRequest) {
		this.om.doNotBagRequest(bagRequest);
	}

	@Override
	public boolean getDoNotBagRequest() {
		return om.getDoNotBagRequest();
	}

	@Override
	public void addItemToOrder(Item item, ScanType method) {
		// not performing action if session is blocked
		if (!isUnblocked())
			throw new IllegalStateException("cannot add item when in a non-normal state");

		// adding the item to the order
		this.om.addItemToOrder(item, method);

		if (!errorAddingItem()) {
			// publishing the event
			notifyItemAdded(item);
		} else {
			notifyAttendant("There was an error scanning the item.");
			blockSession();
		}

		// checking if the scales were overloaded
		if (this.om.isScaleOverloaded()) {
			notifyAttendant("A scale was overloaded due to an item that's too heavy.");
			blockSession();
		}
	}

	@Override
	public Item searchItemsByText(String description) {
		return om.searchItemsByText(description);
	}

	@Override
	public void blockSession() {
		// should be able to block from any state
		setState(SessionStatus.BLOCKED);

		// notify the attendant
		notifyAttendant("Session was blocked.");
	}

	@Override
	public void unblockSession() {
		if (isPaid() || isDisabled()) {
			throw new IllegalStateException("cannot unblock from state a non-NORMAL state");
		}

		setState(SessionStatus.NORMAL);
	}

	protected void setState(SessionStatus state) {
		if (state == null) {
			throw new IllegalArgumentException("cannot set the state of the manager to null");
		}

		// setting the state
		this.state = state;

		// publishing the state
		notifyStateChange(state);
	}

	@Override
	public SessionStatus getState() {
		return state;
	}

	@Override
	public void notifyPaid() {
		if (getState() == SessionStatus.BLOCKED) {
			throw new IllegalStateException("cannot set state from BLOCKED to PAID");
		}

		setState(SessionStatus.PAID);
	}

	@Override
	public boolean postTransactions() {
		// starting state, the function will return true if all the transactions passed
		boolean flag = true;

		for (String number : records.keySet()) {
			// getting the list of records
			List<Pair<Long, Double>> arr = records.get(number);

			// posting them
			for (Pair<Long, Double> pair : arr) {
				if (!issuer.postTransaction(number, pair.getKey(), pair.getValue())) {
					// a transaction failed to post, returning false
					flag = false;
				}
			}
		}

		// returning the state of the posted transactions
		return flag;
	}

	@Override
	public void recordTransaction(CardData card, Long holdnumber, Double amount) {
		if (getState() != SessionStatus.NORMAL) {
			throw new IllegalStateException("cannot record a transaction from a non-normal state");
		}

		// checking for null
		if (card == null) {
			throw new IllegalArgumentException("card cannot be null");
		}
		if (holdnumber == null) {
			throw new IllegalArgumentException("holdnumber cannot be null");
		}
		if (amount == null) {
			throw new IllegalArgumentException("amount cannot be null");
		}

		/// recording the transaction
		Pair<Long, Double> pair = new Pair<>(holdnumber, amount);

		// creating the list if not present
		List<Pair<Long, Double>> ar = records.getOrDefault(card.getNumber(), new ArrayList<>());

		// adding the transaction
		ar.add(pair);

		// recording the transaction
		records.put(card.getNumber(), ar);
	}

	@Override
	public void notifyAttendant(String reason) {
		am.notifyAttendant(reason);
	}

	@Override
	public void printReceipt(PaymentType type, Card card) {
		if (getState() != SessionStatus.PAID) {
			throw new IllegalStateException("Cannot print receipt. System state is not PAID.");
		}

		this.pm.printReceipt(type, card);
	}

	@Override
	public boolean isCardInserted() {
		return pm.isCardInserted();
	}

	public void addCustomerBags(Item bags) {
		if (getState() != SessionStatus.NORMAL) {
			throw new IllegalStateException("cannot add customer bags when not in a normal state");
		}

		this.om.addCustomerBags(bags);
	}

	@Override
	public boolean isScaleOverloaded() {
		return om.isScaleOverloaded();
	}

	@Override
	public BigDecimal priceOf(PLUCodedItem item) {
		return om.priceOf(item);
	}

	@Override
	public boolean errorAddingItem() {
		return om.errorAddingItem();
	}

	@Override
	public BigDecimal getActualWeight() {
		return om.getActualWeight();
	}

	@Override
	public BigDecimal getWeightAdjustment() {
		return om.getWeightAdjustment();
	}

	@Override
	public void signalForAttendant() {
		notifyAttendant("Station wishes the attention of the attendant manager");
		am.signalForAttendant();
	}

	@Override
	public void maintainBanknoteDispensers() {
		am.maintainBanknoteDispensers();
	}

	@Override
	public void maintainPaper() {
		am.maintainPaper();
	}

	@Override
	public void maintainCoinDispensers() {
		am.maintainCoinDispensers();
	}

	@Override
	public void maintainInk() {
		am.maintainInk();
	}

	@Override
	public void maintainBanknoteStorage() {
		am.maintainBanknoteStorage();
	}

	@Override
	public void maintainBags() {
		am.maintainBags();
	}

	@Override
	public void maintainCoinStorage() {
		am.maintainCoinStorage();
	}

	@Override
	public boolean isBlocked() {
		return getState() == SessionStatus.BLOCKED;
	}

	@Override
	public boolean isUnblocked() {
		return getState() == SessionStatus.NORMAL;
	}

	@Override
	public boolean isPaid() {
		return getState() == SessionStatus.PAID;
	}

	@Override
	public void disableMachine() {
		// TODO make this method actually do something
		if (disabledRequest) {
			setState(SessionStatus.DISABLED);
		}

		notifyAttendant("Machine was disabled");
	}

	@Override
	public void enableMachine() {
		// TODO make this method actually do something
		if (isDisabled()) {
			setState(SessionStatus.NORMAL);
			notifyAttendant("Machine was enabled");
		}

		// throwing when not in the expected state
		throw new IllegalStateException("cannot enable a non-disabled machine");
	}

	@Override
	public void attach(ISystemManagerNotify observer) {
		observers.add(observer);
	}

	@Override
	public boolean detach(ISystemManagerNotify observer) {
		if (observers.contains(observer)) {
			observers.remove(observer);
			return true;
		}
		return false;
	}

	@Override
	public List<BigDecimal> getCoinDenominations() {
		return machine.getCoinDenominations();
	}

	@Override
	public List<BigDecimal> getBanknoteDenominations() {
        return new ArrayList<>(Arrays.asList(machine.getBanknoteDenominations()));
	}

	@Override
	public void checkPaid() {
		System.out.println("Checking if the order is fully paid for or not.");
		if (getRemainingBalance().compareTo(BigDecimal.ZERO) <= 0) {
			System.out.println("Session is paid for.");
			notifyPaid();
			System.out.println("Dispensing change.");
			tenderChange();
		}
	}

	@Override
	public CardIssuer getIssuer() {
		return issuer;
	}

	@Override
	public void requestDisableMachine() {
		disabledRequest = true;
	}

	@Override
	public void requestEnableMachine() {
		if (isDisabled())
			disabledRequest = false;
	}

	@Override
	public boolean canPrint() {
		return am.canPrint();
	}

	@Override
	public void requestPurchaseBags(int count) {
		if (!isUnblocked()) {
			throw new IllegalStateException("Cannot add bags when not in a normal state");
		}

		am.requestPurchaseBags(count);
	}

	@Override
	public boolean hasBags() {
		return am.hasBags();
	}

	@Override
	public boolean isBagsLow() {
		return am.isBagsLow();
	}

	@Override
	public boolean isDisabled() {
		return getState() == SessionStatus.DISABLED;
	}

	@Override
	public void reset() {
		// resetting the managers
		am.reset();
		pm.reset();
		om.reset();

		// resetting self
		setState(SessionStatus.NORMAL);
	}

	@Override
	public void notifyItemAdded(Item item) {
		for (ISystemManagerNotify observer : observers) {
			observer.notifyItemAdded(item);
		}
	}

	@Override
	public void notifyItemRemoved(Item item) {
		for (ISystemManagerNotify observer : observers) {
			observer.notifyItemRemoved(item);
		}
	}

	@Override
	public void notifyStateChange(SessionStatus state) {
		for (ISystemManagerNotify observer : observers) {
			observer.notifyStateChange(state);
		}
	}

	@Override
	public void notifyRefresh() {
		for (ISystemManagerNotify observer : observers) {
			observer.notifyRefresh();
		}
	}

	@Override
	public void notifyPaymentAdded(BigDecimal value) {
		for (ISystemManagerNotify observer : observers) {
			observer.notifyPaymentAdded(value);
		}
	}

	@Override
	public void notifyPaymentWindowClosed() {
		for (ISystemManagerNotify observer : observers) {
			observer.notifyPaymentWindowClosed();
		}
	}

	@Override
	public void notifyInvalidCardRead(Card card) {
		for (ISystemManagerNotify observer : observers) {
			observer.notifyInvalidCardRead(card);
		}
	}
}
