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
package test.managers.system;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;
import managers.enums.SessionStatus;

public class TestDisableAndEnableStation {
	
    private StubbedSystemManager sm;

    @Before
    public void setup() {
        StubbedStation.configure();
        sm = new StubbedSystemManager();
        sm.configure(new StubbedStation().machine);
    }
    
    @Test
    public void testBlockSession() {
        sm.blockSession();
        assertEquals(SessionStatus.BLOCKED, sm.getState());
        assertTrue(sm.notifyAttendantCalled);
        assertEquals("Session was blocked.", sm.getAttendantNotification());
    }

    @Test
    public void testUnblockSessionNormal() {
        sm.setState(SessionStatus.BLOCKED);
        sm.unblockSession();
        assertEquals(SessionStatus.NORMAL, sm.getState());
    }

    @Test(expected = IllegalStateException.class)
    public void testUnblockSessionWhenPaidOrDisabled() {
        sm.setState(SessionStatus.PAID); 
        sm.unblockSession();
    }

    @Test
    public void testIsBlockedAndIsUnblocked() {
        sm.setState(SessionStatus.BLOCKED);
        assertTrue(sm.isBlocked());
        assertFalse(sm.isUnblocked());

        sm.setState(SessionStatus.NORMAL);
        assertFalse(sm.isBlocked());
        assertTrue(sm.isUnblocked());
    }

    @Test
    public void testDisableMachine() {
        sm.requestDisableMachine();
        sm.disableMachine();
        assertEquals(SessionStatus.DISABLED, sm.getState());
        assertTrue(sm.notifyAttendantCalled);
        assertEquals("Machine was disabled", sm.getAttendantNotification());
    }

    @Test(expected = IllegalStateException.class)
    public void testEnableMachineWhenNotDisabled() {
        sm.enableMachine();
    }

    @Test
    public void testEnableMachine() {
        sm.requestDisableMachine();
        sm.disableMachine();
        sm.requestEnableMachine();
        sm.enableMachine();
        assertEquals(SessionStatus.NORMAL, sm.getState());
        assertTrue(sm.notifyAttendantCalled);
        assertEquals("Machine was enabled", sm.getAttendantNotification());
    }
    
    @Test
    public void testRequestDisableMachine() {
        sm.requestDisableMachine();
        sm.disableMachine();
        assertEquals(SessionStatus.DISABLED, sm.getState());
    }

    
    @Test
    public void testRequestEnableMachine() {
        sm.requestDisableMachine();
        sm.disableMachine();
        assertEquals(SessionStatus.DISABLED, sm.getState());

        sm.requestEnableMachine();
        try {
            sm.enableMachine();
            assertEquals(SessionStatus.NORMAL, sm.getState());
        } catch (IllegalStateException e) {
            fail("Machine should have been enabled successfully");
        }
    }

    @Test
    public void testIsDisabled() {
        sm.setState(SessionStatus.DISABLED);
        assertTrue(sm.isDisabled());

        sm.setState(SessionStatus.NORMAL);
        assertFalse(sm.isDisabled());
    }


}
