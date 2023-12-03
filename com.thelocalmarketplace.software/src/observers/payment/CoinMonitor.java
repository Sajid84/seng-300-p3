package observers.payment;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenserObserver;
import com.tdc.coin.ICoinDispenser;
import managers.PaymentManager;
import observers.AbstractComponentObserver;

public class CoinMonitor extends AbstractComponentObserver implements CoinDispenserObserver {
    // object references
    PaymentManager pm;

    public CoinMonitor(PaymentManager pm, ICoinDispenser disp) {
        super(disp);

        if (pm == null) {
            throw new IllegalArgumentException("The PaymentManager cannot be null.");
        }

        // saving the reference
        this.pm = pm;
    }

    @Override
    public void enabled(IComponent<? extends IComponentObserver> component) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enabled'");
    }

    @Override
    public void disabled(IComponent<? extends IComponentObserver> component) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disabled'");
    }

    @Override
    public void turnedOn(IComponent<? extends IComponentObserver> component) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'turnedOn'");
    }

    @Override
    public void turnedOff(IComponent<? extends IComponentObserver> component) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'turnedOff'");
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