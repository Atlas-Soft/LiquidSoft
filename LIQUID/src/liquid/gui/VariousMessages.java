package liquid.gui;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import liquid.core.LiquidApplication;

/**
 * Class stores all of the JOptionPane messages for the other classes to use and display.
 */
public class VariousMessages {

	/**
	 * Method prompts the user whether or not to actually exit the simulator.
	 * 
	 * @return - the yes or no option chosen
	 */
	public int exitSimulation() {
		int exitSim = JOptionPane.showConfirmDialog(LiquidApplication.getGUI().frame,
				"Are you sure you want to exit the simulator?", "Exit Simulator?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		return exitSim;
	}
	
	/**
	 * Method prompts the user whether or not to actually start a new simulation.
	 * 
	 * @return - the yes or no option chosen
	 */
	public int newSimulation() {
		int newSim = JOptionPane.showConfirmDialog(LiquidApplication.getGUI().frame,
				"Are you sure you want to make a new simulation?", "Create New Simulation?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		return newSim;
	}
	
	/**
	 * Method checks when the object (obstacle or source) goes
	 * beyond the limit in the X direction, that is, the upper limit.
	 * 
	 * @param enviroLenLimit - the length's limit
	 * @param params   - the ArrayList<Float> of parameters
	 */
	public void xForExceedsLowerLimit(float enviroLenLimit, ArrayList<Float> params) {
		// asks the user if the X-Coordinate or Length/Force parameter is preferred
		int option = JOptionPane.showOptionDialog(LiquidApplication.getGUI().frame,
			"Do you want the X-Coordinate " + params.get(0) + " or the X-Force " + params.get(2) + "?",
			"Invalid Parameters!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
			null, new String[]{"X-Coordinate", "X-Force"}, "default");
		
		// lets the user know the range of acceptable X-Force values
		if (option == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
				"Then your X-Force must be greater than or equal to " + (0 - params.get(0)) + ". Thank you!",
				"Choose A Different X-Force", JOptionPane.WARNING_MESSAGE);
		
		// lets the user know the range of acceptable X-Coordinate values
		} else if (option == JOptionPane.NO_OPTION){
			JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
				"Then your X-Coordinate must be greater than or equal to " + (0 - params.get(2)) + ". Thank you!",
				"Choose A Different X-Coordinate", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Method checks when the object (obstacle or source) goes
	 * beyond the limit in the X direction, that is, the upper limit.
	 * 
	 * @param enviroLenLimit - the length's limit
	 * @param params   - the ArrayList<Float> of parameters
	 */
	public void xObsExceedsUpperLimit(float enviroLenLimit, ArrayList<Float> params) {
		// asks the user if the X-Coordinate or Length parameter is preferred
		int option = JOptionPane.showOptionDialog(LiquidApplication.getGUI().frame,
			"Do you want the X-Coordinate " + params.get(0) + " or the Length " + params.get(2) + "?",
			"Invalid Parameters!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
			null, new String[]{"X-Coordinate", "Length"}, "default");
		
		// lets the user know the range of acceptable Length values
		if (option == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
				"Then your Length must be from 0.0 to " + (enviroLenLimit - params.get(0)) + ". Thank you!",
				"Choose A Different Length", JOptionPane.WARNING_MESSAGE);
		
		// lets the user know the range of acceptable X-Coordinate values
		} else if (option == JOptionPane.NO_OPTION){
			JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
				"Then your X-Coordinate must be from 0.0 to " + (enviroLenLimit - params.get(2)) + ". Thank you!",
				"Choose A Different X-Coordinate", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Method checks when the object (obstacle or source) goes
	 * beyond the limit in the Y direction, that is, the upper limit.
	 * 
	 * @param enviroWidLimit - the width's limit
	 * @param params   - the ArrayList<Float> of parameters
	 */
	public void yObsExceedsUpperLimit(float enviroWidLimit, ArrayList<Float> params) {
		// asks the user if the Y-Coordinate or Width parameter is preferred
		int option = JOptionPane.showOptionDialog(LiquidApplication.getGUI().frame,
			"Do you want the Y-Coordinate " + params.get(1) + " or the Width " + params.get(3) + "?",
			"Invalid Parameters!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
			null, new String[]{"Y-Coordinate", "Width"}, "default");
		
		// lets the user know the range of acceptable Width values
		if (option == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
				"Then your Width must be from 0.0 to " + (enviroWidLimit - params.get(1)) + ". Thank you!",
				"Choose A Different Width", JOptionPane.WARNING_MESSAGE);
		
		// lets the user know the range of acceptable Y-Coordinate values
		} else {
			JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
				"Then your Y-Coordinate must be from 0.0 to " + (enviroWidLimit - params.get(3)) + ". Thank you!",
				"Choose A Different Y-Coordinate", JOptionPane.WARNING_MESSAGE);
		}
	}
}