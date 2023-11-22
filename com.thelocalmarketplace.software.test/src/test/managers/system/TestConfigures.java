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
		assertNotNull(sm.pmStub.getReceiptPrinterObserver());
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
