package liquid.gui;

import javax.swing.JOptionPane;

import liquid.core.LiquidApplication;

/**
 * Class stores all of the JOptionPane messages for the other classes to use and display.
 */
public class VariousMessages {

	/**
	 * Method prompts the user whether or not to actually exit the simulator.
	 * @return - the yes or no option chosen
	 */
	public int exitSimulation() {
		return JOptionPane.showConfirmDialog(LiquidApplication.getGUI().frame,
				"Are you sure you want to exit the simulator?", "Exit Simulator?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	/**
	 * Method prompts the user whether or not to actually start a new simulation.
	 * @return - the yes or no option chosen
	 */
	public int newSimulation() {
		return JOptionPane.showConfirmDialog(LiquidApplication.getGUI().frame,
				"Are you sure you want to make a new simulation?", "Create New Simulation?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	/**
	 * Method shows the message dialog that the directory has been changed to be under AtlasSoft's 'logs' folder.
	 */
	public void changedDirectory() {
		JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame,
			"The directory of your log file has been changed to be under AtlasSoft's 'logs'" +
			"\nfolder to preserve uniformity. Sorry for any inconveniences!",
			"WARNING: Directory Change!!", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Method prompts the user whether or not to actually override the file selected.
	 * @param file the name of the file to be overridden
	 * @return - the yes or no option chosen
	 */
	public int fileOverride(String file) {
		return JOptionPane.showConfirmDialog(LiquidApplication.getGUI().frame,
				"Are you sure you want to overwrite the log file '"+ file + "'?", "Overwrite File?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
//	public void about(){
//		JOptionPane.showMessageDialog(LiquidApplication.getGUI().frame, 
//			"This product is released under the GNU General Purpose License v3.\n" +
//			"Project LIQUID was created using the jBox2D library, a project led by Daniel Murphy.");
//	}
}