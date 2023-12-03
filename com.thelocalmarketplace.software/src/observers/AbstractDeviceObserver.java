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

package observers;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;

/**
 * This is to centralize functionality of the `canUse` function out of the
 * individual observer.
 */

public abstract class AbstractDeviceObserver implements IDeviceListener, IObserverUseable {

	private IDevice<? extends IDeviceListener> device;

	public AbstractDeviceObserver(IDevice<? extends IDeviceListener> d) {
		if (d == null) {
			throw new IllegalArgumentException("observed device cannot be null.");
		}

		// copying hardware reference
		this.device = d;
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// this method is never actually called
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// this method is never actually called
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// this method IS called, but its useless on it's own
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// this method IS called, but its useless on it's own
	}

}
