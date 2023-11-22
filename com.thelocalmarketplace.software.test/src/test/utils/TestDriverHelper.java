// Liam Major 30223023

package test.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import managers.enums.SelfCheckoutTypes;
import utils.DriverHelper;

public class TestDriverHelper {

	// denominations
	private final BigDecimal[] coinDenominations = new BigDecimal[] { new BigDecimal(2) };
	private final BigDecimal[] banknoteDenominations = new BigDecimal[] { new BigDecimal(5) };
	private final int coinDispenserSize = 10;
	private final int banknoteStoragesize = 10;

	@Before
	public void setup() {
		// resetting to defaults
		// if this code doesn't execute, the machine won't be configured properly and raise nullpointerexceptions
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
	}

	@Test
	public void testConfigureBronzeMachine() {
		DriverHelper.configureMachine(coinDenominations, banknoteDenominations, coinDispenserSize, banknoteStoragesize);

		SelfCheckoutStationBronze machine = new SelfCheckoutStationBronze();

		// casting to List
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(coinDenominations[0]);

		// asserting
		Assert.assertArrayEquals(machine.banknoteDenominations, banknoteDenominations);
		Assert.assertEquals(machine.coinDenominations, list);
		Assert.assertEquals(machine.banknoteStorage.getCapacity(), banknoteStoragesize);
		Assert.assertEquals(machine.coinDispensers.get(coinDenominations[0]).getCapacity(), coinDispenserSize);
	}
	
	@Test
	public void testConfigureSilverMachine() {
		DriverHelper.configureMachine(coinDenominations, banknoteDenominations, coinDispenserSize, banknoteStoragesize);

		SelfCheckoutStationSilver machine = new SelfCheckoutStationSilver();

		// casting to List
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(coinDenominations[0]);

		// asserting
		Assert.assertArrayEquals(machine.banknoteDenominations, banknoteDenominations);
		Assert.assertEquals(machine.coinDenominations, list);
		Assert.assertEquals(machine.banknoteStorage.getCapacity(), banknoteStoragesize);
		Assert.assertEquals(machine.coinDispensers.get(coinDenominations[0]).getCapacity(), coinDispenserSize);
	}
	
	@Test
	public void testConfigureGoldMachine() {
		DriverHelper.configureMachine(coinDenominations, banknoteDenominations, coinDispenserSize, banknoteStoragesize);

		SelfCheckoutStationGold machine = new SelfCheckoutStationGold();

		// casting to List
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(coinDenominations[0]);

		// asserting
		Assert.assertArrayEquals(machine.banknoteDenominations, banknoteDenominations);
		Assert.assertEquals(machine.coinDenominations, list);
		Assert.assertEquals(machine.banknoteStorage.getCapacity(), banknoteStoragesize);
		Assert.assertEquals(machine.coinDispensers.get(coinDenominations[0]).getCapacity(), coinDispenserSize);
	}

	@Test
	public void testMachineTypeBronze() {
		AbstractSelfCheckoutStation machine = DriverHelper.createMachine(SelfCheckoutTypes.BRONZE);

		Assert.assertEquals(machine.getClass(), SelfCheckoutStationBronze.class);
	}

	@Test
	public void testMachineTypeSilver() {
		AbstractSelfCheckoutStation machine = DriverHelper.createMachine(SelfCheckoutTypes.SILVER);

		Assert.assertEquals(machine.getClass(), SelfCheckoutStationSilver.class);
	}

	@Test
	public void testMachineTypeGold() {
		AbstractSelfCheckoutStation machine = DriverHelper.createMachine(SelfCheckoutTypes.GOLD);

		Assert.assertEquals(machine.getClass(), SelfCheckoutStationGold.class);
	}

	@Test(expected = InvalidArgumentSimulationException.class)
	public void testMachineTypeNull() {
		DriverHelper.createMachine(null);
	}

	@Test
	public void testChooseMachineType() {
		Assert.assertTrue(DriverHelper.chooseMachineType() instanceof SelfCheckoutTypes);
	}

	@Test
	public void testChooseMachineTypeNotNull() {
		Assert.assertNotNull(DriverHelper.chooseMachineType());
	}
}
