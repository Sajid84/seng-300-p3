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

package test.managers.order;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import com.jjjwelectronics.scanner.BarcodedItem;
import org.junit.Before;
import org.junit.Test;

import enums.SessionStatus;
import stubbing.StubbedBarcodedItem;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedOrderManager;
import stubbing.StubbedSystemManager;
import utils.DatabaseHelper;

public class TestNotifyMassChanged {
	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;
	}

	//Test if the notifyMassChanged throws an expection if null.
	@Test(expected = IllegalArgumentException.class)
	public void testNotifyMassChangeThrowsOnNull() {
		om.notifyMassChanged(null, null);
	}
 
	@Test
	public void testNotifyMassChangeThrowsBlocks() {
		om.notifyMassChanged(null, BigDecimal.ONE);

		// station should be blocked now
		assertEquals(SessionStatus.BLOCKED, om.getState());
	}

	@Test
	public void testNotifyMassChangeWithAdjustmentDoesntBlock() {
		BarcodedItem item = DatabaseHelper.createWeightDiscrepancy();

		// adding the item and setting the adjustment
		om.addItem(item);
		om.setWeightAdjustment(BigDecimal.valueOf(DatabaseHelper.get(item).getExpectedWeight()).negate());

		// testing the adjustment
		om.notifyMassChanged(null, BigDecimal.ZERO);

		System.out.println("Expected Weight: " + om.getExpectedMass());
		System.out.println("Actual Weight: " + om.getActualWeight());
		System.out.println("Adjustment: " + om.getWeightAdjustment());

		// station should still be normal
		assertEquals(SessionStatus.NORMAL, om.getState());
	}

	@Test
	public void testCheckWeightDifferenceTriggersOnNormal() {
		//setup
		om.setState(SessionStatus.NORMAL);

		om.checkWeightDifference(BigDecimal.ONE);

		// the OrderManager should be blocked now
		assertEquals(SessionStatus.BLOCKED, om.getState());
	}

	@Test
	public void testCheckWeightDifferenceDoesntTriggerOnNormal() {
		//setup
		om.setState(SessionStatus.NORMAL);

		om.checkWeightDifference(BigDecimal.ZERO);

		// the OrderManager should still be normal
		assertEquals(om.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testCheckWeightDifferenceUnblocks() {
		//setup
		om.setState(SessionStatus.BLOCKED);

		om.checkWeightDifference(BigDecimal.ZERO);

		// the OrderManager should be unblocked now
		assertEquals(om.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testCheckWeightDifferenceDoesntUnblock() {
		//setup
		om.setState(SessionStatus.BLOCKED);

		om.checkWeightDifference(BigDecimal.ONE);

		// the OrderManager should still be blocked
		assertEquals(SessionStatus.BLOCKED, om.getState());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testNotifyMassChangedThrowsWhenPaid() {
		//setup
		om.setState(SessionStatus.PAID);
		
		//expect to throw an exception
		om.notifyMassChanged(null, BigDecimal.ONE);
	}
	
	//Testing expecting to throw an exception
	@Test(expected = IllegalStateException.class)
	public void testCheckWeightDifferenceThrowsWhenPaid() {
		om.setState(SessionStatus.PAID);
		
		om.checkWeightDifference(BigDecimal.ONE);
	}
}
