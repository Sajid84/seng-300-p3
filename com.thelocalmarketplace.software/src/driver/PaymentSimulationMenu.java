package driver;

import java.awt.*;

import javax.swing.*;

import com.jjjwelectronics.card.Card;

import managers.PaymentManager;
import utils.CardHelper;

public class PaymentSimulationMenu {
	
	private PaymentManager pm;
	private Card SimulatedCard;
	
	private JFrame PaymentWindow;
	
	private JPanel CardPanel;
	private JButton SwipeCardButton;
	private JButton TapCardButton;
	
	private JPanel CoinCashPanel;
	private JButton InsertCoinButton;
	private JButton InsertBanknoteButton;
	
	public PaymentSimulationMenu(PaymentManager pm) {
		this.pm = pm;
		SimulatedCard = CardHelper.createCard(CardHelper.createCardIssuer());
		buildWindow();
		
	}
	
	private void buildWindow() {
		PaymentWindow = new JFrame();
		PaymentWindow.setLayout(new GridLayout(1,2));
		CardPanel = new JPanel();
		SwipeCardButton = new JButton("Swipe Card");
		TapCardButton = new JButton("Tap Card");
		
	}
}
