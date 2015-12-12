package liquid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
public class EnviroFlowmetersAndBreakpointsPanel extends JPanel {

	// list of components needed to create a sensor
	private static final long serialVersionUID = 1L;
	float origLength;
	float origWidth;
	boolean actualChange = true;
	
	JComboBox<String> sensorType;
	JComboBox<Float> sensorX;
	JComboBox<Float> sensorY;
	JComboBox<Float> sensorL;
	JComboBox<Float> sensorW;
	ArrayList<Float> params;
	JButton create;
	
	/**
	 * Constructor creates the Flowmeters and Breakpoints section of the EnvironmentEditorPanel.
	 */
	public EnviroFlowmetersAndBreakpointsPanel() {
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
		
		JLabel lengthLab = new JLabel("Length:");
		lengthLab.setBounds(5,100,(this.getWidth()/2),25);
		lengthLab.setVisible(false);
		add(lengthLab);
		
		JLabel widthLab = new JLabel("Width:");
		widthLab.setBounds(125,100,(this.getWidth()/2),25);
		widthLab.setVisible(false);
		add(widthLab);
		
		// makes the drop-downs needed to creating flow meters and break points
		sensorType = new JComboBox<String>();
		sensorType.addItem(GlobalVar.EnviroOptions.Flowmeters.toString());
		sensorType.addItem(GlobalVar.EnviroOptions.Breakpoints.toString());
		sensorType.setBounds(110,15,(int)(this.getWidth()/2),25);
		sensorType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// sets the Length/Width drop-down options to be invisible for flow meters
				if (arg0.getItem().toString().equals(GlobalVar.EnviroOptions.Flowmeters.toString())) {
					lengthLab.setVisible(false);
					widthLab.setVisible(false);
					sensorL.setVisible(false);
					sensorW.setVisible(false);
					create.setBounds(65,115,115,25);
				} else if (arg0.getItem().toString().equals(GlobalVar.EnviroOptions.Breakpoints.toString())) {
					lengthLab.setVisible(true);
					widthLab.setVisible(true);
					sensorL.setVisible(true);
					sensorW.setVisible(true);
					create.setBounds(65,165,115,25);}
			}
        });
		add(sensorType);
		
		// populates the drop-down information for the X/Y/Length/Width
		sensorX = new JComboBox<Float>();
		sensorY = new JComboBox<Float>();
		sensorL = new JComboBox<Float>();
		sensorW = new JComboBox<Float>();
		create = new JButton("Create");
		sensorW.setVisible(false);
		sensorL.setVisible(false);
		
		actualChange = false;
		xYParam(true);
		lenWidParam(true, true, 0);
		actualChange = true;
		createButton(); // makes the Create button
		resetSensors();
	}
	
	/**
	 * Method adjusts the flow meter/breakpoint's settings to be within the limit of the environment's size. It also provides
	 * a real-time update of the flow meter/breakpoint parameters to prevent them from exceeding the environment's boundaries.
	 * @param enviroChange - determines when the environment size changes
	 */
	public void xYParam(boolean enviroChange) {
		// drop-down is newly populated due to an environment size change
		sensorX.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroLenLimit; i++) {
			sensorX.addItem(Float.valueOf(i));}
		sensorX.setBounds(5,75,(int)(this.getWidth()/2.2),25);
		
		// used to adjust the Length drop-down to be within the environment size
		sensorX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				if (!enviroChange || actualChange) {
					origLength = (float)sensorL.getSelectedItem();
					float num = (float)sensorX.getSelectedItem();
					lenWidParam(false, true, num);}}
		});
		add(sensorX);
		
		// drop-down is newly populated due to an environment size change
		sensorY.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroWidLimit; i++) {
			sensorY.addItem(Float.valueOf(i));}
		sensorY.setBounds(125,75,(int)(this.getWidth()/2.2),25);
		
		// used to adjust the Width drop-down to be within the environment size
		sensorY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				if (!enviroChange || actualChange) {
					origWidth = (float)sensorW.getSelectedItem();
					float num = (float)sensorY.getSelectedItem();
					lenWidParam(false, false, num);}}
		});
		add(sensorY);
	}
	
	/**
	 * Method adjusts the breakpoint's Length/Width to be within the environment's size. It also provides a
	 * real-time update of the breakpoint parameters to prevent them from exceeding the environment's boundaries.
	 * @param enviroChange - determines when the environment size changes
	 * @param length       - determines whether to change the Length or Width
	 * @param limit        - the limit for the Length or Width
	 */
	public void lenWidParam(boolean enviroChange, boolean length, float limit) {
		if (enviroChange || length) {
			// drop-down is newly populated due to either an environment size change or a new X-Coordinate chosen
			sensorL.removeAllItems();
			for (int i = 0; i <= (EnvironmentEditorPanel.enviroLenLimit-limit); i++) {
				sensorL.addItem(Float.valueOf(i));}
			if (origLength <= (EnvironmentEditorPanel.enviroLenLimit-limit))
				sensorL.setSelectedIndex((int)origLength);
			else
				sensorL.setSelectedIndex((int)(EnvironmentEditorPanel.enviroLenLimit-limit));
			sensorL.setBounds(5,125,(int)(this.getWidth()/2.2),25);
			add(sensorL);}
		
		if (enviroChange || !length) {
			// drop-down is newly populated due to either an environment size change or a new Y-Coordinate chosen
			sensorW.removeAllItems();
			for (int i = 0; i <= (EnvironmentEditorPanel.enviroWidLimit-limit); i++) {
				sensorW.addItem(Float.valueOf(i));}
			if (origWidth <= (EnvironmentEditorPanel.enviroWidLimit-limit))
				sensorW.setSelectedIndex((int)origWidth);
			else
				sensorW.setSelectedIndex((int)(EnvironmentEditorPanel.enviroWidLimit-limit));
			sensorW.setBounds(125,125,(int)(this.getWidth()/2.2),25);
			add(sensorW);}
	}
	
	/**
	 *  Method used to call the editor panel to make a Create button for sensors.
	 */
	public void createButton() {
		// button creates the flow meter or breakpoint according to the parameters set
		create.setBounds(60,115,(int)(this.getWidth()/2.2),25);
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				createSensor(false);
			}
        });
		add(create);
	}
	
	/**
	 * Method packages data and sends it to the editor to be added into the object list.
	 * @param update - either adds a new object or updates an object's parameters
	 */
	public void createSensor(boolean update) {
		params = new ArrayList<Float>();
		params.add((Float)sensorX.getSelectedItem());
		params.add((Float)sensorY.getSelectedItem());
		params.add((Float)sensorL.getSelectedItem());
		params.add((Float)sensorW.getSelectedItem());
		LiquidApplication.getGUI().enviroeditor.addObject(sensorType, params, update);
	}
	
	/**
	 * Method splits up the String[] of the log file in order to correctly set the
	 * parameters of the Flowmeters and Breakpoints section of the EnvironmentEditorPanel.
	 * @param tokens - String[] of the log file to split
	 */
	public void updateSensors(String[] tokens) {
		try {
			sensorType.setSelectedItem(tokens[0]);
			sensorX.setSelectedItem(Float.parseFloat(tokens[1]));
			sensorY.setSelectedItem(Float.parseFloat(tokens[2]));
			sensorL.setSelectedItem(Float.parseFloat(tokens[3]));
			sensorW.setSelectedItem(Float.parseFloat(tokens[4]));
		} catch (Exception e) {
			e.printStackTrace();}
	}
	
	/**
	 * Method resets the parameters of the Flowmeters and Breakpoints section.
	 */
	public void resetSensors() {
		origLength = 25;
		origWidth = 25;
		sensorType.setSelectedIndex(0);
		sensorX.setSelectedIndex(0);
		sensorY.setSelectedIndex(0);
		sensorL.setSelectedIndex((int)origLength);
		sensorW.setSelectedIndex((int)origWidth);
	}
}