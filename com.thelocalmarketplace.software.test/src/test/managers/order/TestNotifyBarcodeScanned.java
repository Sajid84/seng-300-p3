// Liam Major 30223023

package test.managers.order;

import static org.junit.Assert.assertEquals;

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
		Numeral[] num = { Numeral.one, Numeral.one };
		om.notifyBarcodeScanned(null, new Barcode(num));
	}

	@Test
	public void testValidUsage() {
		BarcodedItem stub = DatabaseHelper.createRandomBarcodedItem();

		om.notifyBarcodeScanned(null, stub.getBarcode());

		assertEquals(1, om.getProducts().size());
	}
}
