// Liam Major 30223023
// Nezla Annaisha 30123223

package managers.interfaces;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import managers.enums.SessionStatus;

public interface IManager extends IManagerNotify {
	/**
	 * This function uses any implementors {@link AbstractSelfCheckoutStation} and
	 * uses their references to attach observers.
	 * 
	 * @param machine the desired machine to observe
	 */
	void configure(ISelfCheckoutStation machine);

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
	 * This method returns true if the session is blocked, false otherwise.
	 * 
	 * @return true if the session is blocked, false otherwise.
	 */
	boolean isBlocked();

	/**
	 * This method returns true if the session is not blocked, false otherwise.
	 * 
	 * @return true if the session is not blocked, false otherwise.
	 */
	boolean isUnblocked();

	/**
	 * This method returns true if the session is paid, false otherwise.
	 * 
	 * @return true if the session is paid, false otherwise.
	 */
	boolean isPaid();

	/**
	 * This method returns true if the machine is disabled, false otherwise.
	 * 
	 * @return true if the machine is disabled, false otherwise.
	 */
	boolean isDisabled();

	/**
	 * This resets the managers to their beginning state.
	 */
	void reset();
}
