// Myra Latif 30171760
// Liam Major 30223023
// Jason Very 30222040

package test.managers.payment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.PowerGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestCanTenderChange {

	public AbstractSelfCheckoutStation machine;

	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;

	private Coin coin;
	private ICoinDispenser coinDispenser;

	@Before
	public void setUp() {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		this.machine = new StubbedStation().machine;
		this.machine.plugIn(PowerGrid.instance());
		this.machine.turnOn();

		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		pm = sm.pmStub;

		// configuring the machine
		sm.configure(machine);

		Coin.DEFAULT_CURRENCY = Currency.getInstance(Locale.CANADA);
		coin = new Coin(new BigDecimal(2.00));
	}

	//Loads money into machine
	private void loadMoneyIntoTheMachine() throws SimulationException, CashOverloadException {
		BigDecimal denomination = this.machine.coinDenominations.get(5);
		coinDispenser = this.machine.coinDispensers.get(denomination);
		for (int i = 0; i < 10; i++) {
			coinDispenser.load(coin);
		}

	}

	//Tests if canTenderChange when machine has enough cash
	@Test
	public void testCanTenderChangeWithEnoughCash() throws SimulationException, CashOverloadException {
		loadMoneyIntoTheMachine();

		// asserting
		assertTrue(pm.canTenderChange(BigDecimal.valueOf(5)));
	}

	//Tests if you canTenderChange with empty machine
	@Test
	public void testCanTenderChangeWithNoCash() {
		assertFalse(pm.canTenderChange(BigDecimal.valueOf(5)));
	}

	//Tests if you canTenderChange with valid environment
	@Test
	public void testCanTenderChange()throws SimulationException, CashOverloadException {
		loadMoneyIntoTheMachine();
		

		// calling
		BigDecimal change = new BigDecimal("10.00");

		// asserting
		assertTrue(pm.canTenderChange(change));
	}
}