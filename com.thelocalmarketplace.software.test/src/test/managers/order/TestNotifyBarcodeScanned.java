// Liam Major 30223023

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

	@Test(expected = IllegalArgumentException.class)
	public void testNullBarcodeScanned() {
		om.notifyBarcodeScanned(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullItemDetected() {
		om.notifyBarcodeScanned(null, null);
	}

	@Test
	public void testValidUsage() {
		BarcodedItem stub = DatabaseHelper.createRandomBarcodedItem();

		om.notifyBarcodeScanned(null, stub.getBarcode());

		assertFalse(om.errorAddingItem());
	}
}
