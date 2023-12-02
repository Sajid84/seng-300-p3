package driver;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.screen.ITouchScreen;

import managers.SystemManager;
import managers.enums.ScanType;
import managers.enums.SessionStatus;
import managers.interfaces.IScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;


public class SearchItemGui extends JFrame implements IScreen {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private SystemManager sm;
    private JTextField searchTextField;
    private JLabel errorLabel;

    @Override
    public JPanel getPanel() {
        return contentPane;
    }

    @Override
    public JFrame getFrame() {
        return this;
    }

    public SearchItemGui(SystemManager sm) {
        this.sm = sm;

        setTitle("Search For Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 150);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel textLabel = new JLabel("Type to find item:");
        contentPane.add(textLabel);

        searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(200, 30));
        contentPane.add(searchTextField);

        JButton searchButton = new JButton("Search");
        contentPane.add(searchButton);

        errorLabel = new JLabel();
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        contentPane.add(errorLabel);

        // Add action listener to the Search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                sm.notifyPaymentWindowClosed();
            }
        });
    }
    
    // need to work on this!!
    protected void performSearch() {
        String searchText = searchTextField.getText();
        Item foundItem = sm.searchItemsByText(searchText);

        if (foundItem != null) {
            // Item found, add it to the order
            sm.addItemToOrder(foundItem, ScanType.MAIN);
            dispose(); // Close the search window
        } else {
            // Item not found, display an error message
            errorLabel.setText("Product not found, please try again");
        }
    }

    // Reset the window components
    private void resetWindow() {
        searchTextField.setText("");
        errorLabel.setText("");
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            // If the window is becoming visible, reset the components
            resetWindow();
        }
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
	public void configure(ITouchScreen touchScreen) {
		// TODO Auto-generated method stub
		
	}



}