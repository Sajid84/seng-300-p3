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
	public void notifyScaleOverload(IElectronicScale scale, boolean state) {
		// TODO Auto-generated method stub
		
	}
}
