// Liam Major 30223023
// Nezla Annaisha 30123223
// André Beaulieu - 30174544

package managers.interfaces;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import managers.enums.ScanType;

import java.math.BigDecimal;
import java.util.Map;

/**
 * A unified interface of what the order manager should and should not do.
 */
public interface IOrderManager extends IManager {

    /**
     * This returns the total price of the items in the customer's order.
     *
     * @return the total price
     */
    BigDecimal getTotalPrice();

    /**
     * Allows the attendant to override the session and unblock it. This also
     * updates the weight adjustment.
     */
    void onAttendantOverride();

    /**
     * Allows the customer to request the item not be bagged, unblocking the
     * session. This also updates the weight adjustment.
     */
    void doNotBagRequest(boolean bagRequest);

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
    void addItemToOrder(Item item, ScanType method);

    /**
     * This removes an {@link Item} from the order and the bagging area.
     *
     * @param item the {@link Item} to remove
     */
    void removeItemFromOrder(Item item);

    /**
     * Returns the expected mass of the items the customer has scanned.
     *
     * @return the sum of masses of the {@link BarcodedItem}s
     */
    BigDecimal getExpectedMass();

    /**
     * This returns the list of items added to the cart.
     *
     * @return a map of {@link Item} and whether they are to be bagged or not
     */
    Map<Item, Boolean> getItems();

    /**
     * This function returns an {@link IElectronicScale} object if any of the scales
     * are overloaded.
     *
     * @return an {@link IElectronicScale} if a scale is overloaded, {@code null}
     * otherwise
     */
    boolean isScaleOverloaded();

    /**
     * This calculates the price of a PLU coded item.
     *
     * @param item the PLU coded item
     * @return the price by kilogram
     */
    BigDecimal priceOf(PLUCodedItem item);

    /**
     * This method returns true if there was no issue adding an item to the internal list of items.
     *
     * @return true if the item wasn't added properly, false otherwise
     */
    boolean errorAddingItem();
}
