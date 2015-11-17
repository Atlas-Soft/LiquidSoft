package liquid.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import liquid.core.LiquidApplication;

public class EnvironmentEditorPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	JComboBox<String> select;
	JPanel enviro;
		JTextField enviroLen;
		JTextField enviroWid;
	JPanel obstacles;
		JComboBox<String> obstacleType;
		JTextField obstacleX;
		JTextField obstacleY;
		JTextField obstacleL;
		JTextField obstacleW;
	JPanel forces;
		JComboBox<String> forceType;
		JTextField forceX;
		JTextField forceY;
		JTextField forceXComp;
		JTextField forceYComp;
	JPanel sensors;
		JComboBox<String> sensorType;		
		JTextField sensorX;
		JTextField sensorY;
	
	public EnvironmentEditorPanel() {
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.gray);
		setBounds(25,190,250,280);
		setVisible(true);
	}
	
	public void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		String[] options = {"Environment","Obstacles","Forces","Sensors"};
		
		select = new JComboBox<String>(options);
		select.setBounds(5, 5, 240, 25);
		select.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getItem().toString() == "Environment") enviro.setVisible(true);
				else enviro.setVisible(false);
				if(arg0.getItem().toString() == "Obstacles") obstacles.setVisible(true);
				else obstacles.setVisible(false);
				if(arg0.getItem().toString() == "Forces") forces.setVisible(true);
				else forces.setVisible(false);
				if(arg0.getItem().toString() == "Sensors") sensors.setVisible(true);
				else sensors.setVisible(false);
			}
        });
		add(select);
		
		enviro = new JPanel();
		enviro.setBounds(5, 35, 240, 240);
		enviro.setBackground(Color.lightGray);
		enviro.setLayout(null);
		
		JLabel l = new JLabel("Length:");
		l.setBounds(5,0,110,25);
		enviro.add(l);
		
		l = new JLabel("Width:");
		l.setBounds(5,50,110,25);
		enviro.add(l);
		
		enviroLen = new JTextField("500");
		enviroLen.setBounds(5, 25, 110, 25);
		enviro.add(enviroLen);
		
		enviroWid = new JTextField("400");
		enviroWid.setBounds(5, 75, 110, 25);
		enviro.add(enviroWid);
		
		JButton update = new JButton("Update");
		update.setBounds(5,125,110,25);
		update.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try{
					float l = Float.parseFloat(enviroLen.getText());
					if(l >= 0 && l <= 500 ){
						LiquidApplication.getGUI().variables.enviroLength = l;
						LiquidApplication.getGUI().sim.repaint();
					}else LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Length Is Not Between 0 and 500\n");
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Length Is Not An Integer.\n");
				}
				try{
					float w = Float.parseFloat(enviroWid.getText());
					if(w >= 0 && w <= 400 ){
						LiquidApplication.getGUI().variables.enviroWidth = w;
						LiquidApplication.getGUI().sim.repaint();
					}else LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Width Is Not Between 0 and 400\n");
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Width Is Not An Integer.\n");
				}
				LiquidApplication.getGUI().variables.saveState();
			}
        });
		enviro.add(update);
		
		add(enviro);
		
		obstacles = new JPanel();
		obstacles.setBounds(5, 35, 240, 240);
		obstacles.setBackground(Color.lightGray);
		obstacles.setLayout(null);
		
		l = new JLabel("Object Type:");
		l.setBounds(5,0,110,25);
		obstacles.add(l);
		
		l = new JLabel("X:");
		l.setBounds(125,0,110,25);
		obstacles.add(l);
		
		l = new JLabel("Y:");
		l.setBounds(125,50,110,25);
		obstacles.add(l);
		
		l = new JLabel("Length:");
		l.setBounds(125,100,110,25);
		obstacles.add(l);
		
		l = new JLabel("Width:");
		l.setBounds(125,150,110,25);
		obstacles.add(l);
		
		String[] obstype = {"Rectangular","Circular"};
		obstacleType = new JComboBox<String>(obstype);
		obstacleType.setBounds(5, 25, 110, 25);
		obstacles.add(obstacleType);
			
		obstacleX = new JTextField("0");
		obstacleX.setBounds(125, 25, 110, 25);
		obstacles.add(obstacleX);
		
		obstacleY = new JTextField("0");
		obstacleY.setBounds(125, 75, 110, 25);
		obstacles.add(obstacleY);
		
		obstacleL = new JTextField("50");
		obstacleL.setBounds(125, 125, 110, 25);
		obstacles.add(obstacleL);
		
		obstacleW = new JTextField("50");
		obstacleW.setBounds(125, 175, 110, 25);
		obstacles.add(obstacleW);
		
		JButton create = new JButton("Create");
		create.setBounds(125,210,110,25);
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try{
					String arg = obstacleType.getSelectedItem() + " ";
					arg += Float.parseFloat(obstacleX.getText()) + " ";
					arg += Float.parseFloat(obstacleY.getText()) + " ";
					arg += Float.parseFloat(obstacleL.getText()) + " ";
					arg += Float.parseFloat(obstacleW.getText());
					LiquidApplication.getGUI().variables.objects.add(arg);
					LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size()-1;
					LiquidApplication.getGUI().variables.saveState();
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		obstacles.add(create);
		
		JButton delete = new JButton("Delete");
		delete.setBounds(5,175,110,25);
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObject);
				LiquidApplication.getGUI().variables.selectedObject = 0;
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		obstacles.add(delete);
		
		JButton selectUpdate = new JButton("Update Selected");
		selectUpdate.setBounds(5,210,110,25);
		selectUpdate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try{
					String arg = obstacleType.getSelectedItem() + " ";
					arg += Float.parseFloat(obstacleX.getText()) + " ";
					arg += Float.parseFloat(obstacleY.getText()) + " ";
					arg += Float.parseFloat(obstacleL.getText()) + " ";
					arg += Float.parseFloat(obstacleW.getText());
					LiquidApplication.getGUI().variables.objects.add(LiquidApplication.getGUI().variables.selectedObject, arg);
					LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObject+1);
					LiquidApplication.getGUI().variables.saveState();
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		obstacles.add(selectUpdate);
		
		obstacles.setVisible(false);
		add(obstacles);	
		
		forces = new JPanel();
		forces.setBounds(5, 35, 240, 240);
		forces.setLayout(null);
		forces.setBackground(Color.lightGray);
		
		l = new JLabel("Object Type:");
		l.setBounds(5,0,110,25);
		forces.add(l);
		
		l = new JLabel("X:");
		l.setBounds(125,0,110,25);
		forces.add(l);
		
		l = new JLabel("Y:");
		l.setBounds(125,50,110,25);
		forces.add(l);
		
		l = new JLabel("Force-X:");
		l.setBounds(125,100,110,25);
		forces.add(l);
		
		l = new JLabel("Force-Y:");
		l.setBounds(125,150,110,25);
		forces.add(l);
		
		String[] fotype = {"Source"};
		forceType = new JComboBox<String>(fotype);
		forceType.setBounds(5, 25, 110, 25);
		forces.add(forceType);
			
		forceX = new JTextField("0");
		forceX.setBounds(125, 25, 110, 25);
		forces.add(forceX);
		
		forceY = new JTextField("0");
		forceY.setBounds(125, 75, 110, 25);
		forces.add(forceY);
		
		forceXComp = new JTextField("10");
		forceXComp.setBounds(125, 125, 110, 25);
		forces.add(forceXComp);
		
		forceYComp = new JTextField("10");
		forceYComp.setBounds(125, 175, 110, 25);
		forces.add(forceYComp);
		
		create = new JButton("Create");
		create.setBounds(125,210,110,25);
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try{
					String arg = forceType.getSelectedItem() + " ";
					arg += Float.parseFloat(forceX.getText()) + " ";
					arg += Float.parseFloat(forceY.getText()) + " ";
					arg += Float.parseFloat(forceXComp.getText()) + " ";
					arg += Float.parseFloat(forceYComp.getText());
					LiquidApplication.getGUI().variables.objects.add(arg);
					LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size()-1;
					LiquidApplication.getGUI().variables.saveState();
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		forces.add(create);
		
		delete = new JButton("Delete");
		delete.setBounds(5,175,110,25);
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObject);
				LiquidApplication.getGUI().variables.selectedObject = 0;
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		forces.add(delete);
		
		selectUpdate = new JButton("Update Selected");
		selectUpdate.setBounds(5,210,110,25);
		selectUpdate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try{
					String arg = forceType.getSelectedItem() + " ";
					arg += Float.parseFloat(forceX.getText()) + " ";
					arg += Float.parseFloat(forceY.getText()) + " ";
					arg += Float.parseFloat(forceXComp.getText()) + " ";
					arg += Float.parseFloat(forceYComp.getText());
					LiquidApplication.getGUI().variables.objects.add(LiquidApplication.getGUI().variables.selectedObject, arg);
					LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObject+1);
					LiquidApplication.getGUI().variables.saveState();
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		forces.add(selectUpdate);
		
		forces.setVisible(false);
		add(forces);
		
		sensors = new JPanel();
		sensors.setBounds(5, 35, 240, 240);
		sensors.setLayout(null);
		sensors.setBackground(Color.lightGray);
		
		l = new JLabel("Object Type:");
		l.setBounds(5,0,110,25);
		sensors.add(l);
		
		l = new JLabel("X:");
		l.setBounds(125,0,110,25);
		sensors.add(l);
		
		l = new JLabel("Y:");
		l.setBounds(125,50,110,25);
		sensors.add(l);
		
		String[] sentype = {"Flowmeter"};
		sensorType = new JComboBox<String>(sentype);
		sensorType.setBounds(5, 25, 110, 25);
		sensors.add(sensorType);
			
		sensorX = new JTextField("0");
		sensorX.setBounds(125, 25, 110, 25);
		sensors.add(sensorX);
		
		sensorY = new JTextField("0");
		sensorY.setBounds(125, 75, 110, 25);
		sensors.add(sensorY);
		
		create = new JButton("Create");
		create.setBounds(125,210,110,25);
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try{
					String arg = sensorType.getSelectedItem() + " ";
					arg += Float.parseFloat(sensorX.getText()) + " ";
					arg += Float.parseFloat(sensorY.getText());
					LiquidApplication.getGUI().variables.objects.add(arg);
					LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size()-1;
					LiquidApplication.getGUI().variables.saveState();
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		
		delete = new JButton("Delete");
		delete.setBounds(5,175,110,25);
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObject);
				LiquidApplication.getGUI().variables.selectedObject = 0;
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		sensors.add(delete);
		
		sensors.add(create);
		
		selectUpdate = new JButton("Update Selected");
		selectUpdate.setBounds(5,210,110,25);
		selectUpdate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try{
					String arg = sensorType.getSelectedItem() + " ";
					arg += Float.parseFloat(sensorX.getText()) + " ";
					arg += Float.parseFloat(sensorY.getText());
					LiquidApplication.getGUI().variables.objects.add(LiquidApplication.getGUI().variables.selectedObject, arg);
					LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObject+1);
					LiquidApplication.getGUI().variables.saveState();
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		sensors.add(selectUpdate);
		
		ActionListener snext = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(LiquidApplication.getGUI().variables.selectedObject < LiquidApplication.getGUI().variables.objects.size()-1){
					LiquidApplication.getGUI().variables.selectedObject += 1;
				}else{
					LiquidApplication.getGUI().variables.selectedObject = 0;
				}
				update();
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
		};
		
		ActionListener sprev = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(LiquidApplication.getGUI().variables.selectedObject > 0){
					LiquidApplication.getGUI().variables.selectedObject -= 1;
				}else{
					LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size()-1;
				}
				update();
			}
		};
			
		JButton selectNext = new JButton("Select Next");
		selectNext.setBounds(5,125,110,25);
		selectNext.addActionListener(snext);
		obstacles.add(selectNext);
		
		JButton selectPrev = new JButton("Select Prev");
		selectPrev.setBounds(5,150,110,25);
		selectPrev.addActionListener(sprev);
		obstacles.add(selectPrev);
		
		selectNext = new JButton("Select Next");
		selectNext.setBounds(5,125,110,25);
		selectNext.addActionListener(snext);
		forces.add(selectNext);
		
		selectPrev = new JButton("Select Prev");
		selectPrev.setBounds(5,150,110,25);
		selectPrev.addActionListener(sprev);
		forces.add(selectPrev);
		
		selectNext = new JButton("Select Next");
		selectNext.setBounds(5,125,110,25);
		selectNext.addActionListener(snext);
		sensors.add(selectNext);
		
		selectPrev = new JButton("Select Prev");
		selectPrev.setBounds(5,150,110,25);
		selectPrev.addActionListener(sprev);
		sensors.add(selectPrev);
		
		sensors.setVisible(false);
		add(sensors);	
	}
	
	public void update(){
		enviroLen.setText(Float.toString(LiquidApplication.getGUI().variables.enviroLength));
		enviroWid.setText(Float.toString(LiquidApplication.getGUI().variables.enviroWidth));
		try{
			String[] tokens = LiquidApplication.getGUI().variables.objects.get(LiquidApplication.getGUI().variables.selectedObject).split(" ");
			if(tokens[0].equals("Rectangular") || tokens[0].equals("Circular")){
				select.setSelectedItem("Obstacles");
				forces.setVisible(false);
				sensors.setVisible(false);
				obstacles.setVisible(true);
				obstacleType.setSelectedItem(tokens[0]);
				obstacleX.setText(tokens[1]);
				obstacleY.setText(tokens[2]);
				obstacleL.setText(tokens[3]);
				obstacleW.setText(tokens[4]);
			}
			if(tokens[0].equals("Source")){
				select.setSelectedItem("Forces");
				forces.setVisible(true);
				sensors.setVisible(false);
				obstacles.setVisible(false);
				forceType.setSelectedItem(tokens[0]);
				forceX.setText(tokens[1]);
				forceY.setText(tokens[2]);
				forceXComp.setText(tokens[3]);
				forceYComp.setText(tokens[4]);
			}
			if(tokens[0].equals("Flowmeter")){
				select.setSelectedItem("Sensors");
				sensors.setVisible(true);
				forces.setVisible(false);
				obstacles.setVisible(false);
				sensorType.setSelectedItem(tokens[0]);
				sensorX.setText(tokens[1]);
				sensorY.setText(tokens[2]);
			}
		}catch(Exception e){
		}
	}
	
	public void reset(){
		enviroLen.setText(Float.toString(LiquidApplication.getGUI().variables.enviroLength));
		enviroWid.setText(Float.toString(LiquidApplication.getGUI().variables.enviroWidth));
		select.setSelectedItem("Environment");
		obstacleType.setSelectedIndex(0);
		obstacleX.setText("0");
		obstacleY.setText("0");
		obstacleL.setText("50");
		obstacleW.setText("50");
		forceType.setSelectedIndex(0);
		forceX.setText("0");
		forceY.setText("0");
		forceXComp.setText("10");
		forceYComp.setText("10");
		sensorType.setSelectedIndex(0);
		sensorX.setText("0");
		sensorY.setText("0");
	}
	
	public void setEnabled(boolean enable){
		for(Component x : getComponents()){
			x.setEnabled(enable);
			if(x instanceof Container){
				for(Component y : ((Container) x).getComponents()){
					y.setEnabled(enable);
				}
			}
		}
	}
}