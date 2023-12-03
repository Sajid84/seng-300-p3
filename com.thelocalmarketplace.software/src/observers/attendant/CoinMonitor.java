package observers.attendant;

import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenserObserver;
import com.tdc.coin.ICoinDispenser;
import managers.AttendantManager;
import observers.AbstractComponentObserver;

public class CoinMonitor extends AbstractComponentObserver implements CoinDispenserObserver {
    // object references
    AttendantManager am;

    public CoinMonitor(AttendantManager am, ICoinDispenser disp) {
        super(disp);

        if (am == null) {
            throw new IllegalArgumentException("The attendant manager cannot be null.");
        }

        // saving the reference
        this.am = am;
        disp.attach(this);
    }

    @Override
    public void coinsFull(ICoinDispenser dispenser) {
        am.notifyCoinDispenserStateChange(dispenser);
    }

    @Override
    public void coinsEmpty(ICoinDispenser dispenser) {
        am.notifyCoinDispenserStateChange(dispenser);
    }

    @Override
    public void coinAdded(ICoinDispenser dispenser, Coin coin) {
        am.notifyCoinDispenserStateChange(dispenser);
    }

    @Override
    public void coinRemoved(ICoinDispenser dispenser, Coin coin) {
        am.notifyCoinDispenserStateChange(dispenser);
    }

    @Override
    public void coinsLoaded(ICoinDispenser dispenser, Coin... coins) {
        am.notifyCoinDispenserStateChange(dispenser);
    }

    @Override
    public void coinsUnloaded(ICoinDispenser dispenser, Coin... coins) {
        am.notifyCoinDispenserStateChange(dispenser);
    }

}
