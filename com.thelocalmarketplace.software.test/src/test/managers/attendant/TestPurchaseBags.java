package test.managers.attendant;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.OverloadedDevice;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;

import managers.AttendantManager;
import managers.SystemManager;
import stubbing.StubbedAttendantManager;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assertions.*;

public class TestPurchaseBags {

	private ISelfCheckoutStation machine;
    
    private StubbedAttendantManager am;
	private StubbedSystemManager sm;

    @Before
    public void setUp() {
    	
    	StubbedStation.configure();
    		
   		machine = new StubbedStation().machine;
    	machine.plugIn(StubbedGrid.instance());
   		machine.turnOn();
    		
   		sm = new StubbedSystemManager(BigDecimal.ZERO);
   		am = sm.amStub;
    	
    	sm.configure(machine);
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
        assertFalse(am.hasBags());
    }

    @Test
    public void testNotifyBagsEmpty() {
        am.notifyBagsEmpty();
        assertTrue(am.hasBags());
    }


    @Test
    public void testRequestPurchaseBags() {
        am.setBagCount(5);
        am.requestPurchaseBags(3);
        assertEquals(2, am.getBagCount());
    }
    
    @Test
    public void testMaintainBagsWhenBagsEmpty() {
        am.notifyBagsEmpty();
        am.maintainBags();

        assertEquals(5, am.getBagCount()); 
        assertFalse(am.hasBags());
        assertFalse(am.isBagsLow());
    }

    @Test
    public void testMaintainBagsWhenBagsLow() {
        am.notifyBagsLoaded(2);
        am.maintainBags();

        assertEquals(2, am.getBagCount());
        assertFalse(am.hasBags());
        assertFalse(am.isBagsLow());
    }

    @Test
    public void testMaintainBagsWhenNormalBags() {
        am.maintainBags();

        assertEquals(10, am.getBagCount());
        assertFalse(am.hasBags());
        assertFalse(am.isBagsLow());
    }

    @Test(expected = OverloadedDevice.class)
    public void testMaintainBagsWhenOverloadedDeviceException() {
        am.maintainBags();

        assertEquals(10, am.getBagCount());
        am.notifyAttendant("Adding bags to the dispenser caused an overload.");
        am.signalForAttendant();
    }
    
}