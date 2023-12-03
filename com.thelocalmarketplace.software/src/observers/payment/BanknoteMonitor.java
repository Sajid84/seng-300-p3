package observers.payment;

import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispenserObserver;
import com.tdc.banknote.IBanknoteDispenser;
import managers.AttendantManager;
import observers.AbstractComponentObserver;

public class BanknoteMonitor extends AbstractComponentObserver implements BanknoteDispenserObserver {
    // object references
    AttendantManager am;

    public BanknoteMonitor(AttendantManager am, IBanknoteDispenser disp) {
        super(disp);

        if (am == null) {
            throw new IllegalArgumentException("The attendant manager cannot be null.");
        }

        // saving the reference
        this.am = am;
        disp.attach(this);
    }

    @Override
    public void moneyFull(IBanknoteDispenser dispenser) {
        am.notifyBanknoteDispenserStateChange(dispenser);
    }

    @Override
    public void banknotesEmpty(IBanknoteDispenser dispenser) {
        am.notifyBanknoteDispenserStateChange(dispenser);
    }

    @Override
    public void banknoteAdded(IBanknoteDispenser dispenser, Banknote banknote) {
        am.notifyBanknoteDispenserStateChange(dispenser);
    }

    @Override
    public void banknoteRemoved(IBanknoteDispenser dispenser, Banknote banknote) {
        am.notifyBanknoteDispenserStateChange(dispenser);
    }

    @Override
    public void banknotesLoaded(IBanknoteDispenser dispenser, Banknote... banknotes) {
        am.notifyBanknoteDispenserStateChange(dispenser);
    }

    @Override
    public void banknotesUnloaded(IBanknoteDispenser dispenser, Banknote... banknotes) {
        am.notifyBanknoteDispenserStateChange(dispenser);
    }
}
