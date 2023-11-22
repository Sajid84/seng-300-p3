// Liam Major 30223023
// André Beaulieu - 30174544

package managers.interfaces;

import java.math.BigDecimal;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.IBarcodeScanner;

/**
 * This interface is used by any object that {@link IOrderManager} owns, this
 * exists for the child objects to communicate with their parent object without
 * the super class SystemManager being involved does it have to implement these
 * methods
 */
public interface IOrderManagerNotify {

	/**
	 * This method notifies the {@link IOrderManager} that a child object has
	 * scanned a barcode.
	 * 
	 * @param barcode the scanned barcode
	 */
	void notifyBarcodeScanned(IBarcodeScanner scanner, Barcode barcode);

	/**
	 * This method notifies the {@link IOrderManager} that a child object has
	 * changed status, indicating a potential weight discrepancy.
	 * 
	 * @param scale  the scale that announced the event
	 * @param status the status of the scale
	 */
	void notifyMassChanged(ElectronicScaleListener scale, BigDecimal mass);

	/**
	 * An event announcing that an item has been removed from the OrderManager.
	 * 
	 * @param item The item in question.
	 */
	void onItemRemovedFromOrder(Item item);

	/**
	 * Notifies the manager of a scale overload of a scale.
	 * 
	 * @param scale the scale that's overloaded
	 * @param state the state of the overload, true if overloaded, false otherwise
	 */
	void notifyScaleOverload(IElectronicScale scale, boolean state);

}
