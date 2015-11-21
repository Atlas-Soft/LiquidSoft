package liquid.logger;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import liquid.core.Interfaceable;
import liquid.gui.LiquidGUI;

/**
 * LiquidLogger is the main class of the Logger component. Here,
 * the methods to write or load a log file are initialized.
 * 
 * This class interacts with both the GUI to write/load a log file.
 */
public class LiquidLogger implements Interfaceable {
	
	// initializes the variables to write or load a log file,
	// as well as the file name of the current simulation
	private LiquidFileLoader fileLoader;
	private LiquidFileWriter fileWriter;
	private String currentFile;
	
	/**
	 * Constructor initializes the Logger component of the simulation.
	 */
	public LiquidLogger() {
		init();
	}
	
	/**
	 * Initializes the necessary parts to write or load a log file.
	 * Method also checks that the log and config files are located
	 * in the correct directory and contains the correct ending.
	 */
	private void init(){
		fileLoader = new LiquidFileLoader();
		fileWriter = new LiquidFileWriter();
		File f = new File("../logs");
		if (!(f.exists() && f.isDirectory())) f.mkdirs();
			
		f = new File("../configs");
		if (f.exists() && f.isDirectory()) {
			
		}
		else{
			f.mkdirs();
		}
	}
	
	/**
	 * Method defines requested interactions to the GUI.
	 * 
	 * Current Send Interactions:
	 *  - REQUEST_SET_LOG_PARAM - obtains the necessary parameters to send to the GUI to set the simulation up
	 */
	@SuppressWarnings("static-access")
	@Override
	public void send(Interfaceable i, Request request) {
		// sends a String[] of parameters for the GUI to separate
		if (i instanceof LiquidGUI) {
			switch (request) {
			case REQUEST_SET_LOG_PARAM:
				String[] args = fileLoader.loadLogFile(currentFile);
				i.receive(this, request.SET_LOG_PARAM, args);
				break;
			default:}
		}
	}

	/**
	 * Method defines requested interactions from the GUI.
	 * 
	 * Current Receive Interactions:
	 *  - LOAD_LOG - sends a self-request to find file based on the given file name
	 *  - WRITE_LOG - writes the log file beginning with the given file name
	 */
	@SuppressWarnings("static-access")
	@Override
	public void receive(Interfaceable i, Request request, String[] args) {
		// loads the parameters by sending a self-request or writes the log file
		if (i instanceof LiquidGUI) {
			switch (request) {
			case LOAD_LOG:
				currentFile = args[0];
				send(i, request.REQUEST_SET_LOG_PARAM);
				break;
			case WRITE_LOG:
				currentFile = args[0];
				fileWriter.writetoLogFile(currentFile, args);
				break;
			default:}
		}
	}
	
	/**
	 * Sets up the file name by first making sure the 'logs' directory is
	 * present and filtering all files expect ones that end in '.log'.
	 * Method can be used both to set up a new file or load an existing one.
	 * 
	 * @param set   - determines to save or load a file 
	 * @param frame - the frame with which JFileChooser will appear in
	 * @return      - the String name of the file
	 */
	public String setUpFile(String set, Component frame) {
		try {
			// sets the conditions for a log file:
			//  - Folder is set to be 'logs'
			//  - File must end in '.log'
			JFileChooser fileDialog = new JFileChooser("../logs");
			fileDialog.setAcceptAllFileFilterUsed(false);
			fileDialog.setFileFilter(new FileNameExtensionFilter("Log File", "log"));
			
			switch (set) {
			case "LOAD": // case for when a log file needs to be loaded into the simulator
				fileDialog.setApproveButtonText("Load");
				fileDialog.setDialogTitle("Load Log File");
				return fileLoader.setUpFileToLoad(fileDialog, frame);
			
			case "SAVE": // case for when a new log file needs to be created for the simulator
				fileDialog.setApproveButtonText("Save");
				fileDialog.setDialogTitle("Create Log File");
				return fileWriter.setUpFileToSave(fileDialog, frame);
			default:}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void dispose() {
		
	}
}