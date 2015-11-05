package liquid.gui;

import java.awt.Button;
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
	
	Choice select;
	Panel enviroDim;
	Panel obstacles;
	Panel sensors;
	Choice obstacleType;
	TextField obstacleX;
	TextField obstacleY;
	TextField obstacleL;
	TextField obstacleW;
	
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
		select.add("Sensors/Forces");
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
				if(arg0.getItem().toString() == "Sensors/Forces"){
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
		
		TextField tf = new TextField("410");
		tf.setBounds(5, 25, 110, 25);
		enviroDim.add(tf);
		
		l = new Label("Width:");
		l.setBounds(5,50,110,25);
		enviroDim.add(l);
		
		TextField tf1 = new TextField("355");
		tf1.setBounds(5, 75, 110, 25);
		enviroDim.add(tf1);
		
		Button update = new Button("Update");
		update.setBounds(5,125,110,25);
		update.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try{
					int l = Integer.parseInt(tf.getText());
					if(l >= 0 && l <= 410 ){
						LiquidApplication.getGUI().variables.enviroLength = l;
						LiquidApplication.getGUI().sim.repaint();
					}else LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Length Is Not Between 0 and 410\n");
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Length Is Not An Integer.\n");
				}
				try{
					int w = Integer.parseInt(tf1.getText());
					if(w >= 0 && w <= 355 ){
						LiquidApplication.getGUI().variables.enviroWidth = w;
						LiquidApplication.getGUI().sim.repaint();
					}else LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Width Is Not Between 0 and 355\n");
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Width Is Not An Integer.\n");
				}
			}
        });
		enviroDim.add(update);
		
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
		
		l = new Label("X:");
		l.setBounds(125,0,110,25);
		obstacles.add(l);
		
		obstacleX = new TextField("0");
		obstacleX.setBounds(125, 25, 110, 25);
		obstacles.add(obstacleX);
		
		l = new Label("Y:");
		l.setBounds(125,50,110,25);
		obstacles.add(l);
		
		obstacleY = new TextField("0");
		obstacleY.setBounds(125, 75, 110, 25);
		obstacles.add(obstacleY);
		
		l = new Label("Length:");
		l.setBounds(125,100,110,25);
		obstacles.add(l);
		
		obstacleL = new TextField("10");
		obstacleL.setBounds(125, 125, 110, 25);
		obstacles.add(obstacleL);
		
		l = new Label("Width:");
		l.setBounds(125,150,110,25);
		obstacles.add(l);
		
		obstacleW = new TextField("10");
		obstacleW.setBounds(125, 175, 110, 25);
		obstacles.add(obstacleW);
		
		Button create = new Button("Create");
		create.setBounds(125,210,110,25);
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				String arg = obstacleType.getSelectedItem() + " ";
				arg += obstacleX.getText() + " ";
				arg += obstacleY.getText() + " ";
				arg += obstacleL.getText() + " ";
				arg += obstacleW.getText();
				LiquidApplication.getGUI().variables.obstacles.add(arg);
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		obstacles.add(create);
		
		Button selectNext = new Button("Select Next");
		selectNext.setBounds(5,50,110,25);
		selectNext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				
			}
        });
		obstacles.add(selectNext);
		
		Button selectPrev = new Button("Select Prev");
		selectPrev.setBounds(5,75,110,25);
		selectPrev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				
			}
        });
		obstacles.add(selectPrev);
		
		Button selectUpdate = new Button("Update Selected");
		selectUpdate.setBounds(5,210,110,25);
		selectUpdate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				
			}
        });
		obstacles.add(selectUpdate);
		
		add(obstacles);
		
		sensors = new Panel();
		sensors.setBounds(5, 35, 240, 240);
		sensors.setBackground(Color.blue);
		add(sensors);
		
	}
	
}