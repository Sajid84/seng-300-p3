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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
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
	private ISelfCheckoutStation machine;
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

        assertEquals(0, om.getWeightAdjustment().compareTo(new BigDecimal(45)));
		assertTrue(omnStub.gotNoftifyMassChangedMessage = true);
	}

	/*
	 * Adding a bag thats too heavy Expected to heavyBagState to be true
	 */
	@Test
	public void testAddingTooHeavyBag() {
		Item heavyBag = new StubbedItem(machine.getBaggingArea().getMassLimit().inGrams().add(BigDecimal.TEN));
		om.addCustomerBags(heavyBag);
		assertTrue("Bag is too heavy", om.isScaleOverloaded());
	}
}
