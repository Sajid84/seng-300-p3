// Liam Major 30223023

package test.managers.payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.card.Card;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import managers.enums.PaymentType;
import managers.enums.SessionStatus;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;
import utils.CardHelper;

public class TestPrintReceipt {
	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;
	private StubbedOrderManager om;
	private AbstractSelfCheckoutStation machine;
	private CardIssuer issuer;
	private Card card;

	private final int MAXIMUM_INK = 1 << 20;
	private final int MAXIMUM_PAPER = 1 << 10;
	private final int CHARACTERS_PER_LINE = 60;

	@Before
	public void setup() throws OperationNotSupportedException {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());
		machine.turnOn();

		// creating the stubs
		issuer = CardHelper.createCardIssuer();
		sm = new StubbedSystemManager(issuer);
		pm = sm.pmStub;
		om = sm.omStub;
		card = CardHelper.createCard(issuer);

		// configuring the machine
		sm.configure(machine);
		
		// setting the state of the machine
		sm.setState(SessionStatus.PAID);
	}

	@Test
	public void testPrintLineWithLineTooLong() throws OverloadedDevice {
		// loading the machine with paper
		machine.printer.addInk(MAXIMUM_INK);
		machine.printer.addPaper(MAXIMUM_PAPER);

		// creating a string that's too big for the printer
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < CHARACTERS_PER_LINE + 6; ++i) {
			sb.append("A");
		}

		// the printer should throw a device overloaded error, but never a runtime
		// exception
		try {
			pm.printLine(sb.toString());
		} catch (EmptyDevice e) {
			fail("printer ran out of paper");
		} catch (RuntimeException e) {
			fail("this should not have happened");
		}
	}

	@Test
	public void testPrintReceiptRunsOutOfPaperBlocks() throws OverloadedDevice {
		// loading the machine with paper, this updates the internal state of payment
		// manager
		machine.printer.addInk(MAXIMUM_INK);
		machine.printer.addPaper(1);

		// adding a product to the order
		BarcodedProduct prod = new StubbedBarcodedProduct();
		om.addProduct(prod);
		pm.setPayment(prod.getPrice());

		// this should pass
		pm.printReceipt(PaymentType.CARD, card);

		// asserting
		assertEquals(SessionStatus.BLOCKED, pm.getState());
		assertTrue(sm.notifyAttendantCalled);
	}

	@Test
	public void testPrintReceiptRunsOutOfInkBlocks() throws OverloadedDevice {
		// loading the machine with paper, this updates the internal state of payment
		// manager
		machine.printer.addInk(1);
		machine.printer.addPaper(MAXIMUM_PAPER);

		// adding a product to the order
		BarcodedProduct prod = new StubbedBarcodedProduct();
		om.addProduct(prod);
		pm.setPayment(prod.getPrice());

		// this should pass
		pm.printReceipt(PaymentType.CARD, card);

		// asserting
		assertEquals(SessionStatus.BLOCKED, pm.getState());
		assertTrue(sm.notifyAttendantCalled);
	}

	@Test
	public void testPrintReceiptWithCard() throws OverloadedDevice {
		// loading the machine with paper, this updates the internal state of payment
		// manager
		machine.printer.addInk(MAXIMUM_INK);
		machine.printer.addPaper(MAXIMUM_PAPER);

		// adding a product to the order
		BarcodedProduct prod = new StubbedBarcodedProduct();
		om.addProduct(prod);
		pm.setPayment(prod.getPrice());

		// this should pass
		pm.printReceipt(PaymentType.CARD, card);

		// asserting
		assertNotEquals(SessionStatus.BLOCKED, pm.getState());
		assertFalse(sm.notifyAttendantCalled);
	}

	@Test
	public void testPrintReceiptWithoutCard() throws OverloadedDevice {
		// loading the machine with paper, this updates the internal state of payment
		// manager
		machine.printer.addInk(MAXIMUM_INK);
		machine.printer.addPaper(MAXIMUM_PAPER);

		// adding a product to the order
		BarcodedProduct prod = new StubbedBarcodedProduct();
		om.addProduct(prod);
		pm.setPayment(prod.getPrice());

		// this should pass
		pm.printReceipt(PaymentType.CASH, null);

		// asserting
		assertNotEquals(SessionStatus.BLOCKED, pm.getState());
		assertFalse(sm.notifyAttendantCalled);
	}

	@Test
	public void testPrintReceiptBlocksWhenNoPaper() {
		// setting the state of payment manager
		pm.setHasPaper(false);
		pm.setHasInk(true);

		// adding a product to the order
		BarcodedProduct prod = new StubbedBarcodedProduct();
		om.addProduct(prod);
		pm.setPayment(prod.getPrice());

		// this should fail
		pm.printReceipt(PaymentType.CASH, null);

		// asserting
		assertEquals(SessionStatus.BLOCKED, pm.getState());
		assertTrue(sm.notifyAttendantCalled);
	}

	@Test
	public void testPrintReceiptBlocksWhenNoInk() {
		// setting the state of payment manager
		pm.setHasPaper(true);
		pm.setHasInk(false);

		// adding a product to the order
		BarcodedProduct prod = new StubbedBarcodedProduct();
		om.addProduct(prod);
		pm.setPayment(prod.getPrice());

		// this should fail
		pm.printReceipt(PaymentType.CASH, null);

		// asserting
		assertEquals(SessionStatus.BLOCKED, pm.getState());
		assertTrue(sm.notifyAttendantCalled);
	}

	@Test
	public void testPrintReceiptBlocksWhenNeitherInkNorPaper() {
		// setting the state of payment manager
		pm.setHasPaper(false);
		pm.setHasInk(false);

		// adding a product to the order
		BarcodedProduct prod = new StubbedBarcodedProduct();
		om.addProduct(prod);
		pm.setPayment(prod.getPrice());

		// this should fail
		pm.printReceipt(PaymentType.CASH, null);

		// asserting
		assertEquals(SessionStatus.BLOCKED, pm.getState());
		assertTrue(sm.notifyAttendantCalled);
	}

	@Test(expected = RuntimeException.class)
	public void testPaymentLessThanPrice() {
		// adding a product to the order
		BarcodedProduct prod = new StubbedBarcodedProduct();
		om.addProduct(prod);

		pm.printReceipt(PaymentType.CASH, null);
	}

	@Test(expected = RuntimeException.class)
	public void testNoProducts() {
		pm.printReceipt(PaymentType.CASH, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullType() {
		pm.printReceipt(null, null);
	}

	@Test
	public void testTypeCardWithCard() {
		// relying on no products to trigger an exception
		try {
			pm.printReceipt(PaymentType.CARD, card);
		} catch (IllegalArgumentException e) {
			fail("function triggered wrong exception");
		} catch (RuntimeException e) {
			return;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTypeCardWithNullCard() {
		pm.printReceipt(PaymentType.CARD, null);
	}

	@Test
	public void testTypeCashWithNullCard() {
		// relying on no products to trigger an exception
		try {
			pm.printReceipt(PaymentType.CASH, null);
		} catch (IllegalArgumentException e) {
			fail("function triggered wrong exception");
		} catch (RuntimeException e) {
			return;
		}
	}

	@Test
	public void testTypeCryptoWithNullCard() {
		// relying on no products to trigger an exception
		try {
			pm.printReceipt(PaymentType.CRYPTO, null);
		} catch (IllegalArgumentException e) {
			fail("function triggered wrong exception");
		} catch (RuntimeException e) {
			return;
		}
	}

	@Test
	public void testTypeCashWithCard() {
		// relying on no products to trigger an exception
		try {
			pm.printReceipt(PaymentType.CASH, card);
		} catch (IllegalArgumentException e) {
			fail("function triggered wrong exception");
		} catch (RuntimeException e) {
			return;
		}
	}

	@Test
	public void testTypeCryptoWithCard() {
		// relying on no products to trigger an exception
		try {
			pm.printReceipt(PaymentType.CRYPTO, card);
		} catch (IllegalArgumentException e) {
			fail("function triggered wrong exception");
		} catch (RuntimeException e) {
			return;
		}
	}
}
