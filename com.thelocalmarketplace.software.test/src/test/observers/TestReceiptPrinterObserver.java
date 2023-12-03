// Liam Major 30223023
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
