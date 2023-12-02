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
	public EventPanel(AttendantViewGUI avgui,String event) {
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
