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
    private JButton SwipeCardButton;
    private JButton TapCardButton;
    private JButton InsertCardButton;
    private JComboBox CoinValueDropdown;
    private JButton InsertCoinButton;
    private JComboBox CashValueDropdown;
    private JButton InsertBanknoteButton;

    @Override
    public JPanel getPanel() {
        return contentPane;
    }

    @Override
    public JFrame getFrame() {
        return this;
    }

    protected void createDenominationMapping() {
        for (BigDecimal denom : sm.getBanknoteDenominations()) {
            stringToBanknoteDenom.put(denom.toString(), denom);
        }

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

        setTitle("Payment Simulation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 150);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel CardPanel = new JPanel();
        CardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(CardPanel, BorderLayout.WEST);
        CardPanel.setLayout(new BoxLayout(CardPanel, BoxLayout.Y_AXIS));

        JLabel CardLabel = new JLabel("Pay With Card");
        CardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        CardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        CardPanel.add(CardLabel);

        SwipeCardButton = new JButton("Swipe Card");
        SwipeCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        SwipeCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Swiping a card.");
                sm.swipeCard(simulatedCard);
            }
        });
        CardPanel.add(SwipeCardButton);

        TapCardButton = new JButton("Tap Card");
        TapCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        TapCardButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //sm.tapCard(SimulatedCard); TODO: get actual implementation
            }
        });
        CardPanel.add(TapCardButton);

        InsertCardButton = new JButton("Insert Card");
        InsertCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        InsertCardButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //sm.insertCard(SimulatedCard); TODO: get actual implementation

            }
        });
        CardPanel.add(InsertCardButton);

        JPanel CashCoinPanel = new JPanel();
        CashCoinPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(CashCoinPanel, BorderLayout.EAST);
        CashCoinPanel.setLayout(new BoxLayout(CashCoinPanel, BoxLayout.X_AXIS));

        JPanel CoinPanel = new JPanel();
        CoinPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        CashCoinPanel.add(CoinPanel);
        CoinPanel.setLayout(new BoxLayout(CoinPanel, BoxLayout.Y_AXIS));

        JLabel CoinLabel = new JLabel("Coin Value");
        CoinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        CoinPanel.add(CoinLabel);

        CoinValueDropdown = new JComboBox();
        CoinValueDropdown.setModel(new DefaultComboBoxModel(stringToCoinDenom.keySet().toArray()));
        CoinPanel.add(CoinValueDropdown);

        InsertCoinButton = new JButton("Insert Coin");
        InsertCoinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        InsertCoinButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BigDecimal denom = stringToCoinDenom.get(CoinValueDropdown.getSelectedItem());
                sm.insertCoin(new Coin(Currency.getInstance("CAD"), denom));
                System.out.println("Inserted a coin of value: " + denom.toString());
            }
        });
        CoinPanel.add(InsertCoinButton);

        JPanel CashPanel = new JPanel();
        CashPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        CashCoinPanel.add(CashPanel);
        CashPanel.setLayout(new BoxLayout(CashPanel, BoxLayout.Y_AXIS));

        JLabel CashLabel = new JLabel("Banknote Value");
        CashLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        CashPanel.add(CashLabel);

        CashValueDropdown = new JComboBox();
        CashValueDropdown.setModel(new DefaultComboBoxModel(stringToBanknoteDenom.keySet().toArray()));
        CashPanel.add(CashValueDropdown);

        InsertBanknoteButton = new JButton("Insert Banknote");
        InsertBanknoteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        InsertBanknoteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BigDecimal denom = stringToBanknoteDenom.get(CashValueDropdown.getSelectedItem());
                sm.insertBanknote(new Banknote(Currency.getInstance("CAD"), denom));
                System.out.println("Inserted a banknote of value: " + denom.toString());
            }
        });
        CashPanel.add(InsertBanknoteButton);
        
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel AmountInsertedLabel = new JLabel("Amount Inserted:");
        panel.add(AmountInsertedLabel);
        
        JLabel RemainingDueLabel = new JLabel("Remaining Due:");
        panel.add(RemainingDueLabel);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                sm.notifyPaymentWindowClosed();
            }
        });
    }

    @Override
    public void configure(ITouchScreen ts) {
        // does nothing
    }

    @Override
    public void notifyItemAdded(Item item) {
        // if the window is visible and this event was triggered, we
        // can assume that the customer did something that they weren't supposed to
        if (this.isVisible()) {
            sm.blockSession();
            sm.notifyAttendant("An item was removed when the customer was paying for an order.");
        }
    }

    @Override
    public void notifyItemRemoved(Item item) {
        // if the window is visible and this event was triggered, we
        // can assume that the customer did something that they weren't supposed to
        if (this.isVisible()) {
            sm.blockSession();
            sm.notifyAttendant("An item was removed when the customer was paying for an order.");
        }
    }

    @Override
    public void notifyStateChange(SessionStatus state) {
        if (sm.isPaid() || sm.isDisabled()) {
            this.dispose();
        }
    }

    @Override
    public void notifyRefresh() {
        // TODO update some feedback label
    }

    @Override
    public void notifyPaymentAdded(BigDecimal value) {
        // TODO update some feedback label
    }

    @Override
    public void notifyPaymentWindowClosed() {
        // does nothing
    }

    @Override
    public void notifyInvalidCardRead(Card card) {
        // TODO update some feedback label
    }

}
