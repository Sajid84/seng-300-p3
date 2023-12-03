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
	//Instance variable is create to use CardIssuer in test cases
	private CardIssuer issuer;

	//setup method is used to initialize CardIssuer before test cases
	@Before
	public void setup() {
		this.issuer = CardHelper.createCardIssuer();
	}

	//test case for if the CardIssuer created is not null
	@Test
	public void testCreateCardIssuerNotNull() {
		CardIssuer i = CardHelper.createCardIssuer();

		assertNotNull(i);
	}

	//test case to give an IllegalArgumentException if creating card with null issuer
	@Test(expected = IllegalArgumentException.class)
	public void testCreateCardNullIssuer() {
		CardHelper.createCard(null);
	}

	//test case to give an IllegalArgumentException if creating a card with invalid amount
	@Test(expected = IllegalArgumentException.class)
	public void testCreateCardInvalidAmount() {
		CardHelper.createCard(issuer, 0);
	}

	//test case to ensure multiple cards can be created and blocked
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

	//test case to make sure the card created is not null
	@Test
	public void testCreateCardNotNull() {
		Card card = CardHelper.createCard(issuer);

		assertNotNull(card);
	}

	//test case to make sure invalid card is not null
	@Test
	public void testCreateInvalidCardNotNull() {
		Card card = CardHelper.createCard(issuer);

		assertNotNull(card);
	}

	//test case to make sure that a non-issued card is able to be created without being blocked
	@Test
	public void testCreateNullCard() {
		Card card = CardHelper.createNonIssuedCard();
		assertFalse(issuer.block(card.number));
	}

	//test case to make sure a card that is created is added to the issuer
	@Test
	public void testCreateCardInIssuer() {
		Card card = CardHelper.createCard(issuer);

		// if this passes, then we know that the card was actually added
		assertTrue(issuer.block(card.number));
	}

	//test case to make sure card has a valid number
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

	//test case to make sure the card has a valid CCV
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

	//test case to make sure card has a valid kind
	@Test
	public void testCardHasValidKind() {
		Card card = CardHelper.createCard(issuer);

		assertNotNull(card.kind);
		assertTrue(card.kind.length() > 0);
	}

	//test case to make sure card has a valid holder
	@Test
	public void testCardHasValidHolder() {
		Card card = CardHelper.createCard(issuer);

		assertNotNull(card.cardholder);
		assertTrue(card.cardholder.length() > 0);
	}
}
