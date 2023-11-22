// Liam Major 30223023
package test.managers.payment;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import stubbing.StubbedPaymentManager;
import stubbing.StubbedSystemManager;

public class TestGetters {
	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		pm = sm.pmStub;
	}
	
	@Test
	public void testGetCustomerPaymentReturnsZeroOnCreation() {
		assertEquals(pm.getCustomerPayment(), BigDecimal.ZERO);
	}
}
