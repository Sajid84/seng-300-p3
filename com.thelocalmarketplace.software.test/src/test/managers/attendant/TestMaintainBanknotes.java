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

package test.managers.attendant;

import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;
import stubbing.StubbedAttendantManager;
import stubbing.StubbedGrid;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMaintainBanknotes {
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
        sm = new StubbedSystemManager();
        sam = sm.amStub;

        // configuring the machine
        sm.configure(machine);
    }

    @Test
    public void testMaintainBanknoteStorage() throws CashOverloadException {
        Banknote fiveNote = new Banknote(Currency.getInstance(Locale.CANADA), new BigDecimal("5.00"));
        for (int i = 0; i < machine.getBanknoteStorage().getCapacity(); i++) {
            machine.getBanknoteStorage().load(fiveNote);
        }

        assertEquals(machine.getBanknoteStorage().getBanknoteCount(), machine.getBanknoteStorage().getCapacity());
        sam.maintainBanknoteStorage();
        assertEquals(0, machine.getBanknoteStorage().getBanknoteCount());
    }

    @Test
    public void testMaintainBanknoteDispenser() {
        for (BigDecimal denom : machine.getBanknoteDenominations()) {
            IBanknoteDispenser dispenser = machine.getBanknoteDispensers().get(denom);
            dispenser.unload();
            assertEquals(0, dispenser.size());
        }

        sam.maintainBanknoteDispensers();

        for (BigDecimal denom : machine.getBanknoteDenominations()) {
            IBanknoteDispenser dispenser = machine.getBanknoteDispensers().get(denom);
            assertTrue(dispenser.size() > 0);
        }
    }
}


