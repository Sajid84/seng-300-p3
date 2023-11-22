// Liam Major 30223023

package test.managers.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;

import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPLUProduct;
import stubbing.StubbedSystemManager;

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
		om.addProduct(new StubbedBarcodedProduct());

		List<Product> prods = om.getProducts();

		assertEquals(prods.size(), 1);

		// cheating because I know that I just added a barcoded product
		BarcodedProduct prod = (BarcodedProduct) prods.get(0);

		// asserting
		assertNotNull(prod);
		assertEquals(prod.getBarcode(), StubbedBarcodedProduct.BARCODE);
		assertEquals(prod.getDescription(), StubbedBarcodedProduct.DESCRIPTION);
		assertTrue(prod.getExpectedWeight() == StubbedBarcodedProduct.WEIGHT);
		assertEquals(prod.getPrice(), StubbedBarcodedProduct.PRICE);
	}
	
	@Test
	public void testGetTotalOfBarcodedItems() {
		om.addProduct(StubbedBarcodedProduct.getActual());

		// asserting
		assertNotNull(om.getTotalPrice());
		assertEquals(new BigDecimal(StubbedBarcodedProduct.PRICE), om.getTotalPrice());
	}

	@Test
	public void testGetProductsHasNonOnCreation() {
		List<Product> prods = om.getProducts();

		assertEquals(prods.size(), 0);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testTotalPriceThrowsOnPLU() {
		om.addProduct(new StubbedPLUProduct());

		om.getTotalPrice();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testExpectedMassThrowsOnPLU() {
		om.addProduct(new StubbedPLUProduct());

		om.getExpectedMass();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTotalPriceThrowsOnNull() {
		om.addProduct(null);

		om.getTotalPrice();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExpectedMassThrowsOnNull() {
		om.addProduct(null);

		om.getExpectedMass();
	}

	@Test
	public void testExpectedMassEqualsProductMasses() {
		om.addProduct(new StubbedBarcodedProduct());

		BigDecimal mass = om.getExpectedMass();

		assertEquals(mass, new BigDecimal(StubbedBarcodedProduct.WEIGHT));
	}

	@Test
	public void testExpectedMassNotNull() {
		assertNotNull(om.getExpectedMass());
	}

	@Test
	public void testExpectedMassZeroWithNoItems() {
		assertEquals(om.getProducts().size(), 0);
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
