// Liam Major 30223023

package test.managers.system;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import managers.enums.SessionStatus;
import stubbing.StubbedSystemManager;

/**
 * This tests that the functions that control state can only move between
 * certain states.
 */

public class TestTransitionStates {

	private StubbedSystemManager sm;

	@Before
	public void setup() {
		sm = new StubbedSystemManager();
	}
	
	//test case where IllegalArgumentException will be expected for when test cannot set a null state
	@Test(expected = IllegalArgumentException.class)
	public void testCannotSetANullState() {
		sm.setState(null);
	}

	//test case for when blocked session will go from normal to blocked state
	@Test
	public void testBlockSession() {
		sm.setState(SessionStatus.NORMAL);

		sm.blockSession();

		assertEquals(sm.getState(), SessionStatus.BLOCKED);
	}

	//test case for when unblocked session will go from blocked to normal state
	@Test
	public void testUnblockSession() {
		sm.setState(SessionStatus.BLOCKED);

		sm.unblockSession();

		assertEquals(sm.getState(), SessionStatus.NORMAL);
	}

	//test case for when unblocked session goes from paid state and is expected as RuntimeException 
	@Test(expected = RuntimeException.class)
	public void testCannotUnblockSessionFromPaid() {
		sm.setState(SessionStatus.PAID);

		sm.unblockSession();
	}

	//test case for when notifyPaid session will go from normal to paid state
	@Test
	public void testNotifyPaid() {
		sm.setState(SessionStatus.NORMAL);

		sm.notifyPaid();

		assertEquals(sm.getState(), SessionStatus.PAID);
	}

	//test case for when notifyPaid goes from blocked state and is expect as RuntimeException
	@Test(expected = RuntimeException.class)
	public void testNotifyPaidFromBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.notifyPaid();
	}
}
