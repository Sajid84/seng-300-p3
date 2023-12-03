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

package test.managers.system;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestConfigures {

	private StubbedSystemManager sm;

	@Before
	public void setup() {
		StubbedStation.configure();
		
		sm = new StubbedSystemManager();
	}

	@Test
	public void testSystemManagerCopiesHardwareReference() {
		sm.configure(new StubbedStation().machine);

		assertNotNull(sm.getMachine());
	}

	@Test
	public void testSystemManagerConfiguresChildManagers() {
		sm.configure(new StubbedStation().machine);

		assertTrue(sm.omStub.getConfigured());
		assertTrue(sm.pmStub.getConfigured());
	}
	
	@Test
	public void testOrderManagerCopiedHardwareReference() {
		sm.configure(new StubbedStation().machine);

		assertNotNull(sm.omStub.getMachine());
	}
	
	@Test
	public void testPaymentManagerCopiedHardwareReference() {
		sm.configure(new StubbedStation().machine);

		assertNotNull(sm.pmStub.getMachine());
	}
	
	@Test
	public void testPaymentManagerCreatesObserversOnConfigure() {
		sm.configure(new StubbedStation().machine);
		
		assertNotNull(sm.pmStub.getBanknoteCollector());
		assertNotNull(sm.pmStub.getCardReaderObserver());
		assertNotNull(sm.pmStub.getCoinCollector());
	}
	
	@Test
	public void testOrderManagerCreatesObserversOnConfigure() {
		sm.configure(new StubbedStation().machine);
		
		assertNotNull(sm.omStub.getBaggingAreaObserver());
		assertNotNull(sm.omStub.getMainBarcodeObserver());
		assertNotNull(sm.omStub.getHandheldBarcodeObserver());
	}
}
