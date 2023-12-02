package driver;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.border.EmptyBorder;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.screen.ITouchScreen;

import managers.SystemManager;
import managers.enums.SessionStatus;
import managers.interfaces.IScreen;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Component;
import java.awt.Color;

public class AttendantViewGUI extends JPanel implements IScreen{

	private static final long serialVersionUID = 1L;
	private SystemManager sm;
	private JButton FillInkButton;
	private JButton EnableMachineButton;
	private JButton FillPaperButton;
	private JButton DisableMachineButton;
	private JButton FillCoinsButton;
	private JButton UnloadCoinsButton;
	private JButton FillBanknotesButton;
	private JButton UnloadBanknotesButton;
	private JButton UnblockSessionButton;
	private JButton ExtraActionButton;
	private JPanel EventViewerPanel;

	/**
	 * Create the panel.
	 */
	public AttendantViewGUI(SystemManager sm) {
		this.sm = sm;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		EventViewerPanel = new JPanel();
		EventViewerPanel.setBackground(new Color(192, 192, 192));
		EventViewerPanel.setPreferredSize(new Dimension(200, 600));
		EventViewerPanel.setMinimumSize(new Dimension(70, 10));
		EventViewerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(EventViewerPanel);
		EventViewerPanel.setLayout(new BoxLayout(EventViewerPanel, BoxLayout.Y_AXIS));
		
		JPanel ButtonPanel = new JPanel();
		add(ButtonPanel);
		ButtonPanel.setLayout(new GridLayout(5, 2, 5, 5));
		
		FillInkButton = new JButton("Fill Ink");
		FillInkButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sm.maintainInk();
				
			}
			
		});
		ButtonPanel.add(FillInkButton);
		
		EnableMachineButton = new JButton("Enable Machine");
		EnableMachineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sm.requestEnableMachine();
				// TODO Auto-generated method stub
				
			}
			
		});
		ButtonPanel.add(EnableMachineButton);
		
		FillPaperButton = new JButton("Fill Paper");
		FillPaperButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sm.maintainPaper();
				// TODO Auto-generated method stub
				
			}
			
		});
		ButtonPanel.add(FillPaperButton);
		
		DisableMachineButton = new JButton("Disable Machine");
		DisableMachineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sm.requestDisableMachine();
				// TODO Auto-generated method stub
				
			}
			
		});
		ButtonPanel.add(DisableMachineButton);
		
		FillCoinsButton = new JButton("Fill Coins");
		FillCoinsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sm.maintainCoinDispensers();
				// TODO Auto-generated method stub
				
			}
			
		});
		ButtonPanel.add(FillCoinsButton);
		
		UnloadCoinsButton = new JButton("Unload Coins");
		UnloadCoinsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sm.maintainCoinStorage();
				// TODO Auto-generated method stub
				
			}
			
		});
		ButtonPanel.add(UnloadCoinsButton);
		
		FillBanknotesButton = new JButton("Fill Banknotes");
		FillBanknotesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sm.maintainBanknoteDispensers();
				// TODO Auto-generated method stub
				
			}
			
		});
		ButtonPanel.add(FillBanknotesButton);
		
		UnloadBanknotesButton = new JButton("Unload Banknotes");
		UnloadBanknotesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sm.maintainBanknoteStorage();
				// TODO Auto-generated method stub
				
			}
			
		});
		ButtonPanel.add(UnloadBanknotesButton);
		
		UnblockSessionButton = new JButton("Unblock Session");
		UnblockSessionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sm.unblockSession();
				// TODO Auto-generated method stub
				
			}
			
		});
		ButtonPanel.add(UnblockSessionButton);
		
		ExtraActionButton = new JButton("EXTRA");
		ExtraActionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO or not TODO, that is the question (yes if we need an extra thing to do)
				
			}
			
		});
		ButtonPanel.add(ExtraActionButton);

	}
	
	public void AddEvent(String eventName) {
		
	}

	@Override
	public void notifyItemAdded(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyItemRemoved(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyStateChange(SessionStatus state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyPaymentAdded(BigDecimal value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyPaymentWindowClosed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyInvalidCardRead(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPanel getPanel() {
		return this;
	}

	@Override
	public JFrame getFrame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void configure(ITouchScreen touchScreen) {
		// TODO Auto-generated method stub
		
	}

}
