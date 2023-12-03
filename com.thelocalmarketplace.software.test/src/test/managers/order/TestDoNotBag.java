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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.naming.OperationNotSupportedException;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.ScanType;
import managers.enums.SessionStatus;
import stubbing.StubbedGrid;
import stubbing.StubbedItem;
import stubbing.StubbedOrderManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;
import utils.DatabaseHelper;

public class TestDoNotBag {
	// machine
	private ISelfCheckoutStation machine;

	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;

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
		om = sm.omStub;

		// configuring the machine
		sm.configure(machine);
	}

	@Test
	public void testDoNotBagRequestSetsFlag() {
		Item item = new StubbedItem(2);
		om.doNotBagRequest(true);

		// check if the flag for no bagging request is set to true
		assertFalse(om.getBagItem());
	}

	@Test
	public void testWeightAdjustmentInBlockedState() {
		Item item = new StubbedItem(2);

		// set state to a state other than NORMAL
		sm.setState(SessionStatus.BLOCKED);
		om.doNotBagRequest(true);

		// check if the weight adjustment remains unchanged
		assertEquals(BigDecimal.ZERO, om.getWeightAdjustment());
	}

	@Test
	public void testDoNotBagRequestNotifiesAttendant() {
		Item item = new StubbedItem(4);

		sm.setState(SessionStatus.NORMAL);
		om.doNotBagRequest(true);

		// check if the attendant is notified with the correct reason
        assertTrue(sm.notifyAttendantCalled);
	}

	@Test
	public void testDoNotBagAndAddItem() throws OperationNotSupportedException {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		sm.setState(SessionStatus.NORMAL);
		om.doNotBagRequest(true);

		// check if the attendant is notified with the correct reason
		assertTrue(sm.notifyAttendantCalled);
		assertTrue(!om.getBagItem());

		// adding the item to the order
		sm.addItemToOrder(item, ScanType.MAIN);

		// this method should not block the session
		assertEquals(SessionStatus.NORMAL, sm.getState());

		// this flag should have been cleared
		assertTrue(om.getBagItem());
	}

}
