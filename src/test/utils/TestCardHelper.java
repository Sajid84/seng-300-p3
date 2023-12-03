// Liam Major 30223023

package test.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.card.Card;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import utils.CardHelper;

public class TestCardHelper {

	private CardIssuer issuer;

	@Before
	public void setup() {
		this.issuer = CardHelper.createCardIssuer();
	}

	@Test
	public void testCreateCardIssuerNotNull() {
		CardIssuer i = CardHelper.createCardIssuer();

		assertNotNull(i);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateCardNullIssuer() {
		CardHelper.createCard(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateCardInvalidAmount() {
		CardHelper.createCard(issuer, 0);
	}

	@Test(timeout = 7000)
	public void testCreateCardEnsureMultipleUnique() {
		Card[] cards = new Card[10];

		// creating cards
		for (int i = 0; i < 10; ++i) {
			cards[i] = CardHelper.createCard(issuer);
		}

		// if we can block the card, we know it worked
		for (int i = 0; i < 10; ++i) {
			issuer.block(cards[i].number);
		}
	}

	@Test
	public void testCreateCardNotNull() {
		Card card = CardHelper.createCard(issuer);

		assertNotNull(card);
	}

	@Test
	public void testCreateInvalidCardNotNull() {
		Card card = CardHelper.createCard(issuer);

		assertNotNull(card);
	}

	@Test
	public void testCreateNullCard() {
		Card card = CardHelper.createNonIssuedCard();
		assertFalse(issuer.block(card.number));
	}

	@Test
	public void testCreateCardInIssuer() {
		Card card = CardHelper.createCard(issuer);

		// if this passes, then we know that the card was actually added
		assertTrue(issuer.block(card.number));
	}

	@Test
	public void testCardHasValidNumber() {
		Card card = CardHelper.createCard(issuer);

		assertNotNull(card.number);

		try {
			Long.parseLong(card.number);
		} catch (NumberFormatException e) {
			fail("could not extract number from card");
		}
	}

	@Test
	public void testCardHasValidCCV() {
		Card card = CardHelper.createCard(issuer);

		assertNotNull(card.cvv);
		assertTrue(card.cvv.length() == 3);

		try {
			Integer.parseInt(card.cvv);
		} catch (NumberFormatException e) {
			fail("could not extract CCV from card");
		}
	}

	@Test
	public void testCardHasValidKind() {
		Card card = CardHelper.createCard(issuer);

		assertNotNull(card.kind);
		assertTrue(card.kind.length() > 0);
	}

	@Test
	public void testCardHasValidHolder() {
		Card card = CardHelper.createCard(issuer);

		assertNotNull(card.cardholder);
		assertTrue(card.cardholder.length() > 0);
	}
}
