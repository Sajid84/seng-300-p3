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
	
	@Test(expected = IllegalArgumentException.class)
	public void testCannotSetANullState() {
		sm.setState(null);
	}


	@Test
	public void testBlockSession() {
		sm.setState(SessionStatus.NORMAL);

		sm.blockSession();

		assertEquals(sm.getState(), SessionStatus.BLOCKED);
	}

	@Test
	public void testUnblockSession() {
		sm.setState(SessionStatus.BLOCKED);

		sm.unblockSession();

		assertEquals(sm.getState(), SessionStatus.NORMAL);
	}

	@Test(expected = RuntimeException.class)
	public void testCannotUnblockSessionFromPaid() {
		sm.setState(SessionStatus.PAID);

		sm.unblockSession();
	}

	@Test
	public void testNotifyPaid() {
		sm.setState(SessionStatus.NORMAL);

		sm.notifyPaid();

		assertEquals(sm.getState(), SessionStatus.PAID);
	}

	@Test(expected = RuntimeException.class)
	public void testNotifyPaidFromBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.notifyPaid();
	}
}
