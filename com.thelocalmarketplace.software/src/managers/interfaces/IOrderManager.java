// Liam Major 30223023
// Nezla Annaisha 30123223
// André Beaulieu - 30174544

package managers.interfaces;

import java.math.BigDecimal;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import managers.enums.ScanType;

/**
 * A unified interface of what the order manager should and should not do.
 */
public interface IOrderManager extends IManager {

	/**
	 * This returns the total price of the items in the customer's order.
	 * 
	 * @return the total price
	 * @throws NullPointerSimulationException if the {@link BarcodedItem} could not
	 *                                        be found in the product database
	 */
	BigDecimal getTotalPrice() throws NullPointerSimulationException;

	/**
	 * Allows the attendant to override the session and unblock it. This also
	 * updates the weight adjustment.
	 */
	void onAttendantOverride();

	/**
	 * Allows the customer to request the item not be bagged, unblocking the
	 * session. This also updates the weight adjustment.
	 */
	void onDoNotBagRequest(Item item);

	/**
	 * Allows customer to add their own bags. The scale and weight is updated
	 * according to to new weight and adjustment.
	 * 
	 * @param bags, the bags customer wants to add
	 */
	void addCustomerBags(Item bags);

	/**
	 * Simulates adding an {@link Item} to the order.
	 * 
	 * @param item   the item to add
	 * @param method the method of scanning
	 */
	void addItemToOrder(Item item, ScanType method) throws OperationNotSupportedException;

	/**
	 * This removes an {@link Item} from the order and the bagging area.
	 * 
	 * @param item the {@link Item} to remove
	 */
	void removeItemFromOrder(Item item) throws OperationNotSupportedException;

	/**
	 * Returns the expected mass of the items the customer has scanned.
	 * 
	 * @return the sum of masses of the {@link BarcodedItem}s
	 */
	BigDecimal getExpectedMass();

	/**
	 * This returns the list of equivalent products retrieved from the product
	 * database.
	 * 
	 * @return a list of {@link BarcodedProduct}
	 * @throws NullPointerSimulationException if the {@link Barcode} could not be
	 *                                        found in the product database
	 */
	List<Product> getProducts() throws NullPointerSimulationException;

	/**
	 * This function returns an {@link IElectronicScale} object if any of the scales
	 * are overloaded.
	 * 
	 * @return an {@link IElectronicScale} if a scale is overloaded, {@code null}
	 *         otherwise
	 */
	boolean isScaleOverloaded();
}
