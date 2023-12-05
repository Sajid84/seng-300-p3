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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import enums.ScanType;
import powerutility.PowerGrid;
import stubbing.*;
import utils.DatabaseHelper;
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
		StubbedStation.configure();

		// Creating the hardware
		station = new StubbedStation();
		machine = station.machine;
		machine.plugIn(StubbedGrid.instance());
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
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem(massBelow);

		om.addItemToOrder(item, ScanType.MAIN);

		assertFalse("Scale should not be overloaded with a light item", om.isScaleOverloaded());
	}

	@Test
	public void testBagsTooHeavy() {
		// overload
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem(massAbove);

		om.addItemToOrder(item, ScanType.MAIN);

		assertTrue("Scale should be overloaded with a heavy item", om.isScaleOverloaded());
	}

	@Test
	public void testRemovingItemReducesWeight() {
		// resolve an overload
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem(massAbove);

		om.addItemToOrder(item, ScanType.MAIN);
		assertTrue("Scale should be overloaded after adding a heavy item", om.isScaleOverloaded());

		om.removeItemFromOrder(new Pair<>(item, true));

		assertFalse("Scale should not be overloaded after removing the heavy item", om.isScaleOverloaded());
	}

	@Test
	public void testCumulativeWeightCausesOverload() {
		BarcodedItem item1 = DatabaseHelper.createRandomBarcodedItem(massHalf);
		BarcodedItem item2 = DatabaseHelper.createRandomBarcodedItem(massAbove);

		om.addItemToOrder(item1, ScanType.MAIN);
		om.addItemToOrder(item2, ScanType.MAIN);

		assertTrue("Scale should be overloaded with the cumulative weight", om.isScaleOverloaded());
	}

}
