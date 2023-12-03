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

import com.tdc.DisabledException;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import powerutility.PowerGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestInsertBanknote {

	public ISelfCheckoutStation machine;

	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;

	public Banknote fiveNote;

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

		fiveNote = new Banknote(Currency.getInstance(Locale.CANADA), new BigDecimal(5.00));
	}

	// Expect no errors
	@Test
	public void testValidInsert() throws DisabledException, CashOverloadException {
		this.pm.insertBanknote(fiveNote);
	}

	@Test
	public void testDisabledInsert() throws DisabledException, CashOverloadException {
		this.machine.getBanknoteInput().disable();
		this.pm.insertBanknote(fiveNote);
		assertTrue("attendant was called", this.sm.notifyAttendantCalled);
		assertTrue("Session was blocked", this.sm.blockSessionCalled);
	}

	/**
	 * trying to trigger a cash overload exception getting exception from abstract
	 * class not stub
	 * 
	 * @throws CashOverloadException
	 */
	@Test
	public void testOverloadedInsert() throws CashOverloadException, DisabledException {
		// loading storage to max
		for (int i = 0; i < this.machine.getBanknoteStorage().getCapacity(); i++) {
			this.machine.getBanknoteStorage().load(fiveNote);
		}

		// inserting a banknote, this should trigger a cash overload exception
		this.pm.insertBanknote(fiveNote);

		// the session nor attendant should notified because if the storage unit is
		// full, it will go into the rejection sink
		assertFalse("attendant was called", this.sm.notifyAttendantCalled);
		assertFalse("Session was blocked", this.sm.blockSessionCalled);

	}

}
