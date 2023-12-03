// Liam Major 30223023
// Samuel Fasakin, UCID 30161903

package test.managers.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.naming.OperationNotSupportedException;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.ScanType;
import powerutility.PowerGrid;
import stubbing.StubbedBarcodedItem;
import stubbing.StubbedOrderManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;
import utils.Pair;

public class TestBagsTooHeavy {

	// machine
	private StubbedStation station;
	private ISelfCheckoutStation machine;

	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;
	private BigDecimal baseline;
	private BigDecimal massBelow;
	private BigDecimal massAbove;
	private BigDecimal massHalf;

	@Before
	public void setup() {
		// configuring the hardware
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		StubbedStation.configure();

		// Creating the hardware
		station = new StubbedStation();
		machine = station.machine;
		machine.plugIn(PowerGrid.instance());
		machine.turnOn();

		// Creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;

		om.configure(machine);

		// getting the mass limit of the scale and creating masses for the test cases
		baseline = station.getBaggingArea().getMassLimit().inGrams();
		massBelow = baseline.subtract(BigDecimal.ONE);
		massAbove = baseline.add(BigDecimal.ONE);
		massHalf = baseline.divide(BigDecimal.valueOf(2));
	}

	@Test
	public void testBagsNotTooHeavy() {
		// Test to ensure that adding a light item does not trigger the scale overload
		StubbedBarcodedItem item = new StubbedBarcodedItem(massBelow);
		om.addItemToOrder(item, ScanType.MAIN);
		assertFalse("Scale should not be overloaded with a light item", om.isScaleOverloaded());
	}

	@Test
	public void testBagsTooHeavy() {
		// Test to check if adding a single heavy item correctly triggers the scale
		// overload
		StubbedBarcodedItem heavyItem = new StubbedBarcodedItem(massAbove);
		om.addItemToOrder(heavyItem, ScanType.MAIN);
		assertTrue("Scale should be overloaded with a heavy item", om.isScaleOverloaded());
	}

	@Test
	public void testRemovingItemReducesWeight() {
		// Test to confirm that removing an item reduces the total weight and can
		// resolve an overload
		StubbedBarcodedItem heavyItem = new StubbedBarcodedItem(massAbove);
		om.addItemToOrder(heavyItem, ScanType.MAIN);
		assertTrue("Scale should be overloaded after adding a heavy item", om.isScaleOverloaded());

		om.removeItemFromOrder(new Pair<>(heavyItem, true));
		assertFalse("Scale should not be overloaded after removing the heavy item", om.isScaleOverloaded());
	}

	@Test
	public void testCumulativeWeightCausesOverload() {
		// Test to check if the cumulative weight of multiple items leads to an overload

		StubbedBarcodedItem item1 = new StubbedBarcodedItem(massHalf);
		StubbedBarcodedItem item2 = new StubbedBarcodedItem(massAbove);
		om.addItemToOrder(item1, ScanType.MAIN);
		om.addItemToOrder(item2, ScanType.MAIN);

		assertTrue("Scale should be overloaded with the cumulative weight", om.isScaleOverloaded());
	}

}
