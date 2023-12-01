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
}
