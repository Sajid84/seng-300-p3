// Liam Major 30223023
// André Beaulieu, UCID 30174544

package test.observers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import observers.payment.CoinCollector;
import stubbing.StubbedGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestCoinCollector {

	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;
	private CoinCollector cc;
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

		cc = pm.getCoinCollector();
		machine.coinValidator.disable(); // the component is enabled by default, OK
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPaymentManager() {
		new CoinCollector(null, machine.coinValidator);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDevice() {
		new CoinCollector(pm, null);
	}

	/***
	 * Testing when the system detects a new valid coin has been added
	 */
	@Test
	public void validCoinDetectedTest() {
		// We don't care who the validator is, so it may be null.
		cc.validCoinDetected(null, new BigDecimal(2.0f));

		// We expect the PaymentManager to know about an coin of value 3
		assertTrue("The Payment Manager did not recieve the correct coin value",
				pm.notifyBalanceAddedValue.floatValue() == 2.0f);
	}

	@Test
	public void testCannotUseWhenTurnedOffAndDisabled() {
		// asserting
		assertFalse(cc.canUse());
	}

	@Test
	public void testCannotUseWhenTurnedOff() {
		// this can never actually happen
		// the machine needs to be turned on before I can call enable
	}

	@Test
	public void testCannotUseWhenDisabled() {
		machine.coinValidator.activate();

		// asserting
		assertFalse(cc.canUse());
	}

	@Test
	public void testCanUseWhenTurnedOnAndEnabled() {
		machine.coinValidator.activate();
		machine.coinValidator.enable();

		// asserting
		assertTrue(cc.canUse());
	}
}
