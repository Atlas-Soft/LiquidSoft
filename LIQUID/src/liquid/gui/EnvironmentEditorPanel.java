package liquid.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import liquid.core.LiquidApplication;

/**
 * Class stores the details of the EnvironmentEditorPanel section of the ParameterPanel
 * class. Here, users will be able to create obstacles, liquid sources, or flow meters.
 */
public class EnvironmentEditorPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	String[] options = {"Environment", "Obstacles", "Initial Forces", "Flow Sensors"};
	JComboBox<String> select;
	
	// defines parameters needed to adjust the environment size
	JPanel enviro;
	JComboBox<Float> enviroLen;
	JComboBox<Float> enviroWid;
	static float enviroLenLimit = 500;
	static float enviroWidLimit = 400;
	
	// creates the Obstacles, Forces, and Sensors parts of the panel
	EnviroObstaclesPanel obstacles;
	EnviroForcesPanel forces;
	EnviroSensorsPanel sensors;
	EnviroAddiParamPanel addiParam;
	
	/**
	 * Constructor sets up the EnvironmentEditorPanel part of the ParameterPanel. It
	 * is currently located around the center part of the right side of the simulator.
	 */
	public EnvironmentEditorPanel() {
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.GRAY);
		setBounds(25,150,250,310);
		setVisible(true);
	}
	
	/**
	 * Sets up some of the components of the EnvironmentEditorPanel.
	 */
	public void initComponents() {
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		// sets the overall drop-down options for the EnvironmentEditorPanel
		select = new JComboBox<String>(options);
		select.setBounds(5,5,240,25);
		select.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// sets the drop-down option to be invisible if it is not currently selected 
				if (arg0.getItem().toString() == "Environment") enviro.setVisible(true);
				else enviro.setVisible(false);
				if (arg0.getItem().toString() == "Obstacles") obstacles.setVisible(true);
				else obstacles.setVisible(false);
				if(arg0.getItem().toString() == "Initial Forces") forces.setVisible(true);
				else forces.setVisible(false);
				if(arg0.getItem().toString() == "Flow Sensors") sensors.setVisible(true);
				else sensors.setVisible(false);
			}
        });
		add(select);
		
		// creates the Environment section
		createEnvironment();
		
		// creates the Obstacle section, which represents the EnviroObstacles class
		obstacles = new EnviroObstaclesPanel();
		obstacles.setVisible(false);
		add(obstacles);
		
		// creates the Initial Forces section, which represents the EnviroForces class
		forces = new EnviroForcesPanel();
		forces.setVisible(false);
		add(forces);
		
		// creates the Flow Sensors section, which represents the EnviroSensors class
		sensors = new EnviroSensorsPanel();
		sensors.setVisible(false);
		add(sensors);
		
		// creates the additional set of parameters
		addiParam = new EnviroAddiParamPanel();
		addiParam.setVisible(true);
		add(addiParam);
	}
	
	/**
	 * Method creates the parameters to set the length/width of the simulating environment
	 * itself. A new method is created to provide flexibility in where it is created. 
	 */
	public void createEnvironment() {
		// creates the Environment section, which sets the length/width of the environment
		enviro = new JPanel();
		enviro.setBounds(5,30,240,205);
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
		enviroLen.setSelectedIndex((int) enviroLenLimit);
		enviroLen.setBounds(5,25,110,25);
		enviro.add(enviroLen);
			
		enviroWid = new JComboBox<Float>();
		for (int i = 0; i <= enviroWidLimit; i++) {
			enviroWid.addItem(Float.valueOf(i));}
		enviroWid.setSelectedIndex((int) enviroWidLimit);
		enviroWid.setBounds(5,75,110,25);
		enviro.add(enviroWid);
			
		// draws the size of the environment based on the parameters set
		JButton draw = new JButton("Draw");
		draw.setBounds(5,125,110,25);
		draw.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			// sets the length/width according to the drop-downs
			LiquidApplication.getGUI().variables.enviroLength = (float) enviroLen.getSelectedItem();
			LiquidApplication.getGUI().variables.enviroWidth = (float) enviroWid.getSelectedItem();
			LiquidApplication.getGUI().variables.saveState();
			LiquidApplication.getGUI().sim.repaint();
					
			// resets the limits to adjust the boundaries of creating various objects 
			enviroLenLimit = (float) enviroLen.getSelectedItem();
			enviroWidLimit = (float) enviroWid.getSelectedItem();
			obstacles.obstaclesParam();
			forces.forcesParam();
			sensors.sensorsParam();
			}
	    });
		enviro.add(draw);
		add(enviro);
	}
	
	/**
	 * Method checks the boundaries of the environment and throws error messages
	 * when the obstacle will go out of the predefined environment size.
	 * 
	 * For the ArrayList<Float>:
	 *  - 1 - X-Coordinate of Obstacle/Force
	 *  - 2 - Y-Coordinate of Obstacle/Force
	 *  - 3 - Length of Obstacle/X-Force of Force
	 *  - 4 - Width of Obstacle/Y-Force of Force
	 *  - 5 - Rotation of Obstacle/Flow Speed of Force
	 */
	public void checkBoundaries(JComboBox<String> type, ArrayList<Float> params, boolean update) {
		// throws error messages when the obstacle will go beyond the environment (determined
		// by the X-/Y-Coordinates and the Length/Width of the obstacle, respectively)
		if ((type.getSelectedItem().equals("Rectangular") ||  type.getSelectedItem().equals("Circular"))
				&&(params.get(0) + params.get(2) > enviroLenLimit)) {
			JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
				"Warning!! Your X-Coordinate must be from 0.0 - " + (enviroLenLimit - params.get(2)) +
				",\n or our Length must be from 0.0 - " + (enviroLenLimit - params.get(0)) +
				"\n to be in the boundaries of your desired environment size.",
				"Invalid Parameters!!", JOptionPane.WARNING_MESSAGE);
		} else if ((type.getSelectedItem().equals("Rectangular") ||  type.getSelectedItem().equals("Circular"))
				&&(params.get(1) + params.get(3) > enviroWidLimit)) {
			JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
				"Warning!! Your Y-Coordinate must be from 0.0 - " + (enviroWidLimit - params.get(3)) +
				",\n or your Width must be from 0.0 - " + (enviroWidLimit - params.get(1)) +
				"\n to be in the boundaries of your desired environment size.",
				"Invalid Parameters!!", JOptionPane.WARNING_MESSAGE);
					
		// else sends the obstacle's information to the ArrayList of objects to store
		} else {
			String arg = type.getSelectedItem() + "";
			for(int i = 0; i < params.size(); i++) arg += " " + params.get(i);
			if(update){
				LiquidApplication.getGUI().variables.objects.set(LiquidApplication.getGUI().variables.selectedObject, arg);
			}else{
				LiquidApplication.getGUI().variables.objects.add(arg);
				LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size() - 1;
			}
			LiquidApplication.getGUI().variables.saveState();
			LiquidApplication.getGUI().sim.repaint();
		}
    }
	
	/**
	 * Method enables/disables the components of the EnvironmentEditorPanel.
	 */
	public void setEnabled(boolean enable) {
		for (Component x : getComponents()) {
			x.setEnabled(enable);
			if (x instanceof Container) {
				for (Component y : ((Container) x).getComponents()) {
					y.setEnabled(enable);}
			}
		}
	}
	
	/**
	 * Method updates the simulation with the information presented in the log file. 
	 */
	public void update() {
		enviroLen.setSelectedItem(Float.toString(LiquidApplication.getGUI().variables.enviroLength));
		enviroWid.setSelectedItem(Float.toString(LiquidApplication.getGUI().variables.enviroWidth));
		try {
			// obtains the line of parameters associated with a EnvironmentEditorPanel item
			String[] tokens = LiquidApplication.getGUI().variables.objects.get(LiquidApplication.getGUI().variables.selectedObject).split(" ");
			// creates obstacle items, if any present
			if (tokens[0].equals("Rectangular") || tokens[0].equals("Circular")) {
				select.setSelectedItem("Obstacles");
				obstacles.setVisible(true);
				forces.setVisible(false);
				sensors.setVisible(false);
				obstacles.updateObstacles(tokens);
				
			// creates source items, if any present
			} else if (tokens[0].equals("Source")) {
				select.setSelectedItem("Initial Forces");
				obstacles.setVisible(false);
				forces.setVisible(true);
				sensors.setVisible(false);
				forces.updateForces(tokens);
			
			// creates flow meter items, if any present
			} else if (tokens[0].equals("Flowmeter")) {
				select.setSelectedItem("Flow Sensors");
				obstacles.setVisible(false);
				sensors.setVisible(true);
				forces.setVisible(false);
				sensors.updateSensors(tokens);
			}
		} catch(Exception e) {
			e.printStackTrace();}
	}
	
	/**
	 * Resets all parts of the EnvironmentEditorPanel to its default settings.
	 */
	public void reset() {
		select.setSelectedItem("Environment");
		enviroLen.setSelectedItem(enviroLenLimit);
		enviroWid.setSelectedItem(enviroWidLimit);
		obstacles.resetObstacles();
		forces.resetForces();
		sensors.resetSensors();
	}
}