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
	public boolean canUse() {
		if (!device.isPoweredUp()) {
			return false;
		}
		return !device.isDisabled();
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
