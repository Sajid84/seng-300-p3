package observers.payment;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.BanknoteStorageUnitObserver;

import managers.AttendantManager;
import observers.AbstractComponentObserver;

public class BanknoteStorageMonitor extends AbstractComponentObserver implements BanknoteStorageUnitObserver {
    // object references
    AttendantManager am;

    public BanknoteStorageMonitor(AttendantManager am, IComponent<? extends IComponentObserver> d) {
        super(d);
        // TODO Auto-generated constructor stub
        if (am == null) {
            throw new IllegalArgumentException("The attendant manager cannot be null.");
        }

        this.am = am;
    }

    @Override
    public void banknotesFull(BanknoteStorageUnit unit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'banknotesFull'");
    }

    @Override
    public void banknoteAdded(BanknoteStorageUnit unit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'banknoteAdded'");
    }

    @Override
    public void banknotesLoaded(BanknoteStorageUnit unit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'banknotesLoaded'");
    }

    @Override
    public void banknotesUnloaded(BanknoteStorageUnit unit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'banknotesUnloaded'");
    }

}
