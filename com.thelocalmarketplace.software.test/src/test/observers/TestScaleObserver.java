// Liam Major 30223023
//Katelan Ng 30144672
//Coverage: 94.6%

package test.observers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import observers.order.ScaleObserver;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestScaleObserver {

	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;
	private ScaleObserver so;
	private AbstractSelfCheckoutStation machine;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager();
		om = sm.omStub;

		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());

		// configuring the machine
		sm.configure(machine);

		so = om.getBaggingAreaObserver();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullOrderManager() {
		new ScaleObserver(null, machine.baggingArea);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDevice() {
		new ScaleObserver(om, null);
	}

	/**
	 * Testing that the mass observed by the scale was passed correctly to the
	 * OrderManager.
	 */
	@Test
	public void testNotifyOrderManagerMassChange() {
		Mass mass = new Mass(1);

		// We don't care who the validator is, so it may be null.
		so.theMassOnTheScaleHasChanged(null, mass);

		// ensure that the actual weight in the OrderManager has updated
		assertEquals(mass.inGrams(), om.getActualWeight());
	}

	@Test
	public void testCannotUseWhenTurnedOffAndDisabled() {
		// asserting
		assertFalse(so.canUse());
	}

	@Test
	public void testCannotUseWhenTurnedOff() {
		// this can never actually happen
		// the machine needs to be turned on before I can call enable
	}

	@Test
	public void testCannotUseWhenDisabled() {
		machine.baggingArea.turnOn();
		machine.baggingArea.disable();

		// asserting
		assertFalse(so.canUse());
	}

	@Test
	public void testCanUseWhenTurnedOnAndEnabled() {
		machine.baggingArea.turnOn();
		machine.baggingArea.enable();

		// asserting
		assertTrue(so.canUse());
	}
}
