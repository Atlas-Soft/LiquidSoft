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
	 * Constructor creates the Obstacle section of the EnvironmentEditorPanel.
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
		obstacleType.setSelectedIndex(0);
		obstacleType.setBounds(115,5,(this.getWidth()/2),25);
		add(obstacleType);
			
		obstacleX = new JComboBox<Float>();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroLenLimit; i++) {
			obstacleX.addItem(Float.valueOf(i));}
		obstacleX.setSelectedIndex(0);
		obstacleX.setBounds(5,55,(int)(this.getWidth()/2.2),25);
		add(obstacleX);
		
		obstacleY = new JComboBox<Float>();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroWidLimit; i++) {
			obstacleY.addItem(Float.valueOf(i));}
		obstacleY.setSelectedIndex(0);
		obstacleY.setBounds(125,55,(int)(this.getWidth()/2.2),25);
		add(obstacleY);
		
		obstacleL = new JComboBox<Float>();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroLenLimit; i++) {
			obstacleL.addItem(Float.valueOf(i));}
		if (EnvironmentEditorPanel.enviroLenLimit >= 50) {
			obstacleL.setSelectedIndex(50);}
		else {
			obstacleL.setSelectedIndex((int)(EnvironmentEditorPanel.enviroLenLimit/10));}
		obstacleL.setBounds(5,105,(int)(this.getWidth()/2.2),25);
		add(obstacleL);
		
		obstacleW = new JComboBox<Float>();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroWidLimit; i++) {
			obstacleW.addItem(Float.valueOf(i));}
		if (EnvironmentEditorPanel.enviroWidLimit >= 50) {
			obstacleW.setSelectedIndex(50);}
		else {
			obstacleW.setSelectedIndex((int)(EnvironmentEditorPanel.enviroWidLimit/10));}
		obstacleW.setBounds(125,105,(int)(this.getWidth()/2.2),25);
		add(obstacleW);
		createButton();
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
				// throws error messages when the obstacle will go beyond the environment
				// (determined by the X-/Y-Coordinates and the length/width of the obstacle)
				if ((obstacleX.getSelectedIndex() + obstacleL.getSelectedIndex()) > EnvironmentEditorPanel.enviroLenLimit) {
					JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
							"Warning!! The X-Coordinate of your obstacle must be between 0 and " +
							(obstacleL.getSelectedIndex()-EnvironmentEditorPanel.enviroLenLimit),
							"Invalid X-Coordinate!!", JOptionPane.WARNING_MESSAGE);
				} else if ((obstacleY.getSelectedIndex() + obstacleW.getSelectedIndex() > EnvironmentEditorPanel.enviroWidLimit)) {
					JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
							"Warning!! The Y-Coordinate of your obstace must be between 0 and " +
							(obstacleW.getSelectedIndex()-EnvironmentEditorPanel.enviroWidLimit),
							"Invalid Y-Coordinate!!", JOptionPane.WARNING_MESSAGE);
					
				// else sends the obstacle's information to the ArrayList of objects to store
				} else {
					String arg = obstacleType.getSelectedItem() + " " +
						obstacleX.getSelectedItem() + " " + obstacleY.getSelectedItem() + " " +
						obstacleL.getSelectedItem() + " " + obstacleW.getSelectedItem();
					LiquidApplication.getGUI().variables.objects.add(arg);
					LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size()-1;
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
		obstacleL.setSelectedIndex((int)(EnvironmentEditorPanel.enviroLenLimit/10));
		obstacleW.setSelectedIndex((int)(EnvironmentEditorPanel.enviroWidLimit/8));
	}
}