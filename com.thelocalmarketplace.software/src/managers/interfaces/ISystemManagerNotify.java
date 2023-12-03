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
