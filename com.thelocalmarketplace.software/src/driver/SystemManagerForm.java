// Liam Major 30223023

package driver;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.bag.ReusableBag;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.screen.ITouchScreen;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import managers.SystemManager;
import managers.enums.ScanType;
import managers.enums.SessionStatus;
import managers.interfaces.IScreen;
import utils.DatabaseHelper;
import utils.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

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
    private JCheckBox doNotBagItemCheckBox;
    private JButton exitSessionButton;
    protected JLabel priceLabel;
    private final DebugForm debug;
    private PaymentSimualtorGui paymentGui;

    // TABLE HEADERS
    private final String nameColumn = "Name";
    private final String priceColumn = "Price";
    private final String baggedColumn = "Bagged?";
    private final String[] defaultTableHeaders = new String[]{
            nameColumn,
            priceColumn,
            baggedColumn
    };


    public SystemManagerForm(SystemManager sm) {
        // copying the system manager reference
        this.sm = sm;
        this.debug = new DebugForm(sm);

        // attaching to the observer
        sm.attach(this);
        sm.attach(debug);

        // setting the model
        itemsTable.setModel(generateModelSkeleton());

        // setting the label
        resetFeedbackLabel();

        // setting the state of the remove item button
        updateButtonStates();

        // setting the text of the price label
        updatePriceLabel();

        // events
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
     // ActionListener for the "Add Own Bags" button
        addOwnBagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Triggered when the customer wants to add their own bags
                System.out.println("Adding customer bags.");
                // Adding customer bags to the order using the SystemManager
                sm.addCustomerBags(DatabaseHelper.createCustomerBags());
            }
        });

        // ActionListener for the "Signal for Attendant" button
        signalForAttendantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Triggered when the customer signals for the attendant
                System.out.println("Signalling for the attendant.");
                // Signalling the attendant using the SystemManager
                sm.signalForAttendant();
            }
        });

        // ActionListener for the "Search for Item" button
        searchForItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Triggered when the user initiates a search for an item
                System.out.println("Searching for item was initiated");
                // The actual search functionality would be implemented here in a real application
            }
        });
     // ActionListener for the "Do Not Bag Item" checkbox
        doNotBagItemCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Triggered when the user interacts with the "Do Not Bag Item" checkbox
                System.out.println("Do not bag item was triggered: " + e.getActionCommand());
                // Sending a request to the SystemManager to update the bagging preference
                sm.doNotBagRequest(doNotBagItemCheckBox.isSelected());
            }
        });

        // ActionListener for the "Remove Item" button
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Originally, I wanted to implement something where each row would have a checkbox and for
                 * each box that was checked, this function would remove it.
                 *
                 * However, because Java Swing doesn't natively support rendering checkboxes, so I can't do my nice
                 * solution. WHY ISN'T THIS SUPPORTED NATIVELY?????????
                 *
                 * This is my solution, too bad!
                 */
                // Triggered when the user clicks the "Remove Item" button
                // Removing the last item from the order using the SystemManager
                sm.removeItemFromOrder(sm.getItems().get(sm.getItems().size() - 1));
            }
        });

        // ActionListener for the "Purchase Bags" button
        purchaseBagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Triggered when the customer wishes to purchase bags
                System.out.println("The customer wishes to purchase bags.");
                // The actual purchase bags functionality would be implemented here in a real application
            }
        });

        // ActionListener for the "Pay for Order" button
        payForOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Triggered when the customer wishes to pay for their order
                System.out.println("The customer wishes to pay for their order.");

                // Revealing the payment window
                paymentGui.setVisible(true);

                // Blocking buttons
                blockButtons();
                // Updating the state of buttons
                updateButtonStates();
            }
        });

        // ActionListener for the "Exit Session" button
        exitSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Triggered when the user clicks the "Exit Session" button
                System.out.println("Exiting the session.");
            }
        });
    }

 // Method to generate a skeleton DefaultTableModel for a table
    protected DefaultTableModel generateModelSkeleton() {
        return new DefaultTableModel(defaultTableHeaders, 0);
    }

    // Method to update the states of various buttons
    protected void updateButtonStates() {
        // Updating the state of the "Remove Item" button
        updateRemoveItemButtonState();
        
        // Updating the state of the "Pay for Order" button
        updatePayForOrderButtonState();
    }

    // Method to update the state of the "Pay for Order" button
    protected void updatePayForOrderButtonState() {
        // Enabling the "Pay for Order" button if the order is not empty
        payForOrderButton.setEnabled(!sm.getItems().isEmpty());

        // Disabling the "Pay for Order" button if the payment GUI is not null and visible
        if (paymentGui != null && paymentGui.isVisible()) {
            payForOrderButton.setEnabled(false);
        }

        // Disabling the "Pay for Order" button if the system manager is in a blocked state
        if (sm.isBlocked()) {
            payForOrderButton.setEnabled(false);
        }
    }


    /**
     * This function updates the enabled state of the button based on the remove
     * item check boxes.
     */
    protected void updateRemoveItemButtonState() {
        // TODO edge case where adding the first item doesn't scan properly

        // enabling the buttom if there is at least one item in the order
        removeItemButton.setEnabled(!sm.getItems().isEmpty());

        // disabling if the payment gui is not null and visible
        if (paymentGui != null) {
            if (paymentGui.isVisible()) {
                removeItemButton.setEnabled(false);
            }
        }
    }

 // Override method to retrieve the JPanel associated with this screen
    @Override
    public JPanel getPanel() {
        // Return the root JPanel of this screen
        return root;
    }

    // Override method to retrieve the JFrame associated with this screen
    @Override
    public JFrame getFrame() {
        // Throwing an UnsupportedOperationException with a message indicating that this object does not have a JFrame
        throw new UnsupportedOperationException("This object does not have a JFrame");
    }

    @Override
    public void configure(ITouchScreen touchScreen) {
        // attaching debug window to the correct panel
        debugView.setLayout(new GridLayout());
        debugView.add(debug.getPanel());

        // creating the payment gui
        this.paymentGui = new PaymentSimualtorGui(sm);
        sm.attach(paymentGui);
    }

    /**
     * This function regenerates the table each time that an item is added or removed from the managers.
     */
    protected void updateTable() {
        // getting the model
        /**
         * I really have no idea why, but whenever I remove all the rows from the model and re-add them,
         * I get duplicate clusters, i.e., an item will appear n items for each function call.
         * So, I'm creating a new model for each iteration. I really hope the JVM has good
         * garbage collection.
         */
        DefaultTableModel model = generateModelSkeleton();

        // clearing the table
        for (int i = 0; i < model.getRowCount(); ++i) {
            model.removeRow(i);
        }

        // updating the table
        for (Pair<Item, Boolean> pair : sm.getItems()) {
            Item item = pair.getKey();

            // WHY DOESN'T PRODUCT HAVE A GET DESCRIPTION FUNCTION
            String description = "You shouldn't see this";
            double price = 0;

            // getting the product information
            if (item instanceof BarcodedItem) {
                BarcodedProduct prod = DatabaseHelper.get((BarcodedItem) item);
                description = prod.getDescription();
                price = prod.getPrice();
            }
            if (item instanceof PLUCodedItem) {
                PLUCodedProduct prod = DatabaseHelper.get((PLUCodedItem) item);
                description = prod.getDescription();
                price = prod.getPrice();
            }
            if (item instanceof ReusableBag) {
                description = "Reusable Bag";
                price = DatabaseHelper.PRICE_OF_BAG.doubleValue();
            }

            // adding the row
            model.addRow(new Object[]{description, "$ " + price, pair.getValue()});
        }

        // updating the label
        if (sm.getItems().isEmpty()) {
            tableLabel.setText("Items");
        } else {
            tableLabel.setText("Items (" + sm.getItems().size() + ")");
        }

        // updating the table object
        itemsTable.setModel(model);
    }

    @Override
    public void notifyItemAdded(Item item) {
        // updating the table
        updateTable();

        // clearing the do not bag request
        doNotBagItemCheckBox.setSelected(false);

        // updating the state of the remove item button
        updateButtonStates();

        // update the text of the price label
        updatePriceLabel();
    }

    @Override
    public void notifyItemRemoved(Item item) {
        // updating the table
        updateTable();

        // updating the state of the remove item button
        updateButtonStates();

        // update the text of the price label
        updatePriceLabel();
    }

 // Override method to receive notifications about changes in the session state
    @Override
    public void notifyStateChange(SessionStatus state) {
        // Using a switch statement to handle different session states
        switch (state) {
            case NORMAL -> {
                // If the session is in the NORMAL state:
                // Reset the feedback label to its default state
                // Unblock the buttons to allow normal operation
                resetFeedbackLabel();
                unblockButtons();
            }
            case BLOCKED -> {
                // If the session is in the BLOCKED state:
                // Block the buttons to restrict user interaction
                // Determine and handle the cause of the block
                blockButtons();
                determineCause();
            }
            case PAID -> {
                // If the session is in the PAID state:
                // Print a message indicating that the session has been paid for
                // Block the buttons to prevent further actions
                System.out.println("The session has been paid for.");
                blockButtons();
            }
            case DISABLED -> {
                // If the session is in the DISABLED state:
                // TODO: Implement the logic for the disabled state
            }
        }
    }


 // Override method to receive notifications when a refresh is needed
    @Override
    public void notifyRefresh() {
        // Update the table displaying items in the order
        updateTable();
        // Update the state of buttons based on the current session state
        updateButtonStates();
        // Update the displayed price label to reflect the total price of the order
        updatePriceLabel();
    }

    // Method to update the displayed price label with the total price of the order
    protected void updatePriceLabel() {
        // Set the text of the price label to show the total price in dollars
        priceLabel.setText("Price: $" + sm.getTotalPrice().toString());
    }

    // Override method to receive notifications when a payment is added
    @Override
    public void notifyPaymentAdded(BigDecimal value) {
        // Doing nothing with this event (Placeholder comment, actual implementation may be added later)
    }

    // Override method to receive notifications when the payment window is closed
    @Override
    public void notifyPaymentWindowClosed() {
        // Unblock the buttons to allow user interaction
        unblockButtons();
        // Update the state of buttons based on the current session state
        updateButtonStates();
    }


    @Override
    public void notifyInvalidCardRead(Card card) {
        // do nothing here
    }

 // Method to determine the cause of the BLOCKED session status and update the feedback label accordingly
    protected void determineCause() {
        // Check if there was an error adding an item to the cart
        if (sm.errorAddingItem()) {
            updateFeedbackLabel("There was an error adding an item to the cart, please re-scan the item.");
        }

        // Check if the scale is overloaded
        if (sm.isScaleOverloaded()) {
            updateFeedbackLabel("The item you placed in the bagging area was too heavy, please do not bag this item.");
        }
    }

    /**
     * Reset the feedback label by setting it to null, essentially clearing the label.
     */
    protected void resetFeedbackLabel() {
        // Call the updateFeedbackLabel method with null to clear the label
        updateFeedbackLabel(null);
    }

    /**
     * Update the feedback label with the given message in red, indicating an error.
     *
     * @param message The message to be displayed in the feedback label
     */
    protected void updateFeedbackLabel(String message) {
        // Set the text color of the feedback label to red
        feedbackLabel.setForeground(Color.RED);

        // Check if the message is null or empty, if so, clear the label and return
        if (message == null || message.isEmpty()) {
            feedbackLabel.setText("");
            return;
        }

        // Log the cause message
        System.out.println("Cause: " + message);

        // Update the feedback label with the cause message
        feedbackLabel.setText("BLOCKED: " + message);
    }

    // Method to block buttons by setting their state to disabled
    protected void blockButtons() {
        // Call the setButtonsState method with false to disable the buttons
        setButtonsState(false);
    }

    // Method to unblock buttons by setting their state to enabled
    protected void unblockButtons() {
        // Call the setButtonsState method with true to enable the buttons
        setButtonsState(true);
    }


    /**
     * Set the state (enabled or disabled) of certain buttons based on the given boolean value.
     * 
     * @param state The boolean value indicating whether to enable or disable the buttons.
     */
    protected void setButtonsState(boolean state) {
        // Set the enabled state of buttons based on the provided boolean value
        scanByMainScannerButton.setEnabled(state);
        scanByHandheldScannerButton.setEnabled(state);
        searchForItemButton.setEnabled(state);
        addOwnBagsButton.setEnabled(state);
        purchaseBagsButton.setEnabled(state);
        doNotBagItemCheckBox.setEnabled(state);
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
        // Create the root JPanel with GridBagLayout
        root = new JPanel();
        root.setLayout(new GridBagLayout());
        root.setMinimumSize(new Dimension(800, 600));
        root.setOpaque(false);
        root.setPreferredSize(new Dimension(800, 600));

        // Create a JTabbedPane named mainPane
        mainPane = new JTabbedPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        root.add(mainPane, gbc);

        // Create a JPanel named regularView for the "Normal" tab
        regularView = new JPanel();
        regularView.setLayout(new GridBagLayout());
        mainPane.addTab("Normal", regularView);

        // Create labels for items and actions
        tableLabel = new JLabel();
        tableLabel.setText("Items");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        regularView.add(tableLabel, gbc);
        
        buttonsLabel = new JLabel();
        buttonsLabel.setText("Actions");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        regularView.add(buttonsLabel, gbc);

        // Create a panel1 within regularView to hold itemsTable
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        regularView.add(panel1, gbc);

        // Create a JScrollPane named scrollPane1 within panel1 to hold itemsTable
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(31);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(scrollPane1, gbc);

        // Create a JTable named itemsTable within scrollPane1
        itemsTable = new JTable();
        // Set properties for itemsTable
        itemsTable.setAutoCreateRowSorter(false);
        itemsTable.setAutoResizeMode(3);
        itemsTable.setAutoscrolls(false);
        itemsTable.setCellSelectionEnabled(true);
        itemsTable.setColumnSelectionAllowed(true);
        itemsTable.setName("itemsTable");
        itemsTable.putClientProperty("Table.isFileList", Boolean.TRUE);
        scrollPane1.setViewportView(itemsTable);

        // Create a Remove Item button
        removeItemButton = new JButton();
        removeItemButton.setText("Remove Item");
        removeItemButton.setVerticalAlignment(0);
        removeItemButton.setVerticalTextPosition(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(removeItemButton, gbc);

        // Create a panel2 within regularView to hold action buttons
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        regularView.add(panel2, gbc);

        // Create various action buttons within panel2
        scanByMainScannerButton = new JButton();
        scanByMainScannerButton.setText("Scan by Main Scanner");
        // GridBagConstraints for scanByMainScannerButton...
        panel2.add(scanByMainScannerButton, gbc);

        // Similar GridBagConstraints and creation for other buttons...

        // Create a Feedback Label
        feedbackLabel = new JLabel();
        feedbackLabel.setText("Feedback Label");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        regularView.add(feedbackLabel, gbc);

        // Create a Price Label
        priceLabel = new JLabel();
        priceLabel.setText("Price");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        regularView.add(priceLabel, gbc);

        // Create a JPanel named debugView for the "Debug" tab
        debugView = new JPanel();
        debugView.setLayout(new GridBagLayout());
        mainPane.addTab("Debug", debugView);

        // Associate labels with components
        buttonsLabel.setLabelFor(scrollPane1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        // Return the root component (JPanel) created during setup
        return root;
    }


}
