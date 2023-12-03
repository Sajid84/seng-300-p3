// Liam Major 30223023

package managers.interfaces;

import com.thelocalmarketplace.hardware.external.CardIssuer;

import java.math.BigDecimal;
import java.util.List;

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

    /**
     * Gets the coin denominations of the current machine.
     *
     * @return the coin denominations
     */
    List<BigDecimal> getCoinDenominations();

    /**
     * Gets the banknote denominations of the current machine.
     *
     * @return the banknote denominations
     */
    List<BigDecimal> getBanknoteDenominations();

    /**
     * This checks if the machine can go into the PAID state.
     */
    void checkPaid();

    /**
     * Returns the issuer that the system manager uses.
     *
     * @return the card issuer
     */
    CardIssuer getIssuer();
}
