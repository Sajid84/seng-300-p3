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

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.BanknoteStorageUnitObserver;

import managers.AttendantManager;
import observers.AbstractComponentObserver;

public class BanknoteStorageMonitor extends AbstractComponentObserver implements BanknoteStorageUnitObserver {
    // object references
    AttendantManager am;

    public BanknoteStorageMonitor(AttendantManager am, BanknoteStorageUnit d) {
        super(d);

        // TODO Auto-generated constructor stub
        if (am == null) {
            throw new IllegalArgumentException("The attendant manager cannot be null.");
        }

        this.am = am;
        d.attach(this);
    }

    @Override
    public void banknotesFull(BanknoteStorageUnit unit) {
        am.notifyBanknoteStorageUnitStateChange(unit);
    }

    @Override
    public void banknoteAdded(BanknoteStorageUnit unit) {
        am.notifyBanknoteStorageUnitStateChange(unit);
    }

    @Override
    public void banknotesLoaded(BanknoteStorageUnit unit) {
        am.notifyBanknoteStorageUnitStateChange(unit);
    }

    @Override
    public void banknotesUnloaded(BanknoteStorageUnit unit) {
        am.notifyBanknoteStorageUnitStateChange(unit);
    }

}
