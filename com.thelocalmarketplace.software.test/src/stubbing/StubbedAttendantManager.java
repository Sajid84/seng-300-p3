package stubbing;

import managers.AttendantManager;
import observers.payment.ReceiptPrinterObserver;

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
