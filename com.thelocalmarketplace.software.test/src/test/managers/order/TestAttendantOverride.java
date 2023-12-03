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
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

<<<<<<< HEAD
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

=======
>>>>>>> gui-dev
import enums.SessionStatus;
import stubbing.*;
import utils.DatabaseHelper;

public class TestAttendantOverride {
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
	public void testMethodUnblocks() {
		// setup
		om.setState(SessionStatus.BLOCKED);

		// calling
		om.onAttendantOverride();

		//asserting
		assertEquals(om.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testAttendantOverrideSetsAdjustment() {
		// setup
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();
		om.addItem(item, true);
		om.setActualWeight(BigDecimal.TEN);

		// calling
		om.onAttendantOverride();

		// weight but decimal
		BigDecimal weight = item.getMass().inGrams();

		// asserting
		assertEquals(om.getWeightAdjustment(), BigDecimal.TEN.subtract(weight));
	}
}
