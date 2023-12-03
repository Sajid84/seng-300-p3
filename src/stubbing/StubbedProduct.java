package stubbing;

import com.thelocalmarketplace.hardware.Product;

public class StubbedProduct extends Product {

	public static final long PRICE = 10;
	public static final boolean PER_UNIT = true;

	public StubbedProduct() {
		this(StubbedProduct.PRICE, StubbedProduct.PER_UNIT);
	}

	public StubbedProduct(long price, boolean isPerUnit) {
		super(price, isPerUnit);
	}

}
