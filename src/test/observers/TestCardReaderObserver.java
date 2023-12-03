package test.observers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import observers.payment.CardReaderObserver;
import stubbing.StubbedCardData;
import stubbing.StubbedGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestCardReaderObserver {
    // vars
    private StubbedPaymentManager pm;
    private StubbedSystemManager sm;
    private CardReaderObserver cro;
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

        // creating the observer
        cro = pm.getCardReaderObserver();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPaymentManager() {
        // Attempting to create CardReaderObserver with a null PaymentManager should throw an IllegalArgumentException
        new CardReaderObserver(null, machine.getCardReader());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDevice() {
        // Attempting to create CardReaderObserver with a null device should throw an IllegalArgumentException
        new CardReaderObserver(pm, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotifyCardSwipeNullData() {
        // Attempting to notify the observer about a card swipe with null data should throw an IllegalArgumentException
        cro.theDataFromACardHasBeenRead(null);
    }

    @Test
    public void testNotifyCardSwipeValidData() {
        // This test should not throw an exception
        // Simulates notifying the observer about a card swipe with valid data
        cro.theDataFromACardHasBeenRead(new StubbedCardData());
    }
}
