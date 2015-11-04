package liquid.gui;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import liquid.core.LiquidApplication;

public class EnvironmentEditorPanel extends Panel{
	
	private static final long serialVersionUID = 1L;
	
	private Choice select;
	private Panel enviroDim;
	private Panel obstacles;
	private Panel sensors;
	private Choice obstacleType;
	private int enviroLen = 500;
	private int enviroWid = 365;
	
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
		select.add("Obstacles");
		select.add("Sensors");
		select.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getItem().toString() == "Environment Dimension"){
					enviroDim.setVisible(true);
				}else{
					enviroDim.setVisible(false);
				}
				if(arg0.getItem().toString() == "Obstacles"){
					obstacles.setVisible(true);
				}else{
					obstacles.setVisible(false);
				}
				if(arg0.getItem().toString() == "Sensors"){
					sensors.setVisible(true);
				}else{
					sensors.setVisible(false);
				}
				
			}
        });
		add(select);
		
		enviroDim = new Panel();
		enviroDim.setBounds(5, 35, 240, 240);
		enviroDim.setBackground(Color.lightGray);
		enviroDim.setLayout(null);
		
		Label l = new Label("Length:");
		l.setBounds(5,0,110,25);
		enviroDim.add(l);
		
		final TextField tf = new TextField("0 to 300");
		tf.setBounds(5, 25, 110, 25);
		tf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.enviroLength = Integer.parseInt(tf.getText());
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		enviroDim.add(tf);
		
		l = new Label("Width:");
		l.setBounds(5,50,110,25);
		enviroDim.add(l);
		
		final TextField tf1 = new TextField("0 to 300");
		tf1.setBounds(5, 75, 110, 25);
		tf1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.enviroWidth = Integer.parseInt(tf1.getText());
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		enviroDim.add(tf1);
		
		add(enviroDim);
		
		obstacles = new Panel();
		obstacles.setBounds(5, 35, 240, 240);
		obstacles.setBackground(Color.lightGray);
		obstacles.setLayout(null);
		
		l = new Label("Object Type:");
		l.setBounds(5,0,110,25);
		obstacles.add(l);
		
		obstacleType = new Choice();
		obstacleType.setBounds(5, 25, 110, 25);
		obstacleType.add("Rectangular");
		obstacleType.add("Circular");
		obstacles.add(obstacleType);
		
		add(obstacles);
		
		sensors = new Panel();
		sensors.setBounds(5, 35, 240, 240);
		sensors.setBackground(Color.blue);
		add(sensors);
		
	}

	public int getEnvironmentLength(){
		return enviroLen;
	}
	
	public int getEnvironmentWidth(){
		return enviroWid;
	}
}
