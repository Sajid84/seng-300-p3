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

package test.managers.system;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import enums.SessionStatus;
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
			assertNotNull(sm.getItems());
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
