package liquid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import liquid.core.GlobalVariables;
import liquid.core.LiquidApplication;

/**
 * Class creates the additional parameters of the EnvironmentEditorPanel. This is currently
 * located at the bottom of the panel, where it is shared by all drop-down options.
 */
public class EnviroAddiParamPanel extends JPanel {
	
	// defines variables of the additional parameters
	private static final long serialVersionUID = 1L;
	JButton selectPrev;
	JButton selectNext;
	JButton selectUpdate;
	JButton delete;

	/**
	 * Constructor adds the additional parameters of the EnvironmentEditorPanel.
	 */
	public EnviroAddiParamPanel() {
		initComponents();
	}
	
	/**
	 * Initializes the buttons of the additional parameters.
	 */
	public void initComponents() {
		setBounds(5,240,240,65);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);

		selectPrev = new JButton("Prev Item");
		selectNext = new JButton("Next Item");
		selectUpdate = new JButton("Update Item");
		delete = new JButton("Delete");
		prevParam();
		nextParam();
		updateParam();
		deleteParam();
	}
	
	/**
	 * Method adds the "Prev Item" button, which is designed to scroll backwards and
	 * select the previous object, whether it be an obstacle, source, or sensor.
	 */
	public void prevParam() {
		selectPrev.setBounds(5,4,(int)(this.getWidth()/2.2),25);
		selectPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// if multiple objects are present, then the simulator will select the previous item
				if (LiquidApplication.getGUI().getFileVariables().getSelectedObject() > 0) {
					LiquidApplication.getGUI().getFileVariables().setSelectedObject(LiquidApplication.getGUI().getFileVariables().getSelectedObject()-1);
				} else {
					LiquidApplication.getGUI().getFileVariables().setSelectedObject(LiquidApplication.getGUI().getFileVariables().getObjects().size()-1);
				}
				LiquidApplication.getGUI().getEditorPanel().setSelectedObjectPanel();
				LiquidApplication.getGUI().getApplicationState().saveState();
				LiquidApplication.getGUI().getSimulationPanel().repaint();
			}
		});
		add(selectPrev);
	}
	
	/**
	 * Method adds the "Next Item" button, which is designed to scroll forward
	 * and select the next object, whether it be an obstacle, source, or sensor.
	 */
	public void nextParam() {
		selectNext.setBounds(125,4,(int)(this.getWidth()/2.2),25);
		ActionListener next = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// if multiple objects are present, then the simulator will select the next item
				if (LiquidApplication.getGUI().getFileVariables().getSelectedObject() < LiquidApplication.getGUI().getFileVariables().getObjects().size()-1) {
					LiquidApplication.getGUI().getFileVariables().setSelectedObject(LiquidApplication.getGUI().getFileVariables().getSelectedObject()+1);
				} else {
					LiquidApplication.getGUI().getFileVariables().setSelectedObject(0);
				}
				LiquidApplication.getGUI().getEditorPanel().setSelectedObjectPanel();
				LiquidApplication.getGUI().getApplicationState().saveState();
				LiquidApplication.getGUI().getSimulationPanel().repaint();
			}
		};
		selectNext.addActionListener(next);
		add(selectNext);
	}
	
	/**
	 * Method is used to update the parameters of an object, whether it will be an obstacle, source, or sensor. This will
	 * do a quick check to ensure that the new parameters will not go beyond the boundaries of the environment itself.
	 */
	public void updateParam() {
		selectUpdate.setBounds(5,34,(int)(this.getWidth()/2.2),25);
		selectUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// updates an obstacle or drain item
				if (LiquidApplication.getGUI().getEditorPanel().select.getSelectedItem().equals(
						GlobalVariables.EnviroOptions.Obstacles.toString() + " and " +
						GlobalVariables.EnviroOptions.Drains.toString())) {
					LiquidApplication.getGUI().getEditorPanel().obstacles.createObstacle(true);

				// updates a source item
				} else if (LiquidApplication.getGUI().getEditorPanel().select.getSelectedItem().equals(
						GlobalVariables.EnviroOptions.Sources.toString())) {
					LiquidApplication.getGUI().getEditorPanel().sources.createSource(true);
				
				// updates a flow meter item
				} else if (LiquidApplication.getGUI().getEditorPanel().select.getSelectedItem().equals(
						GlobalVariables.EnviroOptions.Flowmeters.toString() + " and " +
						GlobalVariables.EnviroOptions.Breakpoints.toString())) {
					LiquidApplication.getGUI().getEditorPanel().sensors.createSensor(true);
				}
			}
        });
		add(selectUpdate);
	}
	
	/**
	 * Method deletes the currently selected object, whether it be an obstacle, source, or sensor.
	 */
	public void deleteParam() {
		delete.setBounds(125,34,(int)(this.getWidth()/2.2),25);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().getFileVariables().getObjects().remove(LiquidApplication.getGUI().getFileVariables().getSelectedObject());
				
				// additional parameters get disabled when there are no objects present
				if (LiquidApplication.getGUI().getFileVariables().getObjects().size() == 0) {
					setEnabled(false);
				}
				LiquidApplication.getGUI().getFileVariables().setSelectedObject(0);
				LiquidApplication.getGUI().getApplicationState().saveState();
				LiquidApplication.getGUI().getSimulationPanel().repaint();
			}
        });
		add(delete);
	}
	
	/**
	 * Method to enable/disable the additional parameter buttons. HOWEVER, this is separate from enabling/disabling
	 * features when the simulation is running. This enables the buttons ONLY IF there are any objects present. 
	 */
	public void setEnabled(boolean enable) {
		selectPrev.setEnabled(enable);
		selectNext.setEnabled(enable);
		selectUpdate.setEnabled(enable);
		delete.setEnabled(enable);
	}
}