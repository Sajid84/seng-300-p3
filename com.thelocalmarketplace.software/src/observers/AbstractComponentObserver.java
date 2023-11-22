package observers;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;

/**
 * This is to centralize functionality of the `canUse` function out of the
 * individual observer.
 */

public class AbstractComponentObserver implements IComponentObserver, IObserverUseable {

	private IComponent<? extends IComponentObserver> device;

	public AbstractComponentObserver(IComponent<? extends IComponentObserver> d) {
		if (d == null) {
			throw new IllegalArgumentException("observed device cannot be null.");
		}

		// copying hardware reference
		this.device = d;
	}

	@Override
	public boolean canUse() {
		return (!device.isDisabled()) && device.isActivated();
	}

	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		// these functions never actually get called anywhere in
		// AbstractSelfCheckoutStation
	}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
		// these functions never actually get called anywhere in
		// AbstractSelfCheckoutStation
	}

	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		// do nothing, because nothing can actually call this function
	}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
		// do nothing, because nothing can actually call this function
	}

}
