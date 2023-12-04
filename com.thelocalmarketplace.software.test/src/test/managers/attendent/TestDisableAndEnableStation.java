package test.managers.attendent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;

import managers.AttendantManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestDisableAndEnableStation {
    private StubbedStation station;
    private ISelfCheckoutStation machine;
    private StubbedSystemManager sm;
    private AttendantManager attendantManager;

    @Before
    public void setup() {
        // similar setup as before...
        sm = new StubbedSystemManager();
        attendantManager = new AttendantManager(sm);
        // additional setup as necessary...
    }
    
    @Test
    public void testRequestDisableMachine() {
        attendantManager.requestDisableMachine();
        assertTrue(sm.isDisabled());
    }
    
    @Test
    public void testRequestEnableMachine() {
        sm.requestDisableMachine(); 
        attendantManager.requestEnableMachine();
        assertFalse(sm.isDisabled());
    }

    
    @Test
    public void testIsDisabledWhenDisabled() {
        sm.requestDisableMachine();
        assertTrue(attendantManager.isDisabled());
    }

    @Test
    public void testIsDisabledWhenEnabled() {
        sm.requestEnableMachine();
        assertFalse(attendantManager.isDisabled());
    }


}
