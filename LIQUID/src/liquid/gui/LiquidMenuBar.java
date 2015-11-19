package liquid.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import liquid.core.LiquidApplication;

/**
 * Class creates the menu bar along the top of the
 * simulator. This is in relation to other programs,
 * where you can create a new program and view the
 * specifics of the program (the "About").
 * 
 * For our simulation, you can also load a log file
 * to "replay" it or undo/redo the last move performed.
 */
public class LiquidMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	
	// creates the individual sections of
	// the menu bar, including the file name
	JMenuItem New;
	JMenuItem load;
	JMenuItem exit;
	JMenuItem undo;
	JMenuItem redo;
	JMenuItem about;
	String log_filename;

	
	/**
	 * Constructor for the menu bar. Creates
	 * the menu across the top of the simulator.
	 */
	public LiquidMenuBar() {
		super();
		initComponents();
	}
	
	
	/**
	 * Initializes the various components of the
	 * menu bar, such as the font, the main menu
	 * tabs, and the individual sections of the tabs.
	 */
	private void initComponents() {
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		// creates the first main menu tab, which
		// features 'New', 'Load...', and 'Exit'
		JMenu m = new JMenu("File");
		
		// the 'New' feature creates a new
		// simulation by resetting the program
		New = new JMenuItem("New");
		New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().reset();
			}
        });
		m.add(New);
		
		// the 'Load' feature attempts to obtain a log
		// file from the computer's document system
		load = new JMenuItem("Load...");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					// sets the conditions when retrieving a log file, such as
					//  - Folder is set to be 'logs'
					//  - File must end in '.log'
					JFileChooser fileDialog = new JFileChooser("../logs");
					fileDialog.setAcceptAllFileFilterUsed(false);
					fileDialog.setApproveButtonText("Load");
					fileDialog.setDialogTitle("Load Log File");
					fileDialog.setFileFilter(new FileNameExtensionFilter("Log File", "log"));
					
					// opens up a new dialog box to select the file,
					// and proceeds only when it passes the '.log' ending
					int returnVal = fileDialog.showOpenDialog(LiquidApplication.getGUI().frame);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						
						// sets filename to be the chosen file's name, and calls
						// the Logger to obtain and set the necessary parameters
						LiquidApplication.getGUI().variables.filename = fileDialog.getSelectedFile().getPath();
						LiquidApplication.getGUI().send(LiquidApplication.getLogger(), LiquidGUI.REQUEST_LOADLOG);
						LiquidApplication.getGUI().frame.setTitle(LiquidApplication.getGUI().variables.filename +
								" - LIQUID : 2D Fluid Simulator   ");
					}
					LiquidApplication.getGUI().variables.savedStates.clear();
					LiquidApplication.getGUI().variables.saveState();
				} catch(Exception e) {
					LiquidApplication.getGUI().console.print_to_Console("Error Loading File.\n");
				}
			}
        });
		m.add(load);
		
		// the 'Exit' feature leaves the simulation program
		// by deleting the GUI and executing 'System.exit(0);'
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().frame.dispose();
				//System.exit(0);
			}
        });
		m.add(exit);
		add(m);
		
		
		// creates the second main menu tab,
		// which features 'Undo' and 'Redo'
		m = new JMenu("Edit");
		
		// the 'Undo' feature un-does the
		// last action made by the user
		undo = new JMenuItem("Undo");
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.undo();
				LiquidApplication.getGUI().enviroeditor.update();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		m.add(undo);
		
		// the 'Redo' feature re-does the
		// last action made by the user
		redo = new JMenuItem("Redo");
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.redo();
				LiquidApplication.getGUI().enviroeditor.update();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		m.add(redo);
		add(m);
		
		
		// creates the third main menu tab,
		// which features the 'About' sub-tab
		m = new JMenu("Help");
		
		// the 'About' feature provides screenshots to guide
		// the user in setting up and running a simulation
		about = new JMenuItem("About");
		m.add(about);
		add(m);
	}
	
	public void setEnabled(boolean enable){
		New.setEnabled(enable);
		load.setEnabled(enable);
		undo.setEnabled(enable);
		redo.setEnabled(enable);
	}
}