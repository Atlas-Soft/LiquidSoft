package liquid.logger;

import java.io.File;

import liquid.core.GlobalVariables;
import liquid.core.Interfaceable;
import liquid.engine.LiquidEngine;
import liquid.gui.LiquidGUI;

/**
 * LiquidLogger is the main class of the Logger component. Here, the methods to load or write a log file
 * are initialized. The config file is also read here and stored into a separate variable in the GUI.
 * 
 * <p>This class interacts with both the GUI and Engine to pass/receive information for the log file.</p>
 */
public class LiquidLogger implements Interfaceable {
	
	// initializes the variables to load or write a log file, as well as the file name of the current simulation
	private LoadTextData fileLoader;
	private WriteDataToText fileWriter;
	private FileSelection fileChooser;
	private String currentFile;
	private String configFile;
	String[] args;
	
	/**
	 * Constructor initializes the Logger component of the simulation.
	 */
	public LiquidLogger() {
		initComponents();
	}
	
	/**
	 * Method initializes the necessary parts to load or write a log file. A check is also implemented to ensure that
	 * the log and config files are located in the correct directory and contains the correct ending and/or name.
	 */
	private void initComponents() {
		fileLoader = new LoadTextData();
		fileWriter = new WriteDataToText();
		fileChooser = new FileSelection();
		
		// checks for a folder name called 'logs' in the AtlasSoft folder
		File f = new File("../logs");
		if (!(f.exists() && f.isDirectory())) f.mkdirs();
		
		// checks for a folder name called 'configs' in the AtlasSoft folder
		f = new File("../configs");
		if (f.exists() && f.isDirectory()) {
			File[] list = f.listFiles();
			
			// searches through the list of files in the 'configs' folder to find the 'LiquidType.txt' document
			if (list != null) {
				for (File file : list) {
					if (file.getName().equals("LiquidType.txt")) {
						configFile = file.getAbsolutePath();}}
			}
		} else {
			f.mkdirs();
		}
	}
	
	/**
	 * Method defines requested interactions to the GUI.
	 * <p>Current Send Interactions:
	 * <br> - REQUEST_SET_CONFIG    - sends the GUI a request to store the config file information
	 * <br> - REQUEST_SET_LOG_PARAM - sends the GUI a request to store the log file parameter information
	 */
	@Override
	public void send(Interfaceable i, GlobalVariables.Request request) {
		// sends a String[] of parameters for the GUI to use
		if (i instanceof LiquidGUI) {
			switch (request) {
			case REQUEST_SET_CONFIG:
				args = fileLoader.loadConfigFile(configFile);
				i.receive(this, GlobalVariables.Request.SET_CONFIG, args);
				break;
			case REQUEST_SET_LOG_PARAM:
				args = fileLoader.loadLogParam(currentFile);
				i.receive(this, GlobalVariables.Request.SET_LOG_PARAM, args);
				break;
			case REQUEST_SET_SAVE_FILE:
				args[0] = fileChooser.setUpFile(GlobalVariables.FileAction.Save);
				i.receive(this,GlobalVariables.Request.SET_SAVE_FILE,args);
				break;
			case REQUEST_SET_LOAD_FILE:
				args[0] = fileChooser.setUpFile(GlobalVariables.FileAction.Load);
				i.receive(this,GlobalVariables.Request.SET_LOAD_FILE,args);
				break;
			default:}
		}
	}

	/**
	 * Method defines requested interactions from the GUI and Engine.
	 * <p>Current Receive Interactions:
	 * <br> - LOAD_CONFIG_FILE - receives request to send a self-request to load config data based on file name
	 * <br> - LOAD_LOG_PARAM   - receives request to send a self-request to load parameters based on file name
	 * <br> - INIT_WRITE_LOG   - receives request to initialize process of writing a log file
	 * <br> - WRITE_LOG_PARAM  - receives request to write to log file with the given information
	 * <br>
	 * <br> - WRITE_LOG_DATA - receives information from Engine to write to the log file
	 */
	@Override
	public void receive(Interfaceable i, GlobalVariables.Request request, String[] args) {
		// receives requests to either load or write to a file
		if (i instanceof LiquidGUI) {
			switch (request) {
			case LOAD_CONFIG_FILE:
				send(i, GlobalVariables.Request.REQUEST_SET_CONFIG);
				break;
			case LOAD_LOG_PARAM:
				currentFile = args[0];
				send(i, GlobalVariables.Request.REQUEST_SET_LOG_PARAM);
				break;
			case INIT_WRITE_LOG:
				currentFile = args[0];
				fileWriter.initLogFile(currentFile);
				break;
			case WRITE_LOG_PARAM:
				fileWriter.writeLogParam(args);
				break;
			case SAVE_FILE:
				send(i,GlobalVariables.Request.REQUEST_SET_SAVE_FILE);
				break;
			case LOAD_FILE:
				send(i,GlobalVariables.Request.REQUEST_SET_LOAD_FILE);
				break;
			default:}
		}
		
		// receives data from Engine to write into the log file
		if (i instanceof LiquidEngine){
			switch (request) {
			case WRITE_LOG_DATA:
				fileWriter.writeLogData(args);
				break;
			default:}
		}
	}
	
	/**
	 * Method disposes the tools needed to write a log file so that it does not keep continually running.
	 */
	public void dispose() {
		fileWriter.dispose();
	}
}