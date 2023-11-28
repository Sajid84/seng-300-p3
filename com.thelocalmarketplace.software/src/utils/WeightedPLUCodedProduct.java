package utils;

import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;

import java.math.BigDecimal;

public class WeightedPLUCodedProduct extends PLUCodedProduct {

    protected BigDecimal weight;

    public WeightedPLUCodedProduct(PriceLookUpCode pluCode, String description, long price, BigDecimal weight) {
        super(pluCode, description, price);

        this.weight = weight;
    }

    public WeightedPLUCodedProduct(PLUCodedProduct p, BigDecimal weight) {
        super(p.getPLUCode(), p.getDescription(), p.getPrice());

        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

}
