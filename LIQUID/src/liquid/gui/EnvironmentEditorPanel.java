package liquid.gui;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;

public class EnvironmentEditorPanel extends Panel{

	private Choice select;
	
	public EnvironmentEditorPanel() {
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.gray);
		setBounds(25,180,250,270);
		setVisible(true);
	}
	
	public void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		select = new Choice();
		select.setBounds(5, 5, 240, 30);
		select.add("Environment Dimension");
		select.add("Objects");
		select.add("Sensors");
		select.setVisible(true);
		add(select);
	}

}
