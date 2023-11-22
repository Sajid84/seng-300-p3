// Liam Major 30223023
package test.managers.order;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.SessionStatus;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

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
		StubbedBarcodedProduct p = new StubbedBarcodedProduct();
		om.addProduct(p, true);
		om.setActualWeight(BigDecimal.ONE);

		// calling
		om.onAttendantOverride();

		// asserting
		assertEquals(om.getWeightAdjustment(), new BigDecimal(StubbedBarcodedProduct.WEIGHT).subtract(BigDecimal.ONE));
	}
}
