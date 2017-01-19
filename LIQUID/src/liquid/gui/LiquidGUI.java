package liquid.gui;

import liquid.core.GlobalVariables;
import liquid.core.Interfaceable;
import liquid.core.LiquidApplication;
import liquid.engine.LiquidEngine;
import liquid.logger.LiquidLogger;

/**
 * LiquidGUI is the central class of the GUI, where all components of the GUI are initialized
 * here and available for other components to access them when necessary.
 * 
 * <p>This class also interfaces between the Logger and Engine to pass various parameter information.</p>
 */
public class LiquidGUI implements Interfaceable {
	// initializes all classes of the GUI
	VariousMessages  messages;
	FileVariables    fileVars;
	ApplicationState appState;
	Frame            frame;
	MenuBar          menuBar;
	SimulationPanel  simPanel;
	ParameterPanel   paramPanel;
	EditorPanel      editorPanel;
	ConsolePanel     consolePanel;
	
	/**
	 * Constructor initializes the components of the GUI.
	 */
	public LiquidGUI() {
		initComponents();
	}
	
	/**
	 * Method defines the components of the GUI.
	 */
	private void initComponents() {
		messages = new VariousMessages();
		fileVars = new FileVariables();
		appState = new ApplicationState();
		frame = new Frame();
		frame.setJMenuBar(menuBar = new MenuBar());
		frame.add(simPanel = new SimulationPanel());
		frame.add(paramPanel = new ParameterPanel());
		paramPanel.add(editorPanel = new EditorPanel());
		frame.add(consolePanel = new ConsolePanel());
		
		// loads the liquid type information into the system
		send(LiquidApplication.getLogger(), GlobalVariables.Request.REQUEST_LOAD_CONFIG_FILE);
		frame.setVisible(true);
	}
	
	/**
	 * Method defines requested interactions to the Logger and Engine.
	 * <p>Current Send Interactions:
	 * <br> - REQUEST_LOAD_CONFIG_FILE - sends the Logger a request to load config file information
	 * <br> - REQUEST_LOAD_LOG_PARAM   - sends the Logger a request to load parameters from the log file
	 * <br> - REQUEST_INIT_WRITE_LOG   - sends the Logger a request to initialize writing a log file
	 * <br> - REQUEST_WRITE_LOG_PARAM  - sends the Logger the variables needed to write a log file
	 * <br>
	 * <br> - REQUEST_RUN_SIM   - sends the Engine the variables needed to begin simulation
	 * <br> - REQUEST_PAUSE_SIM - sends the Engine a request to pause the simulation
	 * <br> - REQUEST_STEP_SIM  - sends the Engine a request to step through the simulation by a frame
	 * <br> - REQUEST_END_SIM   - sends the Engine a request to end a simulation
	 */
	@Override
	public void send(Interfaceable i, GlobalVariables.Request request) {
		String[] args = new String[1];
		
		// sends requests to the Logger to begin to load or write a log file
		if (i instanceof LiquidLogger) {
			switch (request) {
			case REQUEST_LOAD_CONFIG_FILE:
				i.receive(this, GlobalVariables.Request.LOAD_CONFIG_FILE,args);
				break;
			case REQUEST_LOAD_LOG_PARAM:
				args[0] = fileVars.getFilename();
				i.receive(this, GlobalVariables.Request.LOAD_LOG_PARAM,args);
				break;
			case REQUEST_INIT_WRITE_LOG:
				args[0] = fileVars.getFilename();
				i.receive(this, GlobalVariables.Request.INIT_WRITE_LOG,args);
				break;
			case REQUEST_WRITE_LOG_PARAM:
				args = fileVars.writeArray();
				send(LiquidApplication.getLogger(), GlobalVariables.Request.REQUEST_INIT_WRITE_LOG);
				i.receive(this, GlobalVariables.Request.WRITE_LOG_PARAM,args);
				break;
			case REQUEST_SAVE_FILE:
				i.receive(this,GlobalVariables.Request.SAVE_FILE,args);
				break;
			case REQUEST_LOAD_FILE:
				i.receive(this,GlobalVariables.Request.LOAD_FILE,args);
				break;
			default:}
		}
		
		// sends requests to the Engine to run, pause, or end a simulation
		if (i instanceof LiquidEngine) {
			switch (request) {
			case REQUEST_RUN_SIM:
				paramPanel.setParamToRun();
				args = fileVars.writeArray();
				i.receive(this, GlobalVariables.Request.RUN_SIM, args);
				break;
			case REQUEST_PAUSE_SIM:
				i.receive(this, GlobalVariables.Request.PAUSE_SIM, args);
				break;
			case REQUEST_STEP_SIM:
				paramPanel.setParamToRun();
				args = fileVars.writeArray();
				i.receive(this, GlobalVariables.Request.STEP_SIM, args);
				break;
			case REQUEST_END_SIM:
				i.receive(this, GlobalVariables.Request.END_SIM, args);
				break;
			default:}
		}
	}
	
