// Gurjit Samra - 30172814
// Liam Major 30223023

package driver;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.screen.ITouchScreen;
import enums.ScanType;
import enums.SessionStatus;
import managers.SystemManager;
import managers.interfaces.IScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class AddItemGUI extends JPanel implements IScreen {

    private static NumberFormat PLUFormat = NumberFormat.getInstance();

    private SystemManager sm;

    JLabel searchByTextLabel = new JLabel("Search by text");
    JTextField searchByTextField = new JTextField();
    JButton textSearchButton = new JButton("search");
    JLabel searchByTextStatusLabel = new JLabel("");
    JLabel searchByPLUCodeTextLabel = new JLabel("Enter PLU code");
    JFormattedTextField PLUCodeFormattedTextField = new JFormattedTextField(PLUFormat);
    JButton PLUSearchButton = new JButton("search");
    JLabel searchByPLUStatusLabel = new JLabel("");
    JLabel searchByListTextLabel = new JLabel("Search in List");
    List list = new List();
    JPanel keyboardPanel = new JPanel();

    protected JPanel getKeyboardPanel() {
        // TODO Auto-generated method stub
        return keyboardPanel;
    }

    /**
     * Create the frame.
     */
    public AddItemGUI(SystemManager sm) {
        this.sm = sm;

        GridLayout gridLayout = new GridLayout();
        gridLayout.setVgap(2);
        gridLayout.setHgap(2);
        gridLayout.setColumns(1);
        gridLayout.setRows(0);
        setLayout(gridLayout);

        // add item by description
        searchByTextField.setColumns(8);
        searchByTextLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        searchByTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(searchByTextLabel, "8, 2, 1, 2, center, bottom");
        add(searchByTextField, "8, 4, fill, default");
        add(textSearchButton, "10, 4");
        add(searchByTextStatusLabel, "12, 4, 3, 1");

        // add item by PLU
        PLUCodeFormattedTextField.setToolTipText("Enter PLU Code");
        PLUCodeFormattedTextField.setValue(PLUFormat);
        searchByPLUCodeTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        searchByPLUCodeTextLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        add(searchByPLUCodeTextLabel, "8, 6, center, bottom");
        add(PLUCodeFormattedTextField, "8, 8, fill, default");
        add(PLUSearchButton, "10, 8");
        add(searchByPLUStatusLabel, "12, 8, 3, 1");
        searchByListTextLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        searchByListTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // add item by list
        add(searchByListTextLabel, "8, 10, center, bottom");
        add(list, "3, 12, 10, 6");

        // Component listeners
        textSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = searchByTextField.getText();

                //if "text" is an item, add it to order
                Item item = sm.searchItemsByText(text);
                if (item == null) {
                    searchByTextStatusLabel.setText("Could not find " + "'" + text + "'" + " in database");
                } else {
                    sm.addItemToOrder(item, ScanType.MAIN);
                }
            }
        });

        searchByTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                PopupKeyboard keyboard = new PopupKeyboard(searchByTextField);

            }
        });

        PLUCodeFormattedTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                PopupKeyboard keyboard = new PopupKeyboard(searchByTextField);
            }
        });

        PLUSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object PLUcode = PLUCodeFormattedTextField.getValue();
                //if PLU code exists, continue with order


                // if PLU code does not exist
                searchByTextStatusLabel.setText("Could not find PLU code: " + "'" + PLUcode + "'" + " in the database");
            }
        });

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
        throw new UnsupportedOperationException("This object does not have a panel.");
    }

    @Override
    public void configure(ITouchScreen touchScreen) {
        // TODO Auto-generated method stub

    }
}
