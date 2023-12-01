// Liam Major 30223023
package test.observers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import observers.payment.ReceiptPrinterObserver;
import stubbing.StubbedGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestReceiptPrinterObserver {
    // vars
    private StubbedPaymentManager pm;
    private StubbedSystemManager sm;
    private ReceiptPrinterObserver rpls;
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

        rpls = pm.getReceiptPrinterObserver();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPaymentManager() {
        new ReceiptPrinterObserver(null, machine.getPrinter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDevice() {
        new ReceiptPrinterObserver(pm, null);
    }

    @Test
    public void testNotifyOutOfPaper() {
        rpls.thePrinterIsOutOfPaper();

        // asserting
        assertFalse(pm.getHasPaper());
    }

    @Test
    public void testNotifyPaperAdded() {
        rpls.paperHasBeenAddedToThePrinter();

        // asserting
        assertTrue(pm.getHasPaper());
    }

    @Test
    public void testNotifyOutOfInk() {
        rpls.thePrinterIsOutOfInk();

        // asserting
        assertFalse(pm.getHasInk());
    }

    @Test
    public void testNotifyInkAdded() {
        rpls.inkHasBeenAddedToThePrinter();

        // asserting
        assertTrue(pm.getHasInk());
    }
}
