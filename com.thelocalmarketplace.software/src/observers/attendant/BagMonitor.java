package observers.attendant;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.bag.IReusableBagDispenser;
import com.jjjwelectronics.bag.ReusableBagDispenserListener;
import managers.AttendantManager;
import observers.AbstractDeviceObserver;

public class BagMonitor extends AbstractDeviceObserver implements ReusableBagDispenserListener {

    // manager reference
    private AttendantManager am;

    public BagMonitor(AttendantManager am, IReusableBagDispenser d) {
        super(d);

        // checking if null
        if (am == null) {
            throw new IllegalArgumentException("Attendant manager cannot be null.");
        }

        // copying reference & attaching
        this.am = am;
        d.register(this);
    }

    @Override
    public void aBagHasBeenDispensedByTheDispenser() {
        am.notifyBagDispensed();
    }

    @Override
    public void theDispenserIsOutOfBags() {
        am.notifyBagsEmpty();
    }

    @Override
    public void bagsHaveBeenLoadedIntoTheDispenser(int count) {
        am.notifyBagsLoaded(count);
    }
}
