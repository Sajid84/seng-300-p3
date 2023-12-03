// Liam Major 30223023
package test.managers;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import stubbing.StubbedOrderManager;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedSystemManager;

public class TestChildManagersCallParent {
	// vars
	private StubbedPaymentManager pm;
	private StubbedOrderManager om;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		pm = sm.pmStub;
		om = sm.omStub;
	}

	@Test
	public void testPaymentGetStateCallsSystem() {
		try {
			pm.getState();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.getStateCalled);
	}

	@Test
	public void testPaymentBlockSessionCallsSystem() {
		try {
			pm.blockSession();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.blockSessionCalled);
	}

	@Test
	public void testPaymentUnblockSessionCallsSystem() {
		try {
			pm.unblockSession();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.unblockSessionCalled);
	}

	@Test
	public void testPaymentNotifyAttendantCallsSystem() {
		try {
			pm.notifyAttendant("null");
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.notifyAttendantCalled);
	}

	@Test
	public void testOrderGetStateCallsSystem() {
		try {
			om.getState();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.getStateCalled);
	}

	@Test
	public void testOrderBlockSessionCallsSystem() {
		try {
			om.blockSession();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.blockSessionCalled);
	}

	@Test
	public void testOrderUnblockSessionCallsSystem() {
		try {
			om.unblockSession();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.unblockSessionCalled);
	}

	@Test
	public void testOrderNotifyAttendantCallsSystem() {
		try {
			om.notifyAttendant("null");
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.notifyAttendantCalled);
	}

	@Test
	public void testPaymentRecordTransactionCallsSystem() {
		try {
			pm.recordTransaction(null, null, null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.recordTransactionCalled);
	}
}
