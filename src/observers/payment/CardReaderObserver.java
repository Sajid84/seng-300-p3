// Liam Major 30223023

// Package declaration for observers related to payment
package observers.payment;

import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.CardReaderListener;
import com.jjjwelectronics.card.ICardReader;

import managers.PaymentManager;
import observers.AbstractDeviceObserver;

/**
 * This class represents an observer for a card reader device. It extends
 * AbstractDeviceObserver and implements the CardReaderListener interface.
 */
public class CardReaderObserver extends AbstractDeviceObserver implements CardReaderListener {

    // Reference to the PaymentManager
    private PaymentManager ref;

    /**
     * Constructor for CardReaderObserver.
     * 
     * @param pm     The PaymentManager associated with the observer. It cannot be
     *               null.
     * @param device The ICardReader device being observed. It cannot be null.
     * @throws IllegalArgumentException if either the PaymentManager or the
     *                                  ICardReader device is null.
     */
    public CardReaderObserver(PaymentManager pm, ICardReader device) {
        super(device);

        // Checking for null PaymentManager
        if (pm == null) {
            throw new IllegalArgumentException("PaymentManager cannot be null.");
        }

        // Assigning references and registering the observer with the device
        this.ref = pm;
        device.register(this);
    }

    /**
     * This method is called when a card has been swiped, but its implementation is
     * left empty as the specific action for card swiping is not specified.
     */
    @Override
    public void aCardHasBeenSwiped() {
        // Empty implementation as the specific action for card swiping is not specified
    }

    /**
     * This method is called when data from a card has been read. It notifies the
     * associated PaymentManager about the card swipe and provides the card data.
     * 
     * @param data The CardData object containing information read from the card.
     */
    @Override
    public void theDataFromACardHasBeenRead(CardData data) {
        // Notify the PaymentManager about the card swipe and provide the card data
        this.ref.notifyCardSwipe(data);
    }

    // The following methods are left unimplemented as their specific actions are not specified.

    /**
     * This method is called when a card has been inserted, but its implementation
     * is not specified.
     */
    @Override
    public void aCardHasBeenInserted() {
        // TODO: Auto-generated method stub
    }

    /**
     * This method is called when the card has been removed, but its implementation
     * is not specified.
     */
    @Override
    public void theCardHasBeenRemoved() {
        // TODO: Auto-generated method stub
    }

    /**
     * This method is called when a card has been tapped, but its implementation is
     * not specified.
     */
    @Override
    public void aCardHasBeenTapped() {
        // TODO: Auto-generated method stub
    }

}
