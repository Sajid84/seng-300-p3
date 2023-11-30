// Liam Major 30223023

package managers.interfaces;

public interface ISystemManager extends IManager {
    /**
     * This posts all stored card transactions.
     *
     * @return
     */
    boolean postTransactions();

    /**
     * This tells the machine to disable itself. If there is an active session, this
     * request waits until after the session is done.
     */
    void disableMachine();

    /**
     * This tells the machine to enable itself.
     */
    void enableMachine();

    /**
     * This attaches a listener to the manager object.
     *
     * @param observer the observer to attach;
     */
    void attach(ISystemManagerNotify observer);

    /**
     * This attaches a listener to the manager object.
     *
     * @param observer the observer to attach;
     * @return true if the observer was found and was detached, false otherwise
     */
    boolean detach(ISystemManagerNotify observer);
}
