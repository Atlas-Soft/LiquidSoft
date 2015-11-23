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

public class EnvironmentEditorPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	JComboBox<String> select;
	JPanel enviro;
	JComboBox<Float> enviroLen;
	JComboBox<Float> enviroWid;
	static float enviroLenLimit = 500;
	static float enviroWidLimit = 400;
	
	EnviroObstacles obstacles;
	EnviroSources forces;
	
	JPanel sensors;
		JComboBox<String> sensorType;		
		JTextField sensorX;
		JTextField sensorY;
	
	public EnvironmentEditorPanel() {
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.GRAY);
		setBounds(25, 150, 250, 310);
		setVisible(true);
	}
	
	public void initComponents() {
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		ActionListener snext = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (LiquidApplication.getGUI().variables.selectedObject < LiquidApplication.getGUI().variables.objects.size()-1){
					LiquidApplication.getGUI().variables.selectedObject += 1;
				} else {
					LiquidApplication.getGUI().variables.selectedObject = 0;
				}
				update();
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
		};
		
		ActionListener sprev = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if( LiquidApplication.getGUI().variables.selectedObject > 0) {
					LiquidApplication.getGUI().variables.selectedObject -= 1;
				} else {
					LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size()-1;
				}
				update();
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
		};
		
		String[] options = {"Environment", "Obstacles", "Initial Forces", "Flow Sensors"};
		
		select = new JComboBox<String>(options);
		select.setBounds(5, 5, 240, 25);
		select.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getItem().toString() == "Environment") {
					enviro.setVisible(true);
				} else {
					enviro.setVisible(false);
				}
				if(arg0.getItem().toString() == "Obstacles") {
					//obstacles = new EnviroObstacles();
					//add(obstacles);
					obstacles.setVisible(true);
				} else {
					obstacles.setVisible(false);
				}
				if(arg0.getItem().toString() == "Initial Forces") forces.setVisible(true);
				else forces.setVisible(false);
				if(arg0.getItem().toString() == "Flow Sensors") sensors.setVisible(true);
				else sensors.setVisible(false);
			}
        });
		add(select);
		
		// creates the Environment section, which sets the length/width of the environment
		enviro = new JPanel();
		enviro.setBounds(5,30,240,275);
		enviro.setBackground(Color.LIGHT_GRAY);
		enviro.setLayout(null);
		
		// makes labels for creating an environment
		JLabel l = new JLabel("Length:");
		l.setBounds(5,0,110,25);
		enviro.add(l);
		
		l = new JLabel("Width:");
		l.setBounds(5,50,110,25);
		enviro.add(l);
		
		// makes drop-downs for creating an environment
		enviroLen = new JComboBox<Float>();
		for (int i = 0; i <= enviroLenLimit; i++) {
			enviroLen.addItem(Float.valueOf(i));}
		enviroLen.setSelectedIndex((int)enviroLenLimit);
		enviroLen.setBounds(5,25,110,25);
		enviro.add(enviroLen);
		
		enviroWid = new JComboBox<Float>();
		for (int i = 0; i <= enviroWidLimit; i++) {
			enviroWid.addItem(Float.valueOf(i));}
		enviroWid.setSelectedIndex((int)enviroWidLimit);
		enviroWid.setBounds(5,75,110,25);
		enviro.add(enviroWid);
		
		// draws the size of the environment based on the parameters set
		JButton draw = new JButton("Draw");
		draw.setBounds(5,125,110,25);
		draw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.enviroLength = (float) enviroLen.getSelectedIndex();
				LiquidApplication.getGUI().variables.enviroWidth = (float) enviroWid.getSelectedIndex();
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
				enviroLenLimit = enviroLen.getSelectedIndex();
				enviroWidLimit = enviroWid.getSelectedIndex();
			}
        });
		enviro.add(draw);
		add(enviro);
		
		// creates the Obstacle section, which represents the EnviroObstacle class
		obstacles = new EnviroObstacles();
		obstacles.setVisible(false);
		add(obstacles);
		
		// creates the Initial Forces section, which represents the EnviroSource class
		forces = new EnviroSources();
		add(forces);
		
	 /**JButton selectNext = new JButton("Next Object");
		selectNext.setBounds(5, 200, 110, 25);
		selectNext.addActionListener(snext);
		obstacles.add(selectNext);
		
		JButton selectPrev = new JButton("Prev Object");
		selectPrev.setBounds(125, 200, 110, 25);
		selectPrev.addActionListener(sprev);
		obstacles.add(selectPrev);
		
		JButton selectUpdate = new JButton("Update Obj");
		selectUpdate.setBounds(5, 240, 110, 25);
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
		
		JButton delete = new JButton("Delete");
		delete.setBounds(125, 240, 110, 25);
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObject);
				LiquidApplication.getGUI().variables.selectedObject = 0;
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		obstacles.add(delete);
		obstacles.setVisible(false);
		add(obstacles);	*/
		
		
		
		/**JButton selectNext = new JButton("Next Force");
		selectNext.setBounds(5, 200, 110, 25);
		selectNext.addActionListener(snext);
		forces.add(selectNext);
		
		JButton selectPrev = new JButton("Prev Force");
		selectPrev.setBounds(125, 200, 110, 25);
		selectPrev.addActionListener(sprev);
		forces.add(selectPrev);
		
		JButton selectUpdate = new JButton("Update For");
		selectUpdate.setBounds(5, 240, 110, 25);
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
		
		JButton delete = new JButton("Delete");
		delete.setBounds(125, 240, 110, 25);
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObject);
				LiquidApplication.getGUI().variables.selectedObject = 0;
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		forces.add(delete);
		add(forces);*/
		
		sensors = new JPanel();
		sensors.setBounds(5, 30, 240, 275);
		sensors.setLayout(null);
		sensors.setBackground(Color.LIGHT_GRAY);
		
		l = new JLabel("Object Type:");
		l.setBounds(35, 5, 110, 25);
		sensors.add(l);
		
		l = new JLabel("X:");
		l.setBounds(5, 30, 110, 25);
		sensors.add(l);
		
		l = new JLabel("Y:");
		l.setBounds(125, 30, 110, 25);
		sensors.add(l);
		
		String[] sentype = {"Flowmeter"};	//changed to "Flowmeter" without space to fix flowmeters not being rendered
		sensorType = new JComboBox<String>(sentype);
		sensorType.setBounds(115, 5, 110, 25);
		sensors.add(sensorType);
			
		sensorX = new JTextField("0");
		sensorX.setBounds(5, 55, 110, 25);
		sensors.add(sensorX);
		
		sensorY = new JTextField("0");
		sensorY.setBounds(125, 55, 110, 25);
		sensors.add(sensorY);
		
		JButton create = new JButton("Create");
		create.setBounds(65, 90, 110, 25);
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
		sensors.add(create);
		
		
		JButton selectNext = new JButton("Next Sensor");
		selectNext.setBounds(5, 200, 110, 25);
		selectNext.addActionListener(snext);
		sensors.add(selectNext);
		
		JButton selectPrev = new JButton("Prev Sensor");
		selectPrev.setBounds(125, 200, 110, 25);
		selectPrev.addActionListener(sprev);
		sensors.add(selectPrev);
		
		JButton selectUpdate = new JButton("Update Sen");
		selectUpdate.setBounds(5, 240, 110, 25);
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
		
		JButton delete = new JButton("Delete");
		delete.setBounds(125, 240, 110, 25);
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObject);
				LiquidApplication.getGUI().variables.selectedObject = 0;
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		sensors.add(delete);
		sensors.setVisible(false);
		add(sensors);	
	}
	
	public void update(){
		enviroLen.setSelectedItem(Float.toString(LiquidApplication.getGUI().variables.enviroLength));
		enviroWid.setSelectedItem(Float.toString(LiquidApplication.getGUI().variables.enviroWidth));
		try{
			String[] tokens = LiquidApplication.getGUI().variables.objects.get(LiquidApplication.getGUI().variables.selectedObject).split(" ");
			if(tokens[0].equals("Rectangular") || tokens[0].equals("Circular")){
				select.setSelectedItem("Obstacles");
				forces.setVisible(false);
				sensors.setVisible(false);
				obstacles.setVisible(true);
				
				obstacles.updateObstacles(tokens);
			}
			if(tokens[0].equals("Source")){
				select.setSelectedItem("Initial Forces");
				forces.setVisible(true);
				sensors.setVisible(false);
				obstacles.setVisible(false);
				
				forces.updateSources(tokens);
				
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
			e.printStackTrace();
		}
	}
	
	public void reset(){
		enviroLen.setSelectedItem(Float.toString(LiquidApplication.getGUI().variables.enviroLength));
		enviroWid.setSelectedItem(Float.toString(LiquidApplication.getGUI().variables.enviroWidth));
		select.setSelectedItem("Environment");

		obstacles.resetObstacles();
		forces.resetSources();
		
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