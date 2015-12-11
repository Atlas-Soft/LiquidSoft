package liquid.logger;

import java.io.File;

import liquid.core.GlobalVar;
import liquid.core.Interfaceable;
import liquid.engine.LiquidEngine;
import liquid.gui.LiquidGUI;

/**
 * LiquidLogger is the main class of the Logger component. Here, the methods to load or write a log file
 * are initialized. The config file is also read here and stored into a separate variable in the GUI.
 * <p>This class interacts with both the GUI and Engine to pass/receive information for the log file.</p>
 * @version 3.0
 */
public class LiquidLogger implements Interfaceable {
	
	// initializes the variables to load or write a log file, as well as the file name of the current simulation
	private LiquidFileLoader fileLoader;
	private LiquidFileWriter fileWriter;
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
		fileLoader = new LiquidFileLoader();
		fileWriter = new LiquidFileWriter();
		
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
	 * <p>Current Send Interactions:</p>
	 * <p> - REQUEST_SET_CONFIG    - sends the GUI a request to store the config file information</p>
	 * <p> - REQUEST_SET_LOG_PARAM - sends the GUI a request to store the log file parameter information</p>
	 */
	@Override
	public void send(Interfaceable i, GlobalVar.Request request) {
		// sends a String[] of parameters for the GUI to use
		if (i instanceof LiquidGUI) {
			switch (request) {
			case REQUEST_SET_CONFIG:
				args = fileLoader.loadConfigFile(configFile);
				i.receive(this, GlobalVar.Request.SET_CONFIG, args);
				break;
			case REQUEST_SET_LOG_PARAM:
				args = fileLoader.loadLogParam(currentFile);
				i.receive(this, GlobalVar.Request.SET_LOG_PARAM, args);
				break;
			default:}
		}
	}

	/**
	 * Method defines requested interactions from the GUI and Engine.
	 * <p>Current Receive Interactions:</p>
	 * <p> - LOAD_CONFIG_FILE - receives request to send a self-request to load config data based on file name</p>
	 * <p> - LOAD_LOG_PARAM   - receives request to send a self-request to load parameters based on file name</p>
	 * <p> - INIT_WRITE_LOG   - receives request to initialize process of writing a log file</p>
	 * <p> - WRITE_LOG_PARAM  - receives request to write to log file with the given information</p>
	 * 
	 * <p> - WRITE_LOG_DATA - receives information from Engine to write to the log file</p>
	 */
	@Override
	public void receive(Interfaceable i, GlobalVar.Request request, String[] args) {
		// receives requests to either load or write to a file
		if (i instanceof LiquidGUI) {
			switch (request) {
			case LOAD_CONFIG_FILE:
				send(i, GlobalVar.Request.REQUEST_SET_CONFIG);
				break;
			case LOAD_LOG_PARAM:
				currentFile = args[0];
				send(i, GlobalVar.Request.REQUEST_SET_LOG_PARAM);
				break;
			case INIT_WRITE_LOG:
				currentFile = args[0];
				fileWriter.initLogFile(currentFile);
				break;
			case WRITE_LOG_PARAM:
				fileWriter.writeLogParam(args);
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