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

package test.managers;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.external.CardIssuer;

import managers.OrderManager;
import managers.PaymentManager;
import managers.SystemManager;

/**
 * This class tests how constructors behave relative to their arguments.
 */

public class TestManagerConstructorsNegative {

	private SystemManager sm;
	private CardIssuer issuer = new CardIssuer("testing", 10);
	private BigDecimal leniency = BigDecimal.ONE;
	
	@Before
	public void setup() {
		sm = new SystemManager(issuer, leniency);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPaymentManagerNullIssuer() {
		new PaymentManager(sm, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPaymentManagerNullSystemManager() {
		new PaymentManager(null, issuer);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOrderManagerNullLeniency() {
		new OrderManager(sm, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOrderManagerNullSystemManager() {
		new OrderManager(null, leniency);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSystemManagerNullLeniency() {
		new SystemManager(issuer, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSystemManagerNullIssuer() {
		new SystemManager(null, leniency);
	}
	
}
