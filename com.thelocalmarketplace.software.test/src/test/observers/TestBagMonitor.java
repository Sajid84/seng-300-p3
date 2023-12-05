// Liam Major            - 30223023
// Md Abu Sinan            - 30154627
// Ali Akbari            - 30171539
// Shaikh Sajid Mahmood    - 30182396
// Abdullah Ishtiaq        - 30153185
// Adefikayo Akande        - 30185937
// Alecxia Zaragoza        - 30150008
// Ana Laura Espinosa Garza - 30198679
// Anmol Bansal            - 30159559
// Emmanuel Trinidad    - 30172372
// Gurjit Samra            - 30172814
// Kelvin Jamila        - 30117164
// Kevlam Chundawat        - 30180662
// Logan Miszaniec        - 30156384
// Maleeha Siddiqui        - 30179762
// Michael Hoang        - 30123605
// Nezla Annaisha        - 30123223
// Nicholas MacKinnon    - 30172737
// Ohiomah Imohi        - 30187606
// Sheikh Falah Sheikh Hasan - 30175335
// Umer Rehman            - 30169819

package test.observers;

import com.jjjwelectronics.bag.IReusableBagDispenser;
import com.jjjwelectronics.bag.ReusableBagDispenserListener;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;

import managers.AttendantManager;
import observers.attendant.BagMonitor;
import stubbing.StubbedAttendantManager;
import stubbing.StubbedGrid;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TestBagMonitor {
	
    private StubbedAttendantManager am;
    private StubbedSystemManager sm;
    private IReusableBagDispenser BagDispenser;
    private BagMonitor bm;
    private ISelfCheckoutStation machine;


    @Before
    public void setUp() {
    	 // creating the stubs
        sm = new StubbedSystemManager();
        am = sm.amStub;

        // configuring the hardware
        StubbedStation.configure();

        // creating the hardware
        machine = new StubbedStation().machine;
        machine.plugIn(StubbedGrid.instance());

        // configuring the machine
        sm.configure(machine);

        // creating the observer
        bm = am.getBagMonitor();
    }

    @Test
    public void testBagDispensedNotification() {
        bm.aBagHasBeenDispensedByTheDispenser();

        // bag count should be zero, by dispensing it should be -1
        assertEquals(-1, am.getBagCount());
    }

    @Test
    public void testOutOfBagsNotification() {
        bm.theDispenserIsOutOfBags();
        assertFalse(am.hasBags());
    }

    @Test
    public void testBagsLoadedNotification() {
        int bagsLoadedCount = 5;
        bm.bagsHaveBeenLoadedIntoTheDispenser(bagsLoadedCount);

        assertEquals(5, am.getBagCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullManager() {
        new BagMonitor(null, BagDispenser);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullDispenser() {
        new BagMonitor(am, null);
    }
}
