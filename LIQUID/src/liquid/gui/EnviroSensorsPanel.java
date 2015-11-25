package liquid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import liquid.core.LiquidApplication;

/**
 * Class is a branch of the EnvironmentEditorPanel. Here, all elements
 * linked to creating a sensor are present, such as the X-/Y-Coordinates. 
 */
public class EnviroSensorsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// list of components needed to create a source
	JComboBox<String> sensorType;		
	JComboBox<Float> sensorX;
	JComboBox<Float> sensorY;
	String[] senType = {"Flowmeter"}; // changed to "Flowmeter" without space to fix "flowmeters" not being rendered
	
	/**
	 * Constructor creates the Sensors (or Flow Sensors) section of the EnvironmentEditorPanel.
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
		l.setBounds(35,5,(int)(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("X-Coordinate:");
		l.setBounds(5,30,(int)(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Y-Coordinate:");
		l.setBounds(125, 30, 110, 25);
		add(l);
		
		// makes the drop-downs needed to creating sensors
		sensorType = new JComboBox<String>(senType);
		sensorType.setBounds(115,5,(int)(this.getWidth()/2),25);
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
		sensorX.setBounds(5,55,(int)(this.getWidth()/2.2),25);
		add(sensorX);
		
		sensorY.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroWidLimit; i++) {
			sensorY.addItem(Float.valueOf(i));}
		sensorY.setBounds(125,55,(int)(this.getWidth()/2.2),25);
		add(sensorY);
		
		// sets the default parameters even after an environment size change
		resetSensors();
	}
	
	/**
	 * Method creates the Create button to add in a sensor.
	 */
	public void createButton() {
		// button creates the obstacle according to the parameters set
		JButton create = new JButton("Create");
		create.setBounds(65,90,(int)(this.getWidth()/2.2),25);
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String arg = sensorType.getSelectedItem() + " " + sensorX.getSelectedItem() + " " + sensorY.getSelectedItem();
				LiquidApplication.getGUI().variables.objects.add(arg);
				LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size() - 1;
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		add(create);
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
			sensorX.setSelectedItem(tokens[1]);
			sensorY.setSelectedItem(tokens[2]);
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