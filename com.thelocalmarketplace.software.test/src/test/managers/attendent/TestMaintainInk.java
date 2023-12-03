//Sheikh Falah Sheikh Hasan - 30175335

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import managers.AttendantManager;
import org.junit.Before;
import org.junit.Test;
import stubbing.*;

import java.math.BigDecimal;

public class TestMaintainInk {

    private StubbedStation station;
    private ISelfCheckoutStation machine;
    private StubbedSystemManager sm;
    private StubbedAttendantManager sam;

    @Before
    public void setup(){
        // configuring the hardware
        StubbedStation.configure();

        // creating the hardware
        machine = new StubbedStation().machine;
        machine.plugIn(StubbedGrid.instance());
        machine.turnOn();

        // creating the stubs
        sm = new StubbedSystemManager(BigDecimal.ZERO);
        sam  = new StubbedAttendantManager(sm);
        // configuring the machine
        sm.configure(machine);

    }

    @Test
    public void testInkLevelLow() {
        assertFalse(sam.isInkLow());
        sam.maintainInk();
        assertFalse(sam.getHasInk());
    }

}
