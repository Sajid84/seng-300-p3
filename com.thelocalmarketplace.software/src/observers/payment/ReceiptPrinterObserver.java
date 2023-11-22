// Sheikh Falah Sheikh Hasan - 30175335
// Liam Major 30223023

package observers.payment;

import com.jjjwelectronics.printer.IReceiptPrinter;
import com.jjjwelectronics.printer.ReceiptPrinterListener;

import managers.PaymentManager;
import observers.AbstractDeviceObserver;

public class ReceiptPrinterObserver extends AbstractDeviceObserver implements ReceiptPrinterListener {

	// object references
	private PaymentManager ref;

	public ReceiptPrinterObserver(PaymentManager paymentManager, IReceiptPrinter device) {
		super(device);

		if (paymentManager == null) {
			throw new IllegalArgumentException("payment manager cannot be null");
		}

		this.ref = paymentManager;
		device.register(this);
	}

	@Override
	public void thePrinterIsOutOfPaper() {
		this.ref.notifyPaper(false);
	}

	@Override
	public void thePrinterIsOutOfInk() {
		this.ref.notifyInk(false);
	}

	@Override
	public void thePrinterHasLowInk() {
		// TODO Auto-generated method stub

	}

	@Override
	public void thePrinterHasLowPaper() {
		// TODO Auto-generated method stub
	}

	@Override
	public void paperHasBeenAddedToThePrinter() {
		this.ref.notifyPaper(true);
	}

	@Override
	public void inkHasBeenAddedToThePrinter() {
		this.ref.notifyInk(true);
	}

}
