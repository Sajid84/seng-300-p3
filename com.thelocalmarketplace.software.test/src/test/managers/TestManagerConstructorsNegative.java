// Liam Major 30223023

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
