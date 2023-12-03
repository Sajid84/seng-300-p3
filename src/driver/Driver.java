// Jun Heo - 30173430
// Brandon Smith - 30141515
// Katelan Ng - 30144672
// Muhib Qureshi - 30076351
// Liam Major - 30223023
// André Beaulieu - 30174544

// Package declaration and imports
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
import managers.SystemManager;
import managers.enums.SelfCheckoutTypes;
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

// Driver class for the self-checkout system
public class Driver {

    // References to hardware components
    protected ISelfCheckoutStation machine;

    // Scanner for user input
    protected static Scanner scanner = new Scanner(System.in);

    // Object references
    protected SystemManager system;
    protected CardIssuer cardIssuer;

    // Variables
    protected BigDecimal leniency = BigDecimal.ONE;
    protected Card card;
    protected JFrame gui;

    // Denominations for coins and banknotes
    protected final BigDecimal[] coinDenominations = new BigDecimal[]{new BigDecimal("0.01"), new BigDecimal("0.05"),
            new BigDecimal("0.10"), new BigDecimal("0.25"), new BigDecimal(1), new BigDecimal(2)};
    protected final BigDecimal[] banknoteDenominations = new BigDecimal[]{new BigDecimal(5), new BigDecimal(10),
            new BigDecimal(20), new BigDecimal(50)};

    // Constructor
    public Driver(SelfCheckoutTypes type) {
        // Configuring the self-checkout machine
        DriverHelper.configureMachine(coinDenominations, banknoteDenominations, 100, 1000);

        // Creating the self-checkout machine
        this.machine = DriverHelper.createMachine(type);

        // Creating card-related objects
        cardIssuer = CardHelper.createCardIssuer();
        card = CardHelper.createCard(cardIssuer);

        // Creating the system manager
        this.system = new SystemManager(cardIssuer, leniency);

        // Configuring the system
        this.system.configure(this.machine);

        // Creating the GUI
        gui = new JFrame("Self Checkout Station");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(false);
        gui.setMinimumSize(new Dimension(800, 600));
        gui.addWindowListener(windowClosingHook());
    }

    // Method to perform initial setup
    protected void setup() {
        // Ensuring uninterrupted power supply
        PowerGrid.engageUninterruptiblePowerSource();

        // Creating the GUI
        createGUI();

        // Plugging in and turning on the self-checkout machine
        this.machine.plugIn(PowerGrid.instance());
        this.machine.turnOn();

        // Loading the machine with coins, banknotes, ink, and paper
        loadMachine();
    }

    // Method to set GUI visibility
    protected void setVisible(boolean visibility) {
        gui.setVisible(visibility);
    }

    // Method to create the graphical user interface
    protected void createGUI() {
        // Configuring the main screen
        gui.setUndecorated(false);
        gui.setSize(600, 400);
        gui.setResizable(false);

        // Getting the touch screen
        JFrame screen = machine.getScreen().getFrame();

        // Putting the touch screen in the GUI pane
        gui.add(screen.getRootPane());

        // Packing and setting the frame to visible
        gui.pack();
    }

    // Method to load the self-checkout machine with coins, banknotes, ink, and paper
    protected void loadMachine() {
        // Loading the coin dispensers
        for (BigDecimal coinDenomination : coinDenominations) {
            ICoinDispenser cd = machine.getCoinDispensers().get(coinDenomination);
            for (int j = 0; j < cd.getCapacity(); ++j) {
                try {
                    cd.load(new Coin(coinDenomination));
                } catch (SimulationException | CashOverloadException e) {
                    // Exception handling (shouldn't happen)
                }
            }
        }

        // Loading the banknote dispensers
        for (BigDecimal banknoteDenomination : banknoteDenominations) {
            IBanknoteDispenser abd = machine.getBanknoteDispensers().get(banknoteDenomination);
            for (int j = 0; j < abd.getCapacity(); ++j) {
                try {
                    abd.load(new Banknote(Currency.getInstance(Locale.CANADA), banknoteDenomination));
                } catch (SimulationException | CashOverloadException e) {
                    // Exception handling (shouldn't happen)
                }
            }
        }

        // Adding paper and ink to the printer
        try {
            machine.getPrinter().addInk(1 << 20);
            machine.getPrinter().addPaper(1 << 10);
        } catch (OverloadedDevice e) {
            // Exception handling (shouldn't happen)
        }
    }

    // Method to define actions on window closing
    protected WindowAdapter windowClosingHook() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                // Performing closing actions
                system.postTransactions();
            }
        };
    }

    // Main method of the program
    public static void main(String[] args) {
        // Create driver class
        Driver d = new Driver(DriverHelper.chooseMachineType());

        // Setup driver class
        d.setup();

        // Set the visibility of the frame
        d.setVisible(true);
    }
}
