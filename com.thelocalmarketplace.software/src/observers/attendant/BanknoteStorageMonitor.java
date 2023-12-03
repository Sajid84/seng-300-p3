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
