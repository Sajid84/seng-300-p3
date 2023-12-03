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

package test.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import managers.enums.SessionStatus;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedSystemManager;

public class TestManagerConstructorDefaultValues {
	private StubbedSystemManager sm;
	private StubbedPaymentManager pm;
	private StubbedOrderManager om;

	@Before
	public void setup() {
		sm = new StubbedSystemManager();
		om = sm.omStub;
		pm = sm.pmStub;
	}

	@Test
	public void testSystemDefaultIssuer() {
		assertNotNull(sm.getIssuer());
		assertEquals(sm.getIssuer(), StubbedSystemManager.issuer);
	}

	@Test
	public void testSystemDefaultState() {
		assertNotNull(sm.getState());
		assertEquals(sm.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testSystemDefaultRecords() {
		assertNotNull(sm.getRecords());
		assertTrue(sm.getRecords().isEmpty());
	}

	@Test
	public void testPaymentDefaultSystemManager() {
		assertNotNull(pm.smStub);
		assertEquals(pm.smStub, sm);
	}

	@Test
	public void testPaymentDefaultPayment() {
		assertNotNull(pm.getPayment());
		assertEquals(pm.getPayment(), BigDecimal.ZERO);
	}

	@Test
	public void testPaymentDefaultIssuer() {
		assertNotNull(pm.getIssuer());
		assertEquals(pm.getIssuer(), StubbedSystemManager.issuer);
	}

	@Test
	public void testOrderDefaultSystemManager() {
		assertNotNull(om.smStub);
		assertEquals(om.smStub, sm);
	}

	@Test
	public void testOrderDefaultLeniency() {
		assertNotNull(om.getLeniency());
		assertEquals(om.getLeniency(), StubbedSystemManager.leniency);
	}

	@Test
	public void testOrderDefaultAdjustment() {
		assertNotNull(om.getWeightAdjustment());
		assertEquals(om.getWeightAdjustment(), BigDecimal.ZERO);
	}

	@Test
	public void testOrderDefaultActualWeight() {
		assertNotNull(om.getActualWeight());
		assertEquals(om.getActualWeight(), BigDecimal.ZERO);
	}

	@Test
	public void testOrderDefaultNoBaggingRequest() {
		assertTrue(om.getBagItem());
	}

	@Test
	public void testOrderDefaultProducts() {
		assertNotNull(om.getItems());
		assertTrue(om.getItems().isEmpty());
	}

	@Test
	public void testOrderDefaultListeners() {
		assertNotNull(om.getListeners());
		assertTrue(om.getListeners().isEmpty());
	}

	@Test
	public void testOrderDefaultOverloadedScales() {
		assertNotNull(om.getOverloadedScales());
		assertTrue(om.getOverloadedScales().isEmpty());
	}
}
