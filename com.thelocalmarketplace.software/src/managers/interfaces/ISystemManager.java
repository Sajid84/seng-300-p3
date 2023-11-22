// Liam Major 30223023

package managers.interfaces;

public interface ISystemManager extends IManager {
	/**
	 * This posts all stored card transactions.
	 * @return 
	 */
	boolean postTransactions();
}
