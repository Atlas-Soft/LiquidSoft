package liquid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import liquid.core.GlobalVar;
import liquid.core.LiquidApplication;

/**
 * Class is a branch of the EnvironmentEditorPanel. Here, all elements
 * linked to creating a sensor are present, such as the X-/Y-Coordinates. 
 */
public class EnviroSensorsPanel extends JPanel {

	// list of components needed to create a sensor
	private static final long serialVersionUID = 1L;
	JComboBox<String> sensorType;		
	JComboBox<Float> sensorX;
	JComboBox<Float> sensorY;
	ArrayList<Float> params;
	
	/**
	 * Constructor creates the Sensors section of the EnvironmentEditorPanel.
	 */
	public EnviroSensorsPanel() {
		initComponents();
	}
	
	/**
	 * Method creates the labels and drop-downs associated with creating sensors.
	 */
	public void initComponents() {
		setBounds(5,30,240,205);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		// makes labels specific to creating sensors
		JLabel l = new JLabel("Object Type:");
		l.setBounds(30,15,(int)(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("X-Coordinate:");
		l.setBounds(5,50,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Y-Coordinate:");
		l.setBounds(125,50,(this.getWidth()/2),25);
		add(l);
		
		// makes the drop-downs needed to creating sensors
		sensorType = new JComboBox<String>();
		sensorType.addItem(GlobalVar.EnviroOptions.Flowmeters.toString());
		sensorType.setBounds(110,15,(int)(this.getWidth()/2),25);
		add(sensorType);
			
		sensorX = new JComboBox<Float>();
		sensorY = new JComboBox<Float>();
		sensorsParam(); // populates the drop-down information
		createButton(); // creates the Create button
	}
	
	/**
	 * Method adjusts the sensor's settings to be within the limit of the environment's size. It also provides
	 * a real-time update of the sensor parameters to prevent them from exceeding the environment's boundaries.
	 */
	public void sensorsParam() {
		// each drop-down first gets all items removed from it, then gets
		// populated with items all dependent on the environment boundaries
		sensorX.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroLenLimit; i++) {
			sensorX.addItem(Float.valueOf(i));}
		sensorX.setBounds(5,75,(int)(this.getWidth()/2.2),25);
		add(sensorX);
		
		sensorY.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroWidLimit; i++) {
			sensorY.addItem(Float.valueOf(i));}
		sensorY.setBounds(125,75,(int)(this.getWidth()/2.2),25);
		add(sensorY);
		
		resetSensors(); // sets the parameters even after the environment size changes
	}
	
	/**
	 *  Method used to call the editor panel to make a Create button for Sensors.
	 */
	public void createButton() {
		// button creates the obstacle according to the parameters set
		JButton create = new JButton("Create");
		create.setBounds(60,110,(int)(this.getWidth()/2.2),25);
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				createSensor(false);
			}
        });
		add(create);
	}
	
	/**
	 * Method packages data and sends it to the editor to check if the
	 * parameters are valid, and if so to be added into the object list.
	 */
	public void createSensor(boolean update) {
		params = new ArrayList<Float>();
		params.add((Float) sensorX.getSelectedItem());
		params.add((Float) sensorY.getSelectedItem());
		LiquidApplication.getGUI().enviroeditor.checkBoundaries(sensorType, params, update);
	}
	
	/**
	 * Method splits up the String[] of the log file in order to correctly set
	 * the parameters of the Sensors section of the EnvironmentEditorPanel.
	 * 
	 * @param tokens - String[] of the log file to split
	 */
	public void updateSensors(String[] tokens) {
		try {
			sensorType.setSelectedItem(tokens[0]);
			sensorX.setSelectedItem(Float.parseFloat(tokens[1]));
			sensorY.setSelectedItem(Float.parseFloat(tokens[2]));
		} catch (Exception e) {
			e.printStackTrace();}
	}
	
	/**
	 * Method resets the parameters of the Sensors section.
	 */
	public void resetSensors() {
		sensorType.setSelectedIndex(0);
		sensorX.setSelectedIndex(0);
		sensorY.setSelectedIndex(0);
	}
}