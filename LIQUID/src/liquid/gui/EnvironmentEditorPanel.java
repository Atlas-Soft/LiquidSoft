package liquid.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import liquid.core.LiquidApplication;

/**
 * Class stores the details of the EnvironmentEditorPanel section of the ParameterPanel
 * class. Here, users will be able to create obstacles, liquid sources, or flow meters.
 */
public class EnvironmentEditorPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// defines parameters for the overall structure of the editor panel
	String[] options = {"Environment", "Obstacles", "Initial Forces", "Flow Sensors"};
	JComboBox<String> select;
	static float enviroLenLimit = 500;
	static float enviroWidLimit = 400;
		
	// creates the Obstacles, Forces, and Sensors parts of the panel
	EnviroPanel enviro;
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
		
		// creates the Environment section, which represents the EnviroPanel class
		enviro = new EnviroPanel();
		enviro.setVisible(true);
		add(enviro);
		
		// creates the Obstacle section, which represents the EnviroObstaclesPanel class
		obstacles = new EnviroObstaclesPanel();
		obstacles.setVisible(false);
		add(obstacles);
		
		// creates the Initial Forces section, which represents the EnviroForcesPanel class
		forces = new EnviroForcesPanel();
		forces.setVisible(false);
		add(forces);
		
		// creates the Flow Sensors section, which represents the EnviroSensorsPanel class
		sensors = new EnviroSensorsPanel();
		sensors.setVisible(false);
		add(sensors);
		
		// creates the additional set of parameters
		addiParam = new EnviroAddiParamPanel();
		addiParam.setVisible(true);
		addiParam.setEnabled(false);
		add(addiParam);
	}
	
	/**
	 * Method checks the boundaries of the environment and throws error messages
	 * when the obstacle will go out of the predefined environment size.
	 * 
	 * For the ArrayList<Float>:
	 *  - 0 - X-Coordinate of Obstacle/Force
	 *  - 1 - Y-Coordinate of Obstacle/Force
	 *  - 2 - Length of Obstacle/X-Force of Force
	 *  - 3 - Width of Obstacle/Y-Force of Force
	 *  - 4 - Rotation of Obstacle/Flow Speed of Force
	 */
	public void checkBoundaries(JComboBox<String> type, ArrayList<Float> params, boolean update) {
		// when the obstacle has gone beyond the environment in the X direction (upper limit)
		if ((type.getSelectedItem().equals("Rectangular") ||  type.getSelectedItem().equals("Circular"))
				&& (params.get(0) + params.get(2) > enviroLenLimit+1)) {
			LiquidApplication.getGUI().message.xObsExceedsUpperLimit(enviroLenLimit, params);
		
		// when the obstacle has gone beyond the environment in the Y direction (upper limit)
		} else if ((type.getSelectedItem().equals("Rectangular") ||  type.getSelectedItem().equals("Circular"))
				&& (params.get(1) + params.get(3) > enviroWidLimit+1)) {
			LiquidApplication.getGUI().message.yObsExceedsUpperLimit(enviroWidLimit, params);
		
		// when the source has gone below the environment in the X direction (lower limit)
		} else if ((type.getSelectedItem().equals("Source")) && (params.get(0) + params.get(2) < 0)) {
			LiquidApplication.getGUI().message.xForExceedsLowerLimit(enviroLenLimit, params);
			
		// else sends the obstacle's information to the ArrayList of objects to store
		} else {
			addiParam.setEnabled(true);
			String arg = type.getSelectedItem() + "";
			for (int i = 0; i < params.size(); i++) arg += " " + params.get(i);
			if (update) {
				LiquidApplication.getGUI().variables.objects.set(LiquidApplication.getGUI().variables.selectedObject, arg);
			} else {
				LiquidApplication.getGUI().variables.objects.add(arg);
				LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size() - 1;
			}
			LiquidApplication.getGUI().variables.saveState();
			LiquidApplication.getGUI().sim.repaint();
		}
    }
	
	/**
	 * Method updates the simulation with the information presented in the log file. 
	 */
	public void setSelectedObject() {
		enviro.updateEnviro();
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
	 * Resets all parts of the EnvironmentEditorPanel to its default settings.
	 */
	public void reset() {
		select.setSelectedItem("Environment");
		enviro.resetEnviro();
		obstacles.resetObstacles();
		forces.resetForces();
		sensors.resetSensors();
	}
}