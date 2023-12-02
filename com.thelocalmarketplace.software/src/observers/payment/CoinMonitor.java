package observers.payment;

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
    }

    @Override
    public void coinsFull(ICoinDispenser dispenser) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coinsFull'");
    }

    @Override
    public void coinsEmpty(ICoinDispenser dispenser) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coinsEmpty'");
    }

    @Override
    public void coinAdded(ICoinDispenser dispenser, Coin coin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coinAdded'");
    }

    @Override
    public void coinRemoved(ICoinDispenser dispenser, Coin coin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coinRemoved'");
    }

    @Override
    public void coinsLoaded(ICoinDispenser dispenser, Coin... coins) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coinsLoaded'");
    }

    @Override
    public void coinsUnloaded(ICoinDispenser dispenser, Coin... coins) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coinsUnloaded'");
    }

}
