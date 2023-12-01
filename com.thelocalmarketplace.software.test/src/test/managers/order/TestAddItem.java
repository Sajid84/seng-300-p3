// Liam Major 30223023

package test.managers.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import javax.naming.OperationNotSupportedException;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
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

import database.Database;
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
	private ISelfCheckoutStation machine;

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
    public void searchItemsByText_ValidDescription_ReturnsBarcodedItem() {
		
        BarcodedProduct prod = new StubbedBarcodedProduct();
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(prod.getBarcode(), prod);

        BarcodedItem expectedItem = new BarcodedItem(prod.getBarcode(), new Mass(prod.getExpectedWeight()));

        Item result = om.searchItemsByText(prod.getDescription());

        assertEquals(expectedItem, result);
    }

    @Test
    public void searchItemsByText_InvalidDescription_ReturnsNull() {
    	
        BarcodedProduct prod = new StubbedBarcodedProduct();
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(prod.getBarcode(), prod);

        Item result = om.searchItemsByText("Invalid Description");

        assertNull(result);
    }

    @Test
    public void searchItemsByText_PLUProduct_ReturnsPLUCodedItem() {
    	
        PLUCodedProduct pluProduct = new StubbedPLUProduct();
        Database.PLU_PRODUCT_DATABASE.put(pluProduct.getPLUCode(), pluProduct);

        PLUCodedItem expectedItem = new StubbedPLUItem();
        Database.PLU_ITEM_DATABASE.put(pluProduct.getPLUCode(), expectedItem);

        Item result = om.searchItemsByText(pluProduct.getDescription());

        assertEquals(expectedItem, result);
    }

    @Test
    public void searchItemsByText_NullDescription_ReturnsNull() {
    	
        Item result = om.searchItemsByText(null);

        assertNull(result);
    }
}
