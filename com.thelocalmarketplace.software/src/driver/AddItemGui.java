// Gurjit Samra - 30172814
// Liam Major

package driver;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.screen.ITouchScreen;
import managers.SystemManager;
import managers.enums.SessionStatus;
import managers.interfaces.IScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class AddItemGui extends JFrame implements IScreen {

    private static NumberFormat PLUFormat = NumberFormat.getInstance();

    private SystemManager sm;

    JLabel searchByTextLabel = DefaultComponentFactory.getInstance().createLabel("Search by text");
    JTextField searchByTextField = new JTextField();
    JButton textSearchButton = new JButton("search");
    JLabel searchByTextStatusLabel = new JLabel("");
    JLabel searchByPLUCodeTextLabel = DefaultComponentFactory.getInstance().createLabel("Enter PLU code");
    JFormattedTextField PLUCodeFormattedTextField = new JFormattedTextField(PLUFormat);
    JButton PLUSearchButton = new JButton("search");
    JLabel searchByPLUStatusLabel = new JLabel("");
    JLabel searchByListTextLabel = DefaultComponentFactory.getInstance().createLabel("Search in List");
    List list = new List();
    JPanel keyboardPanel = new JPanel();

    protected JPanel getKeyboardPanel() {
        // TODO Auto-generated method stub
        return keyboardPanel;
    }

    /**
     * Create the frame.
     */
    public AddItemGui(SystemManager sm) {
        this.sm = sm;
        setBounds(100, 100, 747, 576);
        getContentPane().setLayout(new FormLayout(new ColumnSpec[]{
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("max(184dlu;default):grow"),
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),},
                new RowSpec[]{
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(15dlu;default)"),
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(30dlu;default)"),
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(14dlu;default)"),
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("23dlu"),
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(15dlu;default)"),
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("default:grow"),}));

        // add item by description
        searchByTextField.setColumns(10);

        getContentPane().add(searchByTextLabel, "8, 2, 1, 2, center, bottom");
        getContentPane().add(searchByTextField, "8, 4, fill, default");
        getContentPane().add(textSearchButton, "10, 4");
        getContentPane().add(searchByTextStatusLabel, "12, 4, 3, 1");


        // add item by PLU
        PLUCodeFormattedTextField.setToolTipText("Enter PLU Code");
        PLUCodeFormattedTextField.setValue(PLUFormat);

        getContentPane().add(searchByPLUCodeTextLabel, "8, 6, center, bottom");
        getContentPane().add(PLUCodeFormattedTextField, "8, 8, fill, default");
        getContentPane().add(PLUSearchButton, "10, 8");
        getContentPane().add(searchByPLUStatusLabel, "12, 8, 3, 1");


        // add item by list
        getContentPane().add(searchByListTextLabel, "8, 10, center, bottom");
        getContentPane().add(list, "3, 12, 10, 6");


        // Component listeners
        textSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = searchByTextField.getText();
                //if "text" is an item, add it to order


                // if "text" is not an item
                searchByTextStatusLabel.setText("Could not find " + "'" + text + "'" + " in database");
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
        throw new UnsupportedOperationException("This object does not have a panel.");
    }

    @Override
    public JFrame getFrame() {
        return this;
    }

    @Override
    public void configure(ITouchScreen touchScreen) {
        // do nothing
    }
}
