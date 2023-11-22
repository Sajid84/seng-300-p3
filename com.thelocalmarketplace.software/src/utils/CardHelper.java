// Liam Major 30223023

package utils;

import java.util.Calendar;
import java.util.Random;

import com.jjjwelectronics.card.Card;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;

public class CardHelper {

	public static final String ISSUER_NAME = "Blackrock";
	public static final long MAX_HOLDS = 100;
	private static Random r = new Random(0);

	public static final String[] BANKS = { "RBC", "Scotiabank" };

	/**
	 * Creates a card issuer.
	 * 
	 * @return the card issuer
	 */
	public static CardIssuer createCardIssuer() {
		return createCardIssuer(ISSUER_NAME, MAX_HOLDS);
	}

	/**
	 * Creates a card issuer.
	 * 
	 * @param name the name of the issuer
	 * @return the card issuer
	 */
	public static CardIssuer createCardIssuer(String name) {
		return createCardIssuer(name, MAX_HOLDS);
	}

	/**
	 * Creates a card issuer.
	 * 
	 * @param maxHolds the maximum number of holds
	 * @return the card issuer
	 */
	public static CardIssuer createCardIssuer(long maxHolds) {
		return createCardIssuer(ISSUER_NAME, maxHolds);
	}

	/**
	 * Creates a card issuer.
	 * 
	 * @param name     the name of the issuer
	 * @param maxHolds the maximum number of holds
	 * @return the card issuer
	 */
	public static CardIssuer createCardIssuer(String name, long maxHolds) {
		return new CardIssuer(name, maxHolds);
	}

	public static Card createCard(CardIssuer issuer) {
		return createCard(issuer, r.nextDouble(100, 10_000));
	}

	/**
	 * Creates a card with a specified limit.
	 * 
	 * @param issuer the card issuer the card should be added to
	 * @param limit  the limit
	 * @return a randomized card
	 */
	public static Card createCard(CardIssuer issuer, double limit) {
		// creating a card issuer if none exists
		if (issuer == null) {
			throw new IllegalArgumentException("cannot add a card to a null card issuer");
		}

		// creating the fields of the card
		String ccv = String.valueOf(r.nextLong(100, 1_000));
		Calendar expiry = Calendar.getInstance();
		expiry.add(Calendar.DAY_OF_MONTH, 20);
		String holder = "Joe Joeman";

		// finding a non-used card number
		do {
			try {
				String number = String.valueOf(r.nextLong(1, 1_000_000_000));

				// creating the card object
				Card card = new Card("debit", number, holder, ccv);

				// adding the card to the issuer
				issuer.addCardData(number, holder, expiry, ccv, limit);

				// returning the card to the caller
				return card;
			} catch (InvalidArgumentSimulationException e) {
				// this is so we don't loop infinitely
				if (!e.getMessage().contains("The card number is not valid.")) {
					throw new IllegalArgumentException("invalid amount was passed into the function");
				}
				
				// since only the card number is invalid, we can loop
				continue;
			}
		} while (true);
	}

	/**
	 * Simulates the creating of an invalid card, identical to createCard method
	 * except the card is not added to the issuer's database. This is similar to a
	 * counterfeit card in the real world.
	 * 
	 * @param issuer The name of the card issuer
	 * @return the invalid card
	 */
	public static Card createNonIssuedCard() {
		Random r = new Random();

		// creating the fields of the card
		String ccv = String.valueOf(r.nextLong(100, 1_000));
		Calendar expiry = Calendar.getInstance();
		expiry.add(Calendar.DAY_OF_MONTH, 20);
		String holder = "Joe Joeman";

		// finding a non-used card number
		String number = String.valueOf(r.nextLong(1, 1_000_000_000));

		// creating the card object
		Card card = new Card("debit", number, holder, ccv);

		// returning the card to the caller
		return card;
	}
}
