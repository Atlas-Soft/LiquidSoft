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

import liquid.core.GlobalVar;
import liquid.core.LiquidApplication;

/**
 * Class stores the details of the EnvironmentEditorPanel section of the ParameterPanel class. Here,
 * users will be able to adjust environment size and create obstacles, drains, sources, or flow meters.
 */
public class EnvironmentEditorPanel extends JPanel {
	
	// defines parameters for the overall structure of the editor panel
	private static final long serialVersionUID = 1L;
	static float enviroLenLimit = 500;
	static float enviroWidLimit = 400;
	JComboBox<String> select;
		
	// creates the Environment, Obstacles and Drains, Forces, and Sensors parts of the panel
	EnviroPanel enviro;
	EnviroObstaclesAndDrainsPanel obstacles;
	EnviroSourcesPanel sources;
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
		select = new JComboBox<String>();
		select.addItem(GlobalVar.EnviroOptions.Environment.toString());
		select.addItem(GlobalVar.EnviroOptions.Obstacles_And_Drains.toString());
		select.addItem(GlobalVar.EnviroOptions.Sources.toString());
		select.addItem(GlobalVar.EnviroOptions.Flowmeters.toString());
		select.setBounds(5,5,240,25);
		select.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// sets the drop-down option to be invisible if it is not currently selected 
				if (arg0.getItem().toString().equals(GlobalVar.EnviroOptions.Environment.toString())) enviro.setVisible(true);
				else enviro.setVisible(false);
				if (arg0.getItem().toString().equals(GlobalVar.EnviroOptions.Obstacles_And_Drains.toString())) obstacles.setVisible(true);
				else obstacles.setVisible(false);
				if(arg0.getItem().toString().equals(GlobalVar.EnviroOptions.Sources.toString())) sources.setVisible(true);
				else sources.setVisible(false);
				if(arg0.getItem().toString().equals(GlobalVar.EnviroOptions.Flowmeters.toString())) sensors.setVisible(true);
				else sensors.setVisible(false);
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
		
		// creates the Sensors section, which represents the EnviroSensorsPanel class
		sensors = new EnviroSensorsPanel();
		sensors.setVisible(false);
		add(sensors);
		
		// creates the additional set of parameters, which represents the EnviroAddiParamPanel class
		addiParam = new EnviroAddiParamPanel();
		addiParam.setVisible(true);
		addiParam.setEnabled(false);
		add(addiParam);
	}
	
	/**
	 * Method checks the boundaries of the environment and throws error messages
	 * when the obstacle or drain will go out of the predefined environment size.
	 * 
	 * For the ArrayList<Float>:
	 *  - 0 - X-Coordinate of Obstacle/Drain
	 *  - 1 - Y-Coordinate of Obstacle/Drain
	 *  - 2 - Length of Obstacle/Drain
	 *  - 3 - Width of Obstacle/Drain
	 *  - 4 - Rotation of Obstacle/Drain
	 */
	public void checkBoundaries(JComboBox<String> type, ArrayList<Float> params, boolean update) {
		// when the obstacle has gone beyond the environment in the X direction (upper limit)
		if ((type.getSelectedItem().equals(GlobalVar.ObsType.Rectangular.toString()) ||
				type.getSelectedItem().equals(GlobalVar.ObsType.Circular.toString()) ||
				type.getSelectedItem().equals(GlobalVar.ObsType.Rect_Drain.toString()) ||
				type.getSelectedItem().equals(GlobalVar.ObsType.Circ_Drain.toString()))
				&& (params.get(0) + params.get(2) > enviroLenLimit+1)) {
			LiquidApplication.getGUI().message.xObsExceedsUpperLimit(enviroLenLimit, params);
		
		// when the obstacle has gone beyond the environment in the Y direction (upper limit)
		} else if ((type.getSelectedItem().equals(GlobalVar.ObsType.Rectangular.toString()) || 
				type.getSelectedItem().equals(GlobalVar.ObsType.Circular.toString()) ||
				type.getSelectedItem().equals(GlobalVar.ObsType.Rect_Drain.toString()) ||
				type.getSelectedItem().equals(GlobalVar.ObsType.Circ_Drain.toString()))
				&& (params.get(1) + params.get(3) > enviroWidLimit+1)) {
			LiquidApplication.getGUI().message.yObsExceedsUpperLimit(enviroWidLimit, params);
		
		// else sends the obstacle's information to the ArrayList of objects to store
		} else {
			addiParam.setEnabled(true);
			String arg = type.getSelectedItem() + "";
			for (int i = 0; i < params.size(); i++) arg += " " + params.get(i);
			
			// sets the object's parameters if it's to update, else adds another object 
			if (update) {
				LiquidApplication.getGUI().variables.objects.set(LiquidApplication.getGUI().variables.selectedObject, arg);
			} else {
				LiquidApplication.getGUI().variables.objects.add(arg);
				LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size()-1;
			}
			LiquidApplication.getGUI().variables.saveState();
			LiquidApplication.getGUI().sim.repaint();
		}
    }
	
	/**
	 * Method sets the panel according to the previously-selected object type when a log file has been loaded.
	 */
	public void setSelectedObject() {
		enviroLenLimit = LiquidApplication.getGUI().variables.enviroLength;
		enviroWidLimit = LiquidApplication.getGUI().variables.enviroWidth;
		enviro.updateEnviro();
		try {
			// obtains the line of parameters associated the selected object
			String[] tokens = LiquidApplication.getGUI().variables.objects.get(LiquidApplication.getGUI().variables.selectedObject).split(" ");
			// creates an obstacle if it's the last item
			if (tokens[0].equals(GlobalVar.ObsType.Rectangular.toString()) ||
					tokens[0].equals(GlobalVar.ObsType.Circular.toString()) ||
					tokens[0].equals(GlobalVar.ObsType.Rect_Drain.toString()) ||
					tokens[0].equals(GlobalVar.ObsType.Circ_Drain.toString())) {
				select.setSelectedItem(GlobalVar.EnviroOptions.Obstacles_And_Drains.toString());
				obstacles.setVisible(true);
				sources.setVisible(false);
				sensors.setVisible(false);
				obstacles.updateObstacles(tokens);
				
			// creates a source if it's the last item
			} else if (tokens[0].equals(GlobalVar.EnviroOptions.Sources.toString())) {
				select.setSelectedItem(GlobalVar.EnviroOptions.Sources.toString());
				obstacles.setVisible(false);
				sources.setVisible(true);
				sensors.setVisible(false);
				sources.updateSources(tokens);
			
			// creates a flow meter if it's the last item
			} else if (tokens[0].equals(GlobalVar.EnviroOptions.Flowmeters.toString())) {
				select.setSelectedItem(GlobalVar.EnviroOptions.Flowmeters.toString());
				obstacles.setVisible(false);
				sources.setVisible(false);
				sensors.setVisible(true);
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
		enviroLenLimit = 500;
		enviroWidLimit = 400;
		enviro.updateLimits();
		
		select.setSelectedItem(GlobalVar.EnviroOptions.Environment.toString());
		enviro.resetEnviro();
		obstacles.resetObstacles();
		sources.resetSources();
		sensors.resetSensors();
	}
}