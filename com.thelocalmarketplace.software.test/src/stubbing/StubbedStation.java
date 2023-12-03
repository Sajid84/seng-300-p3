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

package stubbing;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import com.jjjwelectronics.scale.ElectronicScaleGold;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;

public class StubbedStation extends SelfCheckoutStationGold {

	public static final BigDecimal[] coinDenominations = new BigDecimal[] { new BigDecimal(0.01), new BigDecimal(0.05),
			new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1), new BigDecimal(2) };
	public static final BigDecimal[] banknoteDenominations = new BigDecimal[] { new BigDecimal(5), new BigDecimal(10),
			new BigDecimal(20), new BigDecimal(50) };
	public static final int coinDispenserCapacity = 100;
	public static final int banknoteStorageCapacity = 100;

	public ISelfCheckoutStation machine;

	public StubbedStation() {
		machine = new SelfCheckoutStationGold();
	}

	public static void configure() {
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		AbstractSelfCheckoutStation.configureCoinDenominations(coinDenominations);
		AbstractSelfCheckoutStation.configureCoinDispenserCapacity(coinDispenserCapacity);
		AbstractSelfCheckoutStation.configureBanknoteDenominations(banknoteDenominations);
		AbstractSelfCheckoutStation.configureBanknoteStorageUnitCapacity(banknoteStorageCapacity);
		AbstractSelfCheckoutStation.configureCurrency(Currency.getInstance(Locale.CANADA));
		Coin.DEFAULT_CURRENCY = Currency.getInstance(Locale.CANADA);
	}

	public static void setBaggingAreaWeightLimit(double weight) {
		AbstractSelfCheckoutStation.configureScaleMaximumWeight(weight);
	}

}
