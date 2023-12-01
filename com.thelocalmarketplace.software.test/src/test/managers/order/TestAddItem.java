// Liam Major 30223023

package test.managers.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import managers.enums.ScanType;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedGrid;
import stubbing.StubbedItem;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPLUItem;
import stubbing.StubbedPLUProduct;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestAddItem {
	// machine
	private AbstractSelfCheckoutStation machine;

	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;
	
	@Before
	public void setup() {
		// configuring the hardware
		StubbedStation.configure();
	
		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());
		machine.turnOn();
	
		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;
	
		// configuring the machine
		sm.configure(machine);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddingNullItem() throws OperationNotSupportedException {
		om.addItemToOrder(null, ScanType.MAIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddingItemWithNullScanner() throws OperationNotSupportedException {
		om.addItemToOrder(new StubbedItem(1), null);
	}

	@Test
	public void addingInstanceOfBarcodedItemViaMainScanner() throws OperationNotSupportedException {
		BarcodedProduct prod = new StubbedBarcodedProduct();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(prod.getBarcode(), prod);

		BarcodedItem item = new BarcodedItem(prod.getBarcode(), new Mass(prod.getExpectedWeight()));
		om.addItemToOrder(item, ScanType.MAIN);

		assertEquals(om.getItems().size(), 1);
	}

	@Test
	public void addingInstanceOfBarcodedItemViaHandheldScanner() throws OperationNotSupportedException {
		BarcodedProduct prod = new StubbedBarcodedProduct();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(prod.getBarcode(), prod);

		BarcodedItem item = new BarcodedItem(prod.getBarcode(), new Mass(prod.getExpectedWeight()));
		om.addItemToOrder(item, ScanType.HANDHELD);

		assertEquals(om.getItems().size(), 1);
	}

	@Test(expected = OperationNotSupportedException.class)
	public void addingInstanceOfPLUViaMainScanner() throws OperationNotSupportedException {
		PLUCodedItem pluCodedItem = new StubbedPLUItem();
		om.addItemToOrder(pluCodedItem, ScanType.MAIN);
	}

	@Test(expected = OperationNotSupportedException.class)
	public void addingInstanceOfPLUViaHandheldScanner() throws OperationNotSupportedException {
		PLUCodedItem pluCodedItem = new StubbedPLUItem();
		om.addItemToOrder(pluCodedItem, ScanType.HANDHELD);
	}
	
	@Test
    public void addingExistingBarcodedItemByText() {
        BarcodedProduct prod = new StubbedBarcodedProduct();
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(prod.getBarcode(), prod);

        String description = prod.getDescription();
        ScanType scanType = ScanType.MAIN;

        om.addItemToOrder(description, scanType);

        assertEquals(om.getItems().size(), 1);
    }

    @Test
    public void addingExistingPLUItemByText() {
        PLUCodedProduct pluCodedProduct = new StubbedPLUProduct();
        ProductDatabases.PLU_PRODUCT_DATABASE.put(pluCodedProduct.getPLUCode(), pluCodedProduct);

        String description = pluCodedProduct.getDescription();
        ScanType scanType = ScanType.MAIN;

        om.addItemToOrder(description, scanType);

        assertEquals(om.getItems().size(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingNullDescriptionItem() {
        ScanType scanType = ScanType.MAIN;
        om.addItemToOrder(null, scanType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingNonexistentItem() {
        String nonExistentDescription = "Nonexistent Item";
        ScanType scanType = ScanType.MAIN;
        om.addItemToOrder(nonExistentDescription, scanType);
    }

    @Test
    public void searchItemsByTextReturnsBarcodedItem() {
        BarcodedProduct prod = new StubbedBarcodedProduct();
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(prod.getBarcode(), prod);

        String description = prod.getDescription();

        Item foundItem = om.searchItemsByText(description);

        assertNotNull(foundItem);
        assertTrue(foundItem instanceof BarcodedItem);
    }

    @Test
    public void searchItemsByTextReturnsPLUCodedItem() {
        PLUCodedProduct pluProd = new PLUCodedProduct(null, null, 0);
        ProductDatabases.PLU_PRODUCT_DATABASE.put(pluProd.getPLUCode(), pluProd);

        String description = pluProd.getDescription();

        Item foundItem = om.searchItemsByText(description);

        assertNotNull(foundItem);
        assertTrue(foundItem instanceof PLUCodedItem);
    }

    @Test
    public void searchItemsByTextReturnsNullForNonexistentItem() {
        String nonExistentDescription = "Nonexistent Item";

        Item foundItem = om.searchItemsByText(nonExistentDescription);

        assertNull(foundItem);
    }
}
