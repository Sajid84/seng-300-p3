package managers.interfaces;

import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.coin.CoinStorageUnit;

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
     * @param denom the dispenser that triggered this event
     */
    void notifyCoinsFull(BigDecimal denom);

    /**
     * This notifies the manager that the observed coin storage unit has full coins.
     *
     * @param unit the storage unit that triggered this event
     */
    void notifyCoinsFull(CoinStorageUnit unit);

    /**
     * This notifies the manager that the observed coin dispenser has no coins.
     *
     * @param denom the denomination of coin dispenser that triggered this event
     */
    void notifyCoinsEmpty(BigDecimal denom);

    /**
     * This notifies the manager that the observed banknote dispenser has full banknotes.
     *
     * @param denom the denomination of dispenser that triggered this event
     */
    void notifyBanknotesFull(BigDecimal denom);

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

    /**
     * This tells the manager that a coin was added and to check the state of the coin dispensers.
     *
     * @param denom the denomination of the coin
     */
    void notifyCoinAdded(BigDecimal denom);

    /**
     * This tells the manager that a banknote was added and to check the state of the banknote dispensers.
     *
     * @param denom the denomination of the coin
     */
    void notifyBanknoteAdded(BigDecimal denom);

    /**
     * This tells the manager that a coin was emitted and to check the state of the coin storage unit.
     *
     * @param unit the storage unit that called this event
     */
    void notifyCoinAdded(CoinStorageUnit unit);

    /**
     * This tells the manager that a banknote was emitted and to check the state of the banknote storage unit.
     *
     * @param unit the storage unit that called this event
     */
    void notifyBanknoteAdded(BanknoteStorageUnit unit);

}
