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
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.CoinStorageUnitObserver;

import managers.AttendantManager;
import observers.AbstractComponentObserver;

public class CoinStorageMonitor extends AbstractComponentObserver implements CoinStorageUnitObserver {
    // object references
    AttendantManager am;

    public CoinStorageMonitor(AttendantManager am, CoinStorageUnit d) {
        super(d);

        if (am == null) {
            throw new IllegalArgumentException("The attendant manager cannot be null.");
        }

        this.am = am;
        d.attach(this);
    }

    @Override
    public void coinsFull(CoinStorageUnit unit) {
        am.notifyCoinStorageUnitStateChange(unit);
    }

    @Override
    public void coinAdded(CoinStorageUnit unit) {
        am.notifyCoinStorageUnitStateChange(unit);
    }

    @Override
    public void coinsLoaded(CoinStorageUnit unit) {
        am.notifyCoinStorageUnitStateChange(unit);
    }

    @Override
    public void coinsUnloaded(CoinStorageUnit unit) {
        am.notifyCoinStorageUnitStateChange(unit);
    }

}
