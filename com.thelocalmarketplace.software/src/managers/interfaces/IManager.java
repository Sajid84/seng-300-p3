// Liam Major 30223023
// Nezla Annaisha 30123223

package managers.interfaces;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.SessionStatus;
import observers.IObserverUseable;

public interface IManager extends IManagerNotify {
	/**
	 * This function uses any implementors {@link AbstractSelfCheckoutStation} and
	 * uses their references to attach observers.
	 * 
	 * @param machine the desired machine to observe
	 */
	void configure(AbstractSelfCheckoutStation machine);

	/**
	 * Gets the state of the manager.
	 * 
	 * @return the state
	 */
	SessionStatus getState();

	/**
	 * This method simply blocks the session, should only be used internally.
	 */
	void blockSession();

	/**
	 * This method unblocks the session.
	 */
	void unblockSession();

	/**
	 * This function tests whether or not the system can be used based on the states
	 * of the components of the machine.
	 * 
	 * @see IObserverUseable#canUse()
	 * @return returns true if all the observers can be used, false otherwise
	 */
	boolean ready();
}
