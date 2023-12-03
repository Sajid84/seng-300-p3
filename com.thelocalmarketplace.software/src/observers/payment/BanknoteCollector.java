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

import java.math.BigDecimal;
import java.util.Currency;

import com.tdc.banknote.BanknoteValidator;
import com.tdc.banknote.BanknoteValidatorObserver;

import managers.PaymentManager;
import observers.AbstractComponentObserver;

public class BanknoteCollector extends AbstractComponentObserver implements BanknoteValidatorObserver {

	// object references
	private PaymentManager ref;

	// Creates and observer to listen to the events emitted by a
	// BanknoteValidator. Cannot be null
	public BanknoteCollector(PaymentManager paymentManager, BanknoteValidator device) {
		super(device);
		
		if (paymentManager == null) {
			throw new IllegalArgumentException("PaymentManager cannot be null.");
		}

		this.ref = paymentManager;
		device.attach(this);
	}

	// Listens for valid Bank note
	@Override
	public void goodBanknote(BanknoteValidator validator, Currency currency, BigDecimal value) {
		this.ref.notifyBalanceAdded(value);
	}

	// listens for invalid bank note
	// Unsure what to implement as its handled in
	// "Hardware"
	@Override
	public void badBanknote(BanknoteValidator validator) {

	}

}
