// Liam Major 30223023
package test.managers.order;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.SessionStatus;
import stubbing.*;
import utils.DatabaseHelper;

public class TestAttendantOverride {
	// machine
	private ISelfCheckoutStation machine;

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
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();
		om.addItem(item, true);
		om.setActualWeight(BigDecimal.TEN);

		// calling
		om.onAttendantOverride();

		// weight but decimal
		BigDecimal weight = item.getMass().inGrams();

		// asserting
		assertEquals(om.getWeightAdjustment(), BigDecimal.TEN.subtract(weight));
	}
}
