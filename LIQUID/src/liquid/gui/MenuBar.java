package liquid.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import liquid.core.GlobalVariables;

/**
 * MenuBar class creates the menu bar above the SimulationPanel/ParameterPanel parts of the simulator. This reflects standard functionalities
 * of creating new simulations, loading old simulations, exiting the simulation, or viewing the specifics of the program (the "About" section).
 * <p>Project LIQUID recently implemented the undo/redo features, allowing quick parameter adjustments while the simulation is not running.</p>
 */
public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	// initializes each individual menu & the class for said menu to call
	JMenu file,edit,help;
	MenuBarFile menuBarFile = new MenuBarFile();
	MenuBarEdit menuBarEdit = new MenuBarEdit();
	MenuBarHelp menuBarHelp = new MenuBarHelp();
	
	/**
	 * Constructor creates the menu bar across the top of the simulator.
	 */
	public MenuBar() {
		super();
		initComponents();
	}
	
	/**
	 * Method initializes the various tab/sub-tab components of the menu bar.
	 */
	private void initComponents() {
		setFont(GlobalVariables.font);
		
		// 1st main menu tab features 'New', 'Load', 'Save', 'Save_As', 'Exit'
		file = new JMenu("File");
		menuBarFile.setUpFileMenu(file);
		add(file);
		
		// 2nd main menu tab features 'Undo', 'Redo'
		edit = new JMenu("Edit");
		menuBarEdit.setUpEditMenu(edit);
		add(edit);
		
		// 3rd main menu tab features 'About'
		help = new JMenu("Help");
		menuBarHelp.setUpHelpMenu(help);
		add(help);
	}
	
	/**
	 * Method enables/disables the menu tabs.
	 */
	public void setEnabled(boolean enable) {
		file.setEnabled(enable);
		edit.setEnabled(enable);
		help.setEnabled(enable);
	}
}