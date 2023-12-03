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

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;

import java.math.BigDecimal;

public class StubbedBarcodedItem extends BarcodedItem {
    public static final Barcode BARCODE = new Barcode(new Numeral[]{Numeral.one});
    public static final long WEIGHT = 5;

    public StubbedBarcodedItem() {
        this(StubbedBarcodedItem.BARCODE, StubbedBarcodedItem.WEIGHT);
    }

    public StubbedBarcodedItem(BigDecimal mass) {
        this(StubbedBarcodedItem.BARCODE, mass.doubleValue());
    }

    public StubbedBarcodedItem(Barcode barcode, double expectedWeightInGrams) {
        super(barcode, new Mass(expectedWeightInGrams));
    }

}
