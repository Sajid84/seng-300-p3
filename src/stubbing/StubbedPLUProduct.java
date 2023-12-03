// Liam Major 30223023

package stubbing;

import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;

public class StubbedPLUProduct extends PLUCodedProduct {

	public static final PriceLookUpCode PLUCODE = new PriceLookUpCode("12345");
	public static final String DESCRIPTION = "fortnite";
	public static final long PRICE = 10;

	public StubbedPLUProduct() {
		this(StubbedPLUProduct.PLUCODE, StubbedPLUProduct.DESCRIPTION, StubbedPLUProduct.PRICE);
	}

	public StubbedPLUProduct(PriceLookUpCode pluCode, String description, long price) {
		super(pluCode, description, price);
	}

}
