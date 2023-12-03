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

package managers.interfaces;

import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.ICoinDispenser;

public interface IAttendantManagerNotify {

    /**
     * This notifies the manager that the paper in the observed printer is low.
     */
    void notifyPaperLow();

    /**
     * This notifies the manager that the ink in the observed printer is low.
     */
    void notifyInkLow();

    /**
     * Notifies the manager that the coin dispenser had a change in its state.
     *
     * @param dispenser the denomination of the dispenser
     */
    void notifyCoinDispenserStateChange(ICoinDispenser dispenser);

    /**
     * Notifies the manager that the banknote dispenser had a change in its state.
     *
     * @param dispenser the denomination of the dispenser
     */
    void notifyBanknoteDispenserStateChange(IBanknoteDispenser dispenser);

    /**
     * Notifies the manager that the coin dispenser had a change in its state.
     *
     * @param unit the storage unit that emitted this event
     */
    void notifyCoinStorageUnitStateChange(CoinStorageUnit unit);

    /**
     * Notifies the manager that the coin dispenser had a change in its state.
     *
     * @param unit the storage unit that emitted this event
     */
    void notifyBanknoteStorageUnitStateChange(BanknoteStorageUnit unit);

    /**
     * Notifies the system about the paper status.
     *
     * @param hasPaper A boolean indicating whether there is paper available (true) or not (false).
     */
    void notifyPaper(boolean hasPaper);

    /**
     * Notifies the system about the ink status.
     *
     * @param hasInk A boolean indicating whether there is ink available (true) or not (false).
     */
    void notifyInk(boolean hasInk);

    /**
     * Notifies the manager that a bag was dispensed.
     */
    void notifyBagDispensed();

    /**
     * Notifies that bags have been loaded into the machine.
     * @param count the number of bags loaded
     */
    void notifyBagsLoaded(int count);

    /**
     * Notifies that there are no more bags left in the machine.
     */
    void notifyBagsEmpty();
}
