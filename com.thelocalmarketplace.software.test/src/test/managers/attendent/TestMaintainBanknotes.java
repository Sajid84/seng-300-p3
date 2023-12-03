//Sheikh Falah Sheikh Hasan - 30175335

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.IBanknoteDispenser;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import observers.attendant.BanknoteStorageMonitor;
import org.junit.Before;
import org.junit.Test;
import stubbing.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class TestMaintainBanknotes {
    private StubbedStation station;
    private ISelfCheckoutStation machine;
    private StubbedSystemManager sm;
    private StubbedPaymentManager pm;
    private StubbedAttendantManager sam;
    private IBanknoteDispenser bd;





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
        pm = sm.pmStub;


        // configuring the machine
        sm.configure(machine);

    }

    @Test
    public void testMaintainBanknoteStorage() throws DisabledException, CashOverloadException {
        assertTrue(machine.getBanknoteStorage().hasSpace());
        Banknote fiveNote = new Banknote(Currency.getInstance(Locale.CANADA), new BigDecimal(5.00));

        for(int i = 0; i < this.machine.getBanknoteStorage().getCapacity(); i++) {
            this.machine.getBanknoteStorage().load(fiveNote);
        }
        assertFalse(machine.getBanknoteStorage().hasSpace());
    }
}


