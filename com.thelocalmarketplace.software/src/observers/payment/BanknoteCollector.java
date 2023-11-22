// Liam Major 30223023
// Jason Very 30222040

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
