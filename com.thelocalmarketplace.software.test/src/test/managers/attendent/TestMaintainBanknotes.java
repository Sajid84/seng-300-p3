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


