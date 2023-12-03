// Liam Major 30223023

package test.managers.payment;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import managers.enums.SessionStatus;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedSystemManager;

/**
 * This test class is just for methods that don't fit into the other files.
 */
public class TestNotifyPaid {
	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager();
		pm = sm.pmStub;
	}

	@Test
	public void testNotifyPaymentAdded() {
		pm.notifyBalanceAdded(BigDecimal.ONE);
		
		assertEquals(BigDecimal.ONE, pm.getCustomerPayment());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNotifyPaymentNull() {
		pm.notifyBalanceAdded(null);
	}
	
	@Test
	public void testNotifyPaidSetsState() {
		sm.setState(SessionStatus.NORMAL);
		
		pm.notifyPaid();
		
		assertEquals(SessionStatus.PAID, sm.getState());
	}
}
