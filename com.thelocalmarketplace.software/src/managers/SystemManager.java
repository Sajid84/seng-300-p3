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
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import driver.SystemManagerForm;
import managers.enums.PaymentType;
import managers.enums.ScanType;
import managers.enums.SessionStatus;
import managers.interfaces.*;
import utils.Pair;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	protected SystemManagerForm smf;

	// vars
	protected SessionStatus state;
	protected CardIssuer issuer;
	protected Map<String, List<Pair<Long, Double>>> records;
	protected boolean disabledRequest = false;

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
		smf = new SystemManagerForm(this);

		// setting the initial state
		setState(SessionStatus.NORMAL);
	}

	@Override
	public JPanel getPanel() {
		return smf.getPanel();
	}

	@Override
	public void configure(ITouchScreen touchScreen) {
		smf.configure(touchScreen);
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
		if (getState() == SessionStatus.PAID) {
			throw new IllegalStateException("cannot remove item from state PAID");
		}

		// not restricting this function, this is used to resolve discrepancies
		this.om.removeItemFromOrder(pair);
	}

	@Override
	public void insertCoin(Coin coin) {
		// not performing action if session is blocked
		if (getState() != SessionStatus.NORMAL)
			throw new IllegalStateException("cannot insert coin when in a non-normal state");

		this.pm.insertCoin(coin);
	}

	@Override
	public void insertBanknote(Banknote banknote) {
		// not performing action if session is blocked
		if (getState() != SessionStatus.NORMAL)
			throw new IllegalStateException("cannot insert banknote when in a non-normal state");

		this.pm.insertBanknote(banknote);
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
	public void swipeCard(Card card) throws IOException {
		// not performing action if session is blocked
		if (getState() != SessionStatus.NORMAL)
			throw new IllegalStateException("cannot swipe card when PAID");

		this.pm.swipeCard(card);
	}

	public boolean tenderChange() throws RuntimeException, NoCashAvailableException {
		if (getState() != SessionStatus.NORMAL)
			throw new IllegalStateException("cannot tender change when in a non-normal state");

		return this.pm.tenderChange();
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
		if (getState() == SessionStatus.PAID) {
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
		if (getState() != SessionStatus.NORMAL)
			throw new IllegalStateException("cannot add item when in a non-normal state");

		this.om.addItemToOrder(item, method);
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
		if (getState() == SessionStatus.PAID) {
			throw new IllegalStateException("cannot unblock from state PAID");
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
	public void signalForAttendant() {
		am.signalForAttendant();
	}

	@Override
	public void maintainBanknotes() {
		am.maintainBanknotes();
	}

	@Override
	public void maintainPaper() {
		am.maintainPaper();
	}

	@Override
	public void maintainCoins() {
		am.maintainCoins();
	}

	@Override
	public void maintainInk() {
		am.maintainInk();
	}

	@Override
	public boolean isBlocked() {
		return getState() == SessionStatus.BLOCKED;
	}

	@Override
	public boolean isUnblocked() {
		return getState() != SessionStatus.BLOCKED;
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
	public void requestDisableMachine() {
		disabledRequest = true;
	}

	@Override
	public void requestEnableMachine() {
		if (isDisabled())
			disabledRequest = false;
	}

	@Override
	public boolean isDisabled() {
		return getState() == SessionStatus.DISABLED;
	}

	@Override
	public void notifyItemAdded(Item item) {
		smf.notifyItemAdded(item);
	}

	@Override
	public void notifyItemRemoved(Item item) {
		smf.notifyItemRemoved(item);
	}

	@Override
	public void notifyStateChange(SessionStatus state) {
		// ТОDO revisit this method as it probably isn't the best way to do things
		smf.notifyStateChange(state);
	}
}
