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
import enums.SelfCheckoutTypes;
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

	//test case to configure bronze self-checkout machine
	@Test
	public void testConfigureBronzeMachine() {
		DriverHelper.configureMachine(coinDenominations, banknoteDenominations, coinDispenserSize, banknoteStoragesize);

		SelfCheckoutStationBronze machine = new SelfCheckoutStationBronze();

		// casting to List
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(coinDenominations[0]);

		// asserting
		Assert.assertArrayEquals(machine.getBanknoteDenominations(), banknoteDenominations);
		Assert.assertEquals(machine.getCoinDenominations(), list);
		Assert.assertEquals(machine.getBanknoteStorage().getCapacity(), banknoteStoragesize);
		Assert.assertEquals(machine.getCoinDispensers().get(coinDenominations[0]).getCapacity(), coinDispenserSize);
	}

	//test case to configure a silve self-checkout machine
	@Test
	public void testConfigureSilverMachine() {
		DriverHelper.configureMachine(coinDenominations, banknoteDenominations, coinDispenserSize, banknoteStoragesize);

		SelfCheckoutStationSilver machine = new SelfCheckoutStationSilver();

		// casting to List
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(coinDenominations[0]);

		// asserting
		Assert.assertArrayEquals(machine.getBanknoteDenominations(), banknoteDenominations);
		Assert.assertEquals(machine.getCoinDenominations(), list);
		Assert.assertEquals(machine.getBanknoteStorage().getCapacity(), banknoteStoragesize);
		Assert.assertEquals(machine.getCoinDispensers().get(coinDenominations[0]).getCapacity(), coinDispenserSize);
	}

	//test to configure a gold self-checkout machine
	@Test
	public void testConfigureGoldMachine() {
		DriverHelper.configureMachine(coinDenominations, banknoteDenominations, coinDispenserSize, banknoteStoragesize);

		SelfCheckoutStationGold machine = new SelfCheckoutStationGold();

		// casting to List
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(coinDenominations[0]);

		// asserting
		Assert.assertArrayEquals(machine.getBanknoteDenominations(), banknoteDenominations);
		Assert.assertEquals(machine.getCoinDenominations(), list);
		Assert.assertEquals(machine.getBanknoteStorage().getCapacity(), banknoteStoragesize);
		Assert.assertEquals(machine.getCoinDispensers().get(coinDenominations[0]).getCapacity(), coinDispenserSize);
	}

	//test case to create a bronze self-checkout machine with DriverHelper
	@Test
	public void testMachineTypeBronze() {
		AbstractSelfCheckoutStation machine = DriverHelper.createMachine(SelfCheckoutTypes.BRONZE);

		Assert.assertEquals(machine.getClass(), SelfCheckoutStationBronze.class);
	}

	//test case to create a silver self-checkout machine with DriverHelper
	@Test
	public void testMachineTypeSilver() {
		AbstractSelfCheckoutStation machine = DriverHelper.createMachine(SelfCheckoutTypes.SILVER);

		Assert.assertEquals(machine.getClass(), SelfCheckoutStationSilver.class);
	}

	//test case to create a gold self-checkout machine with DriverHelper
	@Test
	public void testMachineTypeGold() {
		AbstractSelfCheckoutStation machine = DriverHelper.createMachine(SelfCheckoutTypes.GOLD);

		Assert.assertEquals(machine.getClass(), SelfCheckoutStationGold.class);
	}

	//test case where InvalidArgumentSimulationException is expected if creating a machine with null type
	@Test(expected = InvalidArgumentSimulationException.class)
	public void testMachineTypeNull() {
		DriverHelper.createMachine(null);
	}

	//test case to choose self-checkout machine type
	@Test
	public void testChooseMachineType() {
		Assert.assertTrue(DriverHelper.chooseMachineType() instanceof SelfCheckoutTypes);
	}

	//test case to choose self-checkoutmachinie type is not null
	@Test
	public void testChooseMachineTypeNotNull() {
		Assert.assertNotNull(DriverHelper.chooseMachineType());
	}
}
