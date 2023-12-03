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

import managers.AttendantManager;
import observers.attendant.ReceiptPrinterObserver;

public class StubbedAttendantManager extends AttendantManager {
    public StubbedSystemManager smStub;

    public StubbedAttendantManager(StubbedSystemManager sm) {
        super(sm);

        smStub = sm;
    }

    public ReceiptPrinterObserver getReceiptPrinterObserver() {
        return super.rpls;
    }

    public boolean getHasPaper() {
        return super.hasPaper;
    }

    public boolean getHasInk() {
        return super.hasInk;
    }

    public void setHasPaper(boolean has) {
        super.hasPaper = has;
    }

    public void setHasInk(boolean has) {
        super.hasInk = has;
    }

}
