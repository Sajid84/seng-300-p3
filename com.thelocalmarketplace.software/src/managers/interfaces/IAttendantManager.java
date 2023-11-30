package managers.interfaces;

import java.util.List;

import com.jjjwelectronics.Item;
import com.thelocalmarketplace.hardware.Product;

import utils.Pair;

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
	
	/**
	 * This method is called when an item, obtained through a search,
	 * needs to be added to the order.
	 *
	 * @param searchedItem The item obtained through a search.
	 */
	void addSearchedItemToOrder(Item searchedItem);
	
	/**
     * Searches for products based on text.
     *
     * @param text The text to search for.
     * @return A list of pairs containing matching products and a boolean indicating availability.
     */
	List<Pair<Product, Boolean>> searchProductsByText(String text);

}
