// Liam Major 30223023
//Jason Very 30222040

package test.observers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import observers.payment.BanknoteCollector;
import stubbing.StubbedGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestBanknoteCollector {

	private StubbedPaymentManager pm;
	private BanknoteCollector bc;
	private StubbedSystemManager sm;
	private AbstractSelfCheckoutStation machine;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager();
		pm = sm.pmStub;

		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());

		// configuring the machine
		sm.configure(machine);

		bc = pm.getBanknoteCollector();
		machine.banknoteValidator.disable(); // the component is enabled by default, OK
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPaymentManager() {
		new BanknoteCollector(null, machine.banknoteValidator);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDevice() {
		new BanknoteCollector(pm, null);
	}

	// Testing when the system detects a new valid bank note has been added
	@Test
	public void TestgoodBanknote() {
		Currency currency = Currency.getInstance("CAD");
		bc.goodBanknote(null, currency, new BigDecimal(5.00f));

		// We expect the PaymentManager to know about a bank note of value 5
		assertTrue("The Payment Manager did recieve the correct amount",
				pm.notifyBalanceAddedValue.floatValue() == 5.00f);
	}

	@Test
	public void testCannotUseWhenTurnedOffAndDisabled() {
		// asserting
		assertFalse(bc.canUse());
	}

	@Test
	public void testCannotUseWhenTurnedOff() {
		// this can never actually happen
		// the machine needs to be turned on before I can call enable
	}

	@Test
	public void testCannotUseWhenDisabled() {
		machine.banknoteValidator.activate();

		// asserting
		assertFalse(bc.canUse());
	}

	@Test
	public void testCanUseWhenTurnedOnAndEnabled() {
		machine.banknoteValidator.activate();
		machine.banknoteValidator.enable();

		// asserting
		assertTrue(bc.canUse());
	}

}
