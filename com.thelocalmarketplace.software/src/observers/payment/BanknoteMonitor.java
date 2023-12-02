package observers.payment;

import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispenserObserver;
import com.tdc.banknote.IBanknoteDispenser;
import managers.AttendantManager;
import managers.PaymentManager;
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
    }

    @Override
    public void moneyFull(IBanknoteDispenser dispenser) {

    }

    @Override
    public void banknotesEmpty(IBanknoteDispenser dispenser) {

    }

    @Override
    public void banknoteAdded(IBanknoteDispenser dispenser, Banknote banknote) {
        banknote.getDenomination();
        dispenser.getCapacity();
        // if dispenser.getCapacity * 0.90 <= amount of banknotes
        // if 90% of the capacity is less then the amount of banknotes then notify attendent
    }

    @Override
    public void banknoteRemoved(IBanknoteDispenser dispenser, Banknote banknote) {
        // if dispenser.getCapacity * 0.10 >= amount of banknotes
        // if the amount of banknotes is less than 10% the capacity then notify the attendant
    }

    @Override
    public void banknotesLoaded(IBanknoteDispenser dispenser, Banknote... banknotes) {

    }

    @Override
    public void banknotesUnloaded(IBanknoteDispenser dispenser, Banknote... banknotes) {

    }
}
