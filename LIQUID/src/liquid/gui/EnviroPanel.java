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
 * Class is a branch of the EnvironmentEditorPanel. Here, all elements linked to
 * creating the size of the environment are present, such as the length and width.
 */
public class EnviroPanel extends JPanel {

	// components needed to configure the size of the environment
	private static final long serialVersionUID = 1L;
	JComboBox<Float> enviroLen;
	JComboBox<Float> enviroWid;
	
	/**
	 * Constructor creates the Environment section of the EnvironmentEditorPanel.
	 */
	public EnviroPanel() {
		initComponents();
	}
	
	/**
	 * Method creates the labels and drop-downs associated with setting the environment's size.
	 */
	public void initComponents() {
		setBounds(5,30,240,205);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
			
		// makes labels for creating an environment
		JLabel l = new JLabel("Length:");
		l.setBounds(5,50,(this.getWidth()/2),25);
		add(l);
			
		l = new JLabel("Width:");
		l.setBounds(125,50,(this.getWidth()/2),25);
		add(l);
		
		// populates the drop-down information for the Length/Width
		enviroLen = new JComboBox<Float>();
		enviroWid = new JComboBox<Float>();
		enviroParam();
		drawButton(); // creates the Draw button
		resetEnviro();
	}
	
	/**
	 * Method sets the boundaries, or size, of the environment. The limit of the length and width
	 * are defined separately in order for real-time updates of the other parameters to occur.
	 */
	public void enviroParam() {
		// makes drop-downs for creating an environment
		for (int i = 0; i <= EditorPanel.enviroLenLimit; i++) {
			enviroLen.addItem(Float.valueOf(i));}
		enviroLen.setBounds(5,75,(int)(this.getWidth()/2.2),25);
		add(enviroLen);
					
		for (int i = 0; i <= EditorPanel.enviroWidLimit; i++) {
			enviroWid.addItem(Float.valueOf(i));}
		enviroWid.setBounds(125,75,(int)(this.getWidth()/2.2),25);
		add(enviroWid);
	}
	
	/**
	 * Method creates the button to adjust the size of the environment itself.
	 */
	public void drawButton() {
		// draws the size of the environment based on the parameters set
		JButton draw = new JButton("Draw");
		draw.setBounds(60,110,(int)(this.getWidth()/2.2),25);
		draw.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			// sets the length/width according to the drop-downs
			LiquidApplication.getGUI().getFileVariables().setEnvironmentWidth((float)enviroLen.getSelectedItem());
			LiquidApplication.getGUI().getFileVariables().setEnvironmentHeight((float)enviroWid.getSelectedItem());
			LiquidApplication.getGUI().getApplicationState().saveState();
			LiquidApplication.getGUI().getSimulationPanel().repaint();
			
			EditorPanel.enviroLenLimit = (float) enviroLen.getSelectedItem();
			EditorPanel.enviroWidLimit = (float) enviroWid.getSelectedItem();
			updateLimits(); // resets the boundary limits
			}
		});
		add(draw);
	}
	
	/**
	 * Method sets the parameters of the Environment section of the EnvironmentEditorPanel.
	 */
	public void updateEnviro() {
		enviroLen.setSelectedItem(LiquidApplication.getGUI().getFileVariables().getEnvironmentWidth());
		enviroWid.setSelectedItem(LiquidApplication.getGUI().getFileVariables().getEnvironmentHeight());
		updateLimits();
	}
	
	/**
	 * Resets the limits to adjust the boundaries of creating various objects.
	 */
	public void updateLimits() {
		LiquidApplication.getGUI().getEditorPanel().obstacles.actualChange = false;
		LiquidApplication.getGUI().getEditorPanel().obstacles.xYParam(true);
		LiquidApplication.getGUI().getEditorPanel().obstacles.lenWidParam(true, true, 0);
		LiquidApplication.getGUI().getEditorPanel().obstacles.actualChange = true;
		
		LiquidApplication.getGUI().getEditorPanel().sources.sourcesParam();
		
		LiquidApplication.getGUI().getEditorPanel().sensors.actualChange = false;
		LiquidApplication.getGUI().getEditorPanel().sensors.xYParam(true);
		LiquidApplication.getGUI().getEditorPanel().sensors.lenWidParam(true, true, 0);
		LiquidApplication.getGUI().getEditorPanel().sensors.actualChange = true;
	}
	
	/**
	 * Method resets the parameters of the Environment section.
	 */
	public void resetEnviro() {
		enviroLen.setSelectedItem(EditorPanel.enviroLenLimit);
		enviroWid.setSelectedItem(EditorPanel.enviroWidLimit);
	}
}