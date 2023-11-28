// Jun Heo - 30173430
// Brandon Smith - 30141515
// Katelan Ng - 30144672
// Muhib Qureshi - 30076351
// Liam Major - 30223023
// André Beaulieu - 30174544

package driver;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.*;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.card.Card;
import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import ca.ucalgary.seng300.simulation.SimulationException;
import managers.SystemManager;
import managers.enums.SelfCheckoutTypes;
import powerutility.PowerGrid;
import utils.CardHelper;
import utils.DriverHelper;

// start session use case

public class Driver {

    // hardware references
    protected ISelfCheckoutStation machine;

    // object references
    protected static Scanner scanner = new Scanner(System.in);

    // object ownership
    protected SystemManager system;
    protected CardIssuer cardIssuer;

    // vars
    protected List<Item> items;
    protected BigDecimal leniency = BigDecimal.ONE;
    protected Card card;

    // denominations
    protected final BigDecimal[] coinDenominations = new BigDecimal[]{new BigDecimal(0.01), new BigDecimal(0.05),
            new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1), new BigDecimal(2)};
    protected final BigDecimal[] banknoteDenominations = new BigDecimal[]{new BigDecimal(5), new BigDecimal(10),
            new BigDecimal(20), new BigDecimal(50)};

    public Driver(SelfCheckoutTypes type) {
        // configuring the machine (need to do this before we initialize it)
        DriverHelper.configureMachine(coinDenominations, banknoteDenominations, 100, 1000);

        // create the machine itself
        this.machine = DriverHelper.createMachine(type);

        // creating the vars for the system manager
        cardIssuer = CardHelper.createCardIssuer();
        card = CardHelper.createCard(cardIssuer);

        // creating the system manager
        this.system = new SystemManager(cardIssuer, leniency);
    }

    protected void setup() {
        // configuring the system
        this.system.configure(this.machine);

        // so that no power surges happen
        PowerGrid.engageUninterruptiblePowerSource();

        // TODO replace with java swing implementation
        JFrame main = machine.getScreen().getFrame();

        main.setSize(600, 400);
        main.setContentPane(new SystemManagerForm().root);
        main.pack();

        // plug in and turn on the machine
        this.machine.plugIn(PowerGrid.instance());
        this.machine.turnOn();

        main.setVisible(true);

        // loading the coin dispensers
        for (BigDecimal coinDenomination : coinDenominations) {
            ICoinDispenser cd = machine.getCoinDispensers().get(coinDenomination);
            for (int j = 0; j < cd.getCapacity(); ++j) {
                try {
                    cd.load(new Coin(coinDenomination));
                } catch (SimulationException | CashOverloadException e) {
                    // shouldn't happen
                }
            }
        }

        // loading the banknote dispensers
        for (BigDecimal banknoteDenomination : banknoteDenominations) {
            IBanknoteDispenser abd = machine.getBanknoteDispensers().get(banknoteDenomination);
            for (int j = 0; j < abd.getCapacity(); ++j) {
                try {
                    abd.load(new Banknote(Currency.getInstance(Locale.CANADA), banknoteDenomination));
                } catch (SimulationException | CashOverloadException e) {
                    // shouldn't happen
                }
            }
        }

        // adding paper and ink to the printer
        try {
            machine.getPrinter().addInk(1 << 20);
            machine.getPrinter().addPaper(1 << 10);
        } catch (OverloadedDevice e) {
            // shouldn't happen
        }
    }

    /*
     * Main method of program
     */
    public static void main(String[] args) {
        // create driver class
        Driver d = new Driver(DriverHelper.chooseMachineType());

        // setup driver class
        d.setup();

        // posting the credit card transactions
        d.system.postTransactions();
    }
}
