package liquid.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import liquid.core.GlobalVariables;
import liquid.core.LiquidApplication;

/**
 * MenuBarFile class is a branch of MenuBar. Here, the File tab is declared/defined. Users may create new
 * simulations to run, load previously-saved simulations to view, and perform various file-saving mechanisms.
 */
public class MenuBarFile {
	JMenuItem fileItem;
	
	/**
	 * Method creates the components of the File menu. A global <code>enum</code> provides the specific sub-tab
	 * names as well as the base for a <code>switch</code> statement to define each sub-tab's action functionality.
	 * @param menu - base menu to add sub-tabs to
	 */
	public void setUpFileMenu(JMenu menu) {
		for (GlobalVariables.FileMenuItems item : GlobalVariables.FileMenuItems.values()) {
			fileItem = new JMenuItem(item.toString());
			
			// adds action functionality for each sub-tab via a switch statement
			fileItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					switch (item) {
					
					// 'New' resets the program to begin a new simulation
					case New:
						if (LiquidApplication.getGUI().getVariousMessages().newSimulation() == JOptionPane.YES_OPTION)
							LiquidApplication.getGUI().reset();
						break;
					
					// 'Load' searches the computer's documentation system for a valid log file
					case Load:
						LiquidApplication.getGUI().send(LiquidApplication.getLogger(), GlobalVariables.Request.REQUEST_LOAD_FILE);
						if (LiquidApplication.getGUI().getFileVariables().getFilename().endsWith(".log")) {
							LiquidApplication.getGUI().getEditorPanel().addiParam.setEnabled(true);
							
							// informs the Logger to load the rest of the parameter values
							LiquidApplication.getGUI().send(LiquidApplication.getLogger(), GlobalVariables.Request.REQUEST_LOAD_LOG_PARAM);
							LiquidApplication.getGUI().getApplicationState().reset();}
						break;
					
					// 'Save' records the current parameters to the given log file, if one present
					case Save:
						// opens the computer documentation system when log file not present
						if (!LiquidApplication.getGUI().getFileVariables().getFilename().endsWith(".log"))
							LiquidApplication.getGUI().send(LiquidApplication.getLogger(), GlobalVariables.Request.REQUEST_SAVE_FILE);
						
						// performs saving only in the presence of changed parameters
						if (LiquidApplication.getGUI().getApplicationState().getSavedStates().size() > 1)
							LiquidApplication.getGUI().getParameterPanel().recordParametersToLogFile(true);
						break;
					
					// 'Save_As' creates a brand-new log file to save the current parameters to
					case Save_As:
						LiquidApplication.getGUI().send(LiquidApplication.getLogger(), GlobalVariables.Request.REQUEST_SAVE_FILE);
						LiquidApplication.getGUI().getParameterPanel().recordParametersToLogFile(true);
						break;
						
					// 'Exit' leaves the simulation by disposing the GUI, in turn also disposing the Engine/Logger
					case Exit:
						LiquidApplication.getGUI().getFrame().dispose();
						break;
					default:
						break;}
			}});
			menu.add(fileItem);
		}
	}
}