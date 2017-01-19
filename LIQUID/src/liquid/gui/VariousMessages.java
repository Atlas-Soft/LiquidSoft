package liquid.gui;

import javax.swing.JOptionPane;

import liquid.core.LiquidApplication;

/**
 * VariousMessages class stores all <code>JOptionPane</code> messages that other classes may use/display.
 * Each option message returns an <code>Integer</code> value, representative of the user's selected choice.
 */
public class VariousMessages {
	
	/**
	 * Method prompts the user whether or not to start a new simulation.
	 * @return - yes or no option chosen
	 */
	public int newSimulation() {
		return JOptionPane.showConfirmDialog(LiquidApplication.getGUI().getFrame(),
				"Are you sure you want to start a new simulation?", "Create New Simulation?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	/**
	 * Method prompts the user whether or not to exit the simulator.
	 * @return - yes or no option chosen
	 */
	public int exitSimulation() {
		return JOptionPane.showConfirmDialog(LiquidApplication.getGUI().getFrame(),
				"Are you sure you want to exit the simulator?", "Exit Simulator?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	/**
	 * Method prompts the user the directory has been changed to be under AtlasSoft's 'logs' folder.
	 */
	public void changedFileDirectory() {
		JOptionPane.showMessageDialog(LiquidApplication.getGUI().getFrame(),
			"The directory of your log file has been changed to be under AtlasSoft's 'logs'\n" +
			"folder to preserve uniformity. Sorry for any inconveniences!",
			"WARNING: Directory Change!!", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Method prompts the user whether or not to override the file selected.
	 * @param file - filename to be overridden
	 * @return     - yes or no option chosen
	 */
	public int fileOverride(String file) {
		return JOptionPane.showConfirmDialog(LiquidApplication.getGUI().getFrame(),
				"Are you sure you want to overwrite the log file '"+file+"'?", "Overwrite Log File?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	/**
	 * Method prompts the user of Project LIQUID itself and where the Engine's library was borrowed from.
	 */
	public void about() {
		JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame, 
			"This product is released under the GNU General Purpose License v3.\n" +
			"Project LIQUID was created using the jBox2D library, a project led by Daniel Murphy.");
	}
}