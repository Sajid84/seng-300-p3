// Liam Major 30223023
// André Beaulieu - 30174544

package stubbing;

import java.math.BigDecimal;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.IBarcodeScanner;

import managers.interfaces.IOrderManagerNotify;

public class StubbedOrderManagerNotify implements IOrderManagerNotify {

	public boolean gotNoftifyBarcodeScannedMessage = false;
	public boolean gotNoftifyMassChangedMessage = false;
	public boolean gotOnItemRemovedFromOrderMessage = false;
	public Item itemRemovedFromOrder = null;
	
	@Override
	public void notifyBarcodeScanned(IBarcodeScanner scanner, Barcode barcode) {
		gotNoftifyBarcodeScannedMessage = true;
	}

	@Override
	public void notifyMassChanged(ElectronicScaleListener scale, BigDecimal mass) {
		gotNoftifyMassChangedMessage = true;		
	}

	@Override
	public void onItemRemovedFromOrder(Item item) {
		gotOnItemRemovedFromOrderMessage = true;
		itemRemovedFromOrder = item;
	}

	@Override
	public void notifyScaleOverload(IElectronicScale scale, boolean state) {
		// TODO Auto-generated method stub
		
	}
}
