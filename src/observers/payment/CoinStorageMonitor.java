package observers.payment;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.CoinStorageUnitObserver;

import managers.AttendantManager;
import observers.AbstractComponentObserver;

/**
 * This class represents an observer for a Coin Storage Unit. It listens to
 * events emitted by the Coin Storage Unit, such as coinsFull, coinAdded,
 * coinsLoaded, and coinsUnloaded.
 */
public class CoinStorageMonitor extends AbstractComponentObserver implements CoinStorageUnitObserver {
    // Reference to the AttendantManager
    AttendantManager am;

    /**
     * Creates an observer to listen to events emitted by a Coin Storage Unit.
     * 
     * @param am   The AttendantManager associated with the observer. It cannot be
     *             null.
     * @param unit The Coin Storage Unit being observed. It cannot be null.
     * @throws IllegalArgumentException if either the AttendantManager or the Coin
     *                                  Storage Unit device is null.
     */
    public CoinStorageMonitor(AttendantManager am, CoinStorageUnit unit) {
        super(unit);

        // Checking for null AttendantManager
        if (am == null) {
            throw new IllegalArgumentException("The AttendantManager cannot be null.");
        }

        // Saving the reference and attaching the observer to the device
        this.am = am;
        unit.attach(this);
    }

    /**
     * This method is called when the coin storage unit is full. It notifies the
     * associated AttendantManager about the storage unit state change.
     */
    @Override
    public void coinsFull(CoinStorageUnit unit) {
        // Notify the AttendantManager about the full coin storage unit
        am.notifyCoinStorageUnitStateChange(unit);
    }

    /**
     * This method is called when a coin is added to the storage unit. It notifies
     * the associated AttendantManager about the storage unit state change.
     */
    @Override
    public void coinAdded(CoinStorageUnit unit) {
        // Notify the AttendantManager about the added coin to the storage unit
        am.notifyCoinStorageUnitStateChange(unit);
    }

    /**
     * This method is called when coins are loaded into the storage unit. It
     * notifies the associated AttendantManager about the storage unit state change.
     */
    @Override
    public void coinsLoaded(CoinStorageUnit unit) {
        // Notify the AttendantManager about the loaded coins into the storage unit
        am.notifyCoinStorageUnitStateChange(unit);
    }

    /**
     * This method is called when coins are unloaded from the storage unit. It
     * notifies the associated AttendantManager about the storage unit state change.
     */
    @Override
    public void coinsUnloaded(CoinStorageUnit unit) {
        // Notify the AttendantManager about the unloaded coins from the storage unit
        am.notifyCoinStorageUnitStateChange(unit);
    }

}
