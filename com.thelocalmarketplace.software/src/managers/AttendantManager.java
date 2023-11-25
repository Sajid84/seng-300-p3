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

		// copying the system manager
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
		return sm.getState();
	}

	@Override
	public void blockSession() {
		sm.blockSession();
	}

	@Override
	public void unblockSession() {
		sm.unblockSession();
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
		// TODO replace this with a GUI
		System.out.printf("[ATTENDANT NOTIFY]: %s\n", reason);
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
		return sm.isBlocked();
	}

	@Override
	public boolean isUnblocked() {
		return sm.isUnblocked();
	}

	@Override
	public boolean isPaid() {
		return sm.isPaid();
	}

	@Override
	public void requestDisableMachine() {
		sm.requestDisableMachine();
	}

	@Override
	public void requestEnableMachine() {
		sm.requestEnableMachine();
	}

	@Override
	public boolean isDisabled() {
		return sm.isDisabled();
	}

}
