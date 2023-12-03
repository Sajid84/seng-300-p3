// Jun Heo - 30173430
// Brandon Smith - 30141515
// Katelan Ng - 30144672
// Muhib Qureshi - 30076351
// Liam Major - 30223023

// Package declaration for observers related to orders
package observers.order;

import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;

import managers.OrderManager;
import observers.AbstractDeviceObserver;

/**
 * This class represents an observer for a barcode scanner device. It extends
 * AbstractDeviceObserver and implements the BarcodeScannerListener interface.
 */
public class BarcodeScannerObserver extends AbstractDeviceObserver implements BarcodeScannerListener {

    // Reference to the OrderManager
    private OrderManager ref;

    /**
     * Constructor for BarcodeScannerObserver.
     * 
     * @param om     The OrderManager associated with the observer. It cannot be
     *               null.
     * @param device The IBarcodeScanner device being observed. It cannot be null.
     * @throws IllegalArgumentException if either the OrderManager or the
     *                                  IBarcodeScanner device is null.
     */
    public BarcodeScannerObserver(OrderManager om, IBarcodeScanner device) {
        super(device);

        // Checking for null OrderManager
        if (om == null) {
            throw new IllegalArgumentException("OrderManager cannot be null.");
        }

        // Assigning references and registering the observer with the device
        this.ref = om;
        device.register(this);
    }

    /**
     * This method is called when a barcode has been scanned by the associated
     * barcode scanner device. It notifies the OrderManager about the scanned
     * barcode.
     * 
     * @param barcodeScanner The IBarcodeScanner device that scanned the barcode.
     * @param barcode        The Barcode object representing the scanned barcode.
     */
    @Override
    public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) {
        // Notify the OrderManager about the scanned barcode
        this.ref.notifyBarcodeScanned(barcodeScanner, barcode);
    }
}
