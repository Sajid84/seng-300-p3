package managers.interfaces;

import com.jjjwelectronics.Item;
import managers.enums.SessionStatus;

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

}
