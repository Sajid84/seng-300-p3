// Liam Major 30223023
// Jason Very 30222040

package test.observers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import observers.payment.BanknoteCollector;
import stubbing.StubbedGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestBanknoteCollector {

    private StubbedPaymentManager pm;
    private BanknoteCollector bc;
    private StubbedSystemManager sm;
    private ISelfCheckoutStation machine;

    @Before
    public void setup() {
        // creating the stubs
        sm = new StubbedSystemManager();
        pm = sm.pmStub;

        // configuring the hardware
        StubbedStation.configure();

        // creating the hardware
        machine = new StubbedStation().machine;
        machine.plugIn(StubbedGrid.instance());

        // configuring the machine
        sm.configure(machine);

        bc = pm.getBanknoteCollector();
        machine.getBanknoteValidator().disable(); // the component is enabled by default, OK
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPaymentManager() {
        // Attempting to create BanknoteCollector with a null PaymentManager should throw an IllegalArgumentException
        new BanknoteCollector(null, machine.getBanknoteValidator());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDevice() {
        // Attempting to create BanknoteCollector with a null device should throw an IllegalArgumentException
        new BanknoteCollector(pm, null);
    }

    // Testing when the system detects a new valid banknote has been added
    @Test
    public void TestgoodBanknote() {
        Currency currency = Currency.getInstance("CAD");

        // Calling the goodBanknote method on BanknoteCollector with null arguments (for testing purposes)
        bc.goodBanknote(null, currency, new BigDecimal(5.00f));

        // Verifying that the PaymentManager has received the correct amount
        assertTrue("The Payment Manager did receive the correct amount",
                pm.notifyBalanceAddedValue.floatValue() == 5.00f);
    }
}
