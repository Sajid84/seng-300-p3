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

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import observers.attendant.ReceiptPrinterObserver;
import stubbing.*;

public class TestReceiptPrinterObserver {
    // vars
    private StubbedAttendantManager am;
    private StubbedSystemManager sm;
    private ReceiptPrinterObserver rpls;
    private ISelfCheckoutStation machine;

    @Before
    public void setup() {
        // creating the stubs
        sm = new StubbedSystemManager();
        am = sm.amStub;

        // configuring the hardware
        StubbedStation.configure();

        // creating the hardware
        machine = new StubbedStation().machine;
        machine.plugIn(StubbedGrid.instance());

        // configuring the machine
        sm.configure(machine);

        rpls = am.getReceiptPrinterObserver();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPaymentManager() {
        new ReceiptPrinterObserver(null, machine.getPrinter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDevice() {
        new ReceiptPrinterObserver(am, null);
    }

    @Test
    public void testNotifyOutOfPaper() {
        rpls.thePrinterIsOutOfPaper();

        // asserting
        assertFalse(am.getHasPaper());
    }

    @Test
    public void testNotifyPaperAdded() {
        rpls.paperHasBeenAddedToThePrinter();

        // asserting
        assertTrue(am.getHasPaper());
    }

    @Test
    public void testNotifyOutOfInk() {
        rpls.thePrinterIsOutOfInk();

        // asserting
        assertFalse(am.getHasInk());
    }

    @Test
    public void testNotifyInkAdded() {
        rpls.inkHasBeenAddedToThePrinter();

        // asserting
        assertTrue(am.getHasInk());
    }
}
