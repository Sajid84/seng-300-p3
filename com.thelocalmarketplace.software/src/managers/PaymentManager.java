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

package managers;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import enums.PaymentType;
import enums.SessionStatus;
import managers.interfaces.IPaymentManager;
import managers.interfaces.IPaymentManagerNotify;
import observers.payment.BanknoteCollector;
import observers.payment.CardReaderObserver;
import observers.payment.CoinCollector;
import utils.CardHelper;
import utils.DatabaseHelper;
import utils.Pair;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PaymentManager implements IPaymentManager, IPaymentManagerNotify {

    // hardware references
    protected ISelfCheckoutStation machine;

    // object references
    protected SystemManager sm;
    protected CardIssuer issuer;

    // object ownership
    protected CoinCollector cc;
    protected BanknoteCollector bc;
    protected CardReaderObserver cro;

    // vars
    protected BigDecimal payment = BigDecimal.ZERO;
    protected CardData membershipData;
    protected boolean cardInserted = false;

    /**
     * This controls everything relating to customer payment.
     *
     * @param sm     a reference to the parent {@link SystemManager} object
     * @param issuer a card issuer
     * @throws IllegalArgumentException when either argument is null
     */
    public PaymentManager(SystemManager sm, CardIssuer issuer) {
        // checking arguments
        if (sm == null) {
            throw new IllegalArgumentException("the system manager cannot be null");
        }

        if (issuer == null) {
            throw new IllegalArgumentException("the card issuer cannot be null");
        }

        // copying references
        this.sm = sm;
        this.issuer = issuer;
    }

    @Override
    public void configure(ISelfCheckoutStation machine) {
        // saving reference
        this.machine = machine;

        // passing references, because nothing actually notifies the observers of the
        // machine itself EVER
        cc = new CoinCollector(this, machine.getCoinValidator());
        bc = new BanknoteCollector(this, machine.getBanknoteValidator());
        cro = new CardReaderObserver(this, machine.getCardReader());
    }

    /**
     * This is how you should tell the payment manager that there was payment added
     * to the system.
     *
     * @param value the value
     */
    @Override
    public void notifyBalanceAdded(BigDecimal value) {
        if (value == null)
            throw new IllegalArgumentException("the value added cannot be null");

        this.payment = this.payment.add(value);
    }

    @Override
    public void notifyCardInserted(boolean inserted) {
        cardInserted = inserted;
    }

    @Override
    public BigDecimal getCustomerPayment() {
        return this.payment;
    }

    @Override
    public CardData getMembershipData() {
        return this.membershipData;
    }

    @Override
    public void tapCard(Card card) throws IOException {
        if (card == null) {
            throw new IllegalArgumentException("cannot tap a null card");
        }

        this.machine.getCardReader().tap(card);

    }


    @Override
    public void insertCard(Card card, String pin) throws IOException {
        if (card == null) {
            throw new IllegalArgumentException("Cannot insert a null card");
        }

        this.machine.getCardReader().insert(card, pin);
    }


    @Override
    public void swipeCard(Card card) throws IOException {
        if (card == null) {
            throw new IllegalArgumentException("cannot swipe a null card");
        }

        this.machine.getCardReader().swipe(card);
    }


    @Override
    public void notifyCardDataRead(CardData cardData) {
        if (cardData == null) {
            throw new IllegalArgumentException("received null card data from the observer");
        }

        if (CardHelper.isMembership(cardData)) {
            membershipData = cardData;
            return;
        }
        // vars
        double amountDouble = sm.getTotalPrice().doubleValue();
        long holdNumber = issuer.authorizeHold(cardData.getNumber(), amountDouble);

        // testing the hold number
        if (holdNumber != -1) {
            payment = sm.getTotalPrice();
            recordTransaction(cardData, holdNumber, amountDouble);
        }
    }


    @Override
    public void insertCoin(Coin coin) throws DisabledException, CashOverloadException {
        this.machine.getCoinSlot().receive(coin);
    }

    @Override
    public void insertBanknote(Banknote banknote) throws DisabledException, CashOverloadException {
        this.machine.getBanknoteInput().receive(banknote);
    }

    /**
     * Tests if there is enough money in the machine to dispenser for the customer.
     *
     * @param change the amount of change that needs to be tendered
     * @return true if the amount of coins and banknotes of various denominations are greater or equal to the change
     */
    protected boolean canTenderChange(BigDecimal change) {
        BigDecimal totalMachineCash = BigDecimal.ZERO;

        // summing all the money in each coin dispenser
        for (BigDecimal denom : this.machine.getCoinDenominations()) {
            ICoinDispenser coinDispenser = this.machine.getCoinDispensers().get(denom);
            totalMachineCash = totalMachineCash.add(denom.multiply(BigDecimal.valueOf(coinDispenser.size())));
        }

        // summing all the money in each banknote dispenser
        for (BigDecimal denom : this.machine.getBanknoteDenominations()) {
            IBanknoteDispenser banknoteDispenser = this.machine.getBanknoteDispensers().get(denom);
            totalMachineCash = totalMachineCash.add(denom.multiply(BigDecimal.valueOf(banknoteDispenser.size())));
        }

        // if the total cash in the machine is less than the inputted amount, this will return false
        return totalMachineCash.compareTo(change) >= 0;
    }

    @Override
    public boolean tenderChange() throws RuntimeException, NoCashAvailableException {
        // Determines amount of change needed
        BigDecimal change = this.payment.subtract(sm.getTotalPrice());

        // checking if we can actually dispense change
        if (!canTenderChange(change)) {
            throw new NoCashAvailableException();
        }

        // comparing the change to zero
        switch (change.compareTo(BigDecimal.ZERO)) {
            case 1:
                // Payment greater then total, need to dispense change
                break;
            case 0:
                // Payment == total, no change needed
                return true;
            case -1:
                // The payment was less than the total price
                throw new RuntimeException("The total price is greater than payment");
        }

        // sorting and reversing the denominations
        List<BigDecimal> sorted_banknote_denoms = new ArrayList<BigDecimal>(Arrays.asList(machine.getBanknoteDenominations()));
        Collections.sort(sorted_banknote_denoms);
        Collections.reverse(sorted_banknote_denoms);

        // Iterate through all the banknote denominations in descending order
        // This assumes largest denominations are last in list
        for (BigDecimal denomination : sorted_banknote_denoms) {
            IBanknoteDispenser banknoteDispenser = this.machine.getBanknoteDispensers().get(denomination);

            // check if current dispensers is empty.
            // If empty, switch to next denomination
            if (banknoteDispenser.size() == 0) {
                continue;
            }

            // checks if current change balance is less then
            // current denomination
            if (change.compareTo(denomination) < 0) {
                continue;
            }

            // emits current banknote denomination until balance is less
            // than current denomination
            while (change.compareTo(denomination) >= 0) {
                // Emit current banknote denomination
                // and update current change
                try {
                    banknoteDispenser.emit();
                } catch (NoCashAvailableException | DisabledException | CashOverloadException e) {
                    this.sm.notifyAttendant("$" + denomination + " banknote was not emitted");
                    break;
                }
                change = change.subtract(denomination);
            }
        }

        // sorting and reversing the denominations
        List<BigDecimal> sorted_coin_denoms = machine.getCoinDenominations();
        Collections.sort(sorted_coin_denoms);
        Collections.reverse(sorted_coin_denoms);

        // Iterate through all the cash denominations now
        for (BigDecimal denomination : sorted_coin_denoms) {
            ICoinDispenser coinDispenser = this.machine.getCoinDispensers().get(denomination);

            // checks if current dispenser are empty
            // If empty switch to next denomination
            if (coinDispenser.size() == 0) {
                continue;
            }

            // checks if current balance is less than
            // the current denomination
            if (change.compareTo(denomination) < 0) {
                continue;
            }

            // emits current coin until
            // change is less then coin value
            while (change.compareTo(denomination) >= 0) {
                // emits current coin denomination
                // and updates change balance
                try {
                    coinDispenser.emit();
                } catch (NoCashAvailableException | DisabledException | CashOverloadException e) {
                    this.sm.notifyAttendant("$" + denomination + " coin was not emitted");
                    break;
                }
                change = change.subtract(denomination);
            }
        }

        // Checks to insure proper change was provided
        return switch (change.compareTo(BigDecimal.ZERO)) {
            case 1:
                // We did not dispense enough change
                this.sm.notifyAttendant("Not enough change was dispensed");
                yield false;
            case 0:
                // Dispensed enough change
                this.machine.getBanknoteOutput().dispense();
                yield true;
            case -1:
                // this should never actually happen
                this.sm.notifyAttendant("To much change was dispensed");
                this.machine.getBanknoteOutput().dispense();
                yield false;
            default:
                // I can't imagine this ever happening, I don't know why Java forces you to
                // include it.
                throw new IllegalArgumentException("Unexpected value: " + change.compareTo(BigDecimal.ZERO));
        };
    }

    @Override
    public SessionStatus getState() {
        return sm.getState();
    }

    @Override
    public void blockSession() {
        sm.blockSession();
    }

    @Override
    public void unblockSession() {
        sm.unblockSession();
    }

    @Override
    public void notifyPaid() {
        sm.notifyPaid();
    }

    @Override
    public void recordTransaction(CardData card, Long holdnumber, Double amount) {
        sm.recordTransaction(card, holdnumber, amount);
    }

    @Override
    public void printReceipt(PaymentType type, Card card) {
        // asserting arguments
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null.");
        }
        if (type == PaymentType.CARD && card == null) {
            throw new IllegalArgumentException("Card cannot be null for type CARD.");
        }
        if (sm.getCustomerPayment().compareTo(sm.getTotalPrice()) < 0) {
            throw new RuntimeException("Payment is less than total price.");
        }
        if (sm.getItems().isEmpty()) {
            throw new RuntimeException("Product list cannot be empty.");
        }

        // ensuring that the printer can print
        if (!sm.canPrint()) {
            // Notify the attendant and block the session
            sm.notifyAttendant("Machine could not print receipt, printer is empty.");
            sm.blockSession();
            return;
        }

        // printing the receipt
        try {
            printLine("----- Receipt -----\n");
            for (Pair<Item, Boolean> pair : sm.getItems()) {
                // destructuring
                Item item = pair.getKey();

                // printing the item
                if (item instanceof BarcodedItem) {
                    BarcodedProduct p = DatabaseHelper.get((BarcodedItem) item);
                    printLine("$" + p.getPrice() + "   " + p.getDescription() + "\n");
                    continue;
                }

                // printing the item
                if (item instanceof PLUCodedItem) {
                    PLUCodedProduct p = DatabaseHelper.get((PLUCodedItem) item);
                    printLine("$" + sm.priceOf((PLUCodedItem) item) + "   " + p.getDescription() + "\n");
                    continue;
                }

                // this should never happen because other functions would catch an illegal
                // product
                throw new IllegalArgumentException(
                        "cannot print information of product of type " + item.getClass().toString());
            }
            printLine("Payment Type: " + type.toString() + "\n");
            printLine("Price: $" + sm.getTotalPrice() + "\n");
            printLine("Payment: $" + sm.getCustomerPayment() + "\n");

            // adding card details
            if (type == PaymentType.CARD) {
                printLine("Card Holder: " + card.cardholder + "\n");
                printLine("Card Number: " + card.number + "\n");
                printLine("Kind of Card: " + card.kind + "\n");
            }
        } catch (EmptyDevice e) {
            // Notify the attendant and block the session
            sm.notifyAttendant("Machine could not print receipt in full. Printer is empty.");
            sm.blockSession();
        }

        // cutting the paper
        this.machine.getPrinter().cutPaper();
    }

    @Override
    public boolean isCardInserted() {
        return cardInserted;
    }

    @Override
    public void removeCard() {
        machine.getCardReader().remove();
    }

    protected void printLine(String s) throws EmptyDevice {
        for (int i = 0; i < s.length(); i++) {
            try {
                this.machine.getPrinter().print(s.charAt(i));
            } catch (OverloadedDevice e) {
                try {
                    this.machine.getPrinter().print('\n');
                } catch (OverloadedDevice e_2) {
                    // this should never happen
                    throw new RuntimeException("an unexpected error occurred.");
                }
            }
        }
    }

    @Override
    public void notifyAttendant(String reason) {
        sm.notifyAttendant(reason);
    }

    @Override
    public boolean isBlocked() {
        return sm.isBlocked();
    }

    @Override
    public boolean isUnblocked() {
        return sm.isUnblocked();
    }

    @Override
    public boolean isPaid() {
        return sm.isPaid();
    }

    @Override
    public boolean isDisabled() {
        return sm.isDisabled();
    }

    @Override
    public void reset() {
        payment = BigDecimal.ZERO;
    }
}
