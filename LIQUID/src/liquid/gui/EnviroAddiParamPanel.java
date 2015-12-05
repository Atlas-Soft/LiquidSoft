package liquid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import liquid.core.GlobalVar;
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
				if (LiquidApplication.getGUI().variables.selectedObject > 0) {
					LiquidApplication.getGUI().variables.selectedObject -= 1;
				} else {
					LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size()-1;
				}
				LiquidApplication.getGUI().enviroeditor.setSelectedObject();
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
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
				if (LiquidApplication.getGUI().variables.selectedObject < LiquidApplication.getGUI().variables.objects.size()-1) {
					LiquidApplication.getGUI().variables.selectedObject += 1;
				} else {
					LiquidApplication.getGUI().variables.selectedObject = 0;
				}
				LiquidApplication.getGUI().enviroeditor.setSelectedObject();
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
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
				if (LiquidApplication.getGUI().enviroeditor.select.getSelectedItem().equals(
						GlobalVar.EnviroOptions.Obstacles_And_Drains.toString())) {
					LiquidApplication.getGUI().enviroeditor.obstacles.createObstacle(true);

				// updates a source item
				} else if (LiquidApplication.getGUI().enviroeditor.select.getSelectedItem().equals(
						GlobalVar.EnviroOptions.Sources.toString())) {
					LiquidApplication.getGUI().enviroeditor.sources.createSource(true);
				
				// updates a flow meter item
				} else if (LiquidApplication.getGUI().enviroeditor.select.getSelectedItem().equals(
						GlobalVar.EnviroOptions.Flowmeters.toString())) {
					LiquidApplication.getGUI().enviroeditor.sensors.createSensor(true);
				}
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
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
				LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObject);
				
				// additional parameters get disabled when there are no objects present
				if (LiquidApplication.getGUI().variables.objects.size() == 0) {
					setEnabled(false);
				}
				LiquidApplication.getGUI().variables.selectedObject = 0;
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
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