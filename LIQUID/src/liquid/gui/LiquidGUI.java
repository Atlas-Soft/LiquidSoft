package liquid.gui;

import liquid.core.Interfaceable;
import liquid.engine.LiquidEngine;
import liquid.logger.LiquidLogger;

/**
 * LiquidGUI is the central class of the GUI, where all
 * components of the GUI are initialized here and available
 * for other components to access them when necessary.
 * 
 * This class also interfaces between the Logger and Engine.
 * 
 * @version	2.0
 */
public class LiquidGUI implements Interfaceable {
	
	// initializes all classes of the GUI
	LiquidGuiVariables variables;
	LiquidFrame frame;
	LiquidMenuBar menubar;
	SimulationPanel sim;
	ParameterPanel param;
	EnvironmentEditorPanel enviroeditor;
	ConsolePanel console;
	
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
		variables = new LiquidGuiVariables();
		frame = new LiquidFrame();
		frame.setJMenuBar(menubar = new LiquidMenuBar());
		frame.add(sim = new SimulationPanel());
		frame.add(param = new ParameterPanel());
		param.add(enviroeditor = new EnvironmentEditorPanel());
		frame.add(console = new ConsolePanel());
		frame.setVisible(true);
	}
	
	/**
	 * Method defines requested interactions to the Logger and Engine.
	 * 
	 * Current Send Interactions:
	 *  - REQUEST_LOAD_LOG - sends the Logger the file name of the log file needed to be loaded
	 *  - REQUEST_WRITE_LOG - sends the Logger the variables needed to write a log file
	 *  
	 *  - REQUEST_RUN_SIM - sends the Engine the variables needed to begin simulation
	 *  - REQUEST_PAUSE_SIM - sends the Engine a notice to pause the simulation
	 *  - REQUEST_END_SIM - sends the Engine a notice to end a simulation
	 */
	@SuppressWarnings("static-access")
	@Override
	public void send(Interfaceable i, Request request) {
		String[] args;
		
		// sends requests to the Logger to begin to load or write a log file
		if(i instanceof LiquidLogger) {
			switch (request) {
			case REQUEST_LOAD_LOG:
				args = new String[1];
				args[0] = variables.filename;
				i.receive(this, request.LOAD_LOG, args);
				break;
			case REQUEST_WRITE_LOG:
				args = variables.writeArray();
				i.receive(this, request.WRITE_LOG, args);
				break;
			default:}
		}
		
		// sends requests to the Engine to run, pause, or end a simulation
		if (i instanceof LiquidEngine) {
			switch (request) {
			case REQUEST_RUN_SIM:
				args = variables.writeArray();
				i.receive(this, request.RUN_SIM, args);
				break;
			case REQUEST_PAUSE_SIM:
				args = new String[0];
				i.receive(this, request.PAUSE_SIM, args);
				break;
			case REQUEST_END_SIM:
				args = new String[0];
				i.receive(this, request.END_SIM, args);
				break;
			default:}
		}
	}
	
	/**
	 * Method defines requested interactions from the Logger and Engine.
	 * 
	 * Current Receive Interaction:
	 *  - SET_LOG_PARAM - receives information from Logger to set parameters in the current environment
	 *  
	 *  - DISPLAY_SIM - receives particle information from the Engine to display
	 *  - PRINT_SIM - receives information from the Engine to print onto the console
	 */
	@Override
	public void receive(Interfaceable i, Request request, String[] args) {
		
		// receives information from the Logger to set parameters of the simulator
		if (i instanceof LiquidLogger) {
			switch (request) {
			case SET_LOG_PARAM:
				variables.readArray(args);
				param.logUpdate();
				enviroeditor.update();
				sim.repaint();
				console.print_to_Console("Log File Loaded.\n");
				break;
			default:}
		}
		
		// receives information from the Engine to display particles or print information onto the console
		if (i instanceof LiquidEngine) {
			switch (request) {
			case DISPLAY_SIM:
				variables.particles = args;
				sim.repaint();
				break;
			case PRINT_SIM:
				console.print_to_Console(args[0]);
				break;
			default:}
		}
	}
	
	/**
	 * Method sends a request to the individual GUI components to disable parameters.
	 * 
	 * @param enable - to enable/disable components
	 */
	public void setEnable(boolean enable) {
		menubar.setEnabled(enable);
		enviroeditor.setEnabled(enable);
		param.setEnabled(enable);
	}
	
	/**
	 * Method resets GUI to initial conditions and parameters.
	 */
	public void reset() {
		frame.setTitle("Untitled - LIQUID : 2D Fluid Simulator");
		variables.reset();
		enviroeditor.reset();
		param.reset();
	}
	
	public void dispose() {
		
	}	
}