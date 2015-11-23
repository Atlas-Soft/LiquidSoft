package liquid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import liquid.core.LiquidApplication;

/**
 * Class is a branch of the EnvironmentEditorPanel. Here, all elements linked
 * to creating a source are present, such as the force in the X-/Y-Coordinates. 
 */
public class EnviroSources extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// list of components needed to create a source
	JComboBox<String> forceType;
	JTextField forceX;
	JTextField forceY;
	JTextField forceXComp;
	JTextField forceYComp;
	String[] fotype = {"Source"};
	
	/**
	 * Constructor creates the Source (or Initial Force) section of the EnvironmentEditorPanel.
	 */
	public EnviroSources() {
		initComponents();
	}
	
	/**
	 * Method creates the labels and drop-downs associated with creating sources.
	 */
	public void initComponents() {
		setBounds(5,30,240,275);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		setVisible(false);
		
		// makes labels specific to creating sources
		JLabel l = new JLabel("Object Type:");
		l.setBounds(35,5,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("X-Coordinate:");
		l.setBounds(5,30,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Y-Coordinate:");
		l.setBounds(125,30,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Force-X:");
		l.setBounds(5,80,(this.getWidth()/2),25);
		add(l);
		
		l = new JLabel("Force-Y:");
		l.setBounds(125,80,(this.getWidth()/2),25);
		add(l);
		
		// makes the drop-downs needed to create sources
		forceType = new JComboBox<String>(fotype);
		forceType.setSelectedIndex(0);
		forceType.setBounds(115, 5, 110, 25);
		add(forceType);
			
		forceX = new JTextField("0");
		forceX.setBounds(5, 55, 110, 25);
		add(forceX);
		
		forceY = new JTextField("0");
		forceY.setBounds(125, 55, 110, 25);
		add(forceY);
		
		forceXComp = new JTextField("10");
		forceXComp.setBounds(5, 105, 110, 25);
		add(forceXComp);
		
		forceYComp = new JTextField("10");
		forceYComp.setBounds(125, 105, 110, 25);
		add(forceYComp);
		
		JButton create = new JButton("Create");
		create.setBounds(65, 140, 110, 25);
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					String arg = forceType.getSelectedItem() + " ";
					arg += Float.parseFloat(forceX.getText()) + " ";
					arg += Float.parseFloat(forceY.getText()) + " ";
					arg += Float.parseFloat(forceXComp.getText()) + " ";
					arg += Float.parseFloat(forceYComp.getText());
					LiquidApplication.getGUI().variables.objects.add(arg);
					LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size()-1;
					LiquidApplication.getGUI().variables.saveState();
					LiquidApplication.getGUI().sim.repaint();
				} catch (Exception e) {
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		add(create);
	}
	
	/**
	 * Method splits up the String[] of the log file in order to correctly set
	 * the parameters of the Sources section of the EnvironmentEditorPanel.
	 * 
	 * @param tokens - String[] of the log file to split
	 */
	public void updateObstacles(String[] tokens) {
		try {
			forceType.setSelectedItem(tokens[0]);
			forceX.setText(tokens[1]);
			forceY.setText(tokens[2]);
			forceXComp.setText(tokens[3]);
			forceYComp.setText(tokens[4]);
		} catch (Exception e) {
			e.printStackTrace();}
	}
	
	/**
	 * Method resets the parameters of the Sources section.
	 */
	public void resetSources() {
		forceType.setSelectedIndex(0);
		forceX.setText("0");
		forceY.setText("0");
		forceXComp.setText("10");
		forceYComp.setText("10");
	}
}