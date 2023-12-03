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

package test.managers.payment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
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

	public ISelfCheckoutStation machine;

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
		BigDecimal denomination = this.machine.getCoinDenominations().get(5);
		coinDispenser = this.machine.getCoinDispensers().get(denomination);
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