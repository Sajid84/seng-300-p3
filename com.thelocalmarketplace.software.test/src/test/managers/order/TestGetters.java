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

	//Testing that getItems includes all added products.
	@Test
	public void testGetProductsContainsAllAdded() {
		Item item = DatabaseHelper.createRandomBarcodedItem();
		om.addItem(item);

		List<Pair<Item, Boolean>> items = om.getItems();

		assertEquals(items.size(), 1);

		// getting the product from the database
		BarcodedProduct prod = DatabaseHelper.get((BarcodedItem) items.get(0).getKey());

		// asserting
		assertNotNull(prod);
        assertEquals(prod.getExpectedWeight(), item.getMass().inGrams().doubleValue(), 1.5); // I love floating point numbers
	}

	@Test
	public void testGetProductsHasNonOnCreation() {
		//setup
		List<Pair<Item, Boolean>> prods = om.getItems();

		//asserting
		assertEquals(prods.size(), 0);
	}

	//Add a null item. Expect that getTotalPrice throws an expection.
	@Test(expected = IllegalArgumentException.class)
	public void testTotalPriceThrowsOnNull() {
		om.addItem(null);

		om.getTotalPrice();
	}

	//Add a null item. Expect that getExpectedMass throws an expection.
	@Test(expected = IllegalArgumentException.class)
	public void testExpectedMassThrowsOnNull() {
		om.addItem(null);

		om.getExpectedMass();
	}

	//Testing that getMass and getExpectedMass are equal 
	@Test
	public void testExpectedMassEqualsProductMasses() {
		//add item
		Item item = DatabaseHelper.createRandomBarcodedItem();
		om.addItem(item);

		// getting the mass from the manager
		BigDecimal mass = om.getExpectedMass();

		assertEquals(item.getMass().inGrams(), mass);
	}

	//Testing ExpectedMass to not be null
	@Test
	public void testExpectedMassNotNull() {
		assertNotNull(om.getExpectedMass());
	}

	//No items added. Expect to getExpectedMass is equal to zero.
	@Test
	public void testExpectedMassZeroWithNoItems() {
		assertEquals(om.getItems().size(), 0);
		assertEquals(om.getExpectedMass(), BigDecimal.ZERO);
	}

	
	//Test adjustment.
	@Test
	public void testNoAdjustmentOnCreation() {
		assertEquals(om.getWeightAdjustment(), BigDecimal.ZERO);
	}
	
	
	//No items added. Expect to getTotalPrice is equal to zero.
	@Test
	public void testGetTotalPriceReturnsZeroWithNoProducts() {
		assertEquals(om.getTotalPrice(), BigDecimal.ZERO);
	}
}
