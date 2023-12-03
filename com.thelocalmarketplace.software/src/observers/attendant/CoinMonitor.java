// Liam Major			- 30223023
// Md Abu Sinan			- 30154627
// Ali Akbari			- 30171539
// Shaikh Sajid Mahmood	- 30182396
// Abdullah Ishtiaq		- 30153185
// Adefikayo Akande		- 30185937
// Alecxia Zaragoza		- 30150008
// Ana Laura Espinosa Garza - 30198679
// Anmol Bansal			- 30159559
// Emmanuel Trinidad	- 30172372
// Gurjit Samra			- 30172814
// Kelvin Jamila		- 30117164
// Kevlam Chundawat		- 30180662
// Logan Miszaniec		- 30156384
// Maleeha Siddiqui		- 30179762
// Michael Hoang		- 30123605
// Nezla Annaisha		- 30123223
// Nicholas MacKinnon	- 30172737
// Ohiomah Imohi		- 30187606
// Sheikh Falah Sheikh Hasan - 30175335
// Umer Rehman			- 30169819

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
