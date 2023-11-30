package driver;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jjjwelectronics.card.Card;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;

import managers.PaymentManager;
import utils.CardHelper;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;

public class PaymentSimualtorGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PaymentManager pm;
	private Card SimulatedCard = CardHelper.createCard(CardHelper.createCardIssuer());



	/**
	 * Create the frame.
	 */
	public PaymentSimualtorGui(PaymentManager pm) {
		this.pm = pm;
		
		setTitle("Payment Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel CardPanel = new JPanel();
		CardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(CardPanel);
		CardPanel.setLayout(new BoxLayout(CardPanel, BoxLayout.Y_AXIS));
		
		JLabel CardLabel = new JLabel("Pay With Card");
		CardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		CardLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CardPanel.add(CardLabel);
		
		JButton SwipeCardButton = new JButton("Swipe Card");
		SwipeCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		SwipeCardButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pm.swipeCard(SimulatedCard);
				} catch (IOException e1) {
					// shouldn't happen
				}
				
			}});
		CardPanel.add(SwipeCardButton);
		
		JButton TapCardButton = new JButton("Tap Card");
		TapCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		TapCardButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//pm.tapCard(SimulatedCard); TODO: get actual implementation
			}});
		CardPanel.add(TapCardButton);
		
		JButton InsertCardButton = new JButton("Insert Card");
		InsertCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		InsertCardButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//pm.insertCard(SimulatedCard); TODO: get actual implementation
				
			}});
		CardPanel.add(InsertCardButton);
		
		JPanel CashCoinPanel = new JPanel();
		CashCoinPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(CashCoinPanel);
		CashCoinPanel.setLayout(new BoxLayout(CashCoinPanel, BoxLayout.X_AXIS));
		
		JPanel CoinPanel = new JPanel();
		CoinPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		CashCoinPanel.add(CoinPanel);
		CoinPanel.setLayout(new BoxLayout(CoinPanel, BoxLayout.Y_AXIS));
		
		JLabel CoinLabel = new JLabel("Coin Value");
		CoinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		CoinPanel.add(CoinLabel);
		
		JComboBox CoinValueDropdown = new JComboBox();
		CoinValueDropdown.setModel(new DefaultComboBoxModel(new String[] {"0.01", "0.05", "0.10", "0.25", "1.00", "2.00"}));
		CoinPanel.add(CoinValueDropdown);
		
		JButton InsertCoinButton = new JButton("Insert Coin");
		InsertCoinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		InsertCoinButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pm.insertCoin(new Coin( Currency.getInstance("CAD"),new BigDecimal((char[]) CoinValueDropdown.getSelectedItem())));
				} catch (DisabledException | CashOverloadException e1) {
					// GUI doesn't care if this happens
				}
				
			}});
		CoinPanel.add(InsertCoinButton);
		
		JPanel CashPanel = new JPanel();
		CashPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		CashCoinPanel.add(CashPanel);
		CashPanel.setLayout(new BoxLayout(CashPanel, BoxLayout.Y_AXIS));
		
		JLabel CashLabel = new JLabel("Banknote Value");
		CashLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		CashPanel.add(CashLabel);
		
		JComboBox CashValueDropdown = new JComboBox();
		CashValueDropdown.setModel(new DefaultComboBoxModel(new String[] {"5", "10", "20", "50", "100"}));
		CashPanel.add(CashValueDropdown);
		
		JButton InsertBanknoteButton = new JButton("Insert Banknote");
		InsertBanknoteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		InsertBanknoteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pm.insertBanknote(new Banknote( Currency.getInstance("CAD"),new BigDecimal((char[]) CashValueDropdown.getSelectedItem())));
				} catch (DisabledException | CashOverloadException e1) {
					// GUI doesn't care if this happens
				}
			}});
		CashPanel.add(InsertBanknoteButton);
		
		this.setVisible(true);
	}

}
