// Liam Major 30223023

package stubbing;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import com.jjjwelectronics.scale.ElectronicScaleGold;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;

public class StubbedStation extends SelfCheckoutStationGold {

	public static final BigDecimal[] coinDenominations = new BigDecimal[] { new BigDecimal(0.01), new BigDecimal(0.05),
			new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1), new BigDecimal(2) };
	public static final BigDecimal[] banknoteDenominations = new BigDecimal[] { new BigDecimal(5), new BigDecimal(10),
			new BigDecimal(20), new BigDecimal(50) };
	public static final int coinDispenserCapacity = 100;
	public static final int banknoteStorageCapacity = 100;

	public AbstractSelfCheckoutStation machine;

	public StubbedStation() {
		machine = new SelfCheckoutStationGold();
	}
	
	public ElectronicScaleGold getBaggingAreaScale() {
		// at runtime, this is a ElectronicScaleGold object
		return (ElectronicScaleGold) machine.baggingArea;
	}

	public static void configure() {
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		AbstractSelfCheckoutStation.configureCoinDenominations(coinDenominations);
		AbstractSelfCheckoutStation.configureCoinDispenserCapacity(coinDispenserCapacity);
		AbstractSelfCheckoutStation.configureBanknoteDenominations(banknoteDenominations);
		AbstractSelfCheckoutStation.configureBanknoteStorageUnitCapacity(banknoteStorageCapacity);
		AbstractSelfCheckoutStation.configureCurrency(Currency.getInstance(Locale.CANADA));
	}

	public static void setBaggingAreaWeightLimit(double weight) {
		AbstractSelfCheckoutStation.configureScaleMaximumWeight(weight);
	}

}
