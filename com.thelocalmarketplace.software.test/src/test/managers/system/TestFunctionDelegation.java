// Liam Major 30223023

package test.managers.system;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import managers.enums.SessionStatus;
import stubbing.StubbedSystemManager;

/**
 * This file tests that functions that should be delegated to a certain manager
 * actually do exactly that.
 */

public class TestFunctionDelegation {
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		sm = new StubbedSystemManager();
	}

	@Test
	public void testInsertCoinCallsPaymentManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.insertCoin(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.pmStub.insertCoinCalled);
	}

	@Test
	public void testInsertBanknoteCallsPaymentManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.insertBanknote(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.pmStub.insertBanknoteCalled);
	}

	@Test
	public void testTenderChangeCallsPaymentManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.tenderChange();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.pmStub.tenderChangeCalled);
	}

	@Test
	public void testAddItemToOrderCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.addItemToOrder(null, null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.addItemToOrderCalled);
	}

	@Test
	public void testAddCustomerBagsCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.addCustomerBags(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.addCustomerBagsCalled);
	}

	@Test
	public void testGetTotalPriceCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.getTotalPrice();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.getTotalPriceCalled);
	}

	@Test
	public void testGetCustomerPaymentCallsPaymentManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.getCustomerPayment();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.pmStub.getCustomerPaymentCalled);
	}

	@Test
	public void testGetProductsCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.getProducts();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.getProductsCalled);
	}

	@Test
	public void testGetExpectedMassCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.getExpectedMass();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.getExpectedMassCalled);
	}

	@Test
	public void testOnAttendantOverrideCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.onAttendantOverride();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.onAttendantOverrideCalled);
	}

	@Test
	public void testOnDoNotBagRequestCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.onDoNotBagRequest(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.onDoNotBagRequestCalled);
	}
	
	@Test
	public void testRemoveItemFromOrderCallsOrderManagerWhenNormal() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.removeItemFromOrder(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.removeItemFromOrderCalled);
	}
	
	@Test
	public void testRemoveItemFromOrderCallsOrderManagerWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		try {
			sm.removeItemFromOrder(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.removeItemFromOrderCalled);
	}
	
	@Test
	public void testPrintReceiptCallsPaymentManager() {
		sm.setState(SessionStatus.PAID);

		try {
			sm.printReceipt(null, null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.pmStub.printReceiptCalled);
	}
}
