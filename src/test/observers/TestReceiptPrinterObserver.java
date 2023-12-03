// Liam Major 30223023

package test.observers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import observers.payment.ReceiptPrinterObserver;
import stubbing.StubbedAttendantManager;
import stubbing.StubbedSystemManager;
import stubbing.StubbedGrid;
import stubbing.StubbedStation;

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

        // creating the ReceiptPrinterObserver instance
        rpls = am.getReceiptPrinterObserver();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPaymentManager() {
        // Attempting to create ReceiptPrinterObserver with a null AttendantManager should throw an IllegalArgumentException
        new ReceiptPrinterObserver(null, machine.getPrinter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDevice() {
        // Attempting to create ReceiptPrinterObserver with a null device should throw an IllegalArgumentException
        new ReceiptPrinterObserver(am, null);
    }

    @Test
    public void testNotifyOutOfPaper() {
        // Simulating the event when the printer is out of paper
        rpls.thePrinterIsOutOfPaper();

        // Asserting that the AttendantManager's hasPaper state is false
        assertFalse(am.getHasPaper());
    }

    @Test
    public void testNotifyPaperAdded() {
        // Simulating the event when paper is added to the printer
        rpls.paperHasBeenAddedToThePrinter();

        // Asserting that the AttendantManager's hasPaper state is true
        assertTrue(am.getHasPaper());
    }

    @Test
    public void testNotifyOutOfInk() {
        // Simulating the event when the printer is out of ink
        rpls.thePrinterIsOutOfInk();

        // Asserting that the AttendantManager's hasInk state is false
        assertFalse(am.getHasInk());
    }

    @Test
    public void testNotifyInkAdded() {
        // Simulating the event when ink is added to the printer
        rpls.inkHasBeenAddedToThePrinter();

        // Asserting that the AttendantManager's hasInk state is true
        assertTrue(am.getHasInk());
    }
}
