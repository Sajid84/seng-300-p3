// Liam Major			- 30223023
// Md Abu Sinan			- 30154627
// Ali Akbari			- 30171539
// Shaikh Sajid Mahmood	- 30182396
// Abdullah Ishtiaq		- 30153185
// Adefikayo Akande		- 30185937
// Alecxia Zaragoza		- 30150008
// Ana Laura Espinosa Garza - 30198679
// Anmol Bansal			- 30159559
// Emmanuel Trinidad	- 30172372
// Gurjit Samra			- 30172814
// Kelvin Jamila		- 30117164
// Kevlam Chundawat		- 30180662
// Logan Miszaniec		- 30156384
// Maleeha Siddiqui		- 30179762
// Michael Hoang		- 30123605
// Nezla Annaisha		- 30123223
// Nicholas MacKinnon	- 30172737
// Ohiomah Imohi		- 30187606
// Sheikh Falah Sheikh Hasan - 30175335
// Umer Rehman			- 30169819

package managers;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.bag.ReusableBag;
import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import enums.ScanType;
import enums.SessionStatus;
import managers.interfaces.IAttendantManager;
import managers.interfaces.IAttendantManagerNotify;
import observers.attendant.*;

import java.math.BigDecimal;
import java.util.*;

public class AttendantManager implements IAttendantManager, IAttendantManagerNotify {

	// hardware references
	protected ISelfCheckoutStation machine;

	// object references
	protected SystemManager sm;

	// object ownership
	protected Map<BigDecimal, CoinMonitor> coinMonitorMap = new HashMap<BigDecimal, CoinMonitor>();
	protected Map<BigDecimal, BanknoteMonitor> bankNoteMonitorMap = new HashMap<BigDecimal, BanknoteMonitor>();
	protected ReceiptPrinterObserver rpls;
	protected Map<BigDecimal, Boolean> coinDispenserLow = new HashMap<>();
	protected Map<BigDecimal, Boolean> banknoteDispenserLow = new HashMap<>();
	protected CoinStorageMonitor csu;
	protected BanknoteStorageMonitor bsu;
	protected BagMonitor bm;

	// vars
	protected boolean hasPaper = false;
	protected boolean hasInk = false;
	protected boolean paperLow = false;
	protected boolean inkLow = false;
	protected boolean bagsEmpty = false;
	protected boolean bagsLow = false;
	protected int bagCount = 0;
	protected boolean coinStorageUnitFull = false;
	protected boolean banknoteStorageUnitFull = false;

	public AttendantManager(SystemManager sm) {

		// checking arguments
		if (sm == null) {
			throw new IllegalArgumentException("the system manager cannot be null");
		}

		// copying the system manager
		this.sm = sm;
	}

	@Override
	public void configure(ISelfCheckoutStation machine) {
		// saving reference
		this.machine = machine;

		/// initializing the maps

		// creating the coin monitors for each of the dispensers
		for (BigDecimal denom : machine.getCoinDenominations()) {
			coinMonitorMap.put(denom, new CoinMonitor(this, machine.getCoinDispensers().get(denom)));
			coinDispenserLow.put(denom, false);
		}

		// creating the banknote monitors for each of the dispensers
		for (BigDecimal denom : machine.getBanknoteDenominations()) {
			bankNoteMonitorMap.put(denom, new BanknoteMonitor(this, machine.getBanknoteDispensers().get(denom)));
			banknoteDispenserLow.put(denom, false);
		}

		// creating the observers for the storage units
		bsu = new BanknoteStorageMonitor(this, machine.getBanknoteStorage());
		csu = new CoinStorageMonitor(this, machine.getCoinStorage());

		// creating the printer observer
		rpls = new ReceiptPrinterObserver(this, machine.getPrinter());

		// creating the bag dispenser observer
		bm = new BagMonitor(this, machine.getReusableBagDispenser());
	}

	@Override
	public SessionStatus getState() {
		return sm.getState();
	}

	@Override
	public void blockSession() {
		sm.blockSession();
	}

	@Override
	public void unblockSession() {
		sm.unblockSession();
	}

	@Override
	public void signalForAttendant() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyAttendant(String reason) {
		// TODO replace this with a GUI
		System.out.printf("[ATTENDANT NOTIFY]: %s\n", reason);
	}

