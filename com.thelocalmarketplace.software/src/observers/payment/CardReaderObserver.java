// Liam Major 30223023

package observers.payment;

import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.CardReaderListener;
import com.jjjwelectronics.card.ICardReader;

import managers.PaymentManager;
import observers.AbstractDeviceObserver;

public class CardReaderObserver extends AbstractDeviceObserver implements CardReaderListener {

	// object references
	private PaymentManager ref;

	public CardReaderObserver(PaymentManager pm, ICardReader device) {
		super(device);

		if (pm == null) {
			throw new IllegalArgumentException("PaymentManager cannot be null.");
		}

		this.ref = pm;
		device.register(this);
	}

	@Override
	public void aCardHasBeenSwiped() {
	}

	@Override
	public void theDataFromACardHasBeenRead(CardData data) {
		this.ref.notifyCardSwipe(data);
	}

}
