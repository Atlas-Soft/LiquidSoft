package liquid.core;

import liquid.engine.LiquidEngine;
import liquid.logger.LiquidLogger;
import liquid.gui.LiquidGUI;

/**
 * LiquidApplication class initializes the Fluid Engine, Logger, and Graphical User Interface components
 * of the application. It also provides static access from one component to another using getter methods.
 */
public class LiquidApplication {
	// allows cross static access to the main application components
	private static LiquidEngine engine;
	private static LiquidLogger logger;
	private static LiquidGUI    gui;
	
	/**
	 * Constructor initializes the application components.
	 */
	public LiquidApplication() {
		engine = new LiquidEngine();
		logger = new LiquidLogger();
		gui    = new LiquidGUI();
		gui.getApplicationState().saveState();
	}
	
	/**
	 * Method disposes all components of the LiquidApplication class.
	 */
	public static void dispose() {
		engine.dispose();
		logger.dispose();
		gui.dispose();
	}
	
	/**
	 * Getter allows access to the Engine parts of the simulator.
	 * @return - Engine component
	 */
	public static LiquidEngine getEngine() {return engine;}
	
	/**
	 * Getter allows access to the Logger parts of the simulator.
	 * @return - Logger component
	 */
	public static LiquidLogger getLogger() {return logger;}
	
	/**
	 * Getter allows access to the GUI parts of the simulator.
	 * @return - GUI component
	 */
	public static LiquidGUI getGUI() {return gui;}
}