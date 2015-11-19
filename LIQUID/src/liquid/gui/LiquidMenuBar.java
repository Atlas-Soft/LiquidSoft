package liquid.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import liquid.core.LiquidApplication;

/**
 * Class creates the menu bar along the top of the simulator. This is
 * in relation to other programs, where you can create a new program
 * or view the specifics of the program (the "About" section).
 * 
 * For our simulation, you can also load a log file
 * to "replay" it or undo/redo the last move performed.
 */
public class LiquidMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	
	// creates the individual sections of the menu bar, including the file name
	JMenuItem New;
	JMenuItem Load;
	JMenuItem Exit;
	JMenuItem Undo;
	JMenuItem Redo;
	JMenuItem About;
	String log_filename;
	
	/**
	 * Constructor for the menu bar. Creates the menu across the top of the simulator.
	 */
	public LiquidMenuBar() {
		super();
		initComponents();
	}
	
	/**
	 * Initializes the various components of the menu bar, such as the
	 * main menu tabs and sub-tabs, as well as their functionalities.
	 */
	private void initComponents() {
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		// creates the first main menu tab, which features 'New', 'Load...', and 'Exit'
		JMenu m = new JMenu("File");
		
		// the 'New' feature creates a new simulation by resetting the program
		New = new JMenuItem("New");
		New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				int newSim = JOptionPane.showConfirmDialog(LiquidApplication.getGUI().frame,
						"Are you sure you want to make a new simulation?", "Create New Simulation?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (newSim == JOptionPane.YES_OPTION) {
					LiquidApplication.getGUI().reset();
				}
			}
        });
		m.add(New);
		
		// the 'Load' feature attempts to obtain a log file from the computer's document system
		Load = new JMenuItem("Load...");
		Load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					// sets the conditions when retrieving a log file:
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
		m.add(Load);
		
		// the 'Exit' feature leaves the simulation program by disposing the GUI and exiting altogether
		Exit = new JMenuItem("Exit");
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			    LiquidApplication.getGUI().frame.dispose();
			}
        });
		m.add(Exit);
		add(m);
		
		// creates the second main menu tab, which features 'Undo' and 'Redo'
		m = new JMenu("Edit");
		
		// the 'Undo' feature un-does the last action made by the user
		Undo = new JMenuItem("Undo");
		Undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.undo();
				LiquidApplication.getGUI().enviroeditor.update();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		m.add(Undo);
		
		// the 'Redo' feature re-does the last action made by the user
		Redo = new JMenuItem("Redo");
		Redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.redo();
				LiquidApplication.getGUI().enviroeditor.update();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		m.add(Redo);
		add(m);
		
		// creates the third main menu tab, which features the 'About' sub-tab
		m = new JMenu("Help");
		
		// the 'About' feature provides screenshots to guide the user in setting up and running a simulation
		About = new JMenuItem("About");
		m.add(About);
		add(m);
	}
	
	/**
	 * Method enables/disables the appropriate main menu tabs.
	 */
	public void setEnabled(boolean enable){
		New.setEnabled(enable);
		Load.setEnabled(enable);
		Undo.setEnabled(enable);
		Redo.setEnabled(enable);
	}
}