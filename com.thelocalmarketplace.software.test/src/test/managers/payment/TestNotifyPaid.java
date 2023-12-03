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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import enums.SessionStatus;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedSystemManager;

/**
 * This test class is just for methods that don't fit into the other files.
 */
public class TestNotifyPaid {
	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager();
		pm = sm.pmStub;
	}

	@Test
	public void testNotifyPaymentAdded() {
		pm.notifyBalanceAdded(BigDecimal.ONE);
		
		assertEquals(BigDecimal.ONE, pm.getCustomerPayment());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNotifyPaymentNull() {
		pm.notifyBalanceAdded(null);
	}
	
	@Test
	public void testNotifyPaidSetsState() {
		sm.setState(SessionStatus.NORMAL);
		
		pm.notifyPaid();
		
		assertEquals(SessionStatus.PAID, sm.getState());
	}
}
