package liquid.gui;

import liquid.core.Interfaceable;
import liquid.logger.LiquidLogger;

/**
 * LiquidGUI is the central class of the GUI.
 * This class interfaces between the Logger and Engine.
 * This class initializes all the components of the GUI.
 * 
 * All variables in this package are friendly.
 * 
 *@version	1.0
 */
public class LiquidGUI implements Interfaceable{
	
	public static final int REQUEST_LOADLOG = 0;
	public static final int REQUEST_WRITELOG = 1;
	public static final int SETLOGPARAM = 0;
	
	LiquidGUIVariables variables;
	LiquidFrame frame;
	LiquidMenuBar menubar;
	ParameterPanel param;
	EnvironmentEditorPanel enviroeditor;
	SimulationPanel sim;
	ConsolePanel console;
	
	/**
	 * Constructor initializes components of GUI
	 */
	public LiquidGUI(){
		initComponents();
	}
	
	/**
	 * Method defines requested interactions to the Logger and Engine.
	 * 
	 * Current Send Interactions:
	 * Request_Loadlog - sends the logger the filename of the log file needed to be loaded.
	 * 
	 */
	@Override
	public void send(Interfaceable i, int arg0) {
		String[] args;
		if(i instanceof LiquidLogger){
			switch(arg0){
			case REQUEST_LOADLOG:
				args = new String[variables.storeArray().length];
				//args[0] = variables.filename;
				args = variables.storeArray();
				i.receive(this, LiquidLogger.LOADLOG, args);
			break;
			case REQUEST_WRITELOG:
				args = new String[3];
				args[0] = variables.filename;
				args[1] = "" + variables.enviroLength;
				args[2] = "" + variables.enviroWidth;
				i.receive(this, LiquidLogger.WRITELOG, args);
			break;
			}
		}
	}
	
	/**
	 * Method defines requested interactions from the Logger and Engine.
	 * 
	 * Current Receive Interaction:
	 * SetLogParam - Receives information from logger used to set parameters and environment.
	 */
	@Override
	public void receive(Interfaceable i, int arg0, String[] args) {
		if(i instanceof LiquidLogger){
			switch(arg0){
			case SETLOGPARAM:
				String[] tokens = args[0].split(" ");
				variables.enviroLength = Integer.parseInt(tokens[0]);
				variables.enviroWidth = Integer.parseInt(tokens[1]);
				sim.repaint();
				console.print_to_Console("Log File Loaded.\n");
			break;
			}
		}
	}
	
	/**
	 * Method defines the components in the GUI.
	 */
	private void initComponents(){
		variables = new LiquidGUIVariables();
		frame = new LiquidFrame();
		frame.setJMenuBar(menubar = new LiquidMenuBar());
		frame.add(console = new ConsolePanel());
		frame.add(param = new ParameterPanel());
		param.add(enviroeditor = new EnvironmentEditorPanel());
		frame.add(sim = new SimulationPanel());	
		frame.setVisible(true);
	}
	
}
