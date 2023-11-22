// Jun Heo - 30173430
// Brandon Smith - 30141515
// Katelan Ng - 30144672
// Muhib Qureshi - 30076351
// Liam Major - 30223023

package observers.order;

import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;

import managers.OrderManager;
import observers.AbstractDeviceObserver;

public class BarcodeScannerObserver extends AbstractDeviceObserver implements BarcodeScannerListener {

	// object references
	private OrderManager ref;

	public BarcodeScannerObserver(OrderManager om, IBarcodeScanner device) {
		super(device);

		// checking for null
		if (om == null) {
			throw new IllegalArgumentException("OrderManager cannot be null.");
		}

		this.ref = om;
		device.register(this);
	}

	@Override
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) {
		this.ref.notifyBarcodeScanned(barcodeScanner, barcode);
	}
}
