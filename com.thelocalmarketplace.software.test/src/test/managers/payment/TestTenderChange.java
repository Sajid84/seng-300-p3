// Liam Major 30223023
// Jason Very 30222040
package test.managers.payment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import observers.payment.BanknoteCollector;
import observers.payment.CoinCollector;
import powerutility.PowerGrid;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestTenderChange {
	public AbstractSelfCheckoutStation machine;
	public StubbedSystemManager sm;
	public StubbedPaymentManager pm;
	public StubbedOrderManager om;
	public CardIssuer issuer;
	public CoinCollector cc;
	public BanknoteCollector bc;
	public ICoinDispenser coinDispenser;
	public IBanknoteDispenser banknoteDispenser;

	public Coin fiveCent;
	public Coin oneDollar;
	public Coin twoDollar;
	public Banknote fiveNote;
	public Banknote twentyNote;
	public Banknote fiftyNote;

	public BarcodedProduct prod;

	@Before
	public void setup() throws OperationNotSupportedException {
		// configuring the hardware
		StubbedStation.configure();
		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(PowerGrid.instance());
		machine.turnOn();
		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;
		pm = sm.pmStub;
		// configuring the machine
		sm.configure(machine);

		// Create coins and banknotes
		Coin.DEFAULT_CURRENCY = Currency.getInstance(Locale.CANADA);
		fiveCent = new Coin(new BigDecimal(0.05));
		oneDollar = new Coin(new BigDecimal(1.00));
		twoDollar = new Coin(new BigDecimal(2.00));
		fiveNote = new Banknote(Currency.getInstance(Locale.CANADA), new BigDecimal(5.00));
		twentyNote = new Banknote(Currency.getInstance(Locale.CANADA), new BigDecimal(20.00));
		fiftyNote = new Banknote(Currency.getInstance(Locale.CANADA), new BigDecimal(50.00));

		// Add item to order to get total price
		// Price of item is $10
		prod = new StubbedBarcodedProduct();
		om.addItem(prod);

	}

	// No Coins in machine
	@Test(expected = NoCashAvailableException.class)
	public void testEmptyMachine() throws RuntimeException, NoCashAvailableException {
		this.pm.tenderChange();
	}

	@Test(expected = NoCashAvailableException.class)
	public void testNotEnoughChangeInMachine()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {
		// Load machine with 2 dollar coin
		BigDecimal twoDenom = this.machine.coinDenominations.get(5);
		coinDispenser = this.machine.coinDispensers.get(twoDenom);
		coinDispenser.load(twoDollar);

		this.machine.banknoteInput.receive(fiftyNote);

		this.pm.tenderChange();

	}

	@Test
	public void testPaymentEqualsTotal() throws CashOverloadException, OperationNotSupportedException,
			DisabledException, RuntimeException, NoCashAvailableException {

		// Load machine with 2 dollar coins
		BigDecimal denomination = this.machine.coinDenominations.get(5);
		coinDispenser = this.machine.coinDispensers.get(denomination);
		for (int i = 0; i < 10; i++) {
			coinDispenser.load(twoDollar);
		}
		// Receives $10 from customer
		for (int i = 0; i < 5; i++) {
			this.machine.coinSlot.receive(twoDollar);
		}

		// No Change to give back, should return true
		assertTrue("No change needed", this.pm.tenderChange());

	}

	// tests if tenderChnage returns true when
	// change is returned
	@Test
	public void testReturnChange() throws OperationNotSupportedException, CashOverloadException, DisabledException,
			RuntimeException, NoCashAvailableException {

		// Load machine with 2 dollar coins
		BigDecimal denomination = this.machine.coinDenominations.get(5);
		coinDispenser = this.machine.coinDispensers.get(denomination);
		for (int i = 0; i < 10; i++) {
			coinDispenser.load(twoDollar);
		}
		this.machine.banknoteInput.receive(twentyNote);

		// Change should be dispensed
		assertTrue("Change was dispensed", this.pm.tenderChange());
	}

	@Test(expected = RuntimeException.class)
	public void testPaymentLessThanPrice()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {
		// Load machine with 2 dollar coins
		BigDecimal denomination = this.machine.coinDenominations.get(5);
		coinDispenser = this.machine.coinDispensers.get(denomination);
		for (int i = 0; i < 10; i++) {
			coinDispenser.load(twoDollar);
		}
		this.machine.banknoteInput.receive(fiveNote);
		this.pm.tenderChange();
	}

	@Test
	public void testEmitMultipleBills()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {

		// load machine with five dollar bills
		BigDecimal fiveDenom = this.machine.banknoteDenominations[0];
		banknoteDispenser = this.machine.banknoteDispensers.get(fiveDenom);
		for (int i = 0; i < 10; i++) {
			banknoteDispenser.load(fiveNote);
		}
		// load machine with twenty dollar bills
		BigDecimal twentyDenom = this.machine.banknoteDenominations[3];
		banknoteDispenser = this.machine.banknoteDispensers.get(twentyDenom);

		banknoteDispenser.load(twentyNote);

		this.machine.banknoteInput.receive(fiftyNote);
		assertTrue(this.pm.tenderChange());

	}

	@Test
	public void testEmitMultipleCoins()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {

		// Load machine with 2 dollar coin
		coinDispenser = this.machine.coinDispensers.get(new BigDecimal(2));
		coinDispenser.load(twoDollar);

		// Load machine with 1 dollar coins
		coinDispenser = this.machine.coinDispensers.get(new BigDecimal(1));
		for (int i = 0; i < 10; i++) {
			coinDispenser.load(oneDollar);
		}

		// inputting a $20 bill
		this.machine.banknoteInput.receive(twentyNote);

		// need to update the payment so we don't trigger the wrong error
		pm.setPayment(2 + (1 * 10));

		assertTrue(this.pm.tenderChange());
	}

	@Test
	public void disabledCoinDispenser()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {

		// Load machine with 2 dollar coins
		BigDecimal denomination = this.machine.coinDenominations.get(5);
		coinDispenser = this.machine.coinDispensers.get(denomination);
		for (int i = 0; i < 10; i++) {
			coinDispenser.load(twoDollar);
		}

		// Receives $10 from customer
		for (int i = 0; i < 6; i++) {
			this.machine.coinSlot.receive(twoDollar);
		}
		this.coinDispenser.disable();

		// Change should not be emitted
		assertFalse(this.pm.tenderChange());
		assertTrue("attendant was called", this.sm.notifyAttendantCalled);
		assertTrue("Session was blocked", this.sm.blockSessionCalled);

	}

	@Test
	public void disabledBanknoteDispsenser()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {

		// load machine with five dollar bills
		BigDecimal fiveDenom = this.machine.banknoteDenominations[0];
		banknoteDispenser = this.machine.banknoteDispensers.get(fiveDenom);
		for (int i = 0; i < 10; i++) {
			banknoteDispenser.load(fiveNote);
		}
		this.machine.banknoteInput.receive(twentyNote);

		this.banknoteDispenser.disable();
		// Change should not be emitted
		assertFalse(this.pm.tenderChange());
		assertTrue("attendant was called", this.sm.notifyAttendantCalled);
		assertTrue("Session was blocked", this.sm.blockSessionCalled);

	}

}
