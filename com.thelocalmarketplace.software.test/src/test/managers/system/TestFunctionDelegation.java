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

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import enums.SessionStatus;
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
		sm.setState(SessionStatus.PAID);

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
			sm.getItems();
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
			sm.doNotBagRequest(true);
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
