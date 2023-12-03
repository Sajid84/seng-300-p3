package observers.payment;

import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispenserObserver;
import com.tdc.banknote.IBanknoteDispenser;
import managers.AttendantManager;
import observers.AbstractComponentObserver;

/**
 * This class represents an observer for a banknote dispenser device. It extends
 * AbstractComponentObserver and implements the BanknoteDispenserObserver
 * interface.
 */
public class BanknoteMonitor extends AbstractComponentObserver implements BanknoteDispenserObserver {

    // Reference to the AttendantManager
    AttendantManager am;

    /**
     * Constructor for BanknoteMonitor.
     * 
     * @param am   The AttendantManager associated with the observer. It cannot be
     *             null.
     * @param disp The IBanknoteDispenser device being observed. It cannot be null.
     * @throws IllegalArgumentException if either the AttendantManager or the
     *                                  IBanknoteDispenser device is null.
     */
    public BanknoteMonitor(AttendantManager am, IBanknoteDispenser disp) {
        super(disp);

        // Checking for null AttendantManager
        if (am == null) {
            throw new IllegalArgumentException("The AttendantManager cannot be null.");
        }

        // Saving the reference and attaching the observer to the device
        this.am = am;
        disp.attach(this);
    }

    /**
     * This method is called when the banknote dispenser is full. It notifies the
     * associated AttendantManager about the dispenser state change.
     * 
     * @param dispenser The IBanknoteDispenser device that is full.
     */
    @Override
    public void moneyFull(IBanknoteDispenser dispenser) {
        // Notify the AttendantManager about the dispenser state change
        am.notifyBanknoteDispenserStateChange(dispenser);
    }

    /**
     * This method is called when the banknote dispenser is empty. It notifies the
     * associated AttendantManager about the dispenser state change.
     * 
     * @param dispenser The IBanknoteDispenser device that is empty.
     */
    @Override
    public void banknotesEmpty(IBanknoteDispenser dispenser) {
        // Notify the AttendantManager about the dispenser state change
        am.notifyBanknoteDispenserStateChange(dispenser);
    }

    /**
     * This method is called when a banknote is added to the dispenser. It notifies
     * the associated AttendantManager about the dispenser state change.
     * 
     * @param dispenser The IBanknoteDispenser device to which a banknote was added.
     * @param banknote  The added Banknote.
     */
    @Override
    public void banknoteAdded(IBanknoteDispenser dispenser, Banknote banknote) {
        // Notify the AttendantManager about the dispenser state change
        am.notifyBanknoteDispenserStateChange(dispenser);
    }

    /**
     * This method is called when a banknote is removed from the dispenser. It
     * notifies the associated AttendantManager about the dispenser state change.
     * 
     * @param dispenser The IBanknoteDispenser device from which a banknote was
     *                  removed.
     * @param banknote  The removed Banknote.
     */
    @Override
    public void banknoteRemoved(IBanknoteDispenser dispenser, Banknote banknote) {
        // Notify the AttendantManager about the dispenser state change
        am.notifyBanknoteDispenserStateChange(dispenser);
    }

    /**
     * This method is called when banknotes are loaded into the dispenser. It
     * notifies the associated AttendantManager about the dispenser state change.
     * 
     * @param dispenser The IBanknoteDispenser device into which banknotes were
     *                  loaded.
     * @param banknotes  The loaded Banknotes.
     */
    @Override
    public void banknotesLoaded(IBanknoteDispenser dispenser, Banknote... banknotes) {
        // Notify the AttendantManager about the dispenser state change
        am.notifyBanknoteDispenserStateChange(dispenser);
    }

    /**
     * This method is called when banknotes are unloaded from the dispenser. It
     * notifies the associated AttendantManager about the dispenser state change.
     * 
     * @param dispenser The IBanknoteDispenser device from which banknotes were
     *                  unloaded.
     * @param banknotes  The unloaded Banknotes.
     */
    @Override
    public void banknotesUnloaded(IBanknoteDispenser dispenser, Banknote... banknotes) {
        // Notify the AttendantManager about the dispenser state change
        am.notifyBanknoteDispenserStateChange(dispenser);
    }
}
