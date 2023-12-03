// Liam Major			- 30223023
// Md Abu Sinan			- 30154627
// Ali Akbari			- 30171539
// Shaikh Sajid Mahmood	- 30182396
// Abdullah Ishtiaq		- 30153185
// Adefikayo Akande		- 30185937
// Alecxia Zaragoza		- 30150008
// Ana Laura Espinosa Garza - 30198679
// Anmol Bansal			- 30159559
// Emmanuel Trinidad	- 30172372
// Gurjit Samra			- 30172814
// Kelvin Jamila		- 30117164
// Kevlam Chundawat		- 30180662
// Logan Miszaniec		- 30156384
// Maleeha Siddiqui		- 30179762
// Michael Hoang		- 30123605
// Nezla Annaisha		- 30123223
// Nicholas MacKinnon	- 30172737
// Ohiomah Imohi		- 30187606
// Sheikh Falah Sheikh Hasan - 30175335
// Umer Rehman			- 30169819

package managers.interfaces;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import enums.SessionStatus;

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
