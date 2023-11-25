package managers.interfaces;

public interface IAttendantManager extends IManager {

	/**
	 * This signals for the attendant.
	 */
	void signalForAttendant();

	/**
	 * Maintain banknotes use case.
	 */
	void maintainBanknotes();

	/**
	 * Maintain paper use case.
	 */
	void maintainPaper();

	/**
	 * Maintain coins use case.
	 */
	void maintainCoins();

	/**
	 * Maintain ink use case.
	 */
	void maintainInk();

	/**
	 * Remotely disables a machine.
	 */
	void requestDisableMachine();

	/**
	 * Remotely enable a machine.
	 */
	void requestEnableMachine();

}