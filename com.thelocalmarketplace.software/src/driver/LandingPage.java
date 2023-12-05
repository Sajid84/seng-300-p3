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
import managers.SystemManager;
import enums.SessionStatus;
import managers.interfaces.IScreen;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class LandingPage implements IScreen {
    protected final SystemManager sm;
    protected JPanel root;
    protected JButton startSessionButton;
    protected JLabel welcomeLabel;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        root = new JPanel();
        root.setLayout(new GridBagLayout());
        startSessionButton = new JButton();
        startSessionButton.setText("Start Session");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        root.add(startSessionButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        root.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        root.add(spacer2, gbc);
        welcomeLabel = new JLabel();
        welcomeLabel.setText("Welcome to The Company!");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        root.add(welcomeLabel, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

    public LandingPage(SystemManager sm) {
        this.sm = sm;
    }

    @Override
    public JPanel getPanel() {
        return root;
    }

    @Override
    public JFrame getFrame() {
        throw new UnsupportedOperationException("This object does not have a JFrame.");
    }

    @Override
    public void configure(ITouchScreen touchScreen) {

    }

    public JButton getStartSessionButton() {
        return startSessionButton;
    }

    @Override
    public void notifyItemAdded(Item item) {

    }

    @Override
    public void notifyItemRemoved(Item item) {

    }

    @Override
    public void notifyStateChange(SessionStatus state) {

    }

    @Override
    public void notifyRefresh() {

    }

    @Override
    public void notifyPaymentAdded(BigDecimal value) {

    }

    @Override
    public void notifyWindowClosed(Object screen) {

    }

    @Override
    public void notifyInvalidCardRead(Card card) {

    }

    @Override
    public void notifyReset() {

    }
}
