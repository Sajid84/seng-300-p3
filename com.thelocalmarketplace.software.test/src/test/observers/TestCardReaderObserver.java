package test.observers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import observers.payment.CardReaderObserver;
import stubbing.StubbedCardData;
import stubbing.StubbedGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestCardReaderObserver {
	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;
	private CardReaderObserver cro;
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

		// creating the observer
		cro = pm.getCardReaderObserver();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPaymentManager() {
		new CardReaderObserver(null, machine.cardReader);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDevice() {
		new CardReaderObserver(pm, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotifyCardSwipeNullData() {
		cro.theDataFromACardHasBeenRead(null);
	}

	@Test
	public void testNotifyCardSwipeValidData() {
		// this test should not throw
		cro.theDataFromACardHasBeenRead(new StubbedCardData());
	}

	@Test
	public void testCannotUseWhenTurnedOffAndDisabled() {
		// asserting
		assertFalse(cro.canUse());
	}

	@Test
	public void testCannotUseWhenTurnedOff() {
		// this can never actually happen
		// the machine needs to be turned on before I can call enable
	}

	@Test
	public void testCannotUseWhenDisabled() {
		machine.cardReader.turnOn();
		machine.cardReader.disable();

		// asserting
		assertFalse(cro.canUse());
	}

	@Test
	public void testCanUseWhenTurnedOnAndEnabled() {
		machine.cardReader.turnOn();
		machine.cardReader.enable();

		// asserting
		assertTrue(cro.canUse());
	}
}
