// Liam Major 30223023
// Sheikh Falah Sheikh Hasan - 30175335

package managers.interfaces;

import java.math.BigDecimal;

import com.jjjwelectronics.card.Card.CardData;

/**
 * This interface is used by any object that {@link IPaymentManager} owns, this
 * exists for the child objects to communicate with their parent object without
 * the super class SystemManager being involved does it have to implement these
 * methods
 */
public interface IPaymentManagerNotify {

	/**
	 * This method notifies {@link IPaymentManager that a card has been read.
	 *
	 * @param cardData the data of the read card
	 */
	void notifyCardDataRead(CardData cardData);

	/**
	 * This method notifies the {@link IPaymentManager} that a child object has
	 * received some payment, either from a banknote or from a coin.
	 * 
	 * @param value the value of the inputted banknote or coin
	 */
	void notifyBalanceAdded(BigDecimal value);
}
