package driver;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.screen.ITouchScreen;
import managers.SystemManager;
import managers.enums.ScanType;
import managers.enums.SessionStatus;
import managers.interfaces.IScreen;
import utils.DatabaseHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

// DebugForm class represents a debug interface for testing and debugging the self-checkout system
public class DebugForm implements IScreen {

    // SystemManager responsible for managing the self-checkout system
    private final SystemManager sm;

    // GUI components
    private JButton causeWeightDiscrepancyButton;
    private JButton unblockSessionButtonButton;
    private JButton testEnableMachineButton;
    private JButton testDisableMachineButton;
    private JButton addTooHeavyItemButton;
    private JButton blockSessionButtonButton;
    private JButton refreshButton;
    private JLabel expectedWeightLabel;
    private JLabel weightAdjustmentLabel;
    private JLabel scaleOverloadedLabel;
    private JLabel stateLabel;
    private JLabel actualWeightLabel;
    private JPanel root;
    private JLabel customerPaymentLabel;
    private JLabel priceLabel;

    // Constructor that takes a SystemManager as a parameter
    public DebugForm(SystemManager sm) {
        this.sm = sm;

        // ActionListeners for various buttons to trigger debug events
        causeWeightDiscrepancyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Triggering a weight discrepancy event
                System.out.println("Causing a weight discrepancy.");

                // Creating the item
                BarcodedItem item = DatabaseHelper.createWeightDiscrepancy();

                // Adding the item to the order
                sm.addItemToOrder(item, ScanType.MAIN);
            }
        });

        testEnableMachineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Triggering a request to enable the machine
                System.out.println("Requesting the machine to be enabled.");
                sm.requestEnableMachine();
            }
        });

        blockSessionButtonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Blocking the session
                System.out.println("Blocking the session.");
                sm.blockSession();
            }
        });

        unblockSessionButtonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Using an attendant override to unblock the session
                System.out.println("Using attendant override to unblock the session.");
                sm.onAttendantOverride();
            }
        });

        testDisableMachineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Triggering a request to disable the machine
                System.out.println("Requesting the machine to be disabled.");
                sm.requestDisableMachine();
            }
        });

        addTooHeavyItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Overloading the scales with an item
                System.out.println("Overloading the scales with an item.");

                // Creating the item
                BarcodedItem item = DatabaseHelper.createItemTooHeavy();

                // Adding the item to the order
                sm.addItemToOrder(item, ScanType.MAIN);
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Notifying the system manager to refresh
                sm.notifyRefresh();
            }
        });
    }

    // Method to get the JPanel associated with this DebugForm
    @Override
    public JPanel getPanel() {
        return root;
    }

    // Method to get the JFrame associated with this DebugForm
    @Override
    public JFrame getFrame() {
        // DebugForm does not have a JFrame, so UnsupportedOperationException is thrown
        throw new UnsupportedOperationException("This object does not have a JFrame");
    }

    // Method to configure the DebugForm with an ITouchScreen (not implemented in this case)
    @Override
    public void configure(ITouchScreen touchScreen) {
        // do nothing here
    }

    // Method to update labels in the GUI based on the state of the system manager
    protected void updateLabels() {
        expectedWeightLabel.setText("Expected Weight: " + sm.getExpectedMass());
        actualWeightLabel.setText("Actual Weight: " + sm.getActualWeight());
        weightAdjustmentLabel.setText("Weight Adjustment: " + sm.getWeightAdjustment());
        stateLabel.setText("State: " + sm.getState().toString());
        scaleOverloadedLabel.setText("Scale Overloaded? " + sm.isScaleOverloaded());
        priceLabel.setText("Price of Order: " + sm.getTotalPrice());
        customerPaymentLabel.setText("Customer Payment: " + sm.getCustomerPayment());
    }

 // Method to notify the DebugForm that an item has been added to the order
    @Override
    public void notifyItemAdded(Item item) {
        // Update GUI labels to reflect changes in the system state
        updateLabels();
    }

    // Method to notify the DebugForm that an item has been removed from the order
    @Override
    public void notifyItemRemoved(Item item) {
        // Update GUI labels to reflect changes in the system state
        updateLabels();
    }

    // Method to notify the DebugForm of a change in the session status
    @Override
    public void notifyStateChange(SessionStatus state) {
        // Update GUI labels to reflect changes in the system state
        updateLabels();
    }

    // Method to notify the DebugForm that a refresh is needed
    @Override
    public void notifyRefresh() {
        // Update GUI labels to reflect changes in the system state
        updateLabels();
    }

    // Method to notify the DebugForm that a payment has been added
    @Override
    public void notifyPaymentAdded(BigDecimal value) {
        // Update GUI labels to reflect changes in the system state
        updateLabels();
    }

    // Method to notify the DebugForm that the payment window has been closed
    @Override
    public void notifyPaymentWindowClosed() {
        // Do nothing in response to this event
    }

    // Method to notify the DebugForm of an invalid card read
    @Override
    public void notifyInvalidCardRead(Card card) {
        // Do nothing in response to this event
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
 // GUI initializer generated by IntelliJ IDEA GUI Designer
 // >>> IMPORTANT!! <<<
 // DO NOT EDIT OR ADD ANY CODE HERE!

 // Method generated by IntelliJ IDEA GUI Designer
 // >>> IMPORTANT!! <<<
 // DO NOT edit this method OR call it in your code!
 // This method initializes the UI components using IntelliJ IDEA's GUI Designer.
 // It defines the layout and components of the DebugForm's graphical user interface.
 // Each button, label, and panel is configured with specific properties.

 private void $$$setupUI$$$() {
     // Creating the root panel with a grid layout
     root = new JPanel();
     root.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));

     // Creating buttons for various actions
     causeWeightDiscrepancyButton = new JButton();
     causeWeightDiscrepancyButton.setText("Cause Weight Discrepancy");
     unblockSessionButtonButton = new JButton();
     unblockSessionButtonButton.setText("(Unblock Session) Attendant Override");
     testEnableMachineButton = new JButton();
     testEnableMachineButton.setText("Test Enable Machine");
     testDisableMachineButton = new JButton();
     testDisableMachineButton.setText("Test Disable Machine");
     addTooHeavyItemButton = new JButton();
     addTooHeavyItemButton.setText("Add Too Heavy Item");
     blockSessionButtonButton = new JButton();
     blockSessionButtonButton.setText("Block Session Button");

     // Creating a panel to display labels related to system status
     final JPanel panel1 = new JPanel();
     panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));

     // Adding labels for system status information
     expectedWeightLabel = new JLabel();
     expectedWeightLabel.setText("Expected Weight");
     customerPaymentLabel = new JLabel();
     customerPaymentLabel.setText("Customer Payment");
     weightAdjustmentLabel = new JLabel();
     weightAdjustmentLabel.setText("Weight Adjustment");
     scaleOverloadedLabel = new JLabel();
     scaleOverloadedLabel.setText("Scale overloaded?");
     stateLabel = new JLabel();
     stateLabel.setText("State");
     actualWeightLabel = new JLabel();
     actualWeightLabel.setText("Actual Weight");
     refreshButton = new JButton();
     refreshButton.setText("Refresh");
     priceLabel = new JLabel();
     priceLabel.setText("Price");

     // Adding components to the root panel
     root.add(causeWeightDiscrepancyButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     root.add(unblockSessionButtonButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     root.add(testEnableMachineButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     root.add(testDisableMachineButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     root.add(addTooHeavyItemButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     root.add(blockSessionButtonButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     root.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
     panel1.add(expectedWeightLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     panel1.add(customerPaymentLabel, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     panel1.add(weightAdjustmentLabel, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     panel1.add(scaleOverloadedLabel, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     panel1.add(stateLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     panel1.add(actualWeightLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     panel1.add(refreshButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
     panel1.add(priceLabel, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
 }

 /**
  * @noinspection ALL
  */
 public JComponent $$$getRootComponent$$$() {
     // Returns the root panel, which contains all the UI components
     return root;
 }
}
