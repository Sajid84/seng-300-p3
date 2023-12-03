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

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import observers.payment.CoinCollector;
import stubbing.StubbedGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestCoinCollector {

    // vars
    private StubbedPaymentManager pm;
    private StubbedSystemManager sm;
    private CoinCollector cc;
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

        cc = pm.getCoinCollector();
        machine.getCoinValidator().disable(); // the component is enabled by default, OK
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPaymentManager() {
        new CoinCollector(null, machine.getCoinValidator());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDevice() {
        new CoinCollector(pm, null);
    }

    /***
     * Testing when the system detects a new valid coin has been added
     */
    @Test
    public void validCoinDetectedTest() {
        // We don't care who the validator is, so it may be null.
        cc.validCoinDetected(null, new BigDecimal(2.0f));

        // We expect the PaymentManager to know about an coin of value 3
        assertTrue("The Payment Manager did not recieve the correct coin value",
                pm.notifyBalanceAddedValue.floatValue() == 2.0f);
    }
}
