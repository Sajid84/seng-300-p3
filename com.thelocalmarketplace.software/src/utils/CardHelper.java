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

package utils;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import enums.CardType;

import java.util.Calendar;
import java.util.Random;

public class CardHelper {

    public static final String ISSUER_NAME = "Blackrock";
    public static final long MAX_HOLDS = 10000;
    private static Random r = new Random(0);
    public static final String PIN = "123";
    public static final String HOLDER = "Joe Joeman";

    /**
     * This function randomly picks between a debit card and a credit card, but never a membership card.
     *
     * @return the randomly chosen type of card, never an instance of MEMBERSHIP
     */
    public static CardType chooseRandomType() {
        int choice = r.nextInt(0, 1);
        return switch (choice) {
            case 0 -> CardType.CREDIT;
            case 1 -> CardType.DEBIT;
            default -> CardType.CREDIT;
        };
    }

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
     * @param name     the name of the issuer
     * @param maxHolds the maximum number of holds
     * @return the card issuer
     */
    public static CardIssuer createCardIssuer(String name, long maxHolds) {
        return new CardIssuer(name, maxHolds);
    }

    /**
     * This creates a card and adds it to the issuer. The amount the card has is between
     * 1000 inclusive and 10,000 exclusive. This card will also have tap and insert enabled.
     *
     * @param issuer the card issuer
     * @return a new card authorized by the issuer
     */
    public static Card createCard(CardIssuer issuer) {
        // this function must have an issuer
        if (issuer == null) {
            throw new IllegalArgumentException("cannot add a card to a null card issuer");
        }

        return createCard(issuer, r.nextDouble(1_000, 10_000), chooseRandomType(), true, true);
    }

    /**
     * This creates a card and adds it to the issuer. The amount the card has is between
     * 1000 inclusive and 10,000 exclusive. This card will also have tap and insert enabled.
     *
     * @param issuer the card issuer
     * @param amount the amount to authorize the card for
     * @return a new card authorized by the issuer
     */
    public static Card createCard(CardIssuer issuer, double amount) {
        // this function must have an issuer
        if (issuer == null) {
            throw new IllegalArgumentException("cannot add a card to a null card issuer");
        }

        return createCard(issuer, amount, chooseRandomType(), true, true);
    }

    /**
     * Creates a tap enabled card, of the user's choosing.
     *
     * @param issuer the card issuer
     * @param tap    whether the card should have tap
     * @return a tap enabled card
     */
    public static Card createTapEnabledCard(CardIssuer issuer, boolean tap) {
        // this function must have an issuer
        if (issuer == null) {
            throw new IllegalArgumentException("cannot add a card to a null card issuer");
        }

        return createCard(issuer, r.nextDouble(1_000, 10_000), chooseRandomType(), tap, false);
    }

    /**
     * Creates an insert enabled card, of the user's choosing
     *
     * @param issuer the card issuer
     * @param chip   whether the card has a chip
     * @return a insert enabled card
     */
    public static Card createInsertableCard(CardIssuer issuer, boolean chip) {
        // this function must have an issuer
        if (issuer == null) {
            throw new IllegalArgumentException("cannot add a card to a null card issuer");
        }

        return createCard(issuer, r.nextDouble(1_000, 10_000), chooseRandomType(), true, chip);
    }

    /**
     * Simulates the creating of an invalid card, identical to createCard method
     * except the card is not added to the issuer's database. This is similar to a
     * counterfeit card in the real world. This card will also have tap and insert enabled.
     *
     * @return the invalid card
     */
    public static Card createNonIssuedCard() {
        return createCard(null, 1000, chooseRandomType(), true, true);
    }

    /**
     * Creates a membership card for the customer. This card will also have tap and insert enabled.
     *
     * @return a new membership card
     */
    public static Card createMembershipCard() {
        // creating the card object
        return createCard(null, 1, CardType.MEMBERSHIP, true, true);
    }

    /**
     * Checks if the card is a membership card.
     *
     * @param cardData the card data to check
     * @return true if the card's type is MEMBERSHIP, false otherwise
     */
    public static boolean isMembership(CardData cardData) {
        return cardData.getType().equalsIgnoreCase(CardType.MEMBERSHIP.toString());
    }

    /**
     * Checks if the card is a membership card.
     *
     * @param card the card to check
     * @return true if the card's kind is MEMBERSHIP, false otherwise
     */
    public static boolean isMembership(Card card) {
        return card.kind.equalsIgnoreCase(CardType.MEMBERSHIP.toString());
    }

    /**
     * Creates a card with a specified limit from an issuer.
     *
     * @param issuer  the card issuer, if null, this function ignores the issuer, otherwise it will try an add the card to it
     * @param limit   the limit
     * @param type    the type of card
     * @param hasTap  if the card has tap enabled
     * @param hasChip if the card has a chip to be inserted
     * @return a randomized card
     */
    public static Card createCard(CardIssuer issuer, double limit, CardType type, boolean hasTap, boolean hasChip) {
        // creating a card issuer if none exists
        if (limit <= 0) {
            throw new IllegalArgumentException("cannot create a card with a limit of zero or less");
        }
        if (type == null) {
            throw new IllegalArgumentException("the card must have a type");
        }

        // creating the fields of the card
        String ccv = String.valueOf(r.nextLong(100, 1_000));
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.DAY_OF_MONTH, 20);

        // finding a non-used card number
        do {
            try {
                String number = String.valueOf(r.nextLong(1, 1_000_000_000));

                // creating the card object
                Card card = new Card(type.toString(), number, CardHelper.HOLDER, ccv, CardHelper.PIN, hasTap, hasChip);

                // adding the card to the issuer
                if (issuer != null) {
                    issuer.addCardData(number, CardHelper.HOLDER, expiry, ccv, limit);
                }

                // returning the card to the caller
                return card;
            } catch (InvalidArgumentSimulationException ignored) {
                // do nothing here
            }
        } while (true);
    }

}
