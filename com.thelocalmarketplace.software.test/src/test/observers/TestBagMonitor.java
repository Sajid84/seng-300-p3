package test.observers;

import com.jjjwelectronics.bag.IReusableBagDispenser;
import com.jjjwelectronics.bag.ReusableBagDispenserListener;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;

import managers.AttendantManager;
import observers.attendant.BagMonitor;
import stubbing.StubbedAttendantManager;
import stubbing.StubbedGrid;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class TestBagMonitor {
	
    private StubbedAttendantManager am;
    private StubbedSystemManager sm;
    private IReusableBagDispenser BagDispenser;
    private BagMonitor bm;
    private ISelfCheckoutStation machine;


    @Before
    public void setUp() {
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

        // creating the observer
        bm = am.getBagMonitor();
    }

    @Test
    public void testBagDispensedNotification() {
        bm.aBagHasBeenDispensedByTheDispenser();

        assertTrue(am.notifyBagDispensed());
    }

    @Test
    public void testOutOfBagsNotification() {
        bm.theDispenserIsOutOfBags();
        assertTrue(am.notifyBagsEmpty());
    }

    @Test
    public void testBagsLoadedNotification() {
        int bagsLoadedCount = 5;
        bm.bagsHaveBeenLoadedIntoTheDispenser(bagsLoadedCount);

        assertTrue(am.notifyBagsLoaded(bagsLoadedCount));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullManager() {
        new BagMonitor(null, BagDispenser);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullDispenser() {
        new BagMonitor(am, null);
    }

    @Test
    public void testDeviceObserverMethods() {
        // You may want to add more detailed tests for other methods in AbstractDeviceObserver
        bm.aDeviceHasBeenTurnedOn(BagDispenser);
        bm.aDeviceHasBeenTurnedOff(BagDispenser);

        // Ensure that the methods in AbstractDeviceObserver are called without exceptions
    }
}
