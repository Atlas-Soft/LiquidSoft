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

import liquid.core.GlobalVariables;
import liquid.core.GlobalVariables.ObjectType;
import liquid.core.LiquidApplication;

/**
 * Class stores the details of the EnvironmentEditorPanel section of the ParameterPanel class. Here, users
 * will be able to adjust environment size and create obstacles, drains, sources, flow meters, or breakpoints.
 */
public class EditorPanel extends JPanel {
	
	// defines parameters for the overall structure of the editor panel
	private static final long serialVersionUID = 1L;
	static float enviroLenLimit = 500;
	static float enviroWidLimit = 400;
		
	JComboBox<String> select;
	EnviroPanel enviro;							 // creates the Environment section
	EnviroObstaclesAndDrainsPanel obstacles;	 // creates the Obstacles and Drains section
	EnviroSourcesPanel sources;					 // creates the Sources section
	EnviroFlowmetersAndBreakpointsPanel sensors; // creates the Flowmeters and Breakpoints section
	EnviroAddiParamPanel addiParam;				 // creates the additional buttons (located on the bottom)
	
	/**
	 * Constructor sets up the EnvironmentEditorPanel part of the ParameterPanel. It
	 * is currently located around the center part of the right side of the simulator.
	 */
	public EditorPanel() {
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
		Font font = new Font("Verdana",Font.BOLD,12);
		setFont(font);
		
		// sets the overall drop-down options for the EnvironmentEditorPanel
		select = new JComboBox<String>();
		select.addItem(GlobalVariables.EnviroOptions.Environment.toString());
		select.addItem(GlobalVariables.EnviroOptions.Obstacles.toString() + " and " + GlobalVariables.EnviroOptions.Drains.toString());
		select.addItem(GlobalVariables.EnviroOptions.Sources.toString());
		select.addItem(GlobalVariables.EnviroOptions.Flowmeters.toString() + " and " + GlobalVariables.EnviroOptions.Breakpoints.toString());
		select.setBounds(5,5,240,25);
		select.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// sets the drop-down option to be invisible if it is not currently selected 
				if (arg0.getItem().toString().equals(GlobalVariables.EnviroOptions.Environment.toString())) {
					enviro.setVisible(true);
					obstacles.setVisible(false);
					sources.setVisible(false);
					sensors.setVisible(false);
				} else if (arg0.getItem().toString().equals(GlobalVariables.EnviroOptions.Obstacles.toString() + " and " +
						GlobalVariables.EnviroOptions.Drains.toString())) {
					enviro.setVisible(false);
					obstacles.setVisible(true);
					sources.setVisible(false);
					sensors.setVisible(false);
				} else if (arg0.getItem().toString().equals(GlobalVariables.EnviroOptions.Sources.toString())) {
					enviro.setVisible(false);
					obstacles.setVisible(false);
					sources.setVisible(true);
					sensors.setVisible(false);
				} else if (arg0.getItem().toString().equals(GlobalVariables.EnviroOptions.Flowmeters.toString() + " and " +
						GlobalVariables.EnviroOptions.Breakpoints.toString())) {
					enviro.setVisible(false);
					obstacles.setVisible(false);
					sources.setVisible(false);
					sensors.setVisible(true);
				}
			}
        });
		add(select);
		
		// creates the Environment section, which represents the EnviroPanel class
		enviro = new EnviroPanel();
		enviro.setVisible(true);
		add(enviro);
		
		// creates the Obstacles and Drains section, which represents the EnviroObstaclesAndDrainsPanel class
		obstacles = new EnviroObstaclesAndDrainsPanel();
		obstacles.setVisible(false);
		add(obstacles);
		
		// creates the Sources section, which represents the EnviroSourcesPanel class
		sources = new EnviroSourcesPanel();
		sources.setVisible(false);
		add(sources);
		
		// creates the Flowmeters and Breakpoints section, which represents the EnviroFlowmetersAndBreakpointsPanel class
		sensors = new EnviroFlowmetersAndBreakpointsPanel();
		sensors.setVisible(false);
		add(sensors);
		
		// creates the additional set of parameters, which represents the EnviroAddiParamPanel class
		addiParam = new EnviroAddiParamPanel();
		addiParam.setVisible(true);
		addiParam.setEnabled(false);
		add(addiParam);
	}
	
	/**
	 * Method adds the object--obstacle, drain, source, flow meter, or breakpoint--when the Create button
	 * has been pressed or updates the object's parameters when the Update button has been pressed. 
	 * 
	 * For the ArrayList params:
	 *  - 0 - X-Coordinate of object
	 *  - 1 - Y-Coordinate of object
	 *  - 2 - Length of object
	 *  - 3 - Width of object
	 *  - 4 - Rotation of Obstacle/Drain
	 */
	public void addObject(JComboBox<String> type, ArrayList<Float> params, boolean update) {
		// sends the object's information to the ArrayList of objects to store
		addiParam.setEnabled(true);
		String arg = type.getSelectedItem() + "";
		for (int i = 0; i < params.size(); i++) arg += " " + params.get(i);
			
		// sets the object's parameters if it's to update, else adds another object 
		if (update) {
			LiquidApplication.getGUI().getFileVariables().getObjects().set(LiquidApplication.getGUI().getFileVariables().getSelectedObject(), arg);
		} else {
			LiquidApplication.getGUI().getFileVariables().getObjects().add(arg);
			LiquidApplication.getGUI().getFileVariables().setSelectedObject(LiquidApplication.getGUI().getFileVariables().getObjects().size()-1);
		}
		LiquidApplication.getGUI().getApplicationState().saveState();
		LiquidApplication.getGUI().getSimulationPanel().repaint();
	}
	
	/**
	 * Method sets the panel according to the previously-selected object type when a log file has been loaded.
	 */
	public void setSelectedObjectPanel() {
		enviroLenLimit = LiquidApplication.getGUI().getFileVariables().getEnvironmentWidth();
		enviroWidLimit = LiquidApplication.getGUI().getFileVariables().getEnvironmentHeight();
		enviro.updateEnviro();
		try {
			// obtains the line of parameters associated with the selected object
			if (LiquidApplication.getGUI().getFileVariables().getObjects().size() != 0) {
				String[] tokens = LiquidApplication.getGUI().getFileVariables().getObjects().get(
						LiquidApplication.getGUI().getFileVariables().getSelectedObject()).split(" ");
				// creates an obstacle if it's the last item
				if (tokens[0].equals(ObjectType.Rectangle.toString()) ||
						tokens[0].equals(ObjectType.Circle.toString()) ||
						tokens[0].equals(ObjectType.Rectangle_Drain.toString()) ||
						tokens[0].equals(ObjectType.Circle_Drain.toString())) {
					select.setSelectedItem(GlobalVariables.EnviroOptions.Obstacles.toString() + " and " +
						GlobalVariables.EnviroOptions.Drains.toString());
					obstacles.setVisible(true);
					sources.setVisible(false);
					sensors.setVisible(false);
					obstacles.updateObstacles(tokens);
				
				// creates a source if it's the last item
				} else if (tokens[0].equals(GlobalVariables.EnviroOptions.Sources.toString())) {
					select.setSelectedItem(GlobalVariables.EnviroOptions.Sources.toString());
					obstacles.setVisible(false);
					sources.setVisible(true);
					sensors.setVisible(false);
					sources.updateSources(tokens);
			
				// creates a flow meter if it's the last item
				} else if (tokens[0].equals(GlobalVariables.EnviroOptions.Flowmeters.toString()) ||
						tokens[0].equals(GlobalVariables.EnviroOptions.Breakpoints.toString())) {
					select.setSelectedItem(GlobalVariables.EnviroOptions.Flowmeters.toString() + " and " +
						GlobalVariables.EnviroOptions.Breakpoints.toString());
					obstacles.setVisible(false);
					sources.setVisible(false);
					sensors.setVisible(true);
					sensors.updateSensors(tokens);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();}
	}
	
	/**
	 * Method enables/disables the components of the EnvironmentEditorPanel.
	 * @param enable - to enable/disable components
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
		enviroLenLimit = 500;
		enviroWidLimit = 400;
		select.setSelectedItem(GlobalVariables.EnviroOptions.Environment.toString());
		enviro.resetEnviro();
		obstacles.resetObstacles();
		sources.resetSources();
		sensors.resetSensors();
	}
}