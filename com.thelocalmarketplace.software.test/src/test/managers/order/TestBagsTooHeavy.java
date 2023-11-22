// Liam Major 30223023
// Samuel Fasakin, UCID 30161903

package test.managers.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.ScanType;
import powerutility.PowerGrid;
import stubbing.StubbedItem;
import stubbing.StubbedOrderManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestBagsTooHeavy {

	// machine
	private StubbedStation station;
	private AbstractSelfCheckoutStation machine;

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
		baseline = station.getBaggingAreaScale().getMassLimit().inGrams();
		massBelow = baseline.subtract(BigDecimal.ONE);
		massAbove = baseline.add(BigDecimal.ONE);
		massHalf = baseline.divide(BigDecimal.valueOf(2));
	}

	@Test
	public void testBagsNotTooHeavy() throws OperationNotSupportedException {
		// Test to ensure that adding a light item does not trigger the scale overload
		StubbedItem item = new StubbedItem(massBelow);
		om.addItemToOrder(item, ScanType.MAIN);
		assertFalse("Scale should not be overloaded with a light item", om.isScaleOverloaded());
	}

	@Test
	public void testBagsTooHeavy() throws OperationNotSupportedException {
		// Test to check if adding a single heavy item correctly triggers the scale
		// overload
		StubbedItem heavyItem = new StubbedItem(massAbove);
		om.addItemToOrder(heavyItem, ScanType.MAIN);
		assertTrue("Scale should be overloaded with a heavy item", om.isScaleOverloaded());
	}

	@Test
	public void testRemovingItemReducesWeight() throws OperationNotSupportedException {
		// Test to confirm that removing an item reduces the total weight and can
		// resolve an overload
		StubbedItem heavyItem = new StubbedItem(massAbove);
		om.addItemToOrder(heavyItem, ScanType.MAIN);
		assertTrue("Scale should be overloaded after adding a heavy item", om.isScaleOverloaded());

		om.removeItemFromOrder(heavyItem);
		assertFalse("Scale should not be overloaded after removing the heavy item", om.isScaleOverloaded());
	}

	@Test
	public void testCumulativeWeightCausesOverload() throws OperationNotSupportedException {
		// Test to check if the cumulative weight of multiple items leads to an overload

		StubbedItem item1 = new StubbedItem(massHalf);
		StubbedItem item2 = new StubbedItem(massAbove);
		om.addItemToOrder(item1, ScanType.MAIN);
		om.addItemToOrder(item2, ScanType.MAIN);

		assertTrue("Scale should be overloaded with the cumulative weight", om.isScaleOverloaded());
	}

}
