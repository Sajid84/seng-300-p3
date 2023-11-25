package managers.interfaces;

public interface IAttendantManager extends IManager {

	void signalForAttendant();

	void maintainBanknotes();

	void maintainPaper();

	void maintainCoins();

	void maintainInk();

}