	@Override
	public void notifyLowPaper() {
		paperLow = true;
		notifyAttendant("The receipt paper is below 10%!");
	}

	@Override
	public void notifyLowInk() {
		inkLow = true;
		notifyAttendant("The ink is below 10%!");
	}

	@Override
	public void notifyCoinDispenserStateChange(ICoinDispenser dispenser) {
		// getting the denomination
		BigDecimal denom = null;
		for (BigDecimal d : machine.getCoinDenominations()) {
			if (machine.getCoinDispensers().get(d) == dispenser) {
				denom = d;
				break;
			}
		}

		// checking the state of the dispenser
		checkCoinDispenserState(denom, dispenser);
	}

	protected void checkCoinDispenserState(BigDecimal denom, ICoinDispenser dispenser) {
		// checking if we actually found the denomination of the dispenser
		if (denom == null) {
			throw new IllegalArgumentException("An invalid dispenser was passed into the function");
		}
		if (dispenser == null) {
			throw new IllegalArgumentException("A null coin dispenser was passed into the function.");
		}

		// updating the state of the coin dispenser in the map
		coinDispenserLow.put(denom, dispenser.size() <= (dispenser.getCapacity() * 0.10));

		// checking if the dispenser is empty
		if (dispenser.size() == 0) {
			notifyAttendant("The " + denom + " coin dispenser is empty.");
		}

		// checking if the dispenser is almost empty
		else if (coinDispenserLow.get(denom)) {
			notifyAttendant("The " + denom + " coin dispenser is less than 10% full.");
		}
	}

	@Override
	public void notifyBanknoteDispenserStateChange(IBanknoteDispenser dispenser) {
		// getting the denomination
		BigDecimal denom = null;
		for (BigDecimal d : machine.getBanknoteDenominations()) {
			if (machine.getBanknoteDispensers().get(d) == dispenser) {
				denom = d;
				break;
			}
		}

		// checking the state of the dispenser
		checkBanknoteDispenserState(denom, dispenser);
	}

	protected void checkBanknoteDispenserState(BigDecimal denom, IBanknoteDispenser dispenser) {
		// checking if we actually found the denomination of the dispenser
		if (denom == null) {
			throw new IllegalArgumentException("An invalid dispenser was passed into the function");
		}
		if (dispenser == null) {
			throw new IllegalArgumentException("A null banknote dispenser was passed into the function.");
		}

		// updating the state of the coin dispenser in the map
		banknoteDispenserLow.put(denom, dispenser.size() <= (dispenser.getCapacity() * 0.10));

		// checking if the dispenser is empty
		if (dispenser.size() == 0) {
			notifyAttendant("The " + denom + " banknote dispenser is empty.");
		}

		// checking if the dispenser is almost empty
		else if (banknoteDispenserLow.get(denom)) {
			notifyAttendant("The " + dispenser + " banknote dispenser is less than 10% full.");
		}
	}

	@Override
	public void notifyCoinStorageUnitStateChange(CoinStorageUnit unit) {
		checkCoinStorageUnitState(unit);
	}

	protected void checkCoinStorageUnitState(CoinStorageUnit unit) {
		// if coins almost full
		if (unit.getCoinCount() == unit.getCapacity()) {
			coinStorageUnitFull = true;
			notifyAttendant("The coin storage unit is full.");
		} else if (unit.getCoinCount() >= (unit.getCapacity() * 0.70)) {
			coinStorageUnitFull = true;
			notifyAttendant("The coin storage unit is over 70% full.");
		} else {
			coinStorageUnitFull = false;
		}
	}

	@Override
	public void notifyBanknoteStorageUnitStateChange(BanknoteStorageUnit unit) {
		checkBanknoteStorageUnitState(unit);
	}

	protected void checkBanknoteStorageUnitState(BanknoteStorageUnit unit) {
		// if coins almost full
		if (unit.getBanknoteCount() == unit.getCapacity()) {
			banknoteStorageUnitFull = true;
			notifyAttendant("The banknote storage unit is full.");
		} else if (unit.getBanknoteCount() >= (unit.getCapacity() * 0.70)) {
			banknoteStorageUnitFull = true;
			notifyAttendant("The banknote storage unit is over 70% full.");
		} else {
			banknoteStorageUnitFull = false;
		}
	}

