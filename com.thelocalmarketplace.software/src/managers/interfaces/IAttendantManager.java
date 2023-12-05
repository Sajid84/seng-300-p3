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

package managers.interfaces;

public interface IAttendantManager extends IManager {

    /**
     * This signals for the attendant.
     */
    void signalForAttendant();

    /**
     * Maintain banknotes use case. This function guarantees that the device will be at least 50% full.
     * This function will only execute if the device is either empty or less than 10% capacity.
     */
    void maintainBanknoteDispensers();

    /**
     * Maintain paper use case. This function guarantees that the device will be at least 50% full.
     * This function will only execute if the device is either empty or less than 10% capacity.
     */
    void maintainPaper();

    /**
     * Maintain coins use case. This function guarantees that the device will be at least 50% full.
     * This function will only execute if the device is either empty or less than 10% capacity.
     */
    void maintainCoinDispensers();

    /**
     * Maintain ink use case. This function guarantees that the device will be at least 50% full.
     * This function will only execute if the device is either empty or less than 10% capacity.
     */
    void maintainInk();

    /**
     * Maintain banknotes use case. This function guarantees that the storage unit will be empty after calling.
     * This function will only execute if the storage unit is full or over 70% capacity.
     */
    void maintainBanknoteStorage();

    /**
     * Maintain bags use case. This function guarantees that the device will be at least 50% full.
     * This function will only execute if the device is either empty or less than 10% capacity.
     */
    void maintainBags();

    /**
     * Maintain coins use case. This function guarantees that the storage unit will be empty after calling.
     * This function will only execute if the storage unit is full or over 70% capacity.
     */
    void maintainCoinStorage();

    /**
     * Remotely disables a machine.
     */
    void requestDisableMachine();

    /**
     * Remotely enable a machine.
     */
    void requestEnableMachine();

    /**
     * Tests whether the machine can print or not.
     *
     * @return true if the machine can print, false otherwise
     */
    boolean canPrint();

    /**
     * Signal to the system that the customer wishes to purchase bags.
     *
     * @param count the amount of bags the customer wants to purchase
     */
    void requestPurchaseBags(int count);

    /**
     * Returns if there are bags in the machine.
     *
     * @return true if there is at least one bag in the machine, false otherwise
     */
    boolean hasBags();

    /**
     * Returns if the bags in the machine are low.
     *
     * @return true if low, false otherwise
     */
    boolean isBagsLow();

    /**
     * This method checks if the coins in any of the dispensers are low.
     *
     * @return true if there is at least one dispenser that is low, false otherwise
     */
    boolean isCoinsLow();

    /**
     * This method checks if the banknotes in any of the dispensers are low.
     *
     * @return true if there is at least one dispenser that is low, false otherwise
     */
    boolean isBanknotesLow();

    /**
     * This method checks if the coins in the coin storage unit is full or close to full.
     *
     * @return true if full or close to full, false otherwise
     */
    boolean isCoinsFull();

    /**
     * This method checks if the banknotes in the banknote storage unit is full or close to full.
     *
     * @return true if full or close to full, false otherwise
     */
    boolean isBanknotesFull();
}
