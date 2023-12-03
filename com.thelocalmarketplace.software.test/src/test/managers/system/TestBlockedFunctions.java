// Liam Major 30223023

package test.managers.system;

import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.tdc.NoCashAvailableException;

import managers.enums.SessionStatus;
import stubbing.StubbedSystemManager;

/**
 * This tests that certain functions cannot execute from certain states.
 */

public class TestBlockedFunctions {
	// TODO implement tests for swiping cards

	private StubbedSystemManager sm;

	@Before
	public void setup() {
		sm = new StubbedSystemManager();
	}
	
	//test case for when card is swiped when session is blocked
	@Test(expected = IllegalStateException.class)
	public void testSwipeCardWhenBlocked() throws IOException {
		sm.setState(SessionStatus.BLOCKED);

		sm.swipeCard(null);
	}

	//test case for when card is swiped when session in a paid state
	@Test(expected = IllegalStateException.class)
	public void testSwipeCardWhenPaid() throws IOException {
		sm.setState(SessionStatus.PAID);

		sm.swipeCard(null);
	}
	
	//test case for when you cannot insert a coin because session is in a blocked state
	@Test(expected = IllegalStateException.class)
	public void testCannotInsertCoinWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.insertCoin(null);
	}

	//test case for when you cannot insert a coin when session is in a paid state
	@Test(expected = IllegalStateException.class)
	public void testCannotInsertCoinWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.insertCoin(null);
	}

	//test case for when you cannot insert a banknote when session is in a blocked state
	@Test(expected = IllegalStateException.class)
	public void testCannotInsertBanknoteWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.insertBanknote(null);
	}

	//test case for when you cannot insert a banknote when session is in paid state
	@Test(expected = IllegalStateException.class)
	public void testCannotInsertBanknoteWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.insertBanknote(null);
	}

	//test case for when you cannot tender change when session is in blocked state
	@Test(expected = IllegalStateException.class)
	public void testCannotTenderChangeWhenBlocked() throws RuntimeException, NoCashAvailableException {
		sm.setState(SessionStatus.BLOCKED);

		sm.tenderChange();
	}

	//test case for when you cannot tender change when session is in a normal state
	@Test(expected = IllegalStateException.class)
	public void testCannotTenderChangeWhenNormal() throws RuntimeException, NoCashAvailableException {
		sm.setState(SessionStatus.NORMAL);

		sm.tenderChange();
	}

	//test case for when you cannot add an item when session is in a blocked state
	@Test(expected = IllegalStateException.class)
	public void testCannotAddItemWhenBlocked() throws OperationNotSupportedException {
		sm.setState(SessionStatus.BLOCKED);

		sm.addItemToOrder(null, null);
	}

	//test case for when you cannot add an item when session is in paid state
	@Test(expected = IllegalStateException.class)
	public void testCannotAddItemWhenPaid() throws OperationNotSupportedException {
		sm.setState(SessionStatus.PAID);

		sm.addItemToOrder(null, null);
	}

	//test case for when you cannot record a transaction when session is in blocked state
	@Test(expected = IllegalStateException.class)
	public void testCannotRecordTransactionFromBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.recordTransaction(null, null, null);
	}
	
	//test case for when you cannot record transaction when session is in paid state
	@Test(expected = IllegalStateException.class)
	public void testCannotRecordTransactionFromPaid() {
		sm.setState(SessionStatus.PAID);

		sm.recordTransaction(null, null, null);
	}
	
	//test case for when you cannot add bags when session is in blocked state
	@Test(expected = IllegalStateException.class)
	public void testCannotAddBagsWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.addCustomerBags(null);
	}

	//test case for when you cannot add bags when session is in paid state
	@Test(expected = IllegalStateException.class)
	public void testCannotAddBagsWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.addCustomerBags(null);
	}
	
	//test case for when the attendant cannot override when session is in paid state
	@Test(expected = IllegalStateException.class)
	public void testAttendantCannotOverrideWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.onAttendantOverride();
	}
	
	//test case for when you cannot remove an item when session is in paid state
	@Test(expected = IllegalStateException.class)
	public void testCannotRemoveItemWhenPaid() throws OperationNotSupportedException {
		sm.setState(SessionStatus.PAID);

		sm.removeItemFromOrder(null);
	}
	
	//test case for when you cannot print receipt when session is in blocked state
	@Test(expected = IllegalStateException.class)
	public void testCannotPrintReceiptWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.printReceipt(null, null);
	}
	
	//test case for when you cannot print receipt when session is in normal state
	@Test(expected = IllegalStateException.class)
	public void testCannotPrintReceiptWhenNormal() {
		sm.setState(SessionStatus.NORMAL);

		sm.printReceipt(null, null);
	}

}
