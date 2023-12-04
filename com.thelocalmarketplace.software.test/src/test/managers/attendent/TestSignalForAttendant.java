package test.managers.attendent;

import com.tdc.DisabledException;
import com.tdc.CashOverloadException;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import managers.SystemManager;
import managers.AttendantManager;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import stubbing.*;

public class TestSignalForAttendant {
    private StubbedStation station;
    private ISelfCheckoutStation machine;
    private StubbedSystemManager sm;
    private AttendantManager attendantManager;

    @Before
    public void setup() {
        // Configuring the hardware
        StubbedStation.configure();

        // Creating the hardware
        machine = new StubbedStation().machine;
        machine.plugIn(StubbedGrid.instance());
        machine.turnOn();

        // Creating the stubs
        sm = new StubbedSystemManager();
        
        // Configuring the machine
        sm.configure(machine);

        // Creating AttendantManager with stubbed SystemManager
        attendantManager = new AttendantManager(sm);
        attendantManager.configure(machine);
    }

    @Test
    public void testSignalForAttendant() {
        try {
            attendantManager.signalForAttendant();  
            //Ensures the method call does not throw an exception
            assertTrue(true);
        } catch (Exception e) {
            fail("signalForAttendant method should not throw an exception.");
        }
    }

    @Test
    public void testNotifyAttendant() {
        String testReason = "Test Reason for Notification";
        try {
            attendantManager.notifyAttendant(testReason);
            assertTrue(true);
        } catch (Exception e) {
            fail("notifyAttendant method should not throw an exception.");
        }
    }
}
