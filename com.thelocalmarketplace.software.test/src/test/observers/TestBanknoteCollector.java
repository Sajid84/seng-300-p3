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
        new BanknoteCollector(null, machine.getBanknoteValidator());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDevice() {
        new BanknoteCollector(pm, null);
    }

    // Testing when the system detects a new valid bank note has been added
    @Test
    public void TestgoodBanknote() {
        Currency currency = Currency.getInstance("CAD");
        bc.goodBanknote(null, currency, new BigDecimal(5.00f));

        // We expect the PaymentManager to know about a bank note of value 5
        assertTrue("The Payment Manager did recieve the correct amount",
                pm.notifyBalanceAddedValue.floatValue() == 5.00f);
    }
}
