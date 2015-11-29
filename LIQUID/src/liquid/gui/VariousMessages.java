package liquid.gui;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import liquid.core.LiquidApplication;

/**
 * Class stores all of the JOptionPane messages for the other classes to use and display.
 */
public class VariousMessages {

	/**
	 * Method checks when the object (obstacle or source) goes
	 * beyond the limit in the X direction, that is, the upper limit.
	 * 
	 * @param enviroLenLimit - the length's limit
	 * @param params   - the ArrayList<Float> of parameters
	 */
	public void xExceedsUpperLimit(float enviroLenLimit, ArrayList<Float> params) {
		// asks the user if the X-Coordinate or Length/Force parameter is preferred
		int option = JOptionPane.showOptionDialog(LiquidApplication.getGUI().frame,
			"Do you want the X-Coordinate " + params.get(0) + " or the Length/Force " + params.get(2) + "?",
			"Invalid Parameters!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
			null, new String[]{"X-Coordinate", "Length/Force"}, "default");
		
		// lets the user know the range of acceptable Length/Force values
		if (option == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
				"Then your Length/Force must be from 0.0 - " + (enviroLenLimit - params.get(0)) + ". Thank you!",
				"Choose A Different Length/Force", JOptionPane.WARNING_MESSAGE);
		
		// lets the user know the range of acceptable X-Coordinate values
		} else {
			JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
				"Then your X-Coordinate must be from 0.0 - " + (enviroLenLimit - params.get(2)) + ". Thank you!",
				"Choose A Different X-Coordinate", JOptionPane.WARNING_MESSAGE);
		}
	}
}