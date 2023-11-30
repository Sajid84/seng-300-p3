package managers;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import managers.enums.SessionStatus;
import managers.interfaces.IAttendantManager;
import managers.interfaces.IAttendantManagerNotify;
import managers.interfaces.IOrderManager;
import utils.Pair;

public class AttendantManager implements IAttendantManager, IAttendantManagerNotify {

	// hardware references
	protected ISelfCheckoutStation machine;

	// object references
	protected SystemManager sm;

	public AttendantManager(SystemManager sm) {
		// checking arguments
		if (sm == null) {
			throw new IllegalArgumentException("the system manager cannot be null");
		}

		// copying the system manager
		this.sm = sm;
	}

	@Override
	public void configure(ISelfCheckoutStation machine) {
		// saving reference
		this.machine = machine;

		// creating observers
	}

	/**
	 * Searches for products based on a provided description in both PLU-coded and Barcoded product databases.
	 *
	 * @param description The text used for keyword search to find products.
	 * @return A list of pairs, each containing a product (either PLU-coded or Barcoded) and a boolean value indicating whether the product is found.
	 */
	public List<Pair<Product, Boolean>> searchProductsByText(String description) {

	    List<Pair<Product, Boolean>> foundItems = new ArrayList<>();

	    String lowDescription = description.toLowerCase();

	    // Search in PLU-coded product database
	    for (Map.Entry<PriceLookUpCode, PLUCodedProduct> entry : ProductDatabases.PLU_PRODUCT_DATABASE.entrySet()) {
	        String pluLowDescription = entry.getValue().getDescription().toLowerCase();
	        if (pluLowDescription.startsWith(lowDescription)) {
	            foundItems.add(new Pair<>(entry.getValue(), false));
	        }
	    }

	    // Search in Barcoded product database
	    for (Map.Entry<Barcode, BarcodedProduct> entry : ProductDatabases.BARCODED_PRODUCT_DATABASE.entrySet()) {
	        String barcodeLowDescription = entry.getValue().getDescription().toLowerCase();
	        if (barcodeLowDescription.startsWith(lowDescription)) {
	            foundItems.add(new Pair<>(entry.getValue(), false));
	        }
	    }

	    return foundItems;
	}
	
    /**
     * Add a searched item to the order.
     *
     * @param searchedItem The item obtained through a search.
     */
    public void addSearchedItemToOrder(Item item) {
        // Assuming that the searched item is always valid when added through search
        if (item != null) {
            // Get the OrderManager instance from the SystemManager
        	// Unsure about this
            IOrderManager orderManager = sm.getOrderManager();

            // Call the method to add the searched item
            orderManager.addSearchedItemToOrder(item);
        }
    }
	
	@Override
	public SessionStatus getState() {
		return sm.getState();
	}

	@Override
	public void blockSession() {
		sm.blockSession();
	}

	@Override
	public void unblockSession() {
		sm.unblockSession();
	}

	@Override
	public void signalForAttendant() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyAttendant(String reason) {
		// TODO replace this with a GUI
		System.out.printf("[ATTENDANT NOTIFY]: %s\n", reason);
	}

	@Override
	public void notifyPaperLow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyInkLow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyCoinsFull() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyCoinsEmpty() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyBanknotesFull() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyBanknotesEmpty() {
		// TODO Auto-generated method stub

	}

	@Override
	public void maintainBanknotes() {
		// TODO Auto-generated method stub

	}

	@Override
	public void maintainPaper() {
		// TODO Auto-generated method stub

	}

	@Override
	public void maintainCoins() {
		// TODO Auto-generated method stub

	}

	@Override
	public void maintainInk() {
		// TODO Auto-generated method stub

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
	public void requestDisableMachine() {
		sm.requestDisableMachine();
	}

	@Override
	public void requestEnableMachine() {
		sm.requestEnableMachine();
	}

	@Override
	public boolean isDisabled() {
		return sm.isDisabled();
	}

}
