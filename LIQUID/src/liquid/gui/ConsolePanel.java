package liquid.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * ConsolePanel class creates a textbox area below the SimulationPanel. This is used to print various information
 * regarding the simulator, such as when the simulation begins, pauses, or ends as well as flowmeter velocities.
 */
public class ConsolePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextArea console;
	
	/**
	 * Constructor creates a console panel in the lower left-hand corner of the simulator.
	 */
	public ConsolePanel() {
		super();
		initComponents();
		
		// allows programmer-defined parameter specifications
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		setBounds(0,400,500,240);
		setVisible(true);
	}
	
	/**
	 * Method initializes the label & scrollable console parts of the console panel.
	 */
	private void initComponents() {
		JLabel l = new JLabel("Console");
		l.setBounds(205,0,90,20);
		add(l);
		
		console = new JTextArea();
		console.setBounds(0,20,500,160);
		console.setFont(new Font("Consola",Font.PLAIN,12));
		console.append("Welcome to LIQUID!\n");
		console.setEditable(false);
		
		// makes the console panel scrollable
		JScrollPane sp = new JScrollPane(console);
		sp.setBounds(0,20,500,160);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(sp);
	}
	
	/**
	 * Method appends the inputed string to the text area.
	 * @param message - message to be displayed
	 */
	public void printToConsole(String message) {
		console.append(message);
		console.setCaretPosition(console.getDocument().getLength()); // auto-scroll
	}
}