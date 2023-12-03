package observers;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;

/**
 * This class serves as a base or abstract implementation of the
 * IComponentObserver interface. It centralizes functionality related to the
 * `canUse` function, which is not defined in this class but is expected to be
 * implemented in classes that extend this abstract class.
 */
public class AbstractComponentObserver implements IComponentObserver, IObserverUseable {

    // Reference to the observed device
    private IComponent<? extends IComponentObserver> device;

    /**
     * Constructor for AbstractComponentObserver.
     * 
     * @param d The observed device. It cannot be null.
     * @throws IllegalArgumentException if the observed device is null.
     */
    public AbstractComponentObserver(IComponent<? extends IComponentObserver> d) {
        if (d == null) {
            throw new IllegalArgumentException("Observed device cannot be null.");
        }

        // Copying the hardware reference
        this.device = d;
    }

    /**
     * This method is called when the observed component is enabled, but it's not
     * expected to be called in AbstractSelfCheckoutStation.
     */
    @Override
    public void enabled(IComponent<? extends IComponentObserver> component) {
        // These functions never actually get called anywhere in
        // AbstractSelfCheckoutStation
    }

    /**
     * This method is called when the observed component is disabled, but it's not
     * expected to be called in AbstractSelfCheckoutStation.
     */
    @Override
    public void disabled(IComponent<? extends IComponentObserver> component) {
        // These functions never actually get called anywhere in
        // AbstractSelfCheckoutStation
    }

    /**
     * This method is called when the observed component is turned on, but it's not
     * expected to be called in AbstractSelfCheckoutStation.
     */
    @Override
    public void turnedOn(IComponent<? extends IComponentObserver> component) {
        // Do nothing because nothing can actually call this function
    }

    /**
     * This method is called when the observed component is turned off, but it's not
     * expected to be called in AbstractSelfCheckoutStation.
     */
    @Override
    public void turnedOff(IComponent<? extends IComponentObserver> component) {
        // Do nothing because nothing can actually call this function
    }
}
