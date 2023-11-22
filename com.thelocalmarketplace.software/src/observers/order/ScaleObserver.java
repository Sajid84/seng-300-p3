// André Beaulieu, UCID 30174544
// Samuel Faskin, UCID 30161903
// Liam Major 30223023
package observers.order;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;

import managers.OrderManager;
import observers.AbstractDeviceObserver;

public class ScaleObserver extends AbstractDeviceObserver implements ElectronicScaleListener {
	// object references
	private OrderManager om;

	// Take keep the supplied scale as a pointer
	// and register yourself with that scale.
	public ScaleObserver(OrderManager om, IElectronicScale device) {
		super(device);

		// checking for null
		if (om == null) {
			throw new IllegalArgumentException("OrderManager cannot be null.");
		}

		this.om = om;
		device.register(this);
	}

	// If outside the bounds of expectation, shut the system down.
	// Once we're back within these bounds, re-enable the system
	// This only works if the WeightChecker is enabled.
	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		this.om.notifyMassChanged(this, mass.inGrams());
	}

	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
		this.om.notifyScaleOverload(scale, true);
	}

	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
		this.om.notifyScaleOverload(scale, false);
	}
}