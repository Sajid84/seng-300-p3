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

package managers.interfaces;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;
import enums.PaymentType;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * A unified interface of what the payment manager should and should not do.
 */
public interface IPaymentManager extends IManager {
    /**
     * Returns the amount of money the customer has inputted into the system
     *
     * @return the amount of money
     */
    BigDecimal getCustomerPayment();

    /**
     * Returns membership CardData
     */
    CardData getMembershipData();

    /**
     * Allows the customer to swipe their credit card
     *
     * @param card a credit card
     * @throws IOException
     */
    void swipeCard(Card card) throws IOException;

    /**
     * Allows the customer to tap a credit card
     *
     * @throws IOException
     */
    void tapCard(Card card) throws IOException;

    /**
     * Allows the customer to insert their credit card
     *
     * @param card
     * @throws IOException
     */
    void insertCard(Card card, String pin) throws IOException;


    /**
     * Allows the customer to insert a coin into the system.
     *
     * @param coin a coin
     */
    void insertCoin(Coin coin) throws DisabledException, CashOverloadException;

    /**
     * Allows the customer to insert a banknote into the system.
     *
     * @param banknote a banknote
     */
    void insertBanknote(Banknote banknote) throws DisabledException, CashOverloadException;

    /**
     * Provides change back to customer after completion of payment
     *
     * @throws NoCashAvailableException
     * @throws RuntimeException
     */
    boolean tenderChange() throws RuntimeException, NoCashAvailableException;

    /**
     * This method sets the internal state to {@code PAID}, should only be used
     * internally.
     */
    void notifyPaid();

    /**
     * Stores a transaction for the manager.
     *
     * @param card       the card data
     * @param holdnumber the hold number
     * @param amount     the price of the order
     */
    void recordTransaction(CardData card, Long holdnumber, Double amount);

    /**
     * Prints a receipt for a given set of products and payment details.s
     *
     * @param type The payment type used (e.g., cash, credit card).
     * @param card The card data associated with the payment (if applicable,
     *             otherwise null).
     */
    void printReceipt(PaymentType type, Card card);

    /**
     * Checks if there was a card inserted.
     *
     * @return true if a card was inserted, false otherwise
     */
    boolean isCardInserted();

    /**
     * Removes the card from the hardware.
     */
    void removeCard();
}
