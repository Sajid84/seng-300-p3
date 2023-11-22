// Liam Major 30223023

package observers;

public interface IObserverUseable {

	/**
	 * This method returns true if:
	 * <ul>
	 * <li>the observer received the enabled event, and</li>
	 * <li>the observer received the turned on event</li>
	 * </ul>
	 * 
	 * @return false if the conditions are not met
	 */
	boolean canUse();
}
