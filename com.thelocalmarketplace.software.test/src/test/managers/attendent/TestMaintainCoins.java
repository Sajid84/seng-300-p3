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

package test.managers.attendent;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;
import stubbing.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.*;

public class TestMaintainCoins {
    private ISelfCheckoutStation machine;
    private StubbedSystemManager sm;
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
        sam = sm.amStub;
        sm.configure(machine);


        // configuring the machine
        sm.configure(machine);
    }

    @Test
    public void testMaintainCoinStorage() throws DisabledException, CashOverloadException {
        Coin fiveCent = new Coin(Currency.getInstance(Locale.CANADA), new BigDecimal(0.05));
        for(int i = 0; i < machine.getCoinStorage().getCapacity(); i++) {
            machine.getCoinStorage().load(fiveCent);
        }
        assertEquals(machine.getCoinStorage().getCoinCount(), machine.getCoinStorage().getCapacity());
        sam.maintainCoinStorage();
        assertEquals(0, machine.getCoinStorage().getCoinCount());
    }

    @Test
    public void testMaintainCoinDispensers() {
        for (BigDecimal denom : machine.getCoinDenominations()) {
            ICoinDispenser dispenser = machine.getCoinDispensers().get(denom);
            dispenser.unload();
            assertEquals(0, dispenser.size());
        }
        sam.maintainCoinDispensers();

        for (BigDecimal denom : machine.getCoinDenominations()) {
            ICoinDispenser dispenser = machine.getCoinDispensers().get(denom);
            assertTrue(dispenser.size() > 0);
        }
    }


}
