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

package managers;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import managers.enums.SessionStatus;
import managers.interfaces.IAttendantManager;
import managers.interfaces.IAttendantManagerNotify;

public class AttendantManager implements IAttendantManager, IAttendantManagerNotify {

	// hardware references
	protected ISelfCheckoutStation machine;

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
	public void configure(ISelfCheckoutStation machine) {
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
		this.notifyAttendant("LOW PAPER!");
		this.blockSession();
	}

	@Override
	public void notifyInkLow() {
		this.notifyAttendant("LOW INK!");
		this.blockSession();
	}

	@Override
	public void notifyCoinsFull() {
		this.notifyAttendant("COINS FULL!");

		this.blockSession();
	}

	@Override
	public void notifyCoinsEmpty() {
		this.notifyAttendant("COINS EMPTY!");
		this.blockSession();
	}

	@Override
	public void notifyBanknotesFull() {
		this.notifyAttendant("BANKNOTES FULL!");
		this.blockSession();
	}

	@Override
	public void notifyBanknotesEmpty() {
		this.notifyAttendant("BANKNOTES EMPTY!");
		this.blockSession();
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
