package managers.interfaces;

import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.ICoinDispenser;

import java.math.BigDecimal;

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
     * This notifies the manager that the observed coin dispenser has full coins.
     *
     * @param dispenser the dispenser that triggered this event
     */
    void notifyCoinsFull(ICoinDispenser dispenser);

    /**
     * This notifies the manager that the observed coin storage unit has full coins.
     *
     * @param unit the storage unit that triggered this event
     */
    void notifyBanknotesFull(CoinStorageUnit unit);

    /**
     * This notifies the manager that the observed coin dispenser has no coins.
     *
     * @param denom the denomination of coin dispenser that triggered this event
     */
    void notifyCoinsEmpty(BigDecimal denom);

    /**
     * This notifies the manager that the observed banknote dispenser has full banknotes.
     *
     * @param dispenser the dispenser that triggered this event
     */
    void notifyBanknotesFull(IBanknoteDispenser dispenser);

    /**
     * This notifies the manager that the observed banknote storage unit has full banknotes.
     *
     * @param unit the storage unit that triggered this event
     */
    void notifyBanknotesFull(BanknoteStorageUnit unit);

    /**
     * This notifies the manager that the observed banknote dispenser has no banknotes.
     *
     * @param denom the denomination of coin dispenser that triggered this event
     */
    void notifyBanknotesEmpty(BigDecimal denom);

    /**
     * This tells the manager that a coin was emitted and to check the state of the coin dispensers.
     *
     * @param denom the denomination of the coin
     */
    void notifyCoinEmitted(BigDecimal denom);

    /**
     * This tells the manager that a banknote was emitted and to check the state of the banknote dispensers.
     *
     * @param denom the denomination of the coin
     */
    void notifyBanknoteEmitted(BigDecimal denom);

}