	/**
	 * Method defines requested interactions from the Logger and Engine.
	 * <p>Current Receive Interactions:
	 * <br> - SET_CONFIG    - receives information from Logger to set up liquid type information
	 * <br> - SET_LOG_PARAM - receives information from Logger to set up parameters
	 * <br>
	 * <br> - DISPLAY_SIM   - receives particle information from Engine to display
	 * <br> - PRINT_SIM     - receives information from Engine to print onto the console
	 * <br> - SIM_HAS_ENDED - receives information from Engine that simulation has finished
	 */
	@Override
	public void receive(Interfaceable i, GlobalVariables.Request request, String[] args) {
		
		// receives information from the Logger to set parameters of the simulator
		if (i instanceof LiquidLogger) {
			switch (request) {
			case SET_CONFIG:
				for (int num = 0; num < args.length; num++)
					paramPanel.liquidInfo.add(args[num]);
				paramPanel.initComponents();
				break;
			case SET_LOG_PARAM:
				fileVars.readArray(args);
				paramPanel.logUpdate();
				editorPanel.setSelectedObjectPanel();
				simPanel.repaint();
				consolePanel.printToConsole("["+LiquidApplication.getGUI().fileVars.getFilenameOnlyAndTitle(false)+" File Loaded.]\n");
				break;
			case SET_SAVE_FILE:
				fileVars.setFilename(args[0]);
				break;
			case SET_LOAD_FILE:
				fileVars.setFilename(args[0]);
				break;
			default:}
		}
		
		// receives information from the Engine to display particles or print information onto the console
		if (i instanceof LiquidEngine) {
			switch (request) {
			case DISPLAY_SIM:
				getFileVariables().setParticles(args);
				simPanel.repaint();
				break;
			case PRINT_SIM:
				consolePanel.printToConsole(args[0]);
				break;
			case SIM_HAS_ENDED:
				if (fileVars.getSimulating()) {
					paramPanel.paramPanelButtons.run.setEnabled(false);
					paramPanel.paramPanelButtons.pause.setEnabled(false);
					paramPanel.paramPanelButtons.step.setEnabled(false);
					consolePanel.printToConsole("[Simulation Finished.]\n");}
				break;
			default:}
		}
	}
	
	public void setApplication() {
		fileVars.readArray(appState.savedStates.peek());
		paramPanel.setUndoParam();
		editorPanel.setSelectedObjectPanel();
		simPanel.repaint();
	}
	
	/**
	 * Method sends a request to the individual GUI components to disable parameters.
	 * @param enable - to enable/disable components
	 */
	public void setEnable(boolean enable) {
		menuBar.setEnabled(enable);
		editorPanel.setEnabled(enable);
		paramPanel.setEnabled(enable);
	}
	
	/**
	 * Method resets GUI to initial conditions and parameters.
	 */
	public void reset() {
		fileVars.reset();
		appState.reset();
		editorPanel.reset();
		paramPanel.reset();
		frame.setTitle(LiquidApplication.getGUI().fileVars.getFilenameOnlyAndTitle(true));
		simPanel.repaint();
	}
	
	/**
	 * Represents a placeholder method for when necessary to dispose something.
	 */
	public void dispose() {}
	
	/**
	 * Getter method returns an instance of the VariousMessages class.
	 * @return - a VariousMessages class instance
	 */
	public VariousMessages getVariousMessages() {return messages;}
	
	/**
	 * Getter method returns an instance of the FileVariables class.
	 * @return - a FileVariables class instance
	 */
	public FileVariables getFileVariables() {return fileVars;}
	
	/**
	 * Getter method returns an instance of the ApplicationState class.
	 * @return - an ApplicationState class instance
	 */
	public ApplicationState getApplicationState() {return appState;}
	
	/**
	 * Getter method returns an instance of the Frame class.
	 * @return - a Frame class instance
	 */
	public Frame getFrame() {return frame;}
	
	/**
	 * Getter method returns an instance of the SimulationPanel class.
	 * @return - a SimulationPanel class instance
	 */
	public SimulationPanel getSimulationPanel() {return simPanel;}
	
	/**
	 * Getter method returns an instance of the ParameterPanel class.
	 * @return - a ParameterPanel class instance
	 */
	public ParameterPanel getParameterPanel() {return paramPanel;}
	
	/**
	 * Getter method returns an instance of the EditorPanel class.
	 * @return - a EditorPanel class instance
	 */
	public EditorPanel getEditorPanel() {return editorPanel;}
	
	/**
	 * Getter method returns an instance of the ConsolePanel class.
	 * @return - a ConsolePanel class instance
	 */
	public ConsolePanel getConsolePanel() {return consolePanel;}
}