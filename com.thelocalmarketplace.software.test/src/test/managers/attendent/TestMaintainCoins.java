//Sheikh Falah Sheikh Hasan - 30175335

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;
import stubbing.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestMaintainCoins {
    private StubbedStation station;
    private ISelfCheckoutStation machine;
    private StubbedSystemManager sm;
    private StubbedPaymentManager pm;
    private StubbedAttendantManager sam;

    @Before
    public void setup() {
        // configuring the hardware
        StubbedStation.configure();

        // creating the hardware
        machine = new StubbedStation().machine;
        machine.plugIn(StubbedGrid.instance());
        machine.turnOn();

        // creating the stubs
        sm = new StubbedSystemManager(BigDecimal.ZERO);
        sam = new StubbedAttendantManager(sm);
        sm.configure(machine);


        // configuring the machine
        sm.configure(machine);
    }

    @Test
    public void testMaintainCoinStorage() throws DisabledException, CashOverloadException {
        assertTrue(machine.getCoinStorage().hasSpace());
        Coin fiveCent = new Coin(Currency.getInstance(Locale.CANADA), new BigDecimal(0.05));

        for(int i=0; i < this.machine.getCoinStorage().getCapacity(); i++) {
            this.machine.getCoinStorage().load(fiveCent);
        }
        assertFalse(machine.getCoinStorage().hasSpace());
    }

    @Test
    public void testMaintainCoinDispensers() {

    }


}
