// Liam Major			- 30223023
// Md Abu Sinan			- 30154627
// Ali Akbari			- 30171539
// Shaikh Sajid Mahmood	- 30182396
// Abdullah Ishtiaq		- 30153185
// Adefikayo Akande		- 30185937
// Alecxia Zaragoza		- 30150008
// Ana Laura Espinosa Garza - 30198679
// Anmol Bansal			- 30159559
// Emmanuel Trinidad	- 30172372
// Gurjit Samra			- 30172814
// Kelvin Jamila		- 30117164
// Kevlam Chundawat		- 30180662
// Logan Miszaniec		- 30156384
// Maleeha Siddiqui		- 30179762
// Michael Hoang		- 30123605
// Nezla Annaisha		- 30123223
// Nicholas MacKinnon	- 30172737
// Ohiomah Imohi		- 30187606
// Sheikh Falah Sheikh Hasan - 30175335
// Umer Rehman			- 30169819

package driver;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.screen.ITouchScreen;
import enums.SessionStatus;
import managers.AttendantManager;
import managers.SystemManager;
import managers.interfaces.IScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class AttendantViewGUI extends JPanel implements IScreen {

    private static final long serialVersionUID = 1L;
    private final AttendantManager am;
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
    private JButton FillBagsButton;
    private JPanel EventTextHolder;

    /**
     * Create the panel.
     */
    public AttendantViewGUI(SystemManager sm, AttendantManager am) {
        this.sm = sm;
        this.am = am;

        // attaching for events
        sm.attach(this);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel EventViewerPanel = new JPanel();
        EventViewerPanel.setBackground(new Color(240, 240, 240));
        EventViewerPanel.setPreferredSize(new Dimension(200, 600));
        EventViewerPanel.setMinimumSize(new Dimension(70, 10));
        EventViewerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(EventViewerPanel);
        EventViewerPanel.setLayout(new BoxLayout(EventViewerPanel, BoxLayout.Y_AXIS));

        JScrollPane SysEventsScrollPane = new JScrollPane();
        SysEventsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        EventViewerPanel.add(SysEventsScrollPane);

        EventTextHolder = new JPanel();
        EventTextHolder.setBackground(new Color(192, 192, 192));
        EventTextHolder.setBorder(null);
        SysEventsScrollPane.setViewportView(EventTextHolder);
        EventTextHolder.setLayout(new BoxLayout(EventTextHolder, BoxLayout.Y_AXIS));

        JPanel ButtonPanel = new JPanel();
        ButtonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(ButtonPanel);
        ButtonPanel.setLayout(new GridLayout(5, 2, 5, 5));

        FillInkButton = new JButton("Fill Ink");
        FillInkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AttendantViewGUI.this.sm.maintainInk();
                FillInkButton.setEnabled(false);
                // why is there even in in the first place? aren't receipts typically heat based?
            }

        });
        FillInkButton.setEnabled(false);
        ButtonPanel.add(FillInkButton);

        EnableMachineButton = new JButton("Enable Machine");
        EnableMachineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sm.requestEnableMachine();
                sm.enableMachine();
            }
        });
        ButtonPanel.add(EnableMachineButton);

        FillPaperButton = new JButton("Fill Paper");
        FillPaperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttendantViewGUI.this.sm.maintainPaper();
                FillPaperButton.setEnabled(false);
            }
        });
        FillPaperButton.setEnabled(false);
        ButtonPanel.add(FillPaperButton);

        DisableMachineButton = new JButton("Disable Machine");
        DisableMachineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               sm.requestDisableMachine();
            }
        });
        ButtonPanel.add(DisableMachineButton);

        FillCoinsButton = new JButton("Fill Coins");
        FillCoinsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttendantViewGUI.this.sm.maintainCoinDispensers();
                FillCoinsButton.setEnabled(false);
            }
        });
        FillCoinsButton.setEnabled(false);
        ButtonPanel.add(FillCoinsButton);

        UnloadCoinsButton = new JButton("Unload Coins");
        UnloadCoinsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AttendantViewGUI.this.sm.maintainCoinStorage();

            }

        });
        ButtonPanel.add(UnloadCoinsButton);

        FillBanknotesButton = new JButton("Fill Banknotes");
        FillBanknotesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AttendantViewGUI.this.sm.maintainBanknoteDispensers();
                FillBanknotesButton.setEnabled(false);

            }

        });
        FillBanknotesButton.setEnabled(false);
        ButtonPanel.add(FillBanknotesButton);

        UnloadBanknotesButton = new JButton("Unload Banknotes");
        UnloadBanknotesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AttendantViewGUI.this.sm.maintainBanknoteStorage();
            }

        });
        ButtonPanel.add(UnloadBanknotesButton);

        UnblockSessionButton = new JButton("Unblock Session");
        UnblockSessionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sm.onAttendantOverride();
            }

        });
        ButtonPanel.add(UnblockSessionButton);

        FillBagsButton = new JButton("Fill Bags");
        FillBagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                am.maintainBags();
                notifyRefresh();
            }

        });
        ButtonPanel.add(FillBagsButton);
    }

    public void AddEvent(String eventName) {
        EventPanel newEvent = new EventPanel(this, eventName);

        EventTextHolder.add(newEvent);
    }

    public void removeEvent(EventPanel event) {
        EventTextHolder.remove(event);
    }

    @Override
    public void notifyItemAdded(Item item) {
        updateButtons();
    }

    @Override
    public void notifyItemRemoved(Item item) {
        updateButtons();
    }

    protected void updateButtons() {
        FillInkButton.setEnabled(am.isInkLow());
        FillPaperButton.setEnabled(am.isPaperLow());
        FillCoinsButton.setEnabled(am.isCoinsLow());
        FillBanknotesButton.setEnabled(am.isBanknotesLow());
        FillBagsButton.setEnabled(am.isBagsLow());
        UnloadCoinsButton.setEnabled(am.isCoinsFull());
        UnloadBanknotesButton.setEnabled(am.isBanknotesFull());
    }

    @Override
    public void notifyStateChange(SessionStatus state) {
        updateButtons();
    }

    @Override
    public void notifyRefresh() {
        updateButtons();
    }

    @Override
    public void notifyPaymentAdded(BigDecimal value) {
        updateButtons();
    }

    @Override
    public void notifyWindowClosed(Object screen) {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyInvalidCardRead(Card card) {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyReset() {
        updateButtons();
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public JFrame getFrame() {
        throw new UnsupportedOperationException("This object doesn't have a JFrame");
    }

    @Override
    public void configure(ITouchScreen touchScreen) {

    }
}
