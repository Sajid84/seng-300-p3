package managers.interfaces;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import enums.SessionStatus;

import java.math.BigDecimal;

public interface ISystemManagerNotify {

    /**
     * This notifies all listeners that an item was added to the order.
     *
     * @param item the item added
     */
    void notifyItemAdded(Item item);

    /**
     * This notifies all listeners that an item was removed from the order.
     *
     * @param item the item removed
     */
    void notifyItemRemoved(Item item);

    /**
     * This notifies all listeners that the state of the system manager has changed.
     *
     * @param state the new state of the system manager
     */
    void notifyStateChange(SessionStatus state);

    /**
     * This tells all listeners to refresh their contents, views, etc.
     */
    void notifyRefresh();

    /**
     * This notifies all listeners that some form of payment was added.
     *
     * @param value the value of the payment
     */
    void notifyPaymentAdded(BigDecimal value);

    /**
     * This notifies all listeners that the payment window has closed.
     */
    void notifyPaymentWindowClosed();

    /**
     * This notifies all listeners that there was an issue reading data from the inserted card.
     */
    void notifyInvalidCardRead(Card card);

}
