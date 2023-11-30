// Liam Major 30223023
// André Beaulieu, UCID 30174544
// Nezla Annaisha, UCID 30123223
// Kear Sang Heng, UCID 30087289
// Samuel Fasakin, UCID 30161903
// Joshua Liu, UCID 30174833

package managers;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import managers.enums.ScanType;
import managers.enums.SessionStatus;
import managers.interfaces.IOrderManager;
import managers.interfaces.IOrderManagerNotify;
import observers.order.BarcodeScannerObserver;
import observers.order.ScaleObserver;
import utils.DatabaseHelper;
import utils.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class OrderManager implements IOrderManager, IOrderManagerNotify {

    // hardware references
    protected ISelfCheckoutStation machine;

    // object references
    protected SystemManager sm;

    // object ownership
    protected BarcodeScannerObserver main_bso;
    protected BarcodeScannerObserver handheld_bso;
    protected ScaleObserver baggingarea_so;

    // vars
    protected BigDecimal leniency;
    protected BigDecimal adjustment = BigDecimal.ZERO;
    protected BigDecimal actualWeight = BigDecimal.ZERO;
    protected boolean bagItem = true;
    protected List<Pair<Item, Boolean>> items = new ArrayList<>();
    protected List<IOrderManagerNotify> listeners = new ArrayList<IOrderManagerNotify>();
    protected List<IElectronicScale> overloadedScales = new ArrayList<IElectronicScale>();
    protected Item last_item;

    /**
     * This controls everything relating to adding and removing items from a
     * customer's order.
     *
     * @param sm       a reference to the parent {@link SystemManager} object
     * @param leniency the leniency (tolerance) of the {@link ScaleObserver}s
     * @throws IllegalArgumentException when either argument is null
     */
    public OrderManager(SystemManager sm, BigDecimal leniency) {
        // checking arguments
        if (sm == null) {
            throw new IllegalArgumentException("the system manager cannot be null");
        }
        if (leniency == null) {
            throw new IllegalArgumentException("the leniency cannot be null");
        }

        // copying arguments
        this.sm = sm;
        this.leniency = leniency;
    }

    @Override
    public void configure(ISelfCheckoutStation machine) {
        // saving reference
        this.machine = machine;

        // passing references, because nothing actually notifies the observers of the
        // machine itself EVER
        baggingarea_so = new ScaleObserver(this, machine.getBaggingArea());
        main_bso = new BarcodeScannerObserver(this, machine.getMainScanner());
        handheld_bso = new BarcodeScannerObserver(this, machine.getHandheldScanner());
    }

    @Override
    public void notifyScaleOverload(IElectronicScale scale, boolean state) {
        if (state) {
            // the scale was overloaded
            overloadedScales.add(scale);
            blockSession();
            notifyAttendant("a scale was overloaded");
        } else {
            // the scale is out of overload
            overloadedScales.remove(scale);
            unblockSession();
            notifyAttendant("a scale is out of overload");
        }
    }

    @Override
    public void notifyBarcodeScanned(IBarcodeScanner scanner, Barcode barcode) {
        // checking for null
        if (barcode == null) throw new IllegalArgumentException("invalid barcode was scanned");

        // setting the last item to null to essentially clear it
        // this is useful to determine whether an item was added properly without error
        last_item = null;
    }

    /**
     * Returns the total mass of the order.
     *
     * @return the total mass in grams.
     */
    public BigDecimal getExpectedMass() {
        BigDecimal total = BigDecimal.ZERO;

        for (Pair<Item, Boolean> p : this.items) {
            // destructure
            Item i = p.getKey();

            // checking for null
            if (i == null) {
                throw new IllegalArgumentException("tried to calculate mass of a null item");
            }

            // no bagging request for this item, don't add this to the expected weight
            if (p.getValue()) {
                if (i instanceof BarcodedItem) {
                    /**
                     * I don't know why, but barcoded items have an expected mass whilst PLU items do
                     * not. This threw off my implementation and thus this code exists.
                     */
                    BarcodedProduct prod = DatabaseHelper.get((BarcodedItem) i);

                    // I love floating point numbers
                    BigInteger before = BigDecimal.valueOf(prod.getExpectedWeight()).toBigInteger();

                    // adding the weight to the total
                    total = total.add(new BigDecimal(before));
                } else {
                    // the item class guarantees a non-null positive mass
                    total = total.add(i.getMass().inGrams());
                }
            }
        }

        return total;
    }

    @Override
    public BigDecimal getWeightAdjustment() {
        return this.adjustment;
    }

    /**
     * Sets the weight adjustment.
     *
     * @param a the weight adjustment
     */
    protected void setWeightAdjustment(BigDecimal a) {
        this.adjustment = a;
    }

    @Override
    public BigDecimal getTotalPrice() throws IllegalArgumentException {
        BigDecimal total = BigDecimal.ZERO;

        for (Pair<Item, Boolean> p : this.items) {
            // destructure
            Item i = p.getKey();

            // checking for null
            if (i == null) {
                throw new IllegalArgumentException("tried to calculate mass of a null item");
            }

            // calculating the price for a barcodeditem
            if (i instanceof BarcodedItem) {
                // getting the corresponding product
                BarcodedProduct prod = DatabaseHelper.get((BarcodedItem) i);

                // barcodeditems are always sold per unit, therefore we can just add the price
                // directly
                total = total.add(new BigDecimal(prod.getPrice()));
                continue;
            }

            // calculating the price for a plu coded item
            if (i instanceof PLUCodedItem) {
                total = total.add(priceOf((PLUCodedItem) i));
                continue;
            }

            // Temporary exception, while item types other than Barcode are unsupported.
            throw new UnsupportedOperationException();
        }

        // returning the price
        return total;
    }

    @Override
    public void addItemToOrder(Item item, ScanType method) {
        // checking for null
        if (item == null) {
            throw new IllegalArgumentException("tried to scan a null item");
        }
        if (method == null) {
            throw new IllegalArgumentException("a null scanning type was passed");
        }

        // only adding the item to the order if we receive an event
        // from an observer
        last_item = item;

        // figuring out how to scan the item
        if (item instanceof BarcodedItem) {
            this.addItemToOrder((BarcodedItem) item, method);
        }

        // adding the item
        items.add(new Pair<>(item, bagItem));

        // check if customer wants to bag item (bulky item handler extension)
        if (bagItem) {
            this.machine.getBaggingArea().addAnItem(item);
        }

        // reset bagging request tracker for the next item
        bagItem = true;
    }
    
	@Override
	public void addSearchedItemToOrder(Item item) {
		// Check for null
	    if (item == null) {
	        throw new IllegalArgumentException("Cannot add a null item to the order.");
	    }

	    // Adding the searched item to the order
	    items.add(new Pair<>(item, bagItem));

	    // Check if the customer wants to bag the item (bulky item handler extension)
	    if (bagItem) {
	        this.machine.getBaggingArea().addAnItem(item);
	    }

	    // Reset bagging request tracker for the next item
	    bagItem = true;
	}

    /**
     * Simulates adding an {@link BarcodedItem} to the order.
     *
     * @param item   the item to add
     * @param method the method of scanning
     */
    protected void addItemToOrder(BarcodedItem item, ScanType method) {
        switch (method) {
            case MAIN:
                this.machine.getMainScanner().scan(item);
                break;
            case HANDHELD:
                this.machine.getHandheldScanner().scan(item);
                break;
        }
    }

    /**
     * Removes the specified item from the order, purging it from the list. Informs
     * all listeners of this event. If the item cannot be found, or is null, then
     * customer is displayed an error message.
     */
    @Override
    public void removeItemFromOrder(Pair<Item, Boolean> pair) {
        if (pair == null) {
            throw new IllegalArgumentException("tried to remove a null item from the bagging area");
        }

        /**
         * functionally, there is no difference between removing a barcoded item
         * and a PLU coded item. nor would I suspect a difference from removing
         * any other kind of item
         */

        // removing the item from the map
        if (this.items.remove(pair)) {
            for (IOrderManagerNotify listener : listeners) {
                listener.onItemRemovedFromOrder(pair.getKey());
            }
        }

        // removing if the item was bagged
        if (pair.getValue()) {
            // removing the item from the bagging area
            // this function needs to be here to work with the bags too heavy use case
            this.machine.getBaggingArea().removeAnItem(pair.getKey());
        }
    }

    /**
     * This method handles a customer's request to add their own bags. The system
     * gets the mass of the bags and updates the system adjustment and weight
     * accordingly.
     *
     * @param bags the bag that is going to be added
     */
    @Override
    public void addCustomerBags(Item bags) {
        // Get the weight of just the bags
        BigDecimal bagWeight = bags.getMass().inGrams();

        // Update adjustment for weight of bag
        this.adjustment = this.adjustment.add(bagWeight);

        // Placing the bags in the bagging area
        this.machine.getBaggingArea().addAnItem(bags);
    }

    @Override
    public List<Pair<Item, Boolean>> getItems() {
        return items;
    }

    @Override
    public void onItemRemovedFromOrder(Item item) {
        // Note: Do Not Use! OrderManager calls this for others!
    }

    @Override
    public SessionStatus getState() {
        return sm.getState();
    }

    @Override
    public void onAttendantOverride() {
        // updating the mass
        adjustment = this.getExpectedMass().subtract(actualWeight);

        // unblocking the session
        unblockSession();
    }

    @Override
    public void notifyAttendant(String reason) {
        sm.notifyAttendant(reason);
    }

    /**
     * This method handles a customer's request to skip bagging for a specific item.
     * It adjusts the expected weight and updates the weight adjustment, blocks the
     * session, and notifies the attendant.
     */
    @Override
    public void doNotBagRequest(boolean bagRequest) {
        if (bagRequest) {
            notifyAttendant("Bagging the next item");
            bagItem = true;
        } else {
            notifyAttendant("Not bagging the next item");
            bagItem = false;
        }
    }

    @Override
    public boolean getDoNotBagRequest() {
        return bagItem;
    }

    @Override
    public void notifyMassChanged(ElectronicScaleListener scale, BigDecimal mass) {
        // checking for null
        if (mass == null) throw new IllegalArgumentException("null mass was sent to OrderManager");

        // saving the mass
        this.actualWeight = mass;

        /**
         * calculating the magnitude of the difference, the expected weight should be
         * greater than the actual weight.
         */
        BigDecimal expected = getExpectedMass().subtract(getWeightAdjustment());
        BigDecimal actual = actualWeight;
        BigDecimal difference = expected.subtract(actual).abs();

        // checking the weight difference
        checkWeightDifference(difference);
    }

    /**
     * This separation is just to test resolving weight discrepancies.
     */
    protected void checkWeightDifference(BigDecimal difference) {
        // testing whether to block or unblock the session
        switch (getState()) {
            case NORMAL:
                // blocking the session due to a discrepancy
                if (difference.compareTo(leniency) > 0) {
                    blockSession();
                }
                break;
            case BLOCKED:
                // unblocking the session
                if (difference.compareTo(leniency) <= 0) {
                    unblockSession();
                }
                break;
            default:
                throw new IllegalStateException("cannot check weight difference not blocked nor normal");
        }
    }

    @Override
    public void blockSession() {
        sm.blockSession();
    }

    public void registerListener(IOrderManagerNotify listener) {
        this.listeners.add(listener);
    }

    @Override
    public void unblockSession() {
        sm.unblockSession();
    }

    @Override
    public boolean isScaleOverloaded() {
        return !overloadedScales.isEmpty();
    }

    @Override
    public BigDecimal priceOf(PLUCodedItem item) {
        // getting the corresponding product
        PLUCodedProduct prod = DatabaseHelper.get(item);

        // getting the price
        BigDecimal price = new BigDecimal(prod.getPrice());

        // scaling mass up to kilograms
        BigDecimal scale = item.getMass().inGrams().divide(BigDecimal.valueOf(1_000), RoundingMode.DOWN);

        // multiplying mass by kilograms
        return price.multiply(scale);
    }

    @Override
    public boolean errorAddingItem() {
        // since we know that the notifyBarcodeScanned() function sets this back to null
        // we can assume that the scanner malfunctioned if this variable is not null
        return last_item != null;
    }

    @Override
    public BigDecimal getActualWeight() {
        return actualWeight;
    }

    @Override
    public boolean isBlocked() {
        return sm.isBlocked();
    }

    @Override
    public boolean isUnblocked() {
        return sm.isUnblocked();
    }

    @Override
    public boolean isPaid() {
        return sm.isPaid();
    }

    @Override
    public boolean isDisabled() {
        return sm.isDisabled();
    }

}
