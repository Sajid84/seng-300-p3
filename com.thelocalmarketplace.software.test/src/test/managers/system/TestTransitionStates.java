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

import org.junit.Before;
import org.junit.Test;

import enums.SessionStatus;
import stubbing.StubbedAttendantManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

import static org.junit.Assert.*;

/**
 * This tests that the functions that control state can only move between
 * certain states.
 */

public class TestTransitionStates {

	private StubbedSystemManager sm;
	private StubbedAttendantManager am;

	@Before
	public void setup() {
		sm = new StubbedSystemManager();
		am = sm.amStub;
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

	@Test(expected = IllegalStateException.class)
	public void testUnblockSessionWhenPaidOrDisabled() {
		sm.setState(SessionStatus.PAID);
		sm.unblockSession();
	}

	@Test
	public void testIsBlockedAndIsUnblocked() {
		sm.setState(SessionStatus.BLOCKED);
		assertTrue(sm.isBlocked());
		assertFalse(sm.isUnblocked());

		sm.setState(SessionStatus.NORMAL);
		assertFalse(sm.isBlocked());
		assertTrue(sm.isUnblocked());
	}

	@Test
	public void testDisableMachine() {
		sm.requestDisableMachine();
		sm.disableMachine();
		assertEquals(SessionStatus.DISABLED, sm.getState());
		assertTrue(sm.notifyAttendantCalled);
		assertEquals("Machine was disabled", sm.getAttendantNotification());
	}

	@Test(expected = IllegalStateException.class)
	public void testEnableMachineWhenNotDisabled() {
		sm.enableMachine();
	}

	@Test
	public void testEnableMachine() {
		// setup
		sm.requestDisableMachine();
		sm.disableMachine();

		// testing
		sm.requestEnableMachine();
		sm.enableMachine();

		// asserting
		assertEquals(SessionStatus.NORMAL, sm.getState());
		assertTrue(sm.notifyAttendantCalled);
	}

	@Test
	public void testRequestDisableMachine() {
		sm.requestDisableMachine();
		sm.disableMachine();
		assertEquals(SessionStatus.DISABLED, sm.getState());
	}


	@Test
	public void testRequestEnableMachine() {
		sm.requestDisableMachine();
		sm.disableMachine();
		assertEquals(SessionStatus.DISABLED, sm.getState());

		sm.requestEnableMachine();
		try {
			sm.enableMachine();
			assertEquals(SessionStatus.NORMAL, sm.getState());
		} catch (IllegalStateException e) {
			fail("Machine should have been enabled successfully");
		}
	}

	@Test
	public void testIsDisabled() {
		sm.setState(SessionStatus.DISABLED);
		assertTrue(sm.isDisabled());

		sm.setState(SessionStatus.NORMAL);
		assertFalse(sm.isDisabled());
	}

	@Test
	public void testDisabledAfterCallForDisable() {
		sm.requestDisableMachine();
		sm.disableMachine();
		assertTrue(am.isDisabled());
	}

	@Test
	public void testNotDisabledWithoutCallForDisable() {
		sm.requestDisableMachine();
		assertFalse(am.isDisabled());
	}
}
