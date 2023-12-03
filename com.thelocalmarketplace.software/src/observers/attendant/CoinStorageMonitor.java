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
