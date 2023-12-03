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


}
