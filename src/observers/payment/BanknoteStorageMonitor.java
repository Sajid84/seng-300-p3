package observers.payment;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.BanknoteStorageUnitObserver;

import managers.AttendantManager;
import observers.AbstractComponentObserver;

/**
 * This class represents an observer for a banknote storage unit. It extends
 * AbstractComponentObserver and implements the BanknoteStorageUnitObserver
 * interface.
 */
public class BanknoteStorageMonitor extends AbstractComponentObserver implements BanknoteStorageUnitObserver {

    // Reference to the AttendantManager
    AttendantManager am;

    /**
     * Constructor for BanknoteStorageMonitor.
     * 
     * @param am   The AttendantManager associated with the observer. It cannot be
     *             null.
     * @param d    The BanknoteStorageUnit being observed. It cannot be null.
     * @throws IllegalArgumentException if either the AttendantManager or the
     *                                  BanknoteStorageUnit is null.
     */
    public BanknoteStorageMonitor(AttendantManager am, BanknoteStorageUnit d) {
        super(d);

        // Checking for null AttendantManager
        if (am == null) {
            throw new IllegalArgumentException("The AttendantManager cannot be null.");
        }

        // Assigning references and attaching the observer to the device
        this.am = am;
        d.attach(this);
    }

    /**
     * This method is called when the banknote storage unit is full. It notifies the
     * associated AttendantManager about the unit state change.
     * 
     * @param unit The BanknoteStorageUnit that is full.
     */
    @Override
    public void banknotesFull(BanknoteStorageUnit unit) {
        // Notify the AttendantManager about the unit state change
        am.notifyBanknoteStorageUnitStateChange(unit);
    }

    /**
     * This method is called when a banknote is added to the storage unit. It
     * notifies the associated AttendantManager about the unit state change.
     * 
     * @param unit The BanknoteStorageUnit to which a banknote was added.
     */
    @Override
    public void banknoteAdded(BanknoteStorageUnit unit) {
        // Notify the AttendantManager about the unit state change
        am.notifyBanknoteStorageUnitStateChange(unit);
    }

    /**
     * This method is called when banknotes are loaded into the storage unit. It
     * notifies the associated AttendantManager about the unit state change.
     * 
     * @param unit The BanknoteStorageUnit into which banknotes were loaded.
     */
    @Override
    public void banknotesLoaded(BanknoteStorageUnit unit) {
        // Notify the AttendantManager about the unit state change
        am.notifyBanknoteStorageUnitStateChange(unit);
    }

    /**
     * This method is called when banknotes are unloaded from the storage unit. It
     * notifies the associated AttendantManager about the unit state change.
     * 
     * @param unit The BanknoteStorageUnit from which banknotes were unloaded.
     */
    @Override
    public void banknotesUnloaded(BanknoteStorageUnit unit) {
        // Notify the AttendantManager about the unit state change
        am.notifyBanknoteStorageUnitStateChange(unit);
    }

}
