// Liam Major 30223023
// Katelan Ng 30144672
// Coverage: 94.6%

package test.observers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import observers.order.ScaleObserver;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestScaleObserver {

    // vars
    private StubbedOrderManager om;
    private StubbedSystemManager sm;
    private ScaleObserver so;
    private ISelfCheckoutStation machine;

    @Before
    public void setup() {
        // creating the stubs
        sm = new StubbedSystemManager();
        om = sm.omStub;

        // configuring the hardware
        StubbedStation.configure();

        // creating the hardware
        machine = new StubbedStation().machine;
        machine.plugIn(StubbedGrid.instance());

        // configuring the machine
        sm.configure(machine);

        // creating the ScaleObserver instance
        so = om.getBaggingAreaObserver();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullOrderManager() {
        // Attempting to create ScaleObserver with a null OrderManager should throw an IllegalArgumentException
        new ScaleObserver(null, machine.getBaggingArea());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDevice() {
        // Attempting to create ScaleObserver with a null device should throw an IllegalArgumentException
        new ScaleObserver(om, null);
    }

    /**
     * Testing that the mass observed by the scale was passed correctly to the
     * OrderManager.
     */
    @Test
    public void testNotifyOrderManagerMassChange() {
        Mass mass = new Mass(1);

        // Simulating the mass change event on the scale (bagging area)
        so.theMassOnTheScaleHasChanged(null, mass);

        // Ensure that the actual weight in the OrderManager has updated
        assertEquals(mass.inGrams(), om.getActualWeight());
    }
}
