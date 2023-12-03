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
