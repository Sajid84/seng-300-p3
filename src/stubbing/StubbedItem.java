// Liam Major 30223023
// Kear Sang Heng 30087289

package stubbing;

import java.math.BigDecimal;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;

public class StubbedItem extends Item {

	public StubbedItem(double weightValue) {
		this(new BigDecimal(weightValue));
	}

	public StubbedItem(BigDecimal weightValue) {
		this(new Mass(weightValue));
	}

	public StubbedItem(Mass weightValue) {
		super(weightValue);
	}
}
