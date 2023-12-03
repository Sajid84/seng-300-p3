// Liam Major 30223023
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

	//test case for when system manager copies hardware reference during configuration
	@Test
	public void testSystemManagerCopiesHardwareReference() {
		sm.configure(new StubbedStation().machine);

		assertNotNull(sm.getMachine());
	}
	
	//test case for when system manager configures child managers during configuration
	@Test
	public void testSystemManagerConfiguresChildManagers() {
		sm.configure(new StubbedStation().machine);

		assertTrue(sm.omStub.getConfigured());
		assertTrue(sm.pmStub.getConfigured());
	}
	
	//test case for when order manager copies hardware references during configuration
	@Test
	public void testOrderManagerCopiedHardwareReference() {
		sm.configure(new StubbedStation().machine);

		assertNotNull(sm.omStub.getMachine());
	}
	
	//test case for when payment manager copies hardware references during configuration
	@Test
	public void testPaymentManagerCopiedHardwareReference() {
		sm.configure(new StubbedStation().machine);

		assertNotNull(sm.pmStub.getMachine());
	}
	
	//test case for when payment manager creates observers during configuration
	@Test
	public void testPaymentManagerCreatesObserversOnConfigure() {
		sm.configure(new StubbedStation().machine);
		
		//make sure payment managers are not null
		assertNotNull(sm.pmStub.getBanknoteCollector());
		assertNotNull(sm.pmStub.getCardReaderObserver());
		assertNotNull(sm.pmStub.getCoinCollector());
	}
	
	//test case for when order manager creates observers during configuration
	@Test
	public void testOrderManagerCreatesObserversOnConfigure() {
		sm.configure(new StubbedStation().machine);
		
		//make sure order managers are not null
		assertNotNull(sm.omStub.getBaggingAreaObserver());
		assertNotNull(sm.omStub.getMainBarcodeObserver());
		assertNotNull(sm.omStub.getHandheldBarcodeObserver());
	}
}
