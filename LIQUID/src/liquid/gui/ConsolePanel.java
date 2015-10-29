package liquid.gui;

import java.awt.Color;
import java.awt.Panel;

import javax.swing.JTextArea;

public class ConsolePanel extends Panel {

	public ConsolePanel(){
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.gray);
		setBounds(0,400,500,200);
		setVisible(true);
	}
	
	private void initComponents(){

	}
}
