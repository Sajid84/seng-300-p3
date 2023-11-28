package driver;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.screen.ITouchScreen;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import managers.SystemManager;
import managers.enums.ScanType;
import managers.interfaces.IScreen;
import utils.DatabaseHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class SystemManagerForm implements IScreen {

    private SystemManager sm;
    private JPanel root;
    private JPanel regularView;
    private JTabbedPane mainPane;
    private JPanel debugView;
    private JTable itemsTable;
    private JButton scanByMainScannerButton;
    private JButton signalFurAttendantButton;
    private JButton payForOrderButton;
    private JButton searchForItemButton;
    private JButton scanByHandheldScannerButton;
    private JLabel feedbackLabel;
    private JLabel tableLabel;
    private JLabel buttonsLabel;

    public SystemManagerForm(SystemManager sm) {
        // copying the system manager reference
        this.sm = sm;

        // setup the table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Price");

        // setting the model
        itemsTable.setModel(model);

        // listener
        scanByMainScannerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Scan by Main Scanner was triggered");

                // getting random item
                BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

                // adding an item to the order
                sm.addItemToOrder(item, ScanType.MAIN);
            }
        });
        scanByHandheldScannerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Scan by Handheld Scanner was triggered");

                // getting random item
                BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

                // adding an item to the order
                sm.addItemToOrder(item, ScanType.HANDHELD);
            }
        });
    }

    @Override
    public JPanel getPanel() {
        return root;
    }

    @Override
    public void configure(ITouchScreen touchScreen) {
        touchScreen.getFrame().setContentPane(root);
    }

    protected void updateTable(Item item, boolean append) {
        // getting the model
        DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();

        if (append) {
            // getting the product
            if (item instanceof BarcodedItem) {
                BarcodedProduct prod = DatabaseHelper.get((BarcodedItem) item);
                model.addRow(new Object[]{prod.getDescription(), "$ " + prod.getPrice()});
            }
        }
    }

    @Override
    public void itemWasAdded(Item item) {
        updateTable(item, true);
    }

    @Override
    public void itemWasRemoved(Item item) {
        updateTable(item, false);
    }
}
