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

package test.managers.order;

import java.math.BigDecimal;

import javax.naming.OperationNotSupportedException;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import database.Database;
import enums.ScanType;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedGrid;
import stubbing.StubbedItem;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPLUItem;
import stubbing.StubbedPLUProduct;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;
import utils.DatabaseHelper;

import static org.junit.Assert.*;

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

	// Adds null item. Expect to throw exception 
	@Test(expected = IllegalArgumentException.class)
	public void testAddingNullItem() throws OperationNotSupportedException {
		om.addItemToOrder(null, ScanType.MAIN);
	}

	//Adds item with a null scanner. Expect to throw exception.
	@Test(expected = IllegalArgumentException.class)
	public void testAddingItemWithNullScanner() throws OperationNotSupportedException {
		om.addItemToOrder(new StubbedItem(1), null);
	}

	//Add a barcoded product. Get barcode and expected weight for the item. Add item to the order with ScanType.MAIN. Expect to Items size equal 1.
	@Test
	public void addingInstanceOfBarcodedItemViaMainScanner() throws OperationNotSupportedException {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		// adding the item to the order
		om.addItemToOrder(item, ScanType.MAIN);

		// asserting
		assertEquals(om.getItems().size(), 1);
	}

	//Add a barcoded product. Get barcode and expected weight for the item. Add item to the order with ScanType.HANDHELD. Expect to Items size equal 1.
	@Test
	public void addingInstanceOfBarcodedItemViaHandheldScanner() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		// adding the item to the order
		om.addItemToOrder(item, ScanType.HANDHELD);

		// asserting
		assertEquals(om.getItems().size(), 1);
	}

	@Test
	public void addingInstanceOfPLUViaMainScanner() {
		// creating the item
		PLUCodedItem item = DatabaseHelper.createRandomPLUCodedItem();

		// adding the item to the order
		om.addItemToOrder(item, ScanType.MAIN);

		// asserting
		assertEquals(om.getItems().size(), 1);
	}

	@Test
	public void addingInstanceOfPLUViaHandheldScanner() {
		// creating the item
		PLUCodedItem item = DatabaseHelper.createRandomPLUCodedItem();

		// adding the item to the order
		om.addItemToOrder(item, ScanType.MAIN);

		// asserting
		assertEquals(om.getItems().size(), 1);
	}
	
	@Test
    public void searchItemsByText_ValidDescription_ReturnsBarcodedItem() {
		// setting up
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();
		BarcodedProduct prod = DatabaseHelper.get(item);

		// getting the item
		Item result = om.searchItemsByText(prod.getDescription());

		// asserting
		assertTrue(item instanceof BarcodedItem);
		assertEquals(item, result);
    }

    @Test
    public void searchItemsByText_InvalidDescription_ReturnsNull() {
        Item result = om.searchItemsByText("Invalid Description");

        assertNull(result);
    }

    @Test
    public void searchItemsByText_PLUProduct_ReturnsPLUCodedItem() {
		// setting up
		PLUCodedItem item = DatabaseHelper.createRandomPLUCodedItem();
		PLUCodedProduct prod = DatabaseHelper.get(item);

		// getting the item
        Item result = om.searchItemsByText(prod.getDescription());

		// asserting
		assertTrue(item instanceof PLUCodedItem);
        assertEquals(item, result);
    }

    @Test
    public void searchItemsByText_NullDescription_ReturnsNull() {
        Item result = om.searchItemsByText(null);

        assertNull(result);
    }
}
