package managers.interfaces;

public interface IAttendantManager extends IManager {

    /**
     * This signals for the attendant.
     */
    void signalForAttendant();

    /**
     * Maintain banknotes use case.
     */
    void maintainBanknoteDispensers();

    /**
     * Maintain paper use case.
     */
    void maintainPaper();

    /**
     * Maintain coins use case.
     */
    void maintainCoinDispensers();

    /**
     * Maintain ink use case.
     */
    void maintainInk();

    /**
     * Maintain banknotes use case.
     */
    void maintainBanknoteStorage();

    /**
     * Maintain bags use case.
     */
    void maintainBags();

    /**
     * Maintain coins use case.
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
        private int bagCount = count;
        for (int i = 0; i <= bagCount; i++) {
            dispense();
        }

}
