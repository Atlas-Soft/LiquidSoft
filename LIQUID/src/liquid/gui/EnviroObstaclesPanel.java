package liquid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import liquid.core.LiquidApplication;

/**
 * Class is a branch of the EnvironmentEditorPanel. Here, all elements linked
 * to creating an obstacle are present, such as the X-/Y-Coordinates. 
 */
public class EnviroObstaclesPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// list of components needed to create an obstacle
	JComboBox<String> obstacleType;
	JComboBox<Float> obstacleRotation;
	JComboBox<Float> obstacleX;
	JComboBox<Float> obstacleY;
	JComboBox<Float> obstacleL;
	JComboBox<Float> obstacleW;
	JButton create;
	
	String[] obsType = {"Rectangular", "Circular"};
	ArrayList<Float> params;
	
	/**
	 * Constructor creates the Obstacles section of the EnvironmentEditorPanel.
	 */
	public EnviroObstaclesPanel() {
		initComponents();
	}
	
	/**
	 * Method creates the labels and drop-downs associated with creating obstacles.
	 */
	public void initComponents() {
		setBounds(5,30,240,205);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		// makes labels specific to creating obstacles
		JLabel l = new JLabel("Object Type:");
		l.setBounds(5,5,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Rotation (Degrees):");
		l.setBounds(125,5,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("X-Coordinate:");
		l.setBounds(5,60,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Y-Coordinate:");
		l.setBounds(125,60,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Length:");
		l.setBounds(5,110,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Width:");
		l.setBounds(125,110,(this.getWidth()/2),25);
		add(l);
		
		// makes the drop-downs needed to creating obstacles
		obstacleType = new JComboBox<String>(obsType);
		obstacleType.setBounds(5,30,(int)(this.getWidth()/2.2),25);
		add(obstacleType);
		
		obstacleRotation = new JComboBox<Float>();
		obstacleX = new JComboBox<Float>();
		obstacleY = new JComboBox<Float>();
		obstacleL = new JComboBox<Float>();
		obstacleW = new JComboBox<Float>();
		create = new JButton("Create");
		obstaclesParam(); // populates the drop-down information
		createObstacles(); // makes a Create button
		
		//EnviroAddiParamPanel test = new EnviroAddiParamPanel();
		//add(test);
	}
	
	/**
	 * Method adjusts the obstacle's settings to be within the limit of the environment's size. It also provides
	 * a real-time update of the obstacle parameters to prevent them from exceeding the environment's boundaries.
	 */
	public void obstaclesParam() {
		// each drop-down first gets all items removed from it, then gets
		// populated with items all dependent on the environment boundaries
		obstacleRotation.removeAllItems();
		for (int i = 0; i <= 360; i++) {
			obstacleRotation.addItem(Float.valueOf(i));}
		obstacleRotation.setBounds(125,30,(int)(this.getWidth()/2.2),25);
		add(obstacleRotation);
		
		obstacleX.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroLenLimit; i++) {
			obstacleX.addItem(Float.valueOf(i));}
		obstacleX.setBounds(5,85,(int)(this.getWidth()/2.2),25);
		add(obstacleX);
		
		obstacleY.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroWidLimit; i++) {
			obstacleY.addItem(Float.valueOf(i));}
		obstacleY.setBounds(125,85,(int)(this.getWidth()/2.2),25);
		add(obstacleY);
		
		obstacleL.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroLenLimit; i++) {
			obstacleL.addItem(Float.valueOf(i));}
		obstacleL.setBounds(5,135,(int)(this.getWidth()/2.2),25);
		add(obstacleL);
		
		obstacleW.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroWidLimit; i++) {
			obstacleW.addItem(Float.valueOf(i));}
		obstacleW.setBounds(125,135,(int)(this.getWidth()/2.2),25);
		add(obstacleW);
		
		// sets the default parameters even after an environment size change
		resetObstacles();
	}
	
	/**
	 * Method used to call the editor panel to make a Create button for Obstacles.
	 */
	public void createObstacles() {
		// button creates the obstacle according to the parameters set
		create.setBounds(65,170,(int)(this.getWidth()/2.2),25);
		create.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			params = new ArrayList<Float>();
			params.add((Float) obstacleX.getSelectedItem());
			params.add((Float) obstacleY.getSelectedItem());
			params.add((Float) obstacleL.getSelectedItem());
			params.add((Float) obstacleW.getSelectedItem());
			params.add((Float) obstacleRotation.getSelectedItem());
			LiquidApplication.getGUI().enviroeditor.checkBoundaries(obstacleType, params);
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
			obstacleRotation.setSelectedItem(tokens[5]);
		} catch (Exception e) {
			e.printStackTrace();}
	}
	
	/**
	 * Method resets the parameters of the Obstacles section.
	 */
	public void resetObstacles() {
		obstacleType.setSelectedIndex(0);
		obstacleRotation.setSelectedIndex(0);
		obstacleX.setSelectedIndex(0);
		obstacleY.setSelectedIndex(0);
		obstacleL.setSelectedIndex((int) (EnvironmentEditorPanel.enviroLenLimit/10));
		obstacleW.setSelectedIndex((int) (EnvironmentEditorPanel.enviroWidLimit/8));
	}
}