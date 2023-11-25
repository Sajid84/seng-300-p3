package managers;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.SessionStatus;
import managers.interfaces.IAttendantManager;
import managers.interfaces.IAttendantManagerNotify;

public class AttendantManager implements IAttendantManager, IAttendantManagerNotify {

	// hardware references
	protected AbstractSelfCheckoutStation machine;

	// object references
	protected SystemManager sm;

	public AttendantManager(SystemManager sm) {
		// checking arguments
		if (sm == null) {
			throw new IllegalArgumentException("the system manager cannot be null");
		}

		this.sm = sm;

	}

	@Override
	public void configure(AbstractSelfCheckoutStation machine) {
		// saving reference
		this.machine = machine;

		// creating observers
	}

	@Override
	public SessionStatus getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void blockSession() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unblockSession() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ready() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void signalForAttendant() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyAttendant(String reason) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyPaperLow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyInkLow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyCoinsFull() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyCoinsEmpty() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyBanknotesFull() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyBanknotesEmpty() {
		// TODO Auto-generated method stub

	}

	@Override
	public void maintainBanknotes() {
		// TODO Auto-generated method stub

	}

	@Override
	public void maintainPaper() {
		// TODO Auto-generated method stub

	}

	@Override
	public void maintainCoins() {
		// TODO Auto-generated method stub

	}

	@Override
	public void maintainInk() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isBlocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUnblocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPaid() {
		// TODO Auto-generated method stub
		return false;
	}

}
