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

package observers.order;

import com.jjjwelectronics.keyboard.IKeyboard;
import com.jjjwelectronics.keyboard.KeyboardListener;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;

import managers.OrderManager;
import observers.AbstractDeviceObserver;

public class KeyboardObserver extends AbstractDeviceObserver implements KeyboardListener {

	// object references
	private OrderManager ref;

	public KeyboardObserver(OrderManager om, IKeyboard device) {
		super(device);

		// checking for null
		if (om == null) {
			throw new IllegalArgumentException("OrderManager cannot be null.");
		}

		this.ref = om;
		device.register(this);
	}

	@Override
	public void aKeyHasBeenPressed(String label) {
		this.ref.notifyKeyHasBeenPressed(label);
		
	}

	@Override
	public void aKeyHasBeenReleased(String label) {
		this.ref.notifyKeyHasBeenPressed(label);
	}
	
}