	@Override
	public void maintainBanknoteDispensers() {
		// loading the dispensers if they're low
		for (BigDecimal denom : machine.getBanknoteDenominations()) {
			Boolean low = banknoteDispenserLow.get(denom);

			// filling the dispenser if it's low
			if (low) {
				// getting the dispenser object
				IBanknoteDispenser dispenser = machine.getBanknoteDispensers().get(denom);

				// creating an array of banknotes
				int amount = (int) (dispenser.getCapacity() * 0.5);
				Banknote[] notes = new Banknote[amount];

				for (int i = 0; i < amount; ++i) {
					notes[i] = new Banknote(Currency.getInstance(Locale.CANADA), denom);
				}

				// loading the machine
				try {
					dispenser.load(notes);
				} catch (CashOverloadException e) {
					// from this state, this should never happen
					notifyAttendant("Loading banknotes into the machine caused an overload.");
				}
			}
		}
	}

	@Override
	public void maintainPaper() {
		if (paperLow) {
			try {
				machine.getPrinter().addPaper(1 << 5); // this is hardcoded because there's no getter!
			} catch (OverloadedDevice e) {
				notifyAttendant("Adding paper to the printer cause an overload.");
			}
		}
	}

	@Override
	public void maintainCoinDispensers() {
		// loading the dispensers if they're low
		for (BigDecimal denom : machine.getCoinDenominations()) {
			Boolean low = coinDispenserLow.get(denom);

			// filling the dispenser if it's low
			if (low) {
				// getting the dispenser object
				ICoinDispenser dispenser = machine.getCoinDispensers().get(denom);

				// creating an array of banknotes
				int amount = (int) (dispenser.getCapacity() * 0.5);
				Coin[] coins = new Coin[amount];

				for (int i = 0; i < amount; ++i) {
					coins[i] = new Coin(Currency.getInstance(Locale.CANADA), denom);
				}

				// loading the machine
				try {
					dispenser.load(coins);
				} catch (CashOverloadException e) {
					// from this state, this should never happen
					notifyAttendant("Loading banknotes into the machine caused an overload.");
				}
			}
		}
	}

	@Override
	public void maintainInk() {
		if (inkLow) {
			try {
				machine.getPrinter().addInk(1 << 10); // this is hardcoded because there's no getter!
			} catch (OverloadedDevice e) {
				notifyAttendant("Adding ink to the printer cause an overload.");
			}
		}
	}

	public boolean isInkLow() {
		return inkLow;
	}

	public boolean isPaperLow() {
		return paperLow;
	}

	@Override
	public void maintainBanknoteStorage() {
		if (banknoteStorageUnitFull) {
			List<Banknote> notes = machine.getBanknoteStorage().unload();

			// summation
			BigDecimal total = BigDecimal.ZERO;
			for (Banknote note : notes) {
				total = total.add(note.getDenomination());
			}

			// notifying the station
			notifyAttendant("Unloaded $" + total + " from the banknote storage unit.");
		}
	}

	@Override
	public void maintainBags() {
		if (bagsEmpty || bagsLow) {
			// forming the array
			int len = (int) (machine.getReusableBagDispenser().getCapacity() * 0.5);
			ReusableBag[] bags = new ReusableBag[len];

			// populating it
			for (int i = 0; i < len; ++i) {
				bags[i] = new ReusableBag();
			}

			// filling the bag dispenser
			try {
				machine.getReusableBagDispenser().load(bags);
			} catch (OverloadedDevice e) {
				// this should never happen from this state
			}
		}
	}

	@Override
	public void maintainCoinStorage() {
		if (coinStorageUnitFull) {
			List<Coin> coins = machine.getCoinStorage().unload();

			// summation
			BigDecimal total = BigDecimal.ZERO;
			for (Coin coin : coins) {
				total = total.add(coin.getValue());
			}

			// notifying the station
			notifyAttendant("Unloaded $" + total + " from the coin storage unit.");
		}
	}

	@Override
	public boolean isBlocked() {
		return sm.isBlocked();
	}

	@Override
	public boolean isUnblocked() {
		return sm.isUnblocked();
	}

	@Override
	public boolean isPaid() {
		return sm.isPaid();
	}

