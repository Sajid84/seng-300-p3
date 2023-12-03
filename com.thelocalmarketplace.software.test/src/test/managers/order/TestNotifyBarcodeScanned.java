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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;

import stubbing.StubbedOrderManager;
import stubbing.StubbedSystemManager;
import utils.DatabaseHelper;

public class TestNotifyBarcodeScanned {
	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;
	}

	//Testing the null barcode scanned.
	@Test(expected = IllegalArgumentException.class)
	public void testNullBarcodeScanned() {
		om.notifyBarcodeScanned(null, null);
	}

	//Testing if a null item is detected. 
	@Test(expected = IllegalArgumentException.class)
	public void testNullItemDetected() {
		om.notifyBarcodeScanned(null, null);
	}

	//Testing the barcode.
	@Test
	public void testValidUsage() {
		BarcodedItem stub = DatabaseHelper.createRandomBarcodedItem();

		om.notifyBarcodeScanned(null, stub.getBarcode());

		assertFalse(om.errorAddingItem());
	}
}
