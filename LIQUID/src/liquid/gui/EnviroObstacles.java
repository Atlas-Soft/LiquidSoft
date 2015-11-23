package liquid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import liquid.core.LiquidApplication;

/**
 * Class is a branch of the EnvironmentEditorPanel. Here, all elements linked
 * to creating an obstacle are present, such as the X-/Y-Coordinates. 
 */
public class EnviroObstacles extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// list of components needed to create an obstacle
	JComboBox<String> obstacleType;
	JComboBox<Float> obstacleX;
	JComboBox<Float> obstacleY;
	JComboBox<Float> obstacleL;
	JComboBox<Float> obstacleW;
	String[] obstype = {"Rectangular", "Circular"};
	
	/**
	 * Constructor creates the Obstacles section of the EnvironmentEditorPanel.
	 */
	public EnviroObstacles() {
		initComponents();
	}
	
	/**
	 * Method creates the labels and drop-downs associated with creating obstacles.
	 */
	public void initComponents() {
		setBounds(5,30,240,175);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		// makes labels specific to creating obstacles
		JLabel l = new JLabel("Object Type:");
		l.setBounds(35,5,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("X-Coordinate:");
		l.setBounds(5,30,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Y-Coordinate:");
		l.setBounds(125,30,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Length:");
		l.setBounds(5,80,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Width:");
		l.setBounds(125,80,(this.getWidth()/2),25);
		add(l);
		
		// makes the drop-downs needed to create obstacles
		obstacleType = new JComboBox<String>(obstype);
		obstacleType.setBounds(115,5,(this.getWidth()/2),25);
		add(obstacleType);
		
		// makes drop-downs to represent the parameters of an obstacle
		obstacleX = new JComboBox<Float>();
		obstacleY = new JComboBox<Float>();
		obstacleL = new JComboBox<Float>();
		obstacleW = new JComboBox<Float>();
		adjustSettings(); // populates the drop-down information
		createButton(); // creates the Create button
	}
	
	/**
	 * Method adjusts the obstacles settings to be within the limit of the environment's size. It also provides
	 * a real-time update of the obstacle parameters to prevent them from exceeding the environment's boundaries.
	 */
	public void adjustSettings() {
		// each drop-down first gets all items removed from it, then gets
		// populated with items all dependent on the environment boundaries
		obstacleX.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroLenLimit; i++) {
			obstacleX.addItem(Float.valueOf(i));}
		obstacleX.setBounds(5,55,(int)(this.getWidth()/2.2),25);
		add(obstacleX);
		
		obstacleY.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroWidLimit; i++) {
			obstacleY.addItem(Float.valueOf(i));}
		obstacleY.setBounds(125,55,(int)(this.getWidth()/2.2),25);
		add(obstacleY);
		
		// sets the default settings to be always 50, unless restricted by the environment
		obstacleL.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroLenLimit; i++) {
			obstacleL.addItem(Float.valueOf(i));}
		obstacleL.setBounds(5,105,(int)(this.getWidth()/2.2),25);
		add(obstacleL);
		
		obstacleW.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroWidLimit; i++) {
			obstacleW.addItem(Float.valueOf(i));}
		obstacleW.setBounds(125,105,(int)(this.getWidth()/2.2),25);
		add(obstacleW);
		
		// sets what the default parameters should be
		resetObstacles();
	}
	
	/**
	 * Method creates the Create button and throws error messages when
	 * the obstacle will go out of the predefined environment size.
	 */
	public void createButton() {
		// button creates the obstacle according to the parameters set
		JButton create = new JButton("Create");
		create.setBounds(65,140,(int)(this.getWidth()/2.2),25);
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// throws error messages when the obstacle will go beyond the environment (determined
				// by the X-/Y-Coordinates and the Length/Width of the obstacle, respectively)
				if ((obstacleX.getSelectedIndex() + obstacleL.getSelectedIndex()) > EnvironmentEditorPanel.enviroLenLimit) {
					JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
							"Warning!! Your X-Coordinate must be from 0.0 - " + (EnvironmentEditorPanel.enviroLenLimit - obstacleL.getSelectedIndex()) +
							",\n or your Length must be from 0.0 - " + (EnvironmentEditorPanel.enviroLenLimit - obstacleX.getSelectedIndex()) +
							"\n to be in the boundaries of your desired environment size.",
							"Invalid Parameters!!", JOptionPane.WARNING_MESSAGE);
				} else if ((obstacleY.getSelectedIndex() + obstacleW.getSelectedIndex() > EnvironmentEditorPanel.enviroWidLimit)) {
					JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
							"Warning!! Your Y-Coordinate must be from 0.0 - " + (EnvironmentEditorPanel.enviroWidLimit - obstacleW.getSelectedIndex()) +
							",\n or your Width must be from 0.0 - " + (EnvironmentEditorPanel.enviroWidLimit - obstacleY.getSelectedIndex()) +
							"\n to be in the boundaries of your desired environment size.",
							"Invalid Parameters!!", JOptionPane.WARNING_MESSAGE);
					
				// else sends the obstacle's information to the ArrayList of objects to store
				} else {
					String arg = obstacleType.getSelectedItem() + " " +
						obstacleX.getSelectedItem() + " " + obstacleY.getSelectedItem() + " " +
						obstacleL.getSelectedItem() + " " + obstacleW.getSelectedItem();
					LiquidApplication.getGUI().variables.objects.add(arg);
					LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size() - 1;
					LiquidApplication.getGUI().variables.saveState();
					LiquidApplication.getGUI().sim.repaint();
				}
			}
        });
		add(create);
	}
	
	/**
	 * Method splits up the String[] of the log file in order to correctly set
	 * the parameters of the Obstacles section of the EnvironmentEditorPanel.
	 * 
	 * @param tokens - String[] of the log file to split
	 */
	public void updateObstacles(String[] tokens) {
		try {
			obstacleType.setSelectedItem(tokens[0]);
			obstacleX.setSelectedItem(tokens[1]);
			obstacleY.setSelectedItem(tokens[2]);
			obstacleL.setSelectedItem(tokens[3]);
			obstacleW.setSelectedItem(tokens[4]);
		} catch (Exception e) {
			e.printStackTrace();}
	}
	
	/**
	 * Method resets the parameters of the Obstacles section.
	 */
	public void resetObstacles() {
		obstacleType.setSelectedIndex(0);
		obstacleX.setSelectedIndex(0);
		obstacleY.setSelectedIndex(0);
		
		// sets the default settings to be always 50, unless restricted by the environment
		if (EnvironmentEditorPanel.enviroLenLimit >= 50) obstacleL.setSelectedIndex(50);
		else obstacleL.setSelectedIndex((int) (EnvironmentEditorPanel.enviroLenLimit/10));
		
		if (EnvironmentEditorPanel.enviroLenLimit >= 50) obstacleW.setSelectedIndex(50);
		else obstacleW.setSelectedIndex((int) (EnvironmentEditorPanel.enviroLenLimit/10));
	}
}