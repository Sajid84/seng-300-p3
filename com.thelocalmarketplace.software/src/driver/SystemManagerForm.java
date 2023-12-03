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

import com.jjjwelectronics.Item;
import com.jjjwelectronics.bag.ReusableBag;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.screen.ITouchScreen;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import managers.SystemManager;
import enums.ScanType;
import enums.SessionStatus;
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
    private AddItemGui addItemGui;

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
        addOwnBagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Adding customer bags.");
                sm.addCustomerBags(DatabaseHelper.createCustomerBags());
            }
        });

        signalForAttendantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Signalling for the attendant.");
                sm.signalForAttendant();
            }
        });
        searchForItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Searching for item was initiated");
            }
        });
        doNotBagItemCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Do not bag item was triggered: " + e.getActionCommand());
                sm.doNotBagRequest(doNotBagItemCheckBox.isSelected());
            }
        });
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
                sm.removeItemFromOrder(sm.getItems().get(sm.getItems().size() - 1));
            }
        });
        purchaseBagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("The customer wishes to purchase bags.");
            }
        });
        payForOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("The customer wishes to pay for their order.");

                // revealing the payment window
                paymentGui.setVisible(true);

                // blocking buttons
                blockButtons();
                updateButtonStates();
            }
        });
        exitSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting the session.");
            }
        });
        searchForItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("The customer wishes to pay for their order.");

                // revealing the payment window
                addItemGui.setVisible(true);

                // blocking buttons
                blockButtons();
                updateButtonStates();
            }
        });
    }

    protected DefaultTableModel generateModelSkeleton() {
        return new DefaultTableModel(defaultTableHeaders, 0);
    }

    protected void updateButtonStates() {
        updateRemoveItemButtonState();
        updatePayForOrderButtonState();
    }

    protected void updatePayForOrderButtonState() {
        payForOrderButton.setEnabled(!sm.getItems().isEmpty());

        // disabling if the payment gui is not null and visible
        if (paymentGui != null) {
            if (paymentGui.isVisible()) {
                payForOrderButton.setEnabled(false);
            }
        }

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
        // attaching debug window to the correct panel
        debugView.setLayout(new GridLayout());
        debugView.add(debug.getPanel());

        // creating the payment gui
        paymentGui = new PaymentSimualtorGui(sm);
        sm.attach(paymentGui);

        // creating the add item gui
        // TODO this will crash because the program cannot find the jgoodies jar.
//        addItemGui = new AddItemGui(sm);
//        sm.attach(addItemGui);
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

    @Override
    public void notifyStateChange(SessionStatus state) {
        switch (state) {
            case NORMAL -> {
                resetFeedbackLabel();
                unblockButtons();
            }
            case BLOCKED -> {
                blockButtons();
                determineCause();
            }
            case PAID -> {
                /**
                 * For reasons that I cannot comprehend, this doesn't actually work.
                 */
                System.out.println("The session has been paid for.");
                blockButtons();
            }
            case DISABLED -> {
                // TODO implement the disabled state
            }
        }
    }

    @Override
    public void notifyRefresh() {
        updateTable();
        updateButtonStates();
        updatePriceLabel();
    }

    protected void updatePriceLabel() {
        priceLabel.setText("Price: $" + sm.getTotalPrice().toString());
    }

    @Override
    public void notifyPaymentAdded(BigDecimal value) {
        // doing nothing with this event
    }

    @Override
    public void notifyPaymentWindowClosed() {
        unblockButtons();
        updateButtonStates();
    }

    @Override
    public void notifyInvalidCardRead(Card card) {
        // do nothing here
    }

    protected void determineCause() {
        if (sm.errorAddingItem()) {
            updateFeedbackLabel("There was an error adding an item to the cart, please re-scan the item.");
        }

        if (sm.isScaleOverloaded()) {
            updateFeedbackLabel("The item you placed in the bagging area was too heavy, please do not bag this item.");
        }
    }

    /**
     * This is to quickly set the feedback label to nothing,
     * essentially clearing the label.
     */
    protected void resetFeedbackLabel() {
        updateFeedbackLabel(null);
    }

    /**
     * This updates the feedback label to the desired text in the color RED.
     * This should only be used to indicate some form of error that the system encountered.
     *
     * @param message the message to be displayed
     */
    protected void updateFeedbackLabel(String message) {
        // setting the color of the text
        feedbackLabel.setForeground(Color.RED);

        // breaking early there is no cause
        if ((message == null) || (message.isEmpty())) {
            feedbackLabel.setText("");
            return;
        }

        // logging
        System.out.println("Cause: " + message);

        // updating label with the cause
        feedbackLabel.setText("BLOCKED: " + message);
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
        root = new JPanel();
        root.setLayout(new GridBagLayout());
        root.setMinimumSize(new Dimension(800, 600));
        root.setOpaque(false);
        root.setPreferredSize(new Dimension(800, 600));
        mainPane = new JTabbedPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        root.add(mainPane, gbc);
        regularView = new JPanel();
        regularView.setLayout(new GridBagLayout());
        mainPane.addTab("Normal", regularView);
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        regularView.add(panel1, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(31);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(scrollPane1, gbc);
        itemsTable = new JTable();
        itemsTable.setAutoCreateRowSorter(false);
        itemsTable.setAutoResizeMode(3);
        itemsTable.setAutoscrolls(false);
        itemsTable.setCellSelectionEnabled(true);
        itemsTable.setColumnSelectionAllowed(true);
        itemsTable.setName("itemsTable");
        itemsTable.putClientProperty("Table.isFileList", Boolean.TRUE);
        scrollPane1.setViewportView(itemsTable);
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
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        regularView.add(panel2, gbc);
        scanByMainScannerButton = new JButton();
        scanByMainScannerButton.setText("Scan by Main Scanner");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(scanByMainScannerButton, gbc);
        signalForAttendantButton = new JButton();
        signalForAttendantButton.setText("Signal for Attendant");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(signalForAttendantButton, gbc);
        payForOrderButton = new JButton();
        payForOrderButton.setText("Pay for Order");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(payForOrderButton, gbc);
        searchForItemButton = new JButton();
        searchForItemButton.setText("Search For Item");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(searchForItemButton, gbc);
        scanByHandheldScannerButton = new JButton();
        scanByHandheldScannerButton.setText("Scan by Handheld Scanner");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(scanByHandheldScannerButton, gbc);
        addOwnBagsButton = new JButton();
        addOwnBagsButton.setText("Add Own Bags");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(addOwnBagsButton, gbc);
        purchaseBagsButton = new JButton();
        purchaseBagsButton.setText("Purchase Bags");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(purchaseBagsButton, gbc);
        doNotBagItemCheckBox = new JCheckBox();
        doNotBagItemCheckBox.setText("Do Not Bag Item");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel2.add(doNotBagItemCheckBox, gbc);
        exitSessionButton = new JButton();
        exitSessionButton.setText("Exit Session");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(exitSessionButton, gbc);
        feedbackLabel = new JLabel();
        feedbackLabel.setText("Feedback Label");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        regularView.add(feedbackLabel, gbc);
        priceLabel = new JLabel();
        priceLabel.setText("Price");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        regularView.add(priceLabel, gbc);
        debugView = new JPanel();
        debugView.setLayout(new GridBagLayout());
        mainPane.addTab("Debug", debugView);
        buttonsLabel.setLabelFor(scrollPane1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

}
