package liquid.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * ConsolePanel contains a TextArea used
 * to print textual information.
 *
 *@version 1.0
 */
public class ConsolePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTextArea console;
	
	/**
	 * 
	 */
	public ConsolePanel(){
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.lightGray);
		setBounds(0,400,500,240);
		setVisible(true);
	}
	
	/**
	 * 
	 */
	private void initComponents(){
		JLabel l = new JLabel("Console");
		l.setBounds(205,0,90,20);
		add(l);
		
		console = new JTextArea(); 
		console.setFont(new Font("Verdana", Font.PLAIN, 12));
		console.setBounds(0, 20, 500, 160);
		console.append ("Welcome to LIQUID!\n");
		console.setEditable(false);
		JScrollPane sp = new JScrollPane(console);
		sp.setBounds(0, 20, 500, 160);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(sp);
	}
	
	/**
	 * Method appends inputed string to textArea.
	 * @param str
	 */
	public void print_to_Console(String str){
		console.append(str);
	}
}
