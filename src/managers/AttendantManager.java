// Ali Akbari 30171539
// Sheikh Falah Sheikh Hasan 30175335
// Ohiomah Imohi 30187606
// Emmanuel Trinidad 30172372
// Nicholas MacKinnon 30172737
// Abdullah Ishtiaq 30153185
// Md Abu Sinan 30154627
// Gurjit Samra: 30172814
// Michael Hoang: 30123605
// Ana Laura Espinosa Garza: 30198679
// Umer Rehman: 30169819
// Liam Major: 30223023
// Logan Miszaniec: 30156384
// Nezla Annaisha: 30123223
// Maleeha Siddiqui: 30179762
// Kelvin Jamila: 30117164
// Adefikayo Akande 30185937
// Shaikh Sajid Mahmood 30182396
// Alecxia Zaragoza 30150008
// Kevlam Chundawat 30180662
// Anmol Bansal 30159559

package managers;

import com.jjjwelectronics.OverloadedDevice;
import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import managers.enums.SessionStatus;
import managers.interfaces.IAttendantManager;
import managers.interfaces.IAttendantManagerNotify;
import observers.payment.*;

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

	// vars
	protected boolean hasPaper = false;
	protected boolean hasInk = false;
	protected boolean paperLow = false;
	protected boolean inkLow = false;
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
	public void notifyPaperLow() {
		paperLow = true;
	}

	@Override
	public void notifyInkLow() {
		inkLow = true;
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

		// checking if we actually found the denomination of the dispenser
		if (denom == null) {
			throw new IllegalArgumentException("An invalid dispenser was passed into the function");
		}

		// updating the state of the coin dispenser in the map
		coinDispenserLow.put(denom, dispenser.size() <= (dispenser.getCapacity() * 0.10));

		// checking if the dispenser is empty
		if (dispenser.size() == 0) {
			notifyAttendant("The " + denom + " coin dispenser is empty.");
		}

		// checking if the dispenser is almost empty
		if (coinDispenserLow.get(denom)) {
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

		// checking if we actually found the denomination of the dispenser
		if (denom == null) {
			throw new IllegalArgumentException("An invalid dispenser was passed into the function");
		}

		// updating the state of the coin dispenser in the map
		banknoteDispenserLow.put(denom, dispenser.size() <= (dispenser.getCapacity() * 0.10));

		// checking if the dispenser is empty
		if (dispenser.size() == 0) {
			notifyAttendant("The " + dispenser + " banknote dispenser is empty.");
		}

		// checking if the dispenser is almost empty
		if (banknoteDispenserLow.get(denom)) {
			notifyAttendant("The " + dispenser + " banknote dispenser is less than 10% full.");
		}
	}

	@Override
	public void notifyCoinStorageUnitStateChange(CoinStorageUnit unit) {
		// if coins almost full
		if (unit.getCoinCount() == unit.getCapacity()) {
			coinStorageUnitFull = true;
			notifyAttendant("The coin storage unit is full.");
		} else if (unit.getCoinCount() >= (unit.getCapacity() * 0.90)) {
			notifyAttendant("The coin storage unit is over 90% full.");
		}
	}

	@Override
	public void notifyBanknoteStorageUnitStateChange(BanknoteStorageUnit unit) {
		// if coins almost full
		if (unit.getBanknoteCount() == unit.getCapacity()) {
			banknoteStorageUnitFull = true;
			notifyAttendant("The banknote storage unit is full.");
		} else if (unit.getBanknoteCount() >= (unit.getCapacity() * 0.90)) {
			notifyAttendant("The banknote storage unit is over 90% full.");
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
		// TODO implement this method
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
		getPrinterStatus();
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
		// TODO implement this method
	}

	@Override
	public boolean canPrint() {
		return hasInk && hasPaper;
	}

	@Override
	public void requestPurchaseBags(int count) {
		// TODO implement this method
	}

	/**
	 * Getting the state of the printer's ink & paper.
	 */
	protected void getPrinterStatus() {
		try {
			hasInk = machine.getPrinter().inkRemaining() > 0;
		} catch (UnsupportedOperationException e) {
			// WHYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
			hasInk = true;
		}

		try {
			hasPaper = machine.getPrinter().paperRemaining() > 0;
		} catch (UnsupportedOperationException e) {
			// WHYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
			hasPaper = true;
		}
	}

}
