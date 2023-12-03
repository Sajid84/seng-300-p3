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

package observers.attendant;

import com.jjjwelectronics.printer.IReceiptPrinter;
import com.jjjwelectronics.printer.ReceiptPrinterListener;
import managers.AttendantManager;
import observers.AbstractDeviceObserver;

public class ReceiptPrinterObserver extends AbstractDeviceObserver implements ReceiptPrinterListener {

    // object references
    private final AttendantManager ref;

    public ReceiptPrinterObserver(AttendantManager am, IReceiptPrinter device) {
        super(device);

        if (am == null) {
            throw new IllegalArgumentException("attendant manager cannot be null");
        }

        this.ref = am;
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
        this.ref.notifyInkLow();
    }

    @Override
    public void thePrinterHasLowPaper() {
        this.ref.notifyPaperLow();
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
