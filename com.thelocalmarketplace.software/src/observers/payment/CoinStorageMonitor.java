package observers.payment;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.CoinStorageUnitObserver;

import managers.AttendantManager;
import observers.AbstractComponentObserver;

public class CoinStorageMonitor extends AbstractComponentObserver implements CoinStorageUnitObserver {
    // object references
    AttendantManager am;

    public CoinStorageMonitor(AttendantManager am, IComponent<? extends IComponentObserver> d) {
        super(d);

        if (am == null) {
            throw new IllegalArgumentException("The attendant manager cannot be null.");
        }

        this.am = am;
    }

    @Override
    public void coinsFull(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coinsFull'");
    }

    @Override
    public void coinAdded(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coinAdded'");
    }

    @Override
    public void coinsLoaded(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coinsLoaded'");
    }

    @Override
    public void coinsUnloaded(CoinStorageUnit unit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coinsUnloaded'");
    }

}