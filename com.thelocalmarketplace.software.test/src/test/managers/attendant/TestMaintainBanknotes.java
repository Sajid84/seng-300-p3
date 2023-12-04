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
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.bag.ReusableBag;
import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import stubbing.*;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class TestMaintainBanknotes {
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
        sm = new StubbedSystemManager();
        sam  = sm.amStub;

        // configuring the machine
        sm.configure(machine);
    }

    @Test
    public void testMaintainBanknoteStorage() throws CashOverloadException {
        Banknote fiveNote = new Banknote(Currency.getInstance(Locale.CANADA), new BigDecimal("5.00"));
        for(int i = 0; i < machine.getBanknoteStorage().getCapacity(); i++) {
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

    public static class TestPurchaseBags {

        private ISelfCheckoutStation machine;
        private StubbedAttendantManager am;
        private StubbedSystemManager sm;
        private int expected = 0;
        private int threshold = 0;


        @Before
        public void setUp() {
            StubbedStation.configure();

            machine = new StubbedStation().machine;
            machine.plugIn(StubbedGrid.instance());
            machine.turnOn();

            sm = new StubbedSystemManager(BigDecimal.ZERO);
            am = sm.amStub;

            sm.configure(machine);

            // setting the expected amount
            expected = (int) (machine.getReusableBagDispenser().getCapacity() * 0.5);
            threshold = (int) (machine.getReusableBagDispenser().getCapacity() * 0.1);
        }

        private void loadBagsIntoMachine(int count) throws OverloadedDevice {
            // forming the array
            ReusableBag[] bags = new ReusableBag[count];

            // populating it
            for (int i = 0; i < count; ++i) {
                bags[i] = new ReusableBag();
            }

            // filling the bag dispenser
            machine.getReusableBagDispenser().load(bags);
        }

        @Test
        public void testNotifyBagDispensed() {
            am.setBagCount(5);
            am.notifyBagDispensed();
            assertEquals(4, am.getBagCount());
        }

        @Test
        public void testNotifyBagsLoaded() {
            am.notifyBagsLoaded(10);
            assertEquals(10, am.getBagCount());
            assertTrue(am.hasBags());
        }

        @Test
        public void testNotifyBagsEmpty() {
            am.notifyBagsEmpty();
            assertFalse(am.hasBags());
        }


        @Test
        public void testRequestPurchaseBags() throws OverloadedDevice {
            // loading bags into the machine
            loadBagsIntoMachine(5);

            // requesting bags, this should update the internal count
            am.requestPurchaseBags(3);

            // asserting
            assertEquals(2, am.getBagCount());
        }

        @Test
        public void testMaintainBagsWhenBagsEmpty() {
            am.notifyBagsEmpty();
            am.maintainBags();

            // the bag count should be 50% of the bag dispenser's total capacity
            assertTrue(expected <= am.getBagCount());

            // asserting booleans
            assertTrue(am.hasBags());
            assertFalse(am.isBagsLow());
        }

        @Test
        public void testMaintainBagsWhenBagsLow() {
            // loading bags that are below the threshold to set bagsLow to true
            am.notifyBagsLoaded(threshold - 5);

            // asserting
            assertTrue(am.hasBags());
            assertTrue(am.isBagsLow());

            // this should insert bags into the machine
            am.maintainBags();

            // asserting
            assertEquals(expected + threshold - 5, am.getBagCount());
            assertTrue(am.hasBags());
            assertFalse(am.isBagsLow());
        }

        @Test
        public void testMaintainBagsWhenNormalBags() throws OverloadedDevice {
            loadBagsIntoMachine(threshold + 1);

            // this function should never actually execute
            am.maintainBags();

            // asserting
            assertEquals(threshold + 1, am.getBagCount());
            assertTrue(am.hasBags());
            assertFalse(am.isBagsLow());
        }

        @Test
        public void testCheckBagDispenserStateWhenRemainingBelowThreshold() throws OverloadedDevice {
            loadBagsIntoMachine(threshold - 2);

            // this function should never actually execute
            am.checkBagDispenserState();

            // asserting
            assertTrue(am.hasBags());
            assertTrue(am.isBagsLow());
        }

        @Test
        public void testCheckBagDispenserStateWhenRemainingAboveThreshold() throws OverloadedDevice {
            loadBagsIntoMachine(threshold + 2);

            // this function should never actually execute
            am.checkBagDispenserState();

            // asserting
            assertTrue(am.hasBags());
            assertFalse(am.isBagsLow());
        }
    }
}


