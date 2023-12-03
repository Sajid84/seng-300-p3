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

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.screen.ITouchScreen;
import managers.SystemManager;
import enums.ScanType;
import enums.SessionStatus;
import managers.interfaces.IScreen;
import utils.DatabaseHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class DebugForm implements IScreen {
    private final SystemManager sm;
    private JButton causeWeightDiscrepancyButton;
    private JButton unblockSessionButtonButton;
    private JButton testEnableMachineButton;
    private JButton testDisableMachineButton;
    private JButton addTooHeavyItemButton;
    private JButton blockSessionButtonButton;
    private JLabel expectedWeightLabel;
    private JLabel weightAdjustmentLabel;
    private JLabel scaleOverloadedLabel;
    private JLabel stateLabel;
    private JLabel actualWeightLabel;
    private JPanel root;
    private JButton refreshButton;
    private JLabel customerPaymentLabel;
    private JLabel priceLabel;

    public DebugForm(SystemManager sm) {
        this.sm = sm;

        causeWeightDiscrepancyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Causing a weight discrepancy.");

                // creating the item
                BarcodedItem item = DatabaseHelper.createWeightDiscrepancy();

                // adding it to the order
                sm.addItemToOrder(item, ScanType.MAIN);
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

                // creating the item
                BarcodedItem item = DatabaseHelper.createItemTooHeavy();

                // adding it to the order
                sm.addItemToOrder(item, ScanType.MAIN);
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sm.notifyRefresh();
            }
        });
    }

    @Override
    public JPanel getPanel() {
        return root;
    }

    @Override
    public JFrame getFrame() {
        throw new UnsupportedOperationException("This object does not have a JFrame");
    }

    @Override
    public void configure(ITouchScreen touchScreen) {
        // do nothing here
    }

    protected void updateLabels() {
        expectedWeightLabel.setText("Expected Weight: " + sm.getExpectedMass());
        actualWeightLabel.setText("Actual Weight: " + sm.getActualWeight());
        weightAdjustmentLabel.setText("Weight Adjustment: " + sm.getWeightAdjustment());
        stateLabel.setText("State: " + sm.getState().toString());
        scaleOverloadedLabel.setText("Scale Overloaded? " + sm.isScaleOverloaded());
        priceLabel.setText("Price of Order: " + sm.getTotalPrice());
        customerPaymentLabel.setText("Customer Payment: " + sm.getCustomerPayment());
    }

    @Override
    public void notifyItemAdded(Item item) {
        updateLabels();
    }

    @Override
    public void notifyItemRemoved(Item item) {
        updateLabels();
    }

    @Override
    public void notifyStateChange(SessionStatus state) {
        updateLabels();
    }

    @Override
    public void notifyRefresh() {
        updateLabels();
    }

    @Override
    public void notifyPaymentAdded(BigDecimal value) {
        updateLabels();
    }

    @Override
    public void notifyPaymentWindowClosed() {
        // do nothing with this
    }

    @Override
    public void notifyInvalidCardRead(Card card) {
        // do nothing here
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
    private void $$$setupUI$$$() {
        root = new JPanel();
        root.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        causeWeightDiscrepancyButton = new JButton();
        causeWeightDiscrepancyButton.setText("Cause Weight Discrepancy");
        root.add(causeWeightDiscrepancyButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        unblockSessionButtonButton = new JButton();
        unblockSessionButtonButton.setText("(Unblock Session) Attendant Override");
        root.add(unblockSessionButtonButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        testEnableMachineButton = new JButton();
        testEnableMachineButton.setText("Test Enable Machine");
        root.add(testEnableMachineButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        testDisableMachineButton = new JButton();
        testDisableMachineButton.setText("Test Disable Machine");
        root.add(testDisableMachineButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addTooHeavyItemButton = new JButton();
        addTooHeavyItemButton.setText("Add Too Heavy Item");
        root.add(addTooHeavyItemButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        blockSessionButtonButton = new JButton();
        blockSessionButtonButton.setText("Block Session Button");
        root.add(blockSessionButtonButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        root.add(panel1, new GridConstraints(0, 2, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        expectedWeightLabel = new JLabel();
        expectedWeightLabel.setText("Expected Weight");
        panel1.add(expectedWeightLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        customerPaymentLabel = new JLabel();
        customerPaymentLabel.setText("Customer Payment");
        panel1.add(customerPaymentLabel, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        weightAdjustmentLabel = new JLabel();
        weightAdjustmentLabel.setText("Weight Adjustment");
        panel1.add(weightAdjustmentLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scaleOverloadedLabel = new JLabel();
        scaleOverloadedLabel.setText("Scale overloaded?");
        panel1.add(scaleOverloadedLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stateLabel = new JLabel();
        stateLabel.setText("State");
        panel1.add(stateLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        actualWeightLabel = new JLabel();
        actualWeightLabel.setText("Actual Weight");
        panel1.add(actualWeightLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refreshButton = new JButton();
        refreshButton.setText("Refresh");
        panel1.add(refreshButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceLabel = new JLabel();
        priceLabel.setText("Price");
        panel1.add(priceLabel, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

}
