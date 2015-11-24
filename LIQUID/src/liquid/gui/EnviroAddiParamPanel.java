package liquid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import liquid.core.LiquidApplication;

public class EnviroAddiParamPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public EnviroAddiParamPanel() {
		initComponents();
	}
	
	public void initComponents() {
		setBounds(5,200,240,115);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		nextParam();
		prevParam();
		updateParam();
		deleteParam();
	}
	
	public void nextParam() {
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
		
		JButton selectNext = new JButton("Next Item");
		selectNext.setBounds(5,200,(int)(this.getWidth())/2,25);
		selectNext.addActionListener(next);
		add(selectNext);
	}
	
	public void prevParam() {
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
		
		JButton selectPrev = new JButton("Prev Item");
		selectPrev.setBounds(125,200,(int)(this.getWidth()/2),25);
		selectPrev.addActionListener(prev);
		add(selectPrev);
	}
	
	public void updateParam() {
		JButton selectUpdate = new JButton("Update Item");
		selectUpdate.setBounds(5,240,(int)(this.getWidth()/2),25);
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
		JButton delete = new JButton("Delete");
		delete.setBounds(125,240,(int)(this.getWidth()/2),25);
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