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
