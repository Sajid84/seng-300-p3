// Liam Major			- 30223023
// Md Abu Sinan			- 30154627
// Ali Akbari			- 30171539
// Shaikh Sajid Mahmood	- 30182396
// Abdullah Ishtiaq		- 30153185
// Adefikayo Akande		- 30185937
// Alecxia Zaragoza		- 30150008
// Ana Laura Espinosa Garza - 30198679
// Anmol Bansal			- 30159559
// Emmanuel Trinidad	- 30172372
// Gurjit Samra			- 30172814
// Kelvin Jamila		- 30117164
// Kevlam Chundawat		- 30180662
// Logan Miszaniec		- 30156384
// Maleeha Siddiqui		- 30179762
// Michael Hoang		- 30123605
// Nezla Annaisha		- 30123223
// Nicholas MacKinnon	- 30172737
// Ohiomah Imohi		- 30187606
// Sheikh Falah Sheikh Hasan - 30175335
// Umer Rehman			- 30169819

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