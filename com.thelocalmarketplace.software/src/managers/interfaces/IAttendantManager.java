package managers.interfaces;

public interface IAttendantManager extends IManager {

    /**
     * This signals for the attendant.
     */
    void signalForAttendant();

    /**
     * Maintain banknotes use case. This function guarantees that the device will be at least 50% full.
     * This function will only execute if the device is either empty or less than 10% capacity.
     */
    void maintainBanknoteDispensers();

    /**
     * Maintain paper use case. This function guarantees that the device will be at least 50% full.
     * This function will only execute if the device is either empty or less than 10% capacity.
     */
    void maintainPaper();

    /**
     * Maintain coins use case. This function guarantees that the device will be at least 50% full.
     * This function will only execute if the device is either empty or less than 10% capacity.
     */
    void maintainCoinDispensers();

    /**
     * Maintain ink use case. This function guarantees that the device will be at least 50% full.
     * This function will only execute if the device is either empty or less than 10% capacity.
     */
    void maintainInk();

    /**
     * Maintain banknotes use case. This function guarantees that the storage unit will be empty after calling.
     * This function will only execute if the storage unit is full or over 90% capacity.
     */
    void maintainBanknoteStorage();

    /**
     * Maintain bags use case. This function guarantees that the device will be at least 50% full.
     * This function will only execute if the device is either empty or less than 10% capacity.
     */
    void maintainBags();

    /**
     * Maintain coins use case. This function guarantees that the storage unit will be empty after calling.
     * This function will only execute if the storage unit is full or over 90% capacity.
     */
    void maintainCoinStorage();

    /**
     * Remotely disables a machine.
     */
    void requestDisableMachine();

    /**
     * Remotely enable a machine.
     */
    void requestEnableMachine();

    /**
     * Tests whether the machine can print or not.
     *
     * @return true if the machine can print, false otherwise
     */
    boolean canPrint();

    /**
     * Signal to the system that the customer wishes to purchase bags.
     *
     * @param count the amount of bags the customer wants to purchase
     */
    void requestPurchaseBags(int count);

    /**
     * Returns if there are bags in the machine.
     *
     * @return true if there is at least one bag in the machine, false otherwise
     */
    boolean hasBags();

    /**
     * Returns if the bags in the machine are low.
     *
     * @return true if low, false otherwise
     */
    boolean isBagsLow();
}
