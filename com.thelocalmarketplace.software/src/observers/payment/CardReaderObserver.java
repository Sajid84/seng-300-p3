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
	public void theDataFromACardHasBeenRead(CardData data) {
		this.ref.notifyCardDataRead(data);

	}

	@Override
	public void aCardHasBeenSwiped() {
		// do nothing here
	}

	@Override
	public void aCardHasBeenInserted() {
		ref.notifyCardInserted(true);
	}

	@Override
	public void theCardHasBeenRemoved() {
		ref.notifyCardInserted(false);
	}

	@Override
	public void aCardHasBeenTapped() {
		// do nothing here
	}

}
