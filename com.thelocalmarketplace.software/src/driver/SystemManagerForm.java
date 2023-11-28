package driver;

import com.jjjwelectronics.screen.ITouchScreen;
import managers.SystemManager;
import managers.interfaces.IScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemManagerForm implements IScreen {

    private SystemManager sm;
    private JPanel root;
    private JPanel regularView;
    private JTabbedPane mainPane;
    private JPanel debugView;
    private JTable table1;
    private JButton scanByMainScannerButton;
    private JButton signalFurAttendantButton;
    private JButton payForOrderButton;
    private JButton searchForItemButton;
    private JButton scanByHandheldScannerButton;
    private JLabel feedbackLabel;
    private JLabel tableLabel;
    private JLabel buttonsLabel;

    public SystemManagerForm(SystemManager sm) {
        scanByMainScannerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Scan by Main Scanner was triggered");
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
}
