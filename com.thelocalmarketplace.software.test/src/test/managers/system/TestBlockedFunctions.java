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
	
	@Test(expected = IllegalStateException.class)
	public void testSwipeCardWhenBlocked() throws IOException {
		sm.setState(SessionStatus.BLOCKED);

		sm.swipeCard(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testSwipeCardWhenPaid() throws IOException {
		sm.setState(SessionStatus.PAID);

		sm.swipeCard(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotInsertCoinWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.insertCoin(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotInsertCoinWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.insertCoin(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotInsertBanknoteWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.insertBanknote(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotInsertBanknoteWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.insertBanknote(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotTenderChangeWhenBlocked() throws RuntimeException, NoCashAvailableException {
		sm.setState(SessionStatus.BLOCKED);

		sm.tenderChange();
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotTenderChangeWhenNormal() throws RuntimeException, NoCashAvailableException {
		sm.setState(SessionStatus.NORMAL);

		sm.tenderChange();
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotAddItemWhenBlocked() throws OperationNotSupportedException {
		sm.setState(SessionStatus.BLOCKED);

		sm.addItemToOrder(null, null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotAddItemWhenPaid() throws OperationNotSupportedException {
		sm.setState(SessionStatus.PAID);

		sm.addItemToOrder(null, null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotRecordTransactionFromBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.recordTransaction(null, null, null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotRecordTransactionFromPaid() {
		sm.setState(SessionStatus.PAID);

		sm.recordTransaction(null, null, null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCannotAddBagsWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.addCustomerBags(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotAddBagsWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.addCustomerBags(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testAttendantCannotOverrideWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.onAttendantOverride();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCannotRemoveItemWhenPaid() throws OperationNotSupportedException {
		sm.setState(SessionStatus.PAID);

		sm.removeItemFromOrder(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCannotPrintReceiptWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.printReceipt(null, null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCannotPrintReceiptWhenNormal() {
		sm.setState(SessionStatus.NORMAL);

		sm.printReceipt(null, null);
	}

}
