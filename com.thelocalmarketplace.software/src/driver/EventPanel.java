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

import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Dimension;

public class EventPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton ResolveIssueButton;
	private AttendantViewGUI avgui;
	private EventPanel self;
	

	/**
	 * Create the panel.
	 * 
	 * visually represents an event from the customer station.
	 */
	public EventPanel(AttendantViewGUI avgui, String event) {
		this.self = this;
		this.avgui = avgui;
		setMinimumSize(new Dimension(200, 50));
		setPreferredSize(new Dimension(200, 50));
		setMaximumSize(new Dimension(200, 200));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textArea.setText(event);
		add(textArea);
		
		ResolveIssueButton = new JButton("Resolve Issue");
		ResolveIssueButton.setSize(new Dimension(50, 15));
		ResolveIssueButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		ResolveIssueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// kinda wish I could explicitly tell the system to destroy this, but i just gotta trust java's garbage collector
				avgui.removeEvent(self);
			}});
		add(ResolveIssueButton);
	}
}
