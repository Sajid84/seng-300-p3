// Ali Akbari 30171539
// Sheikh Falah Sheikh Hasan 30175335
// Ohiomah Imohi 30187606
// Emmanuel Trinidad 30172372
// Nicholas MacKinnon 30172737
// Abdullah Ishtiaq 30153185
// Md Abu Sinan 30154627
// Gurjit Samra: 30172814
// Michael Hoang: 30123605
// Ana Laura Espinosa Garza: 30198679
// Umer Rehman: 30169819
// Liam Major: 30223023
// Logan Miszaniec: 30156384
// Nezla Annaisha: 30123223
// Maleeha Siddiqui: 30179762
// Kelvin Jamila: 30117164
// Adefikayo Akande 30185937
// Shaikh Sajid Mahmood 30182396
// Alecxia Zaragoza 30150008
// Kevlam Chundawat 30180662
// Anmol Bansal 30159559

package observers.payment;

import com.jjjwelectronics.printer.IReceiptPrinter;
import com.jjjwelectronics.printer.ReceiptPrinterListener;
import managers.AttendantManager;
import observers.AbstractDeviceObserver;

/**
 * This class represents an observer for a Receipt Printer. It listens to events
 * emitted by the Receipt Printer, such as out-of-paper, out-of-ink, low ink,
 * low paper, paper added, and ink added.
 */
public class ReceiptPrinterObserver extends AbstractDeviceObserver implements ReceiptPrinterListener {

    // Reference to the AttendantManager
    private final AttendantManager ref;

    /**
     * Creates an observer to listen to events emitted by a Receipt Printer.
     * 
     * @param am     The AttendantManager associated with the observer. It cannot be
     *               null.
     * @param device The Receipt Printer being observed. It cannot be null.
     * @throws IllegalArgumentException if either the AttendantManager or the
     *                                  Receipt Printer device is null.
     */
    public ReceiptPrinterObserver(AttendantManager am, IReceiptPrinter device) {
        super(device);

        // Checking for null AttendantManager
        if (am == null) {
            throw new IllegalArgumentException("AttendantManager cannot be null.");
        }

        // Assigning references and registering the observer with the device
        this.ref = am;
        device.register(this);
    }

    /**
     * This method is called when the printer is out of paper. It notifies the
     * associated AttendantManager about the paper status.
     */
    @Override
    public void thePrinterIsOutOfPaper() {
        // Notify the AttendantManager about the out-of-paper status
        this.ref.notifyPaper(false);
    }

    /**
     * This method is called when the printer is out of ink. It notifies the
     * associated AttendantManager about the ink status.
     */
    @Override
    public void thePrinterIsOutOfInk() {
        // Notify the AttendantManager about the out-of-ink status
        this.ref.notifyInk(false);
    }

    /**
     * This method is called when the printer has low ink. It notifies the associated
     * AttendantManager about the low ink status.
     */
    @Override
    public void thePrinterHasLowInk() {
        // Notify the AttendantManager about the low ink status
        this.ref.notifyPaperLow();
    }

    /**
     * This method is called when the printer has low paper. It notifies the
     * associated AttendantManager about the low paper status.
     */
    @Override
    public void thePrinterHasLowPaper() {
        // Notify the AttendantManager about the low paper status
        this.ref.notifyPaperLow();
    }

    /**
     * This method is called when paper has been added to the printer. It notifies
     * the associated AttendantManager about the paper status.
     */
    @Override
    public void paperHasBeenAddedToThePrinter() {
        // Notify the AttendantManager that paper has been added
        this.ref.notifyPaper(true);
    }

    /**
     * This method is called when ink has been added to the printer. It notifies the
     * associated AttendantManager about the ink status.
     */
    @Override
    public void inkHasBeenAddedToThePrinter() {
        // Notify the AttendantManager that ink has been added
        this.ref.notifyInk(true);
    }

}
