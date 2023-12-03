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

import com.tdc.coin.Coin;
import com.tdc.coin.CoinValidator;
import com.tdc.coin.CoinValidatorObserver;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import driver.Driver;
import managers.PaymentManager;
import observers.AbstractComponentObserver;

/**
 * This is the "Pay via Coin" use case.
 */

/**
 * <p>
 * This class is an observer that listens to the events emitted by a
 * {@link CoinValidator} object. The reason being is that there is no other way
 * to access the coins or cash inputed into the system from the
 * {@code SelfCheckoutStation}.
 * </p>
 * 
 * <p>
 * This class does nothing but listen for the {@link Coin}s emitted by a
 * {@link CoinValidator}.
 * </p>
 * 
 * @see CoinValidator
 * @see CoinValidatorObserver
 * @see Driver
 */
public class CoinCollector extends AbstractComponentObserver implements CoinValidatorObserver {

	// object references
	private PaymentManager ref;

	/**
	 * Creates an observer to listen to the events emitted by a
	 * {@link CoinValidator}.
	 */
	public CoinCollector(PaymentManager pm, CoinValidator device) {
		super(device);

		// checking for null
		if (pm == null) {
			throw new IllegalArgumentException("PaymentManager cannot be null.");
		}

		this.ref = pm;
		device.attach(this);
	}

	/**
	 * Listens for the `validCoinDetected` event from a {@link CoinValidator} so
	 * that this object can infer the amount of coins or cash inputed into the
	 * {@link AbstractSelfCheckoutStation} and therefore its balance.
	 * 
	 * @see CoinValidator
	 * @see CoinValidatorObserver
	 */
	@Override
	public void validCoinDetected(CoinValidator validator, BigDecimal value) {
		this.ref.notifyBalanceAdded(value);
	}

	@Override
	public void invalidCoinDetected(CoinValidator validator) {
		// TODO notify the parent object to dispense the invalid coin
	}

}
