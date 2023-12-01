// Gurjit Samra - 30172814

package driver;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.TextField;
import java.awt.GridLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.List;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;

public class addItemGUI extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addItemGUI frame = new addItemGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public addItemGUI() {
		setBounds(100, 100, 599, 425);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
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
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(15dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(30dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(14dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("17dlu"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(15dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		// add item by description
		JLabel searchByTextLabel = DefaultComponentFactory.getInstance().createLabel("Search by text");
		getContentPane().add(searchByTextLabel, "8, 2, 1, 2, center, default");
		JTextField searchByTextField = new JTextField();
		searchByTextField.addCaretListener(new CaretListener() {
		// insert implementation of what to do when text entered has changed
		// i.e check if text entered is an instance of an Item that can be added to OrderManager
			public void caretUpdate(CaretEvent e) {
			}
		});
		getContentPane().add(searchByTextField, "8, 4, fill, default");
		searchByTextField.setColumns(10);
		
		
		// add item by PLU
		JLabel searchByPLUCodeTextLabel = DefaultComponentFactory.getInstance().createLabel("Search by PLU code");
		getContentPane().add(searchByPLUCodeTextLabel, "8, 6, center, default");
		NumberFormat format = NumberFormat.getNumberInstance(); 
		JFormattedTextField PLUCodeFormattedTextField = new JFormattedTextField(format);
		PLUCodeFormattedTextField.setToolTipText("Enter PLU Code");
		getContentPane().add(PLUCodeFormattedTextField, "8, 8, fill, default");
		
		
		// add item by list
		JLabel serachByListTextLabel = DefaultComponentFactory.getInstance().createLabel("Search by List");
		getContentPane().add(serachByListTextLabel, "8, 10, center, bottom");
		List list = new List();
		getContentPane().add(list, "3, 12, 10, 6");
		
		
		// Component listeners
		searchByTextField.addCaretListener(new CaretListener() {
			// insert implementation of what to do when text entered has changed
			// i.e check if text entered is an instance of an Item that can be added to OrderManager
				public void caretUpdate(CaretEvent e) {
					searchByTextField.getText();
				}
			});
		
		searchByTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		
		PLUCodeFormattedTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}
		});

	}

}
