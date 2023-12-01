package observers.payment;

import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispenserObserver;
import com.tdc.banknote.IBanknoteDispenser;
import managers.PaymentManager;
import observers.AbstractComponentObserver;

public class BanknoteMonitor extends AbstractComponentObserver implements BanknoteDispenserObserver {
    // object references
    PaymentManager pm;

    public BanknoteMonitor(PaymentManager pm, IBanknoteDispenser disp) {
        super(disp);

        if (pm == null) {
            throw new IllegalArgumentException("The PaymentManager cannot be null.");
        }

        // saving the reference
        this.pm = pm;
    }

    @Override
    public void moneyFull(IBanknoteDispenser dispenser) {

    }

    @Override
    public void banknotesEmpty(IBanknoteDispenser dispenser) {

    }

    @Override
    public void banknoteAdded(IBanknoteDispenser dispenser, Banknote banknote) {

    }

    @Override
    public void banknoteRemoved(IBanknoteDispenser dispenser, Banknote banknote) {

    }

    @Override
    public void banknotesLoaded(IBanknoteDispenser dispenser, Banknote... banknotes) {

    }

    @Override
    public void banknotesUnloaded(IBanknoteDispenser dispenser, Banknote... banknotes) {

    }
}
