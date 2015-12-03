package liquid.logger;

import java.io.File;

import liquid.core.GlobalVar;
import liquid.core.Interfaceable;
import liquid.engine.LiquidEngine;
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
		initComponents();
	}
	
	/**
	 * Initializes the necessary parts to write or load a log file.
	 * Method also checks that the log and config files are located
	 * in the correct directory and contains the correct ending.
	 */
	private void initComponents(){
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
	public void send(Interfaceable i, GlobalVar.Request request) {
		// sends a String[] of parameters for the GUI to separate
		if (i instanceof LiquidGUI) {
			switch (request) {
			case REQUEST_SET_LOG_PARAM:
				String[] args = fileLoader.loadLogParam(currentFile);
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
	public void receive(Interfaceable i, GlobalVar.Request request, String[] args) {
		if (i instanceof LiquidGUI) {
			switch (request) {
			case LOAD_LOG_PARAM:
				currentFile = args[0];
				send(i, request.REQUEST_SET_LOG_PARAM);
				break;
			case INIT_WRITE_LOG:
				currentFile = args[0];
				fileWriter.initLogFile(currentFile);
				break;
			case WRITE_LOG_PARAM:
				fileWriter.writeLogParam(args);
				break;
			default:
			}
		}
		
		if (i instanceof LiquidEngine){
			switch (request) {
			case WRITE_LOG_DATA:
				fileWriter.writeLogData(args);
				break;
			default:
			}
		}
	}
	
	public void dispose() {
		fileWriter.dispose();
	}
}