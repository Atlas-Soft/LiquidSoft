package liquid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import liquid.core.LiquidApplication;

/**
 * Class creates the additional parameters of the EnvironmentEditorPanel. This is currently
 * located at the bottom of the panel, where it is shared by all drop-down options.
 */
public class EnviroAddiParamPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// defines variables of the additional parameters
	JButton selectNext;
	JButton selectPrev;
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
		
		//selectNext = new JButton("Next Item");
		//selectNext.setBounds(35,5,(this.getWidth()/2),25);
		//add(selectNext);
		
		selectNext = new JButton("Next Item");
		selectPrev = new JButton("Prev Item");
		selectUpdate = new JButton("Update Item");
		delete = new JButton("Delete");
		nextParam();
		prevParam();
		updateParam();
		deleteParam();
	}
	
	public void nextParam() {
		selectNext.setBounds(5,4,(int)(this.getWidth()/2.2),25);
		ActionListener next = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (LiquidApplication.getGUI().variables.selectedObject < LiquidApplication.getGUI().variables.objects.size()-1){
					LiquidApplication.getGUI().variables.selectedObject += 1;
				} else {
					LiquidApplication.getGUI().variables.selectedObject = 0;
				}
				LiquidApplication.getGUI().enviroeditor.update();
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
		};
		selectNext.addActionListener(next);
		add(selectNext);
	}
	
	public void prevParam() {
		selectPrev.setBounds(125,4,(int)(this.getWidth()/2.2),25);
		ActionListener prev = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if( LiquidApplication.getGUI().variables.selectedObject > 0) {
					LiquidApplication.getGUI().variables.selectedObject -= 1;
				} else {
					LiquidApplication.getGUI().variables.selectedObject = LiquidApplication.getGUI().variables.objects.size()-1;
				}
				LiquidApplication.getGUI().enviroeditor.update();
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
		};
		selectPrev.addActionListener(prev);
		add(selectPrev);
	}
	
	public void updateParam() {
		selectUpdate.setBounds(5,34,(int)(this.getWidth()/2.2),25);
		selectUpdate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if (LiquidApplication.getGUI().enviroeditor.select.getSelectedItem().equals("Obstacles")) {
					LiquidApplication.getGUI().enviroeditor.obstacles.createObstacles();
				} else if (LiquidApplication.getGUI().enviroeditor.select.getSelectedItem().equals("Initial Forces")) {
					LiquidApplication.getGUI().enviroeditor.forces.createForces();
				} else if (LiquidApplication.getGUI().enviroeditor.select.getSelectedItem().equals("Flow Sensors")) {
					LiquidApplication.getGUI().enviroeditor.sensors.createButton();
				}
			}
        });
		add(selectUpdate);
	}
	
	public void deleteParam() {
		delete.setBounds(125,34,(int)(this.getWidth()/2.2),25);
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObject);
				LiquidApplication.getGUI().variables.selectedObject = 0;
				LiquidApplication.getGUI().variables.saveState();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		add(delete);
	}
}