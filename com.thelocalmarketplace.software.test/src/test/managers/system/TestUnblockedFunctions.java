// Liam Major 30223023

package test.managers.system;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import managers.enums.SessionStatus;
import stubbing.StubbedSystemManager;

/**
 * This tests the functions that aren't bound to state can still be called
 * without issue.
 * 
 * Basically, all the getter functions should never be blocked, with the exception of notify attendant.
 * These tests rely on the functions returning non-null values upon creation.
 */

public class TestUnblockedFunctions {
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		sm = new StubbedSystemManager();
	}
	
	@Test
	public void testGetRemainingBalanceFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setState(status);
			assertNotNull(sm.getRemainingBalance());
		}
	}
	
	@Test
	public void testGetTotalPriceFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setState(status);
			assertNotNull(sm.getTotalPrice());
		}
	}
	
	@Test
	public void testGetCustomerPaymentFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setState(status);
			assertNotNull(sm.getCustomerPayment());
		}
	}
	
	@Test
	public void testGetExpectedMassFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setState(status);
			assertNotNull(sm.getExpectedMass());
		}
	}
	
	@Test
	public void testGetProductsFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setState(status);
			assertNotNull(sm.getProducts());
		}
	}
	
	@Test
	public void testGetStateFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setState(status);
			assertNotNull(sm.getRemainingBalance());
		}
	}
	
	@Test
	public void testIsScaleOverloadedWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setState(status);
			assertNotNull(sm.isScaleOverloaded());
		}
	}
	
	@Test
	public void testPostTransactionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setState(status);
			assertTrue(sm.postTransactions());
		}
	}
}
