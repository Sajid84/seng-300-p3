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

package test.managers.payment;

import ca.ucalgary.seng300.simulation.SimulationException;
import com.tdc.CashOverloadException;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;
import stubbing.StubbedGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCanTenderChange {

    public ISelfCheckoutStation machine;

    // vars
    private StubbedPaymentManager pm;
    private StubbedSystemManager sm;

    private Coin coin;
    private BigDecimal defaultDenomination;

    @Before
    public void setUp() {
        // configuring the hardware
        StubbedStation.configure();

        // creating the hardware
        this.machine = new StubbedStation().machine;
        this.machine.plugIn(StubbedGrid.instance());
        this.machine.turnOn();

        // creating the stubs
        sm = new StubbedSystemManager(BigDecimal.ZERO);
        pm = sm.pmStub;

        // configuring the machine
        sm.configure(machine);

        // getting any denomination from the machine, for testing purposes, it shouldn't matter what denomination we choose
        defaultDenomination = machine.getCoinDenominations().get(0); // this should be $0.01
    }

    //Loads money into machine
    private void loadMoneyIntoTheMachine(int amount) throws SimulationException, CashOverloadException {
        // getting the dispenser of the denomination
        ICoinDispenser dispenser = machine.getCoinDispensers().get(defaultDenomination);

        // checking the dispenser
        if (dispenser == null) {
            throw new RuntimeException("Tried to access a coin dispenser for a denomination that doesn't exist: " + defaultDenomination);
        }

        // creating an array of coins to load into the machine
        Coin[] coins = new Coin[amount];
        for (int i = 0; i < amount; i++) {
            coins[i] = new Coin(defaultDenomination);
        }

        // loading the coins into the dispenser
        dispenser.load(coins);
    }

    @Test
    public void testCanTenderChangeNotEnoughMoney() throws SimulationException, CashOverloadException {
        // loading money into the machine
        loadMoneyIntoTheMachine(1);

        // asserting that we get false here, there is not enough money in the machine
        assertFalse(pm.canTenderChange(BigDecimal.TEN));
    }

    //Tests if you canTenderChange with empty machine
    @Test
    public void testCanTenderChangeWithNoCash() {
        assertFalse(pm.canTenderChange(BigDecimal.TEN));
    }

    //Tests if you canTenderChange with valid environment
    @Test
    public void testCanTenderChange() throws SimulationException, CashOverloadException {
        loadMoneyIntoTheMachine(5);

        // asserting
        assertTrue(pm.canTenderChange(defaultDenomination));
    }
}