package liquid.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import liquid.core.GlobalVar;
import liquid.core.LiquidApplication;

/**
 * Class creates the menu bar along the top of the simulator. This is in relation to other programs,
 * where you can create a new program or view the specifics of the program (the "About" section).
 * For our program, you can also load a log file to "replay" it or undo/redo the last move performed.
 * @version 3.0
 */
public class LiquidMenuBar extends JMenuBar {

	// creates the individual sections of the menu bar, including the file name
	private static final long serialVersionUID = 1L;
	JMenuItem New;
	JMenuItem Load;
	JMenuItem Exit;
	JMenuItem Undo;
	JMenuItem Redo;
	JMenuItem About;
	
	/**
	 * Constructor for the menu bar. Creates the menu across the top of the simulator.
	 */
	public LiquidMenuBar() {
		super();
		initComponents();
	}
	
	/**
	 * Initializes the various components of the menu bar, as well as defining their functionalities.
	 */
	private void initComponents() {
		Font font = new Font("Verdana",Font.BOLD,12);
		setFont(font);
		
		// creates the first main menu tab, which features 'New', 'Load...', and 'Exit'
		JMenu m = new JMenu("File");
		
		// the 'New' feature creates a new simulation by resetting the program
		New = new JMenuItem("New");
		New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (LiquidApplication.getGUI().message.newSimulation() == JOptionPane.YES_OPTION) {
					LiquidApplication.getGUI().reset();
					LiquidApplication.getGUI().sim.repaint();
				}
			}
        });
		m.add(New);
		
		// the 'Load' feature attempts to obtain a log file from the computer's document system
		Load = new JMenuItem("Load...");
		Load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// calls the Logger to obtain a valid log file to replay on the simulator
				String filename = LiquidFileChooser.setUpFile("LOAD");
				
				// proceeds when a file name is actually present
				if (filename != null) {
					LiquidApplication.getGUI().variables.filename = filename;
					LiquidApplication.getGUI().enviroeditor.addiParam.setEnabled(true);
					LiquidApplication.getGUI().send(LiquidApplication.getLogger(), GlobalVar.Request.REQUEST_LOAD_LOG_PARAM);
					LiquidApplication.getGUI().frame.setTitle(LiquidApplication.getGUI().variables.onlyFileName+GlobalVar.title);
					LiquidApplication.getGUI().variables.savedStates.clear();
					LiquidApplication.getGUI().variables.saveState();
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
				LiquidApplication.getGUI().enviroeditor.setSelectedObject();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		m.add(Undo);
		
		// the 'Redo' feature re-does the last action made by the user
		Redo = new JMenuItem("Redo");
		Redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.redo();
				LiquidApplication.getGUI().enviroeditor.setSelectedObject();
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		m.add(Redo);
		add(m);
		
		// creates the third main menu tab, which features the 'About' sub-tab
		m = new JMenu("Help");
		
		// the 'About' feature provides information about the development of the program
		About = new JMenuItem("About");
		m.add(About);
		add(m);
	}
	
	/**
	 * Method enables/disables the appropriate main menu tabs.
	 * @param enable - to enable/disable components
	 */
	public void setEnabled(boolean enable) {
		New.setEnabled(enable);
		Load.setEnabled(enable);
		Undo.setEnabled(enable);
		Redo.setEnabled(enable);
	}
}