// Gurjit Samra - 30172814

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
