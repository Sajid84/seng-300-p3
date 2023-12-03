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

	//test case for when inserted coin calls payment manager when in normal state
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

	//test case for when insert banknote calls payment manager when in normal state
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

	//test case for when tender change calls payment manager when in paid state
	@Test
	public void testTenderChangeCallsPaymentManager() {
		sm.setState(SessionStatus.PAID);

		try {
			sm.tenderChange();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.pmStub.tenderChangeCalled);
	}
	
	//test case for when add item to order calls order manager in normal state
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

	//test case for when add customer bags calls order manager when in normal state
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

	//test case for when getTotalPrice calls order manager in normal state
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

	//test case for when getCustomerPayment calls payment manager in normal state
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

	//test case for when getProduct calls order manager in normal state
	@Test
	public void testGetProductsCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.getItems();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.getProductsCalled);
	}

	//test case for when getExceptionMass calls order manager in normal state
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

	//test case for when onAttendantOverride calls order manager when in normal state
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

	//test case for onDoNotBagRequest calls order manager when in normal state
	@Test
	public void testOnDoNotBagRequestCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.doNotBagRequest(true);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.onDoNotBagRequestCalled);
	}
	
	//test case for when remove item from OrderCalls OrderManager when in normal state
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
	
	//test case for when remove item from OrcerCalls OrderManager when in blocked state
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
	
	//test case for when print receipt calls payment manager when in paid state
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
