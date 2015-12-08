package liquid.logger;

import java.io.File;

import liquid.core.GlobalVar;
import liquid.core.Interfaceable;
import liquid.engine.LiquidEngine;
import liquid.gui.LiquidGUI;

/**
 * LiquidLogger is the main class of the Logger component. Here, the methods to load or write a log file
 * are initialized. The config file is also read here and stored into a separate variable in the GUI.
 * 
 * This class interacts with both the GUI and Engine to pass/receive information for the log file.
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
	 * 
	 * Current Send Interactions:
	 *  - REQUEST_SET_CONFIG    - gathers information from the config file to send to the GUI to use
	 *  - REQUEST_SET_LOG_PARAM - obtains the necessary parameters to send to the GUI to set the simulation up
	 */
	@SuppressWarnings("static-access")
	@Override
	public void send(Interfaceable i, GlobalVar.Request request) {
		// sends a String[] of parameters for the GUI to use
		if (i instanceof LiquidGUI) {
			switch (request) {
			case REQUEST_SET_CONFIG:
				args = fileLoader.loadConfigFile(configFile);
				i.receive(this, request.SET_CONFIG, args);
				break;
			case REQUEST_SET_LOG_PARAM:
				args = fileLoader.loadLogParam(currentFile);
				i.receive(this, request.SET_LOG_PARAM, args);
				break;
			default:}
		}
	}

	/**
	 * Method defines requested interactions from the GUI and Engine.
	 * 
	 * Current Receive Interactions:
	 *  - LOAD_CONFIG_FILE - 
	 *  - LOAD_LOG_PARAM   - sends a self-request to find file based on the given file name
	 *  - INIT_WRITE_LOG   - 
	 *  - WRITE_LOG_PARAM  - writes the log file beginning with the given file name
	 */
	@SuppressWarnings("static-access")
	@Override
	public void receive(Interfaceable i, GlobalVar.Request request, String[] args) {
		if (i instanceof LiquidGUI) {
			switch (request) {
			case LOAD_CONFIG_FILE:
				args[0] = configFile;
				send(i, request.REQUEST_SET_CONFIG);
				break;
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