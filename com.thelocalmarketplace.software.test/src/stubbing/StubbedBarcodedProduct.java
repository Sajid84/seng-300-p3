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

// Liam Major 30223023

package stubbing;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;

public class StubbedBarcodedProduct extends BarcodedProduct {

	public static final Barcode BARCODE = new Barcode(new Numeral[] { Numeral.one });
	public static final String DESCRIPTION = "fortnite";
	public static final long PRICE = 10;
	public static final long WEIGHT = 5;

	public StubbedBarcodedProduct() {
		this(StubbedBarcodedProduct.BARCODE, StubbedBarcodedProduct.DESCRIPTION, StubbedBarcodedProduct.PRICE,
				StubbedBarcodedProduct.WEIGHT);
	}

	public StubbedBarcodedProduct(Barcode barcode, String description, long price, double expectedWeightInGrams) {
		super(barcode, description, price, expectedWeightInGrams);
	}

	public static BarcodedProduct getActual() {
		return new BarcodedProduct(StubbedBarcodedProduct.BARCODE, StubbedBarcodedProduct.DESCRIPTION,
				StubbedBarcodedProduct.PRICE, StubbedBarcodedProduct.WEIGHT);
	}

}
