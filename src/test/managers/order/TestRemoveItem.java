// André Beaulieu - 30174544
// Liam Major 30223023

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
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PriceLookUpCode;

import managers.enums.ScanType;
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
		om.removeItemFromOrder((Item) null);
	}

	/**
	 * TODO redo this method
	 */
	@Test(expected = OperationNotSupportedException.class)
	public void testWhenItemIsPLUCoded() throws OperationNotSupportedException {
		// Currently, PLU codes are not accepted so we expect this exception.
		PLUCodedItem item = new PLUCodedItem(new PriceLookUpCode("1234"), new Mass(1));
		om.removeItemFromOrder(new Pair<>(item, true));
	}

	/**
	 * Trying to add an item not currently in the order has this case happen. This
	 * sets up a new item not in the current order and tries to remove it.
	 * 
	 * @throws OperationNotSupportedException This is expected to happen.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWhenItemNotInOrder() {
		Barcode otherBarcode = new Barcode(new Numeral[] { Numeral.nine, Numeral.nine, Numeral.nine, });
		BarcodedItem item = new BarcodedItem(otherBarcode, new Mass(2));
		om.removeItemFromOrder((Item) item);
	}

	/**
	 * When an Item isn't PLU-Coded, or Barcoded, then this happens. A null item is
	 * a good example of this type.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSystemManagerItemTypeNotRecognized() {
		sm.removeItemFromOrder(null);
	}

	/**
	 * PLU Coded items are unimplemented, so we expect an exception for the time
	 * being.
	 * 
	 * @throws OperationNotSupportedException This is expected to happen.
	 */
	@Test(expected = OperationNotSupportedException.class)
	public void testSystemManagerItemIsPLUCoded() throws OperationNotSupportedException {
		// Currently, PLU codes are not accepted so we expect this exception.
		PLUCodedItem item = new PLUCodedItem(new PriceLookUpCode("1234"), new Mass(1));
		sm.removeItemFromOrder(new Pair<>(item, true));
	}

	/**
	 * Trying to add an item not currently in the order has this case happen. This
	 * sets up a new item not in the current order and tries to remove it.
	 * 
	 * @throws OperationNotSupportedException This is expected to happen.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSystemManagerItemNotInOrder() throws OperationNotSupportedException {
		Barcode otherBarcode = new Barcode(new Numeral[] { Numeral.nine, Numeral.nine, Numeral.nine, });
		BarcodedItem item = new BarcodedItem(otherBarcode, new Mass(2));
		sm.removeItemFromOrder(new Pair<>(item, true));
	}

	/**
	 * If the item is a Barcoded type and is in the current order, we expect that it
	 * will be removed, all OrderManager's listeners will be informed, and that the
	 * item will be removed from the scale.
	 * 
	 * @throws OperationNotSupportedException
	 * @throws OverloadedDevice
	 */
	@Test
	public void testSystemManagerItemInOrder() throws OperationNotSupportedException, OverloadedDevice {
		AbstractElectronicScale scale = (AbstractElectronicScale) machine.getBaggingArea();
		StubbedOrderManagerNotify omnStub = new StubbedOrderManagerNotify();
		sm.omStub.registerListener(omnStub);

		// creating a random item
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		// adding
		sm.addItemToOrder(item, ScanType.MAIN);

		// removing
		sm.removeItemFromOrder(new Pair<>(item, true));

		// We expect our listeners to hear about this.
		assertTrue(omnStub.gotOnItemRemovedFromOrderMessage);
        assertSame(omnStub.itemRemovedFromOrder, item);

		// We expect the item to be removed from the bagging area.
		// Because this is the only item, removing it will make the current mass == 0
        assertEquals(0, scale.getCurrentMassOnTheScale().inGrams().floatValue(), 0.0);
	}

	/**
	 * If the item is a Barcoded type and is in the current order, we expect that it
	 * will be removed, all OrderManager's listeners will be informed, and that the
	 * item will be removed from the scale.
	 */
	@Test
	public void testWhenItemInOrder() throws OverloadedDevice {
		AbstractElectronicScale scale = (AbstractElectronicScale) machine.getBaggingArea();
		StubbedOrderManagerNotify omnStub = new StubbedOrderManagerNotify();
		om.registerListener(omnStub);

		// creating a random item
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		// Make it so an item of this product already exists in the list.
		sm.addItemToOrder(item, ScanType.MAIN);

		// removing
		om.removeItemFromOrder((Item) item);

		// We expect our listeners to hear about this.
		assertTrue(omnStub.gotOnItemRemovedFromOrderMessage);
        assertSame(omnStub.itemRemovedFromOrder, item);

		// We expect the item to be removed from the bagging area.
		// Because this is the only item, removing it will make the current mass == 0
        assertEquals(0, scale.getCurrentMassOnTheScale().inGrams().floatValue(), 0.0);
	}
}
