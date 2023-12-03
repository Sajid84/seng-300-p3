// André Beaulieu, UCID 30174544
// Samuel Faskin, UCID 30161903
// Liam Major 30223023

// Package declaration for observers related to orders
package observers.order;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;

import managers.OrderManager;
import observers.AbstractDeviceObserver;

/**
 * This class represents an observer for an electronic scale device. It extends
 * AbstractDeviceObserver and implements the ElectronicScaleListener interface.
 */
public class ScaleObserver extends AbstractDeviceObserver implements ElectronicScaleListener {

    // Reference to the OrderManager
    private OrderManager om;

    /**
     * Constructor for ScaleObserver.
     * 
     * @param om     The OrderManager associated with the observer. It cannot be
     *               null.
     * @param device The IElectronicScale device being observed. It cannot be null.
     * @throws IllegalArgumentException if either the OrderManager or the
     *                                  IElectronicScale device is null.
     */
    public ScaleObserver(OrderManager om, IElectronicScale device) {
        super(device);

        // Checking for null OrderManager
        if (om == null) {
            throw new IllegalArgumentException("OrderManager cannot be null.");
        }

        // Assigning references and registering the observer with the device
        this.om = om;
        device.register(this);
    }

    /**
     * This method is called when the mass on the observed electronic scale has
     * changed. It notifies the OrderManager about the updated mass.
     * 
     * @param scale The IElectronicScale device whose mass has changed.
     * @param mass  The Mass object representing the new mass.
     */
    @Override
    public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
        // Notify the OrderManager about the changed mass
        this.om.notifyMassChanged(this, mass.inGrams());
    }

    /**
     * This method is called when the mass on the observed electronic scale has
     * exceeded its limit. It notifies the OrderManager about the scale overload.
     * 
     * @param scale The IElectronicScale device that exceeded its mass limit.
     */
    @Override
    public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
        // Notify the OrderManager about the scale overload
        this.om.notifyScaleOverload(scale, true);
    }

    /**
     * This method is called when the mass on the observed electronic scale no
     * longer exceeds its limit. It notifies the OrderManager that the scale is no
     * longer overloaded.
     * 
     * @param scale The IElectronicScale device that is no longer overloaded.
     */
    @Override
    public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
        // Notify the OrderManager that the scale is no longer overloaded
        this.om.notifyScaleOverload(scale, false);
    }
}
