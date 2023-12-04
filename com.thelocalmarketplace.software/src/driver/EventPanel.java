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

// Package declaration for the 'driver' package
package driver;

// Import statements for necessary Java classes
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

// Class definition for the 'EventPanel' class, extending 'JPanel'
public class EventPanel extends JPanel {

	// Serialization version UID
	private static final long serialVersionUID = 1L;
	
	// Declaration of private member variables
	private JButton ResolveIssueButton;
	private AttendantViewGUI avgui;
	private EventPanel self;

	/**
	 * Constructor for the EventPanel class.
	 * @param avgui - An instance of the AttendantViewGUI class
	 * @param event - A string representing the event from the customer station
	 */
	public EventPanel(AttendantViewGUI avgui, String event) {
		// Initialization of member variables
		this.self = this;
		this.avgui = avgui;
		
		// Setting the minimum, preferred, and maximum size of the panel
		setMinimumSize(new Dimension(200, 50));
		setPreferredSize(new Dimension(200, 50));
		setMaximumSize(new Dimension(200, 200));
		
		// Setting the layout manager for the panel as BoxLayout in the Y_AXIS direction
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Creating and configuring a JTextArea to display the event text
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textArea.setText(event);
		add(textArea);
		
		// Creating and configuring a 'Resolve Issue' button
		ResolveIssueButton = new JButton("Resolve Issue");
		ResolveIssueButton.setSize(new Dimension(50, 15));
		ResolveIssueButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		// Adding an ActionListener to the 'Resolve Issue' button
		ResolveIssueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Action performed when the button is clicked, removing the event from the GUI
				avgui.removeEvent(self);
			}
		});
		
		// Adding the 'Resolve Issue' button to the panel
		add(ResolveIssueButton);
	}
}
