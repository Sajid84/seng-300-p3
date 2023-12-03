import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import stubbing.StubbedAttendantManager;
import stubbing.StubbedGrid;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

import java.math.BigDecimal;

public class TestMaintainPaper {

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
    public void testPaperLevelLow() {
        assertFalse(sam.isPaperLow());
        sam.maintainPaper();
        assertFalse(sam.getHasPaper());


    }
}
