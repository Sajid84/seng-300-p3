// Liam Major 30223023

package test.managers.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.BarcodedItem;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;

import stubbing.*;
import utils.DatabaseHelper;
import utils.Pair;

public class TestGetters {
	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;
	}

	@Test
	public void testGetProductsContainsAllAdded() {
		om.addItem(new StubbedBarcodedItem());

		List<Pair<Item, Boolean>> items = om.getItems();

		assertEquals(items.size(), 1);

		// cheating because I know that I just added a barcoded product
		BarcodedProduct prod = DatabaseHelper.get((BarcodedItem) items.get(0).getKey());

		// asserting
		assertNotNull(prod);
		assertEquals(prod.getBarcode(), StubbedBarcodedItem.BARCODE);
		assertTrue(prod.getExpectedWeight() == StubbedBarcodedItem.WEIGHT);
	}

	@Test
	public void testGetProductsHasNonOnCreation() {
		List<Pair<Item, Boolean>> prods = om.getItems();

		assertEquals(prods.size(), 0);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testTotalPriceThrowsOnPLU() {
		om.addItem(new StubbedPLUItem());

		om.getTotalPrice();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testExpectedMassThrowsOnPLU() {
		om.addItem(new StubbedPLUItem());

		om.getExpectedMass();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTotalPriceThrowsOnNull() {
		om.addItem(null);

		om.getTotalPrice();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExpectedMassThrowsOnNull() {
		om.addItem(null);

		om.getExpectedMass();
	}

	@Test
	public void testExpectedMassEqualsProductMasses() {
		om.addItem(new StubbedBarcodedItem());

		BigDecimal mass = om.getExpectedMass();

		assertEquals(mass, new BigDecimal(StubbedBarcodedProduct.WEIGHT));
	}

	@Test
	public void testExpectedMassNotNull() {
		assertNotNull(om.getExpectedMass());
	}

	@Test
	public void testExpectedMassZeroWithNoItems() {
		assertEquals(om.getItems().size(), 0);
		assertEquals(om.getExpectedMass(), BigDecimal.ZERO);
	}

	@Test
	public void testNoAdjustmentOnCreation() {
		assertEquals(om.getWeightAdjustment(), BigDecimal.ZERO);
	}
	
	@Test
	public void testGetTotalPriceReturnsZeroWithNoProducts() {
		assertEquals(om.getTotalPrice(), BigDecimal.ZERO);
	}
}
