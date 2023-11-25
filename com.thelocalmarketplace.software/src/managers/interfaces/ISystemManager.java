// Liam Major 30223023

package managers.interfaces;

public interface ISystemManager extends IManager {
	/**
	 * This posts all stored card transactions.
	 * 
	 * @return
	 */
	boolean postTransactions();

	/**
	 * This tells the machine to disable itself. If there is an active session, this
	 * request waits until after the session is done.
	 */
	void disableMachine();

	/**
	 * This tells the machine to enable itself.
	 */
	void enableMachine();
}
