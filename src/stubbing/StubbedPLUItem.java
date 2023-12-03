// Liam Major 30223023

package stubbing;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PriceLookUpCode;

public class StubbedPLUItem extends PLUCodedItem {

	public static final PriceLookUpCode PLUCODE = new PriceLookUpCode("12345");
	public static final Mass mass = new Mass(10);

	public StubbedPLUItem() {
		this(StubbedPLUItem.PLUCODE, StubbedPLUItem.mass);
	}

    public StubbedPLUItem(PriceLookUpCode plucode2, Mass mass2) {
        super(plucode2, mass2);

    }

}
