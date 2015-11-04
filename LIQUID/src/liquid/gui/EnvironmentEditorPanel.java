package liquid.gui;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import liquid.core.LiquidApplication;

public class EnvironmentEditorPanel extends Panel{
	
	private static final long serialVersionUID = 1L;
	
	private Choice select;
	private Panel enviroDim;
	private Panel objects;
	private Panel sensors;
	
	public EnvironmentEditorPanel() {
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.gray);
		setBounds(25,175,250,280);
		setVisible(true);
	}
	
	public void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		select = new Choice();
		select.setBounds(5, 5, 240, 25);
		select.add("Environment Dimension");
		select.add("Objects");
		select.add("Sensors");
		select.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getItem().toString() == "Environment Dimension"){
					enviroDim.setVisible(true);
				}else{
					enviroDim.setVisible(false);
				}
				
			}
        });
		add(select);
		
		enviroDim = new Panel();
		enviroDim.setBounds(0, 35, 250, 245);
		enviroDim.setBackground(Color.white);
		add(enviroDim);
		
	}

}
