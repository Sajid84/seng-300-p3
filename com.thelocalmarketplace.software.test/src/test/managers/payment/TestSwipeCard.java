//Simon Bondad, 30164301
// Liam Major 30223023

package test.managers.payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.math.BigDecimal;

import javax.naming.OperationNotSupportedException;

import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import managers.enums.SessionStatus;
import stubbing.*;
import utils.CardHelper;

public class TestSwipeCard {

	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;
	private StubbedOrderManager om;
	private ISelfCheckoutStation machine;
	private CardIssuer issuer;

	@Before
	public void setup() throws OperationNotSupportedException {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());
		machine.turnOn();

		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		pm = sm.pmStub;
		om = sm.omStub;
		BarcodedItem prod = new StubbedBarcodedItem();
		issuer = CardHelper.createCardIssuer();
		sm.setIssuer(issuer);

		// configuring the machine
		om.addItem(prod);
		sm.configure(machine);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPaymentSwipeNullCard() throws IOException {
		pm.swipeCard(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSystemSwipeNullCard() throws IOException {
		sm.swipeCard(null);
	}

	@Test
	public void testSuccessfulNotifyCardSwipe() throws IOException {
		Card card = CardHelper.createCard(issuer, sm.getTotalPrice().doubleValue());
		pm.notifyCardSwipe(card.swipe());
		assertEquals(SessionStatus.PAID, sm.getState());
		assertEquals(sm.getTotalPrice(), sm.getCustomerPayment());
	}

	@Test(timeout = 5000)
	public void testUnsuccessfulNotifyCardSwipe() throws IOException {
		Card card = CardHelper.createCard(issuer, 2);
		CardSwipeData data = card.swipe();
		pm.notifyCardSwipe(data);
		assertEquals(SessionStatus.NORMAL, sm.getState());
		assertNotEquals(sm.getTotalPrice(), sm.getCustomerPayment());
	}

	@Test
	public void swipingNullCard() throws IOException {
		Card card = CardHelper.createNonIssuedCard();
		pm.swipeCard(card);
		assertEquals(SessionStatus.NORMAL, sm.getState());
		assertNotEquals(sm.getTotalPrice(), sm.getCustomerPayment());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotifyNullCard() throws IOException {
		pm.notifyCardSwipe(null);
	}

}