// Liam Major 30223023
// André Beaulieu, UCID 30174544
// Nezla Annaisha, UCID 30123223
// Kear Sang Heng, UCID 30087289
// Samuel Fasakin, UCID 30161903
// Joshua Liu, UCID 30174833

package managers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.*;

import managers.enums.ScanType;
import managers.enums.SessionStatus;
import managers.interfaces.IOrderManager;
import managers.interfaces.IOrderManagerNotify;
import observers.order.BarcodeScannerObserver;
import observers.order.ScaleObserver;
import utils.DatabaseHelper;

public class OrderManager implements IOrderManager, IOrderManagerNotify {

	// hardware references
	protected AbstractSelfCheckoutStation machine;

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
	protected Map<Item, Boolean> items = new HashMap<>();
	protected List<IOrderManagerNotify> listeners = new ArrayList<IOrderManagerNotify>();
	protected List<IElectronicScale> overloadedScales = new ArrayList<IElectronicScale>();
    protected boolean barcodeScanned = false;

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
	public void configure(AbstractSelfCheckoutStation machine) {
		// saving reference
		this.machine = machine;

		// passing references, because nothing actually notifies the observers of the
		// machine itself EVER
		baggingarea_so = new ScaleObserver(this, machine.baggingArea);
		main_bso = new BarcodeScannerObserver(this, machine.mainScanner);
		handheld_bso = new BarcodeScannerObserver(this, machine.handheldScanner);
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
		if (barcode == null)
			throw new IllegalArgumentException("invalid barcode was scanned");

		// notifying that the item was actually scanned and there was nothing wrong
        // happened
        barcodeScanned = true;
	}

	/**
	 * Returns the total mass of the order.
	 *
	 * @return the total mass in grams.
	 */
	public BigDecimal getExpectedMass() {
		BigDecimal total = BigDecimal.ZERO;

		for (Item i : this.items.keySet()) {
			// checking for null
			if (i == null) {
				throw new IllegalArgumentException("tried to calculate mass of a null Product");
			}

			// no bagging request for this item, don't add this to the expected weight
			if (!items.get(i)) {
				continue;
			}

			// the item class guarantees a non-null positive mass
			total = total.add(i.getMass().inGrams());
		}

		return total;
	}

	/**
	 * Gets the current weight adjustment.
	 *
	 * @return the weight adjustment
	 */
	protected BigDecimal getWeightAdjustment() {
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

		for (Item i : this.items.keySet()) {
			// checking for null
			if (i == null) {
				throw new IllegalArgumentException("tried to calculate mass of a null Product");
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
                // getting the corresponding product
                PLUCodedProduct prod = DatabaseHelper.get((PLUCodedItem) i);

                // getting the price
                BigDecimal price = new BigDecimal(prod.getPrice());

                // scaling mass up to kilograms
                BigDecimal scale = i.getMass().inGrams().divide(BigDecimal.valueOf(1_000), RoundingMode.DOWN);

                // multiplying mass by kilograms
                price = price.multiply(scale);

                // adding the price
                total = total.add(price);
                continue;
            }

			// Temporary exception, while item types other than Barcode are unsupported.
			throw new UnsupportedOperationException();
		}

		// returning the price
		return total;
	}

	@Override
	public void addItemToOrder(Item item, ScanType method) throws OperationNotSupportedException {
		// checking for null
		if (item == null) {
			throw new IllegalArgumentException("tried to scan a null item");
		}
		if (method == null) {
			throw new IllegalArgumentException("a null scanning type was passed");
		}

		// figuring out how to scan the item
		if (item instanceof BarcodedItem) {
			this.addItemToOrder((BarcodedItem) item, method);
		}

		if (item instanceof PLUCodedItem) {
			this.addItemToOrder((PLUCodedItem) item, method);
		}

		// check if customer wants to bag item (bulky item handler extension)
		if (bagItem) {
			this.machine.baggingArea.addAnItem(item);
		}

        // adding the item to the order
        items.put(item, bagItem);

		// reset bagging request tracker for the next item
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
			this.machine.mainScanner.scan(item);
			break;
		case HANDHELD:
			this.machine.handheldScanner.scan(item);
			break;
		}

	}

	/**
	 * Simulates adding an {@link PLUCodedItem} to the order.
	 *
	 * @param item   the item to add
	 * @param method the method of scanning
	 */
	protected void addItemToOrder(PLUCodedItem item, ScanType method) throws OperationNotSupportedException {
        // TODO implement java swing stuff here

		throw new OperationNotSupportedException("adding items by PLU code is not supported yet");
	}

	/**
	 * Removes the specified item from the order, purging it from the list. Informs
	 * all listeners of this event. If the item cannot be found, or is null, then
	 * customer is displayed an error message.
	 *
	 * @throws OperationNotSupportedException
	 */
	@Override
	public void removeItemFromOrder(Item item) throws OperationNotSupportedException {
		if (item == null) {
			throw new IllegalArgumentException("tried to remove a null item from the bagging area");
		}

		if (item instanceof BarcodedItem) {
			this.removeItemFromOrder((BarcodedItem) item);
		}

		else if (item instanceof PLUCodedItem) {
			this.removeItemFromOrder((PLUCodedItem) item);
		}

		// removing the item from the bagging area
		// this function needs to be here to work with the bags too heavy use case
		this.machine.baggingArea.removeAnItem(item);
	}

	/**
	 * This removes a {@link BarcodedItem} from the order and the bagging area.
	 *
	 * @param item the {@link BarcodedItem} to remove
	 */
	protected void removeItemFromOrder(BarcodedItem item) {
		// removing
        if (this.items.remove(item)) {
            for (IOrderManagerNotify listener : listeners) {
                listener.onItemRemovedFromOrder(item);
            }
        }
	}

	/**
	 * This removes a {@link PLUCodedItem} from the order and the bagging area.
	 *
	 * @param item the {@link PLUCodedItem} to remove
	 */
	protected void removeItemFromOrder(PLUCodedItem item) throws OperationNotSupportedException {
		throw new OperationNotSupportedException("removing PLU coded items is not supported yet");
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
		this.machine.baggingArea.addAnItem(bags);
	}

	@Override
	public Map<Item, Boolean> getItems() {
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
	 *
	 * @param item the item for which bagging is skipped
	 */
	@Override
	public void onDoNotBagRequest(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("a null item was requested not to be bagged");
		}

		// notifying the attendant
		notifyAttendant("do not bag request was received");

		// Set the flag for no bagging
		bagItem = false;
	}

	@Override
	public void notifyMassChanged(ElectronicScaleListener scale, BigDecimal mass) {
		// checking for null
		if (mass == null)
			throw new IllegalArgumentException("null mass was sent to OrderManager");

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
	public boolean wasBarcodeScanned() {
		return barcodeScanned;
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
