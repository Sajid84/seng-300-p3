package driver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import com.jjjwelectronics.card.Card;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;

import managers.PaymentManager;
import utils.CardHelper;

/*
 * DO NOT USE THIS! THIS IS OLD!
 * 
 * use PaymentSimulatorGui.java instead, its a lot better!
 * 
 * I REPEAT, DO NOT USE THIS!
 */

public class PaymentSimulationMenu {
	
	private PaymentManager pm;
	private Card SimulatedCard;
	
	private JFrame PaymentWindow = new JFrame("Payment Simulation");
	private JPanel ButtonPane = new JPanel();
		
	private JPanel CardPanel = new JPanel();
	private JButton SwipeCardButton = new JButton("Swipe Card");
	private JButton TapCardButton = new JButton("Tap Card");
	private JButton InsertCardButton = new JButton("Insert Card");
	
	private JPanel CoinCashPanel = new JPanel();
	private JButton InsertCoinButton = new JButton("Insert Coin");
	private JButton InsertBanknoteButton = new JButton("Insert Banknote");
	
	public PaymentSimulationMenu(PaymentManager pm) {
		this.pm = pm;
		SimulatedCard = CardHelper.createCard(CardHelper.createCardIssuer());
		buildWindow();
		setupEvents();
		PaymentWindow.setSize(200, 400);
		PaymentWindow.setVisible(true);
		
	}
	
	private void buildWindow() {
		CardPanel.setLayout(new BoxLayout(CardPanel, BoxLayout.Y_AXIS));
		CardPanel.add(SwipeCardButton);
		CardPanel.add(Box.createRigidArea(new Dimension(0,5)));
		CardPanel.add(TapCardButton);
		CardPanel.add(Box.createRigidArea(new Dimension(0,5)));
		CardPanel.add(InsertCardButton);
		
		CoinCashPanel.setLayout(new BoxLayout(CoinCashPanel, BoxLayout.Y_AXIS));
		CoinCashPanel.add(InsertCoinButton);
		CoinCashPanel.add(Box.createRigidArea(new Dimension(0,5)));
		CoinCashPanel.add(InsertBanknoteButton);
		
		ButtonPane.setLayout(new BoxLayout(ButtonPane, BoxLayout.X_AXIS));
		ButtonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		ButtonPane.add(CoinCashPanel);
		ButtonPane.add(Box.createRigidArea(new Dimension(5,0)));
		ButtonPane.add(CardPanel);
		
		PaymentWindow.add(ButtonPane);
		
	}
	
	private void setupEvents() {
		SwipeCardButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pm.swipeCard(SimulatedCard);
				} catch (IOException e1) {
					// shouldn't happen
				}
				
			}
			
		});
		TapCardButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//pm.tapCard(SimulatedCard); // TODO: update when implemented
				
			}
			
		});
		InsertCardButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//pm.insertCard(SimulatedCard); // TODO: update when implemented
				
			}
			
		});
		
		InsertCoinButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pm.insertCoin(null);
				} catch (DisabledException | CashOverloadException e1) {
					// do nothing
				} // TODO: put an actual coin here
				
			}
			
		});
		InsertBanknoteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pm.insertBanknote(null);
				} catch (DisabledException | CashOverloadException e1) {
					// do nothing
				} // TODO: put an actual banknote here
				
			}
			
		});
	}
}
