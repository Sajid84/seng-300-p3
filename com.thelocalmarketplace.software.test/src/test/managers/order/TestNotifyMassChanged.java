// Liam Major 30223023

package test.managers.order;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import managers.enums.SessionStatus;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedOrderManager;
import stubbing.StubbedSystemManager;

public class TestNotifyMassChanged {
	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotifyMassChangeThrowsOnNull() {
		om.notifyMassChanged(null, null);
	}

	@Test
	public void testNotifyMassChangeThrowsBlocks() {
		om.notifyMassChanged(null, BigDecimal.ONE);

		// station should be blocked now
		assertEquals(om.getState(), SessionStatus.BLOCKED);
	}

	@Test
	public void testNotifyMassChangeThrowsWithAdjustmentDoesntBlock() {
		om.addProduct(new StubbedBarcodedProduct());
		om.setWeightAdjustment(new BigDecimal(StubbedBarcodedProduct.WEIGHT));

		om.notifyMassChanged(null, BigDecimal.ZERO);

		// station should still be normal
		assertEquals(om.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testCheckWeightDifferenceTriggersOnNormal() {
		om.setState(SessionStatus.NORMAL);

		om.checkWeightDifference(BigDecimal.ONE);

		// the OrderManager should be blocked now
		assertEquals(om.getState(), SessionStatus.BLOCKED);
	}

	@Test
	public void testCheckWeightDifferenceDoesntTriggerOnNormal() {
		om.setState(SessionStatus.NORMAL);

		om.checkWeightDifference(BigDecimal.ZERO);

		// the OrderManager should still be normal
		assertEquals(om.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testCheckWeightDifferenceUnblocks() {
		om.setState(SessionStatus.BLOCKED);

		om.checkWeightDifference(BigDecimal.ZERO);

		// the OrderManager should be unblocked now
		assertEquals(om.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testCheckWeightDifferenceDoesntUnblock() {
		om.setState(SessionStatus.BLOCKED);

		om.checkWeightDifference(BigDecimal.ONE);

		// the OrderManager should still be blocked
		assertEquals(om.getState(), SessionStatus.BLOCKED);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testNotifyMassChangedThrowsWhenPaid() {
		om.setState(SessionStatus.PAID);
		
		om.notifyMassChanged(null, BigDecimal.ONE);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCheckWeightDifferenceThrowsWhenPaid() {
		om.setState(SessionStatus.PAID);
		
		om.checkWeightDifference(BigDecimal.ONE);
	}
}
