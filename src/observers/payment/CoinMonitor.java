package observers.payment;

import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenserObserver;
import com.tdc.coin.ICoinDispenser;
import managers.AttendantManager;
import observers.AbstractComponentObserver;

/**
 * This class represents an observer for a Coin Dispenser. It listens to events
 * emitted by the Coin Dispenser, such as coinsFull, coinsEmpty, coinAdded,
 * coinRemoved, coinsLoaded, and coinsUnloaded.
 */
public class CoinMonitor extends AbstractComponentObserver implements CoinDispenserObserver {
    
    // Reference to the AttendantManager
    AttendantManager am;

    /**
     * Creates an observer to listen to events emitted by a Coin Dispenser.
     * 
     * @param am   The AttendantManager associated with the observer. It cannot be
     *             null.
     * @param disp The Coin Dispenser being observed. It cannot be null.
     * @throws IllegalArgumentException if either the AttendantManager or the Coin
     *                                  Dispenser device is null.
     */
    public CoinMonitor(AttendantManager am, ICoinDispenser disp) {
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
     * This method is called when the coin dispenser is full. It notifies the
     * associated AttendantManager about the dispenser state change.
     */
    @Override
    public void coinsFull(ICoinDispenser dispenser) {
        // Notify the AttendantManager about the full coin dispenser
        am.notifyCoinDispenserStateChange(dispenser);
    }

    /**
     * This method is called when the coin dispenser is empty. It notifies the
     * associated AttendantManager about the dispenser state change.
     */
    @Override
    public void coinsEmpty(ICoinDispenser dispenser) {
        // Notify the AttendantManager about the empty coin dispenser
        am.notifyCoinDispenserStateChange(dispenser);
    }

    /**
     * This method is called when a coin is added to the dispenser. It notifies the
     * associated AttendantManager about the dispenser state change.
     */
    @Override
    public void coinAdded(ICoinDispenser dispenser, Coin coin) {
        // Notify the AttendantManager about the added coin
        am.notifyCoinDispenserStateChange(dispenser);
    }

    /**
     * This method is called when a coin is removed from the dispenser. It notifies
     * the associated AttendantManager about the dispenser state change.
     */
    @Override
    public void coinRemoved(ICoinDispenser dispenser, Coin coin) {
        // Notify the AttendantManager about the removed coin
        am.notifyCoinDispenserStateChange(dispenser);
    }

    /**
     * This method is called when coins are loaded into the dispenser. It notifies
     * the associated AttendantManager about the dispenser state change.
     */
    @Override
    public void coinsLoaded(ICoinDispenser dispenser, Coin... coins) {
        // Notify the AttendantManager about the loaded coins
        am.notifyCoinDispenserStateChange(dispenser);
    }

    /**
     * This method is called when coins are unloaded from the dispenser. It notifies
     * the associated AttendantManager about the dispenser state change.
     */
    @Override
    public void coinsUnloaded(ICoinDispenser dispenser, Coin... coins) {
        // Notify the AttendantManager about the unloaded coins
        am.notifyCoinDispenserStateChange(dispenser);
    }

}
