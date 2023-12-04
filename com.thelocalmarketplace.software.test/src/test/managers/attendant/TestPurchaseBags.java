package test.managers.attendant;

import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.bag.ReusableBag;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;
import stubbing.StubbedAttendantManager;
import stubbing.StubbedGrid;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TestPurchaseBags {

    private ISelfCheckoutStation machine;
    private StubbedAttendantManager am;
    private StubbedSystemManager sm;
    private int expected = 0;
    private int threshold = 0;


    @Before
    public void setUp() {
        StubbedStation.configure();

        machine = new StubbedStation().machine;
        machine.plugIn(StubbedGrid.instance());
        machine.turnOn();

        sm = new StubbedSystemManager(BigDecimal.ZERO);
        am = sm.amStub;

        sm.configure(machine);

        // setting the expected amount
        expected = (int) (machine.getReusableBagDispenser().getCapacity() * 0.5);
        threshold = (int) (machine.getReusableBagDispenser().getCapacity() * 0.1);
    }

    private void loadBagsIntoMachine(int count) throws OverloadedDevice {
        // forming the array
        ReusableBag[] bags = new ReusableBag[count];

        // populating it
        for (int i = 0; i < count; ++i) {
            bags[i] = new ReusableBag();
        }

        // filling the bag dispenser
        machine.getReusableBagDispenser().load(bags);
    }

    @Test
    public void testNotifyBagDispensed() {
        am.setBagCount(5);
        am.notifyBagDispensed();
        assertEquals(4, am.getBagCount());
    }

    @Test
    public void testNotifyBagsLoaded() {
        am.notifyBagsLoaded(10);
        assertEquals(10, am.getBagCount());
        assertTrue(am.hasBags());
    }

    @Test
    public void testNotifyBagsEmpty() {
        am.notifyBagsEmpty();
        assertFalse(am.hasBags());
    }


    @Test
    public void testRequestPurchaseBags() throws OverloadedDevice {
        // loading bags into the machine
        loadBagsIntoMachine(5);

        // requesting bags, this should update the internal count
        am.requestPurchaseBags(3);

        // asserting
        assertEquals(2, am.getBagCount());
    }

    @Test
    public void testMaintainBagsWhenBagsEmpty() {
        am.notifyBagsEmpty();
        am.maintainBags();

        // the bag count should be 50% of the bag dispenser's total capacity
        assertTrue(expected <= am.getBagCount());

        // asserting booleans
        assertTrue(am.hasBags());
        assertFalse(am.isBagsLow());
    }

    @Test
    public void testMaintainBagsWhenBagsLow() {
        // loading bags that are below the threshold to set bagsLow to true
        am.notifyBagsLoaded(threshold - 5);

        // asserting
        assertTrue(am.hasBags());
        assertTrue(am.isBagsLow());

        // this should insert bags into the machine
        am.maintainBags();

        // asserting
        assertEquals(expected + threshold - 5, am.getBagCount());
        assertTrue(am.hasBags());
        assertFalse(am.isBagsLow());
    }

    @Test
    public void testMaintainBagsWhenNormalBags() throws OverloadedDevice {
        loadBagsIntoMachine(threshold + 1);

        // this function should never actually execute
        am.maintainBags();

        // asserting
        assertEquals(threshold + 1, am.getBagCount());
        assertTrue(am.hasBags());
        assertFalse(am.isBagsLow());
    }

    @Test
    public void testCheckBagDispenserStateWhenRemainingBelowThreshold() throws OverloadedDevice {
        loadBagsIntoMachine(threshold - 2);

        // this function should never actually execute
        am.checkBagDispenserState();

        // asserting
        assertTrue(am.hasBags());
        assertTrue(am.isBagsLow());
    }

    @Test
    public void testCheckBagDispenserStateWhenRemainingAboveThreshold() throws OverloadedDevice {
        loadBagsIntoMachine(threshold + 2);

        // this function should never actually execute
        am.checkBagDispenserState();

        // asserting
        assertTrue(am.hasBags());
        assertFalse(am.isBagsLow());
    }
}