	@Override
	public void requestDisableMachine() {
		sm.requestDisableMachine();
	}

	@Override
	public void requestEnableMachine() {
		sm.requestEnableMachine();
	}

	@Override
	public boolean isDisabled() {
		return sm.isDisabled();
	}

	@Override
	public void reset() {
		// setting has papper & ink
		checkPrinterState();

		/// checking the status of the devices

		// bag dispenser
		checkBagDispenserState();

		// storage units
		checkCoinStorageUnitState(machine.getCoinStorage());
		checkBanknoteStorageUnitState(machine.getBanknoteStorage());

		// coin dispensers
		for (BigDecimal denom : machine.getCoinDenominations()) {
			checkCoinDispenserState(denom, machine.getCoinDispensers().get(denom));
		}

		// banknote dispensers
		for (BigDecimal denom : machine.getBanknoteDenominations()) {
			checkBanknoteDispenserState(denom, machine.getBanknoteDispensers().get(denom));
		}
	}

	@Override
	public void notifyPaper(boolean hasPaper) {
		this.hasPaper = hasPaper;
	}

	@Override
	public void notifyInk(boolean hasInk) {
		this.hasInk = hasInk;
	}

	@Override
	public void notifyBagDispensed() {
		// decrementing the bag count
		--bagCount;

		// checking the state of the dispenser
		checkBagDispenserState();
	}

	@Override
	public void notifyBagsLoaded(int count) {
		if (count > 0) {
			bagCount = count; // treating this as the maximum capacity if there's an exception

			// resetting the count of the bags
			bagsEmpty = false;

			// checking the state of the bag dispenser
			checkBagDispenserState();
		}
	}

	@Override
	public void notifyBagsEmpty() {
		bagsEmpty = true;
	}

	protected void checkBagDispenserState() {
		// getting the capacity of the machine, this will never cause an error
		int capacity = machine.getReusableBagDispenser().getCapacity();
		int remaining;

		try {
			// trying to access the amount of bags remaining
			remaining = machine.getReusableBagDispenser().getQuantityRemaining();

			// updating the internal variable from this
			bagCount = remaining;
		} catch (UnsupportedOperationException e) {
			// setting the remaining to the bag count
			remaining = bagCount;
		}

		// checking if low
		if (remaining <= (capacity * 0.1)) {
			bagsLow = true;
			notifyAttendant("The bag dispenser has less than 10% capacity.");
		} else {
			bagsLow = false;
		}
	}

	@Override
	public boolean canPrint() {
		return hasInk && hasPaper;
	}

	@Override
	public void requestPurchaseBags(int count) {
		// checking if empty
		if (!hasBags()) {
			notifyAttendant("The customer tried to purchase bags but there are none available.");
			return;
		}

		int bound = count;

		// getting the upper bound
		if (count > bagCount) {
			bound = bagCount;
			notifyAttendant(
					"Cannot dispense the requested amount of bags for the customer, dispensing" + bagCount + " bags.");
		}

		// dispensing bags
		for (int i = 0; i < bound; ++i) {
			try {
				// dispensing a bag
				ReusableBag bag = machine.getReusableBagDispenser().dispense();

				// adding the item to the order
				sm.addItemToOrder(bag, ScanType.MAIN);
			} catch (EmptyDevice e) {
				notifyAttendant("Not enough bags could be dispensed for the customer.");
				break;
			}
		}
	}

	@Override
	public boolean hasBags() {
		return !bagsEmpty;
	}

	@Override
	public boolean isBagsLow() {
		return bagsLow;
	}

	/**
	 * Getting the state of the printer's ink & paper.
	 */
	protected void checkPrinterState() {
		try {
			hasInk = machine.getPrinter().inkRemaining() > 0;
		} catch (UnsupportedOperationException e) {
			/**
			 * Because I cannot actually obtain the amount of ink remaining, I need to
			 * rely on the events given to me by my observer.
			 *
			 * Why does this error exist.
			 */
		}

		try {
			hasPaper = machine.getPrinter().paperRemaining() > 0;
		} catch (UnsupportedOperationException e) {
			/**
			 * Because I cannot actually obtain the amount of ink remaining, I need to
			 * rely on the events given to me by my observer.
			 *
			 * Why does this error exist.
			 */
		}
	}

}
