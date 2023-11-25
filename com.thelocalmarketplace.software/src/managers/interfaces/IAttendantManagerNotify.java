package managers.interfaces;

public interface IAttendantManagerNotify {
	
	void notifyPaperLow();

	void notifyInkLow();

	void notifyCoinsFull();

	void notifyCoinsEmpty();

	void notifyBanknotesFull();

	void notifyBanknotesEmpty();

}
