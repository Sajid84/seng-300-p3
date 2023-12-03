// Liam Major 30223023

package test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import database.Database;
import org.junit.Test;

import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import utils.DatabaseHelper;

public class TestDatabaseHelper {

	private int length = 10;

	/**
	 * I really only care that the random mass and price are greater than zero.
	 */

	@Test
	public void testRandomMassGreaterThanZero() {
		assertTrue(DatabaseHelper.createRandomMass() > 0);
	}

	@Test
	public void testRandomPriceGreaterThanZero() {
		assertTrue(DatabaseHelper.createRandomPrice() > 0);
	}

	@Test
	public void testRandomBarcodeLength() {
		Barcode barcode = DatabaseHelper.createRandomBarcode(length);

		assertEquals(barcode.digitCount(), length);
	}

	@Test
	public void testRandomBarcodeNotNull() {
		Barcode barcode = DatabaseHelper.createRandomBarcode(length);

		assertNotNull(barcode);
	}

	@Test
	public void testRandomBarcodeValidNumerals() {
		Barcode barcode = DatabaseHelper.createRandomBarcode(length);

		// basically testing for not null
		for (int i = 0; i < barcode.digitCount(); ++i) {
			assertTrue(barcode.getDigitAt(i) != null);
		}
	}

	@Test(expected = InvalidArgumentSimulationException.class)
	public void testRandomBarcodeZeroLength() {
		DatabaseHelper.createRandomBarcode(0);
	}

	@Test(expected = InvalidArgumentSimulationException.class)
	public void testRandomBarcodeLengthTooBig() {
		DatabaseHelper.createRandomBarcode(100);
	}

	@Test
	public void testRandomDescriptionNotNull() {
		String s = DatabaseHelper.createRandomDescription();

		assertNotNull(s);
	}

	@Test
	public void testRandomDescriptionLengthNotZero() {
		String s = DatabaseHelper.createRandomDescription();

		assertTrue(s.length() > 0);
	}

	@Test
	public void testRandomBarcodedItemNotNull() {
		assertNotNull(DatabaseHelper.createRandomBarcodedItem());
	}

	@Test
	public void testRandomBarcodedItemDefaultBarcodeLength() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		assertEquals(item.getBarcode().digitCount(), DatabaseHelper.DEFAULT_BARCODE_LENGTH);
	}

	@Test
	public void testRandomBarcodedItemFieldsNotNull() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		// checking for null item
		assertNotNull(DatabaseHelper.createRandomBarcodedItem());

		// checking for null fields
		assertNotNull(item.getBarcode());
		assertNotNull(item.getMass());

		// ensuring that the mass is greater than zero
		assertTrue(item.getMass().inGrams().compareTo(BigDecimal.ZERO) > 0);
	}

	@Test
	public void testRandomBarcodedItemHasProductInDatabase() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		assertTrue(Database.BARCODED_PRODUCT_DATABASE.containsKey(item.getBarcode()));
		assertNotNull(Database.BARCODED_PRODUCT_DATABASE.keySet().size() > 0);
	}

	@Test
	public void testRandomBarcodedItemAndProductHaveSameBarcode() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();
		BarcodedProduct prod = Database.BARCODED_PRODUCT_DATABASE.get(item.getBarcode());

		assertEquals(item.getBarcode(), prod.getBarcode());
	}

	@Test
	public void testCreateWeightDiscrepancy() {
		BarcodedItem item = DatabaseHelper.createWeightDiscrepancy();
		BarcodedProduct prod = Database.BARCODED_PRODUCT_DATABASE.get(item.getBarcode());

		// getting masses
		double item_mass = item.getMass().inGrams().doubleValue();
		double prod_mass = prod.getExpectedWeight();
		double difference = Math.abs(item_mass - prod_mass);

		assertTrue(item_mass != prod_mass);
		assertTrue(difference > 10);
	}

}
