package driver;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.screen.ITouchScreen;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;
import managers.SystemManager;
import managers.enums.SessionStatus;
import managers.interfaces.IScreen;
import utils.CardHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class PaymentSimualtorGui extends JFrame implements IScreen {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private SystemManager sm;
    private Card simulatedCard;

    private Map<String, BigDecimal> stringToCoinDenom = new HashMap<>();
    private Map<String, BigDecimal> stringToBanknoteDenom = new HashMap<>();

    /**
     * Gets the panel associated with this GUI.
     *
     * @return The JPanel representing the content of this GUI.
     */
    @Override
    public JPanel getPanel() {
        return contentPane;
    }

    /**
     * Gets the frame associated with this GUI.
     *
     * @return The JFrame object representing this GUI.
     */
    @Override
    public JFrame getFrame() {
        return this;
    }

    /**
     * Creates a mapping between string representations of denominations and their BigDecimal values.
     * This mapping is used for processing user input related to denominations.
     */
    protected void createDenominationMapping() {
        // Create mapping for banknote denominations
        for (BigDecimal denom : sm.getBanknoteDenominations()) {
            stringToBanknoteDenom.put(denom.toString(), denom);
        }

        // Create mapping for coin denominations
        for (BigDecimal denom : sm.getCoinDenominations()) {
            stringToCoinDenom.put(denom.toString(), denom);
        }
    }

    /**
     * Create the frame.
     */
    public PaymentSimualtorGui(SystemManager sm) {
        this.sm = sm;

        // creating the denominations
        createDenominationMapping();

        // creating a card
        simulatedCard = CardHelper.createCard(sm.getIssuer());

     // Set the title of the JFrame to "Payment Simulation"
        setTitle("Payment Simulation");

        // Set the default close operation for the JFrame to dispose when closed
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the bounds (position and size) of the JFrame
        setBounds(100, 100, 400, 150);

        // Create the main content pane for the JFrame
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Create a JPanel for card-related components
        JPanel CardPanel = new JPanel();
        CardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(CardPanel);
        CardPanel.setLayout(new BoxLayout(CardPanel, BoxLayout.Y_AXIS));

        // Create a JLabel for displaying "Pay With Card"
        JLabel CardLabel = new JLabel("Pay With Card");
        CardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        CardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        CardPanel.add(CardLabel);

        // Create a JButton for simulating card swiping
        JButton SwipeCardButton = new JButton("Swipe Card");
        SwipeCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        SwipeCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action listener for the "Swipe Card" button
                System.out.println("Swiping a card.");
                sm.swipeCard(simulatedCard);  // Simulate swiping a card using the SessionManager
            }
        });
     // Add the "Swipe Card" button to the CardPanel
        CardPanel.add(SwipeCardButton);

        // Create a JButton for simulating tapping a card
        JButton TapCardButton = new JButton("Tap Card");
        TapCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        TapCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action listener for the "Tap Card" button
                // TODO: Uncomment the line below once the tapCard method is implemented in SessionManager
                // sm.tapCard(SimulatedCard);
            }
        });

     // Add the "Tap Card" button to the CardPanel
        CardPanel.add(TapCardButton);

        // Create a JButton for simulating inserting a card
        JButton InsertCardButton = new JButton("Insert Card");
        InsertCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        InsertCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action listener for the "Insert Card" button
                // TODO: Uncomment the line below once the insertCard method is implemented in SessionManager
                // sm.insertCard(SimulatedCard);
            }
        });

     // Create a JPanel for handling cash and coin-related components
        JPanel CashCoinPanel = new JPanel();
        CashCoinPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(CashCoinPanel);
        CashCoinPanel.setLayout(new BoxLayout(CashCoinPanel, BoxLayout.X_AXIS));

        // Create a JPanel specifically for coin-related components
        JPanel CoinPanel = new JPanel();
        CoinPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        CashCoinPanel.add(CoinPanel);
        CoinPanel.setLayout(new BoxLayout(CoinPanel, BoxLayout.Y_AXIS));

        // Create a JLabel for displaying "Coin Value"
        JLabel CoinLabel = new JLabel("Coin Value");
        CoinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        CoinPanel.add(CoinLabel);

        // Create a JComboBox for selecting coin denominations
        JComboBox CoinValueDropdown = new JComboBox();
        CoinValueDropdown.setModel(new DefaultComboBoxModel(stringToCoinDenom.keySet().toArray()));
        CoinPanel.add(CoinValueDropdown);

        // Create a JButton for simulating inserting a coin
        JButton InsertCoinButton = new JButton("Insert Coin");
        InsertCoinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        InsertCoinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action listener for the "Insert Coin" button
                // Get the denomination from the selected item in the dropdown
                BigDecimal denom = stringToCoinDenom.get(CoinValueDropdown.getSelectedItem());
                // Simulate inserting a coin with the selected denomination using SessionManager
                sm.insertCoin(new Coin(Currency.getInstance("CAD"), denom));
                System.out.println("Inserted a coin of value: " + denom.toString());
            }
        });

     // Create a JPanel specifically for cash-related components
        JPanel CashPanel = new JPanel();
        CashPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        CashCoinPanel.add(CashPanel);
        CashPanel.setLayout(new BoxLayout(CashPanel, BoxLayout.Y_AXIS));

        // Create a JLabel for displaying "Banknote Value"
        JLabel CashLabel = new JLabel("Banknote Value");
        CashLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        CashPanel.add(CashLabel);

        // Create a JComboBox for selecting banknote denominations
        JComboBox CashValueDropdown = new JComboBox();
        CashValueDropdown.setModel(new DefaultComboBoxModel(stringToBanknoteDenom.keySet().toArray()));
        CashPanel.add(CashValueDropdown);

        // Create a JButton for simulating inserting a banknote
        JButton InsertBanknoteButton = new JButton("Insert Banknote");
        InsertBanknoteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        InsertBanknoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action listener for the "Insert Banknote" button
                // Get the denomination from the selected item in the dropdown
                BigDecimal denom = stringToBanknoteDenom.get(CashValueDropdown.getSelectedItem());
                // Simulate inserting a banknote with the selected denomination using SessionManager
                sm.insertBanknote(new Banknote(Currency.getInstance("CAD"), denom));
                System.out.println("Inserted a banknote of value: " + denom.toString());
            }
        });
     // Add the "Insert Banknote" button to the CashPanel
        CashPanel.add(InsertBanknoteButton);

        // Add a window listener to the current JFrame
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // When the window is closed, notify the SessionManager about the payment window closure
                sm.notifyPaymentWindowClosed();
            }
        });
    }

    @Override
    public void configure(ITouchScreen ts) {
        // This method does nothing when configuring the touch screen
    }

    @Override
    public void notifyItemAdded(Item item) {
        // Check if the window is visible and an item was added
        if (this.isVisible()) {
            // If so, block the session and notify the attendant about the unusual customer action
            sm.blockSession();
            sm.notifyAttendant("An item was removed when the customer was paying for an order.");
        }
    }

    @Override
    public void notifyItemRemoved(Item item) {
        // Check if the window is visible and an item was removed
        if (this.isVisible()) {
            // If so, block the session and notify the attendant about the unusual customer action
            sm.blockSession();
            sm.notifyAttendant("An item was removed when the customer was paying for an order.");
        }
    }


    @Override
    public void notifyStateChange(SessionStatus state) {
        // Check if the session manager is paid or disabled
        if (sm.isPaid() || sm.isDisabled()) {
            // Dispose of resources if the session is paid or disabled
            this.dispose();
        }
    }

    @Override
    public void notifyRefresh() {
        // TODO: Update some feedback label when a refresh notification is received
    }

    @Override
    public void notifyPaymentAdded(BigDecimal value) {
        // TODO: Update some feedback label when a payment is added
    }

    @Override
    public void notifyPaymentWindowClosed() {
        // This method does nothing when the payment window is closed
    }

    @Override
    public void notifyInvalidCardRead(Card card) {
        // TODO: Update some feedback label when an invalid card is read
    }

}
