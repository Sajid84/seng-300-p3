package stubbing;

import managers.AttendantManager;
import observers.payment.ReceiptPrinterObserver;

public class StubbedAttendantManager extends AttendantManager {
    public StubbedSystemManager smStub;
    
    public boolean notifyAttendantCalled;
	private String attendantNotification;


    public StubbedAttendantManager(StubbedSystemManager sm) {
        super(sm);

        smStub = sm;
        
        //variables to check if predict is called, copied from StubbedSystemManager
        notifyAttendantCalled = false;
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
    
    //getter for checking paperLow, inkLow variables
    public boolean isPaperLow() {
    	return paperLow;
    }
    
    public boolean isInkLow() {
    	return inkLow;
    }
    
    //needed to check predict notifications
	@Override
	public void notifyAttendant(String reason) {
		notifyAttendantCalled = true;
		this.attendantNotification = reason;
		super.notifyAttendant(reason);
	}
	
	public String getAttendantNotification() {
		return attendantNotification;
	}
 
}
