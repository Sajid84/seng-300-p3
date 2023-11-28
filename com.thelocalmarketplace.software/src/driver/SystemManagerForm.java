package driver;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.screen.ITouchScreen;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import managers.SystemManager;
import managers.enums.ScanType;
import managers.enums.SessionStatus;
import managers.interfaces.IScreen;
import utils.DatabaseHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemManagerForm implements IScreen {

    private SystemManager sm;
    private JPanel root;
    private JPanel regularView;
    private JTabbedPane mainPane;
    private JPanel debugView;
    private JTable itemsTable;
    private JButton scanByMainScannerButton;
    private JButton signalForAttendantButton;
    private JButton payForOrderButton;
    private JButton searchForItemButton;
    private JButton scanByHandheldScannerButton;
    private JLabel feedbackLabel;
    private JLabel tableLabel;
    private JLabel buttonsLabel;
    private JButton removeItemButton;
    private JButton addOwnBagsButton;
    private JButton purchaseBagsButton;
    private JButton causeWeightDiscrepancyButton;
    private JButton blockSessionButtonButton;
    private JButton unblockSessionButtonButton;
    private JButton testEnableMachineButton;
    private JButton testDisableMachineButton;
    private JButton addTooHeavyItemButton;

    public SystemManagerForm(SystemManager sm) {
        // copying the system manager reference
        this.sm = sm;

        // setup the table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Price");

        // setting the model
        itemsTable.setModel(model);

        // listener
        scanByMainScannerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Scan by Main Scanner was triggered");

                // getting random item
                BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

                // adding an item to the order
                sm.addItemToOrder(item, ScanType.MAIN);
            }
        });

        scanByHandheldScannerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Scan by Handheld Scanner was triggered");

                // getting random item
                BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

                // adding an item to the order
                sm.addItemToOrder(item, ScanType.HANDHELD);
            }
        });
        addOwnBagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Adding customer bags.");
                sm.addCustomerBags(DatabaseHelper.createCustomerBags());
            }
        });
        purchaseBagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO purchase bags use case
            }
        });
        payForOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO do something here to actually pay for the order
            }
        });
        causeWeightDiscrepancyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Causing a weight discrepancy.");
            }
        });
        testEnableMachineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Requesting the machine to be enabled.");
                sm.requestEnableMachine();
            }
        });
        blockSessionButtonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Blocking the session.");
                sm.blockSession();
            }
        });
        unblockSessionButtonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Using attendant override to unblock the session.");
                sm.onAttendantOverride();
            }
        });
        testDisableMachineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Requesting the machine to be disabled.");
                sm.requestDisableMachine();
            }
        });
        addTooHeavyItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Overloading the scales with an item.");
            }
        });
        signalForAttendantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Signalling for the attendant.");
                sm.signalForAttendant();
            }
        });
    }

    @Override
    public JPanel getPanel() {
        return root;
    }

    @Override
    public void configure(ITouchScreen touchScreen) {
        touchScreen.getFrame().setContentPane(root);
    }

    protected void updateTable(Item item, boolean append) {
        // getting the model
        DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();

        // adding the item to the table
        if (append) {
            // getting the product
            if (item instanceof BarcodedItem) {
                BarcodedProduct prod = DatabaseHelper.get((BarcodedItem) item);
                model.addRow(new Object[]{prod.getDescription(), "$ " + prod.getPrice()});
            }
        }

        // updating the label
        if (sm.getItems().isEmpty()) {
            tableLabel.setText("Items");
        } else {
            tableLabel.setText("Items (" + sm.getItems().size() + ")");
        }
    }

    @Override
    public void notifyItemAdded(Item item) {
        updateTable(item, true);
    }

    @Override
    public void notifyItemRemoved(Item item) {
        updateTable(item, false);
    }

    @Override
    public void notifyStateChange(SessionStatus state) {
        switch (state) {
            case NORMAL -> {
                unblockButtons();
            }
            case BLOCKED -> {
                blockButtons();
            }
            case PAID -> {
                // TODO do something different here?
                blockButtons();
            }
            case DISABLED -> {
                // TODO implement the disabled state
            }
        }
    }

    protected void blockButtons() {
        setButtonsState(false);
    }

    protected void unblockButtons() {
        setButtonsState(true);
    }

    protected void setButtonsState(boolean state) {
        scanByMainScannerButton.setEnabled(state);
        scanByHandheldScannerButton.setEnabled(state);
        payForOrderButton.setEnabled(state);
        searchForItemButton.setEnabled(state);
        addOwnBagsButton.setEnabled(state);
        purchaseBagsButton.setEnabled(state);
    }
}
