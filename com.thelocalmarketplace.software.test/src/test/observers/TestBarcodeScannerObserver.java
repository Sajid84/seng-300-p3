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

package test.observers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
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
	private ISelfCheckoutStation machine;

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
		new BarcodeScannerObserver(null, machine.getHandheldScanner());
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
