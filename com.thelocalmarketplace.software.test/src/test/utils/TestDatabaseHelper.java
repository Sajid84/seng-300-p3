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

	//test case to make sure random mass is greater than zero
	@Test
	public void testRandomMassGreaterThanZero() {
		assertTrue(DatabaseHelper.createRandomMass() > 0);
	}

	//test case to make sure random price is greater than zero
	@Test
	public void testRandomPriceGreaterThanZero() {
		assertTrue(DatabaseHelper.createRandomPrice() > 0);
	}

	//test case to test random barcode length 
	@Test
	public void testRandomBarcodeLength() {
		Barcode barcode = DatabaseHelper.createRandomBarcode(length);

		assertEquals(barcode.digitCount(), length);
	}

	//test case to make sure random barcode is not null
	@Test
	public void testRandomBarcodeNotNull() {
		Barcode barcode = DatabaseHelper.createRandomBarcode(length);

		assertNotNull(barcode);
	}

	//test case to make sure random barcode has valid numerals
	@Test
	public void testRandomBarcodeValidNumerals() {
		Barcode barcode = DatabaseHelper.createRandomBarcode(length);

		// basically testing for not null
		for (int i = 0; i < barcode.digitCount(); ++i) {
			assertTrue(barcode.getDigitAt(i) != null);
		}
	}
	
	//test case where InvalidArgumentSimulationException is expected if random barcode has zero length
	@Test(expected = InvalidArgumentSimulationException.class)
	public void testRandomBarcodeZeroLength() {
		DatabaseHelper.createRandomBarcode(0);
	}

	//test case where InvalidArgumentSimulationException is expected if random barcode length is too big
	@Test(expected = InvalidArgumentSimulationException.class)
	public void testRandomBarcodeLengthTooBig() {
		DatabaseHelper.createRandomBarcode(100);
	}

	//test case for seeing if random description is not null
	@Test
	public void testRandomDescriptionNotNull() {
		String s = DatabaseHelper.createRandomDescription();

		assertNotNull(s);
	}

	//test case to see if random description length is not zero
	@Test
	public void testRandomDescriptionLengthNotZero() {
		String s = DatabaseHelper.createRandomDescription();

		assertTrue(s.length() > 0);
	}
	
	//test case to see if random barcoded item is not null
	@Test
	public void testRandomBarcodedItemNotNull() {
		assertNotNull(DatabaseHelper.createRandomBarcodedItem());
	}

	//test case to make sure the random barcoded item has the default barcode length
	@Test
	public void testRandomBarcodedItemDefaultBarcodeLength() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		assertEquals(item.getBarcode().digitCount(), DatabaseHelper.DEFAULT_BARCODE_LENGTH);
	}

	//test case for seeing if random barcoded item in field is not null
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

	//test case to see if random barcoded item has a product in the database
	@Test
	public void testRandomBarcodedItemHasProductInDatabase() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		assertTrue(Database.BARCODED_PRODUCT_DATABASE.containsKey(item.getBarcode()));
		assertNotNull(Database.BARCODED_PRODUCT_DATABASE.keySet().size() > 0);
	}

	//test case to see if the random barcoded item and product have the same barcode 
	@Test
	public void testRandomBarcodedItemAndProductHaveSameBarcode() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();
		BarcodedProduct prod = Database.BARCODED_PRODUCT_DATABASE.get(item.getBarcode());

		assertEquals(item.getBarcode(), prod.getBarcode());
	}

	//test case to create weight discrepancy
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
