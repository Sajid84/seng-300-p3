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

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import enums.ScanType;
import utils.Pair;

import java.math.BigDecimal;
import java.util.List;

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
     *
     * @param bagRequest if true, the internal item flag will be set to false. Likewise for false
     */
    void doNotBagRequest(boolean bagRequest);

    /**
     * This gets the do not bag request.
     *
     * @return true if the item is to be bagged, false otherwise.
     */
    boolean getDoNotBagRequest();

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
     * Searches for products based on a provided description in both PLU-coded and Barcoded product databases.
     *
     * @param description The text used for keyword search to find products.
     * @return A list of pairs, each containing a product (either PLU-coded or Barcoded) and a boolean value indicating whether the product is found.
     */
    Item searchItemsByText(String description);
    
    /**
     * This removes an {@link Item} from the order and the bagging area.
     *
     * @param pair the {@link Item} to remove
     */
    void removeItemFromOrder(Pair<Item, Boolean> pair);

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
    List<Pair<Item, Boolean>> getItems();

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

    /**
     * This returns the last received actual weight that the manager received.
     *
     * @return the actual weight
     */
    BigDecimal getActualWeight();

    /**
     * Gets the current weight adjustment.
     *
     * @return the weight adjustment
     */
    BigDecimal getWeightAdjustment();
}
