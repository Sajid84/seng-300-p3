// Liam Major 30223023

package managers.interfaces;

public interface IManagerNotify {
	/**
	 * This method notifies the attendant about a specific event or request, such as
	 * a "do not bag" request.
	 */
	void notifyAttendant(String reason);
}
