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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import enums.SessionStatus;
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
