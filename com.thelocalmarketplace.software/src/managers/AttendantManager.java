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

import java.util.*;
import java.math.BigDecimal;

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
import observers.payment.CoinMonitor;
import observers.payment.ReceiptPrinterObserver;

public class AttendantManager implements IAttendantManager, IAttendantManagerNotify {

	// hardware references
	protected ISelfCheckoutStation machine;

	// object references
	protected SystemManager sm;

	// object ownership
	protected Map<BigDecimal, CoinMonitor> coinMonitorMap = new HashMap<BigDecimal, CoinMonitor>();
	protected Map<BigDecimal, CoinMonitor> bankNoteMonitorMap = new HashMap<BigDecimal, CoinMonitor>();
	protected ReceiptPrinterObserver rpls;

	// vars
	protected boolean hasPaper = false;
	protected boolean hasInk = false;
	protected boolean paperLow = false;
	protected boolean inkLow = false;
	protected boolean coinsFull = false;
	protected boolean banknotesFull = false;
	protected Map<BigDecimal, Boolean> coinDispenserLow = new HashMap<>();
	protected Map<BigDecimal, Boolean> banknoteDispenserLow = new HashMap<>();


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

        // initializing the maps
        for (BigDecimal denom : machine.getCoinDenominations()) {
            coinDispenserLow.put(denom, false);
        }
        for (BigDecimal denom : machine.getBanknoteDenominations()) {
            banknoteDispenserLow.put(denom, false);
        }

		// creating the printer observer
		rpls = new ReceiptPrinterObserver(this, machine.getPrinter());

		// creating coin dispenser observers
//		coinMonitorMap.put(new BigDecimal(0.01), new CoinMonitor());
//		coinMonitorMap.put(new BigDecimal(0.05), new CoinMonitor());
//		coinMonitorMap.put(new BigDecimal(0.10), new CoinMonitor());
//		coinMonitorMap.put(new BigDecimal(0.25), new CoinMonitor());
//		coinMonitorMap.put(new BigDecimal(1.00), new CoinMonitor());
//		coinMonitorMap.put(new BigDecimal(2.00), new CoinMonitor());
//
//		// banknote dispenser observers
//		bankNoteMonitorMap.put(new BigDecimal(5.00), new CoinMonitor());
//		bankNoteMonitorMap.put(new BigDecimal(10.00), new CoinMonitor());
//		bankNoteMonitorMap.put(new BigDecimal(20.00), new CoinMonitor());
//		bankNoteMonitorMap.put(new BigDecimal(50.00), new CoinMonitor());
//		bankNoteMonitorMap.put(new BigDecimal(100.00), new CoinMonitor());
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
	public void notifyCoinsFull(BigDecimal denom) {

	}

	@Override
	public void notifyCoinsFull(CoinStorageUnit unit) {

	}

	@Override
	public void notifyCoinsEmpty(BigDecimal denom) {

	}

	@Override
	public void notifyBanknotesFull(BigDecimal denom) {

	}

	@Override
	public void notifyBanknotesFull(BanknoteStorageUnit unit) {

	}

	@Override
	public void notifyBanknotesEmpty(BigDecimal denom) {

	}

	@Override
	public void notifyCoinEmitted(BigDecimal denom) {
		checkCoinDispenserState(denom);
	}

	@Override
	public void notifyBanknoteEmitted(BigDecimal denom) {
		checkBanknoteDispenserState(denom);
	}

	@Override
	public void notifyCoinAdded(BigDecimal denom) {
		checkCoinDispenserState(denom);
	}

	@Override
	public void notifyBanknoteAdded(BigDecimal denom) {
		checkBanknoteDispenserState(denom);
	}

	@Override
	public void notifyCoinAdded(CoinStorageUnit unit) {
		// TODO check state of the coin storage unit

		// if coins full
		notifyCoinsFull(unit);
	}

	@Override
	public void notifyBanknoteAdded(BanknoteStorageUnit unit) {
		// TODO check state of the banknote storage unit

		// if banknotes full
		notifyBanknotesFull(unit);
	}

	protected void checkCoinDispenserState(BigDecimal denom) {

	}

	protected void checkBanknoteDispenserState(BigDecimal denom) {

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
		if (banknotesFull) {
			List<Banknote> notes = machine.getBanknoteStorage().unload();

			// summation
			BigDecimal total = BigDecimal.ZERO;
			for (Banknote note : notes) {
				total = total.add(note.getDenomination());
			}

			// notifying the station
			notifyAttendant("Unloaded $" + total.toString() + " from the banknote storage unit.");
		}
	}

	@Override
	public void maintainCoinStorage() {
		if (coinsFull) {
			List<Coin> coins = machine.getCoinStorage().unload();

			// summation
			BigDecimal total = BigDecimal.ZERO;
			for (Coin coin : coins) {
				total = total.add(coin.getValue());
			}

			// notifying the station
			notifyAttendant("Unloaded $" + total.toString() + " from the coin storage unit.");
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
	public boolean canPrint() {
		return hasInk && hasPaper;
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
