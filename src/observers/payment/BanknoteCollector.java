// Liam Major 30223023
// Jason Very 30222040

// Package declaration for observers related to payment
package observers.payment;

import java.math.BigDecimal;
import java.util.Currency;

import com.tdc.banknote.BanknoteValidator;
import com.tdc.banknote.BanknoteValidatorObserver;

import managers.PaymentManager;
import observers.AbstractComponentObserver;

/**
 * This class represents an observer for a banknote collector device. It extends
 * AbstractComponentObserver and implements the BanknoteValidatorObserver
 * interface.
 */
public class BanknoteCollector extends AbstractComponentObserver implements BanknoteValidatorObserver {

    // Reference to the PaymentManager
    private PaymentManager ref;

    /**
     * Constructor for BanknoteCollector.
     * 
     * @param paymentManager The PaymentManager associated with the observer. It
     *                       cannot be null.
     * @param device          The BanknoteValidator device being observed. It cannot
     *                       be null.
     * @throws IllegalArgumentException if either the PaymentManager or the
     *                                  BanknoteValidator device is null.
     */
    public BanknoteCollector(PaymentManager paymentManager, BanknoteValidator device) {
        super(device);

        // Checking for null PaymentManager
        if (paymentManager == null) {
            throw new IllegalArgumentException("PaymentManager cannot be null.");
        }

        // Assigning references and attaching the observer to the device
        this.ref = paymentManager;
        device.attach(this);
    }

    /**
     * This method is called when a valid banknote is detected by the observed
     * BanknoteValidator. It notifies the associated PaymentManager about the added
     * balance.
     * 
     * @param validator The BanknoteValidator device that detected the valid
     *                  banknote.
     * @param currency  The currency of the valid banknote.
     * @param value     The value of the valid banknote.
     */
    @Override
    public void goodBanknote(BanknoteValidator validator, Currency currency, BigDecimal value) {
        // Notify the PaymentManager about the added balance
        this.ref.notifyBalanceAdded(value);
    }

    /**
     * This method is called when an invalid banknote is detected by the observed
     * BanknoteValidator. The implementation is left empty as handling of invalid
     * banknotes is handled in the "Hardware".
     * 
     * @param validator The BanknoteValidator device that detected the invalid
     *                  banknote.
     */
    @Override
    public void badBanknote(BanknoteValidator validator) {
        // Left empty as handling of invalid banknotes is handled in the "Hardware"
    }

}
