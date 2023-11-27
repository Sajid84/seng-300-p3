//Katelan Ng 30144672
// Liam Major 30223023
//Coverage: 77.2%

package test.observers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import observers.order.BarcodeScannerObserver;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;
import utils.DatabaseHelper;

public class TestBarcodeScannerObserver {

	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;
	private BarcodeScannerObserver bso;
	private BarcodedItem item;
	private AbstractSelfCheckoutStation machine;

	@Before
	public void setup() {
		// creating a random item and putting in the database
		this.item = DatabaseHelper.createRandomBarcodedItem();

		// creating the stubs
		sm = new StubbedSystemManager();
		om = sm.omStub;

		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());

		// configuring the machine
		sm.configure(machine);

		bso = om.getMainBarcodeObserver();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullOrderManager() {
		new BarcodeScannerObserver(null, machine.handheldScanner);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDevice() {
		new BarcodeScannerObserver(om, null);
	}

	/***
	 * Tests if the OrderManager actually received the item the hardware scanned.
	 * 
	 * This disregards most of the implementation of the method in OrderManager, the
	 * method `notifyBarcodeScanned` is tested in TestOrderManager.java
	 */
	@Test
	public void testNotifyOrderManagerBarcodeScanned() {
		// TODO redo this test and assert the function
	}
}
