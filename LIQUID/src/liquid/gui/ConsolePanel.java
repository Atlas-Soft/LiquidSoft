package liquid.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Class contains a text box area used to print textual information about the simulation.
 * @version 3.0
 */
public class ConsolePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextArea console;
	
	/**
	 * Constructor creates a console panel in the lower left-hand side of the simulation.
	 */
	public ConsolePanel() {
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		setBounds(0,400,500,240);
		setVisible(true);
	}
	
	/**
	 * Initializes the two components of the console panel, the label and the console itself (which is scrollable).
	 */
	private void initComponents() {
		JLabel l = new JLabel("Console");
		l.setBounds(205,0,90,20);
		add(l);
		
		console = new JTextArea(); 
		console.setFont(new Font("Consola",Font.PLAIN,12));
		console.setBounds(0,20,500,160);
		console.append("Welcome to LIQUID!\n");
		console.setEditable(false);
		
		// makes the console panel scrollable
		JScrollPane sp = new JScrollPane(console);
		sp.setBounds(0,20,500,160);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(sp);
	}
	
	/**
	 * Method appends inputed string to the text area.
	 * @param str - the string to be inputed
	 */
	public void print_to_Console(String str) {
		console.append(str);
		console.setCaretPosition(console.getDocument().getLength()); // auto-scroll
	}
}