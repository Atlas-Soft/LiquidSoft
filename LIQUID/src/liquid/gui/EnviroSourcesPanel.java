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
 * Class is a branch of the EnvironmentEditorPanel. Here, all elements linked
 * to creating a source are present, such as the force in the X/Y directions. 
 */
public class EnviroSourcesPanel extends JPanel {

	// list of components needed to create a source
	private static final long serialVersionUID = 1L;
	JComboBox<String> sourceType;
	JComboBox<Float> sourceSpeed;
	JComboBox<Float> sourceX;
	JComboBox<Float> sourceY;
	JComboBox<Float> sourceXComp;
	JComboBox<Float> sourceYComp;
	ArrayList<Float> params;
	
	/**
	 * Constructor creates the Sources section of the EnvironmentEditorPanel.
	 */
	public EnviroSourcesPanel() {
		initComponents();
	}
	
	/**
	 * Method creates the labels and drop-downs associated with creating sources.
	 */
	public void initComponents() {
		setBounds(5,30,240,205);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		// makes labels specific to creating sources
		JLabel l = new JLabel("Object Type:");
		l.setBounds(5,5,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Flow Speed:");
		l.setBounds(125,5,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("X-Coordinate:");
		l.setBounds(5,60,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Y-Coordinate:");
		l.setBounds(125,60,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("X-Force:");
		l.setBounds(5,110,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Y-Force:");
		l.setBounds(125,110,(this.getWidth()/2),25);
		add(l);
		
		// makes the drop-downs needed to create sources
		sourceType = new JComboBox<String>();
		sourceType.addItem(GlobalVar.EnviroOptions.Sources.toString());
		sourceType.setBounds(5,30,(int)(this.getWidth()/2.2),25);
		add(sourceType);
		
		sourceSpeed = new JComboBox<Float>();
		sourceX = new JComboBox<Float>();
		sourceY = new JComboBox<Float>();
		sourceXComp = new JComboBox<Float>();
		sourceYComp = new JComboBox<Float>();
		sourcesParam(); // populates the drop-down information
		createButton(); // makes a Create button
	}
	
	/**
	 * Method adjusts the source's settings to be within the limit of the environment's size. It also provides
	 * a real-time update of the source parameters to prevent them from exceeding the environment's boundaries.
	 */
	public void sourcesParam() {
		// each drop-down first gets all items removed from it, then gets
		// populated with items all dependent on the environment boundaries
		sourceSpeed.removeAllItems();
		for (int i = 0; i <= 60; i++) {
			sourceSpeed.addItem(Float.valueOf(i));}
		sourceSpeed.setBounds(125,30,(int)(this.getWidth()/2.2),25);
		add(sourceSpeed);
		
		sourceX.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroLenLimit; i++) {
			sourceX.addItem(Float.valueOf(i));}
		sourceX.setBounds(5,85,(int)(this.getWidth()/2.2),25);
		add(sourceX);
		
		sourceY.removeAllItems();
		for (int i = 0; i <= EnvironmentEditorPanel.enviroWidLimit; i++) {
			sourceY.addItem(Float.valueOf(i));}
		sourceY.setBounds(125,85,(int)(this.getWidth()/2.2),25);
		add(sourceY);
		
		sourceXComp.removeAllItems();
		for (int i = -60; i <= 60; i++) {
			sourceXComp.addItem(Float.valueOf(i));}
		sourceXComp.setBounds(5,135,(int)(this.getWidth()/2.2),25);
		add(sourceXComp);
		
		sourceYComp.removeAllItems();
		for (int i = -60; i <= 60; i++) {
			sourceYComp.addItem(Float.valueOf(i));}
		sourceYComp.setBounds(125,135,(int)(this.getWidth()/2.2),25);
		add(sourceYComp);
		
		resetSources(); // sets the parameters even after the environment size changes
	}
	
	/**
	 * Method used to call the editor panel to make a Create button for Sources.
	 */
	public void createButton() {
		// button creates the source according to the parameters set
		JButton create = new JButton("Create");
		create.setBounds(65,170,(int)(this.getWidth()/2.2),25);
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				createSource(false);
			}
		});
		add(create);
	}
	
	/**
	 * Method packages data and sends it to the editor to check if the
	 * parameters are valid, and if so to be added into the object list.
	 */
	public void createSource(boolean update) {
		params = new ArrayList<Float>();
		params.add((Float) sourceX.getSelectedItem());
		params.add((Float) sourceY.getSelectedItem());
		params.add((Float) sourceXComp.getSelectedItem());
		params.add((Float) sourceYComp.getSelectedItem());
		params.add((Float) sourceSpeed.getSelectedItem());
		LiquidApplication.getGUI().enviroeditor.addObject(sourceType, params, update);
	}
	
	/**
	 * Method splits up the String[] of the log file in order to correctly set
	 * the parameters of the Sources section of the EnvironmentEditorPanel.
	 * 
	 * @param tokens - String[] of the log file to split
	 */
	public void updateSources(String[] tokens) {
		try {
			sourceType.setSelectedItem(tokens[0]);
			sourceX.setSelectedItem(Float.parseFloat(tokens[1]));
			sourceY.setSelectedItem(Float.parseFloat(tokens[2]));
			sourceXComp.setSelectedItem(Float.parseFloat(tokens[3]));
			sourceYComp.setSelectedItem(Float.parseFloat(tokens[4]));
			sourceSpeed.setSelectedItem(Float.parseFloat(tokens[5]));
		} catch (Exception e) {
			e.printStackTrace();}
	}
	
	/**
	 * Method resets the parameters of the Sources section.
	 */
	public void resetSources() {
		sourceType.setSelectedIndex(0);
		sourceSpeed.setSelectedIndex(20);
		sourceX.setSelectedIndex(0);
		sourceY.setSelectedIndex(0);
		sourceXComp.setSelectedIndex(75);
		sourceYComp.setSelectedIndex(75);
	}
}