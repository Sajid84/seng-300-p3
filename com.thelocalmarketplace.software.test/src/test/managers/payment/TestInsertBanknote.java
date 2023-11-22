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
import com.tdc.banknote.Banknote;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import powerutility.PowerGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestInsertBanknote {

	public AbstractSelfCheckoutStation machine;

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
	public void testValidInsert() {
		this.pm.insertBanknote(fiveNote);
	}

	@Test
	public void testDisabledInsert() {
		this.machine.banknoteInput.disable();
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
	public void testOverloadedInsert() throws CashOverloadException {
		// loading storage to max
		for (int i = 0; i < this.machine.banknoteStorage.getCapacity(); i++) {
			this.machine.banknoteStorage.load(fiveNote);
		}

		// inserting a banknote, this should trigger a cash overload exception
		this.pm.insertBanknote(fiveNote);

		// the session nor attendant should notified because if the storage unit is
		// full, it will go into the rejection sink
		assertFalse("attendant was called", this.sm.notifyAttendantCalled);
		assertFalse("Session was blocked", this.sm.blockSessionCalled);

	}

}
