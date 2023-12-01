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

	//var
	private boolean cardSwiped = false;
	private boolean cardTapped = false;

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
		cardSwiped = true;
	}

	@Override
	public void theDataFromACardHasBeenRead(CardData data) {
		if (cardSwiped) {
			this.ref.notifyCardSwipe(data);
			cardSwiped = false;
		}
		else if (cardTapped){
			this.ref.notifyCardTap(data);
			cardTapped = false;
		}
	}

	@Override
	public void aCardHasBeenInserted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void theCardHasBeenRemoved() {
		// TODO Auto-generated method stub

	}

	@Override
	public void aCardHasBeenTapped() {
		cardTapped = false;
	}

}
