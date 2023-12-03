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

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import com.tdc.DisabledException;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import powerutility.PowerGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestInsertCoin {

	public ISelfCheckoutStation machine;

	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;

	public Coin fiveCent;

	@Before
	public void setup() {
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
		fiveCent = new Coin(new BigDecimal(0.05));

	}

	// Expect no errors
	@Test
	public void testValidInsert() throws DisabledException, CashOverloadException {
		this.pm.insertCoin(fiveCent);
	}

	@Test(expected = DisabledException.class)
	public void testDisabledInsert() throws DisabledException, CashOverloadException {
		this.machine.getCoinSlot().disable();
		this.pm.insertCoin(fiveCent);
	}

	/**
	 * trying to trigger a cash overload exception
	 * 
	 * @throws CashOverloadException
	 */
	@Test(expected = CashOverloadException.class)
	public void testOverloadedInsert() throws CashOverloadException, DisabledException {
		CoinStorageUnit csu = this.machine.getCoinStorage();

		// loading the dispensers to the max
		for (int i = 0; i < this.machine.getCoinDenominations().size(); i++) {
			BigDecimal denomination = this.machine.getCoinDenominations().get(i);
			ICoinDispenser coinDispenser = this.machine.getCoinDispensers().get(denomination);
			for (int j = 0; j < coinDispenser.getCapacity(); j++) {
				coinDispenser.load(fiveCent);
			}
		}

		// loading the storage unit to the max
		for (int i = 0; i < csu.getCapacity(); i++) {
			csu.load(fiveCent);
		}

		// inserting a coin, this should trigger a cash overload exception
		this.pm.insertCoin(fiveCent);
	}

}
