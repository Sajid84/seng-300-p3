// Liam Major 30223023
// Kear Sang Heng 30087289
package test.managers.order;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PriceLookUpCode;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import powerutility.PowerGrid;
import stubbing.StubbedItem;
import stubbing.StubbedOrderManager;
import stubbing.StubbedOrderManagerNotify;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestAddOwnBags {
	// stubs
	private StubbedOrderManager om;
	private AbstractSelfCheckoutStation machine;
	private StubbedStation station;
	private StubbedSystemManager sm;

	/*
	 * Sets up the self checkout station, order manager, and system manager to test.
	 * Sets the scale weight limit to 50
	 */
	@Before
	public void setup() {
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

		sm.configure(machine);

	}

	/*
	 * Adds a valid bag to the checkout station Expected for the bag to added
	 * Listeners are informed to about mass change
	 * 
	 * @throws OverloadedDevice
	 */
	@Test
	public void testValidAddCustomerBags() throws OverloadedDevice {

		StubbedOrderManagerNotify omnStub = new StubbedOrderManagerNotify();
		om.registerListener(omnStub);

		// Bag with weight of 10
		Item normalBag = new StubbedItem(10);
		om.addCustomerBags(normalBag);

		assertTrue(om.getWeightAdjustment().compareTo(new BigDecimal(10)) == 0);
		assertTrue(omnStub.gotNoftifyMassChangedMessage = true);
	}

	/*
	 * Adds a bag with no mass. No bag. It is expected to throw
	 * InvalidArgumentSimulationException
	 */
	@Test(expected = InvalidArgumentSimulationException.class)
	public void testAddingNoBags() throws InvalidArgumentSimulationException {
		// Bag with no weight
		Item normalBag = new StubbedItem(0);
		om.addCustomerBags(normalBag);
	}

	/*
	 * Adds multiple bags with different weights Expected for all bags to be added
	 * Listeners are informed about mass changes
	 */
	@Test
	public void testAddingMultipleValidBags() {
		StubbedOrderManagerNotify omnStub = new StubbedOrderManagerNotify();
		om.registerListener(omnStub);

		// Three bags with different weights
		Item normalBag1 = new StubbedItem(10);
		Item normalBag2 = new StubbedItem(15);
		Item normalBag3 = new StubbedItem(20);
		om.addCustomerBags(normalBag1);
		om.addCustomerBags(normalBag2);
		om.addCustomerBags(normalBag3);

		assertTrue(om.getWeightAdjustment().compareTo(new BigDecimal(45)) == 0);
		assertTrue(omnStub.gotNoftifyMassChangedMessage = true);
	}

	/*
	 * Adding an item (not a bag) to customer bags It is expected to throw
	 * InvalidArgumentSimulationException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddingItem() {
		// Added an mock item
		PLUCodedItem item = new PLUCodedItem(new PriceLookUpCode("1234"), new Mass(1));
		om.addCustomerBags(item);
	}

	/*
	 * Adding an bag with negative weight It is expected to throw
	 * InvalidArgumentSimulationException
	 */
	@Test(expected = InvalidArgumentSimulationException.class)
	public void testAddingBagWithNegativeWeight() {
		// Mass of -10
		Item negativeBag = new StubbedItem(-10);
		om.addCustomerBags(negativeBag);
	}

	/*
	 * Adding a bag thats too heavy Expected to heavyBagState to be true
	 */
	@Test
	public void testAddingTooHeavyBag() {
		Item heavyBag = new StubbedItem(station.getBaggingAreaScale().getMassLimit().inGrams().add(BigDecimal.TEN));
		om.addCustomerBags(heavyBag);
		assertTrue("Bag is too heavy", om.isScaleOverloaded());
	}
}
