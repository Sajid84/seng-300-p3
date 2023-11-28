// Liam Major 30223023
package test.managers.order;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.SessionStatus;
import stubbing.*;

public class TestAttendantOverride {
	// machine
	private AbstractSelfCheckoutStation machine;

	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());
		machine.turnOn();

		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;

		// configuring the machine
		sm.configure(machine);
	}

	@Test
	public void testMethodUnblocks() {
		om.setState(SessionStatus.BLOCKED);

		om.onAttendantOverride();

		assertEquals(om.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testAttendantOverrideSetsAdjustment() {
		// setup
		StubbedBarcodedItem p = new StubbedBarcodedItem();
		om.addItem(p, true);
		om.setActualWeight(BigDecimal.ONE);

		// calling
		om.onAttendantOverride();

		// asserting
		assertEquals(om.getWeightAdjustment(), new BigDecimal(StubbedBarcodedItem.WEIGHT).subtract(BigDecimal.ONE));
	}
}
