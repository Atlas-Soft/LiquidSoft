package liquid.gui;

import liquid.core.Interfaceable;
import liquid.engine.LiquidEngine;
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
public class LiquidGUI implements Interfaceable {
	
	public static final int REQUEST_LOADLOG = 0;
	public static final int REQUEST_WRITELOG = 1;
	public static final int REQUEST_RUNSIM = 2;
	public static final int REQUEST_PAUSESIM = 5;
	public static final int REQUEST_ENDSIM = 6;
	public static final int SETLOGPARAM = 0;
	public static final int DISPLAYSIM = 3;
	public static final int PRINTSIM = 4;
	
	LiquidGuiVariables variables;
	LiquidFrame frame;
	LiquidMenuBar menubar;
	ParameterPanel param;
	EnvironmentEditorPanel enviroeditor;
	SimulationPanel sim;
	ConsolePanel console;
	ValidityCheck check;
	
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
				args = new String[1];
				args[0] = variables.filename;
				i.receive(this, LiquidLogger.LOADLOG, args);
			break;
			case REQUEST_WRITELOG:
				args = variables.writeArray();
				i.receive(this, LiquidLogger.WRITELOG, args);
			break;
			}
		}
		if(i instanceof LiquidEngine){
			switch(arg0){
			case REQUEST_RUNSIM:
				args = variables.writeArray();
				i.receive(this, LiquidEngine.RUNSIM, args);
			break;
			case REQUEST_PAUSESIM:
				args = new String[0];
				i.receive(this, LiquidEngine.PAUSESIM, args);
			break;
			case REQUEST_ENDSIM:
				args = new String[0];
				i.receive(this, LiquidEngine.ENDSIM, args);
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
				variables.readArray(args);
				param.update();
				enviroeditor.update();
				sim.repaint();
				console.print_to_Console("Log File Loaded.\n");
			break;
			}
		}
		if(i instanceof LiquidEngine){
			switch(arg0){
			case DISPLAYSIM:
				variables.particles = args;
				sim.repaint();
			break;
			case PRINTSIM:
				console.print_to_Console(args[0]);
			break;
			}
		}
	}
	
	/**
	 * Method defines the components in the GUI.
	 */
	private void initComponents(){
		variables = new LiquidGuiVariables();
		frame = new LiquidFrame();
		frame.setJMenuBar(menubar = new LiquidMenuBar());
		frame.add(console = new ConsolePanel());
		frame.add(param = new ParameterPanel());
		param.add(enviroeditor = new EnvironmentEditorPanel());
		frame.add(sim = new SimulationPanel());	
		check = new ValidityCheck();
		frame.setVisible(true);
	}
	
	/**
	 * Method resets GUI to initial conditions and parameters.
	 */
	public void reset(){
		variables.reset();
		enviroeditor.reset();
		param.reset();
		frame.setTitle("Untitled - LIQUID : 2D Fluid Simulator");
	}
	
	public void setEnable(boolean enable){
		menubar.setEnabled(enable);
		enviroeditor.setEnabled(enable);
		param.setEnabled(enable);
	}
	
	public void dispose(){
		
	}
	
}
