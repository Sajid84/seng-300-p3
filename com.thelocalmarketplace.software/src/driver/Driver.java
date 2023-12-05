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

package driver;

import ca.ucalgary.seng300.simulation.SimulationException;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.card.Card;
import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import enums.SelfCheckoutTypes;
import managers.SystemManager;
import powerutility.PowerGrid;
import utils.CardHelper;
import utils.DriverHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Scanner;

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
    protected BigDecimal leniency = BigDecimal.ONE;
    protected Card card;
    protected JFrame gui;

    // denominations
    protected final BigDecimal[] coinDenominations = new BigDecimal[]{new BigDecimal("0.01"), new BigDecimal("0.05"),
            new BigDecimal("0.10"), new BigDecimal("0.25"), new BigDecimal(1), new BigDecimal(2)};
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

        // configuring the system
        this.system.configure(this.machine);

        // creating the gui
        gui = new JFrame("Self Checkout Station");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(false);
        gui.setMinimumSize(new Dimension(800, 600));
        gui.addWindowListener(windowClosingHook());
    }

    protected void setup() {
        // so that no power surges happen
        PowerGrid.engageUninterruptiblePowerSource();

        // creating the GUI
        createGUI();

        // plug in and turn on the machine
        this.machine.plugIn(PowerGrid.instance());
        this.machine.turnOn();

        // loading the machine
        loadMachine();
    }

    protected void setVisible(boolean visibility) {
        gui.setVisible(visibility);
    }

    protected void createGUI() {
        // configuring the main screen
        gui.setUndecorated(false);
        gui.setSize(600, 400);
        gui.setResizable(false);

        // getting the touch screen
        JFrame screen = machine.getScreen().getFrame();

        // putting the touch screen in the gui pane
        gui.add(screen.getRootPane());

        // pack & set the frame to visible
        gui.pack();
    }

    protected void loadMachine() {
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

    protected WindowAdapter windowClosingHook() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                // doing closing actions
                system.postTransactions();
            }
        };
    }

    /*
     * Main method of program
     */
    public static void main(String[] args) {
        // create driver class
        Driver d = new Driver(DriverHelper.chooseMachineType());

        // setup driver class
        d.setup();

        // setting the visibility of the frame
        d.setVisible(true);
    }
}
