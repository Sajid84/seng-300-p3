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

}
