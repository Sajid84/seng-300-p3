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
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PriceLookUpCode;

import enums.ScanType;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedOrderManagerNotify;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;
import utils.DatabaseHelper;
import utils.Pair;

import static org.junit.Assert.*;

public class TestRemoveItem {
	private StubbedOrderManager om;
	private StubbedSystemManager sm;
	private ISelfCheckoutStation machine;

	/**
	 * Sets up a selfcheckout station, and an ordermanager to test Establishes some
	 * go-to objects we'll use, such as a barcode and an item. Populates the
	 * database for item-checking reasons.
	 */
	@Before
	public void setUp() {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;

		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;

		// configuring the machine
		sm.configure(machine);

		machine.plugIn(StubbedGrid.instance());
		machine.turnOn();
	}

	/**
	 * When an Item isn't PLU-Coded, or Barcoded, then this happens. A null item is
	 * a good example of this type.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWhenItemTypeNotRecognized() {
		om.removeItemFromOrder(null);
	}

	@Test
	public void testWhenItemIsPLUCoded() {
		PLUCodedItem item = DatabaseHelper.createRandomPLUCodedItem();

		om.setBagItem(true);
		om.addItemToOrder(item, ScanType.MAIN);

		// asserting
		assertEquals(1, sm.getItems().size());

		om.removeItemFromOrder(new Pair<>(item, true));

		// asserting
		assertEquals(0, sm.getItems().size());
		assertEquals(BigDecimal.ZERO, sm.getActualWeight());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWhenItemNotInOrder() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();
		om.removeItemFromOrder(new Pair<>(item, true));
	}

	/**
	 * When an Item isn't PLU-Coded, or Barcoded, then this happens. A null item is
	 * a good example of this type.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSystemManagerItemTypeNotRecognized() {
		sm.removeItemFromOrder(null);
	}

	@Test
	public void testSystemManagerItemIsPLUCoded() {
		PLUCodedItem item = DatabaseHelper.createRandomPLUCodedItem();

		om.addItemToOrder(item, ScanType.MAIN);

		om.removeItemFromOrder(new Pair<>(item, true));

		// asserting
		assertEquals(0, sm.getItems().size());
		assertEquals(BigDecimal.ZERO, sm.getActualWeight());
	}

	/**
	 * Trying to add an item not currently in the order has this case happen. This
	 * sets up a new item not in the current order and tries to remove it.
	 * 
	 * @throws OperationNotSupportedException This is expected to happen.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSystemManagerItemNotInOrder() {
		Barcode otherBarcode = new Barcode(new Numeral[] { Numeral.nine, Numeral.nine, Numeral.nine, });
		BarcodedItem item = new BarcodedItem(otherBarcode, new Mass(2));
		sm.removeItemFromOrder(new Pair<>(item, true));
	}

	/**
	 * If the item is a Barcoded type and is in the current order, we expect that it
	 * will be removed, all OrderManager's listeners will be informed, and that the
	 * item will be removed from the scale.
	 */
	@Test
	public void testSystemManagerItemInOrder() {
		// creating a random item
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		// adding
		sm.addItemToOrder(item, ScanType.MAIN);

		// removing
		sm.removeItemFromOrder(new Pair<>(item, true));

		// asserting
		assertEquals(0, sm.getItems().size());
		assertEquals(BigDecimal.ZERO, sm.getActualWeight());
	}

	@Test
	public void testWhenItemInOrder() {
		// creating a random item
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		// Make it so an item of this product already exists in the list.
		sm.addItemToOrder(item, ScanType.MAIN);

		// asserting
		assertEquals(1, sm.getItems().size());

		// removing
		om.removeItemFromOrder(new Pair<>(item, true));

		// asserting
		assertEquals(0, sm.getItems().size());
		assertEquals(BigDecimal.ZERO, sm.getActualWeight());
	}
}
