package stubbing;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;

public class StubbedBarcodedItem extends BarcodedItem {
    public static final Barcode BARCODE = new Barcode(new Numeral[]{Numeral.one});
    public static final long WEIGHT = 5;

    public StubbedBarcodedItem() {
        this(StubbedBarcodedItem.BARCODE, StubbedBarcodedItem.WEIGHT);
    }

    public StubbedBarcodedItem(Barcode barcode, double expectedWeightInGrams) {
        super(barcode, new Mass(expectedWeightInGrams));
    }

}
