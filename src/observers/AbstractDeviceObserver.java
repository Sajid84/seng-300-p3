package observers;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;

/**
 * This abstract class serves as a base implementation of the IDeviceListener
 * interface. It centralizes functionality related to the `canUse` function,
 * which is expected to be implemented in classes that extend this abstract
 * class.
 */
public abstract class AbstractDeviceObserver implements IDeviceListener, IObserverUseable {

    // Reference to the observed device
    private IDevice<? extends IDeviceListener> device;

    /**
     * Constructor for AbstractDeviceObserver.
     * 
     * @param d The observed device. It cannot be null.
     * @throws IllegalArgumentException if the observed device is null.
     */
    public AbstractDeviceObserver(IDevice<? extends IDeviceListener> d) {
        if (d == null) {
            throw new IllegalArgumentException("Observed device cannot be null.");
        }

        // Copying the hardware reference
        this.device = d;
    }

    /**
     * This method is called when a device has been enabled, but it's not expected
     * to be called in AbstractDeviceObserver.
     */
    @Override
    public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
        // This method is never actually called
    }

    /**
     * This method is called when a device has been disabled, but it's not expected
     * to be called in AbstractDeviceObserver.
     */
    @Override
    public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
        // This method is never actually called
    }

    /**
     * This method is called when a device has been turned on, but it's not expected
     * to be used on its own in AbstractDeviceObserver.
     */
    @Override
    public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
        // This method is called, but it's useless on its own
    }

    /**
     * This method is called when a device has been turned off, but it's not
     * expected to be used on its own in AbstractDeviceObserver.
     */
    @Override
    public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
        // This method is called, but it's useless on its own
    }
}
