package liquid.core;

import liquid.engine.LiquidEngine;
import liquid.gui.LiquidGUI;
import liquid.logger.LiquidLogger;

/**
 * The Liquid Application class initializes the Fluid Engine, GUI, and
 * Logger components. It also provides static access to these components.
 */
public class LiquidApplication {
	
	// allows static access to the main simulation components
	private static LiquidEngine engine;
	private static LiquidGUI gui;
	private static LiquidLogger logger;
	
	/**
	 * Constructor initializes the application components.
	 */
	public LiquidApplication() {
		engine = new LiquidEngine();
		gui = new LiquidGUI();
		logger = new LiquidLogger();
	}
	
	/**
	 * Method disposes all components of the liquid simulator.
	 */
	public static void dispose() {
		engine.dispose();
		gui.dispose();
		logger.dispose();
	}
	
	/**
	 * Getter allows access to the Engine parts of the simulator.
	 * 
	 * @return engine - the Engine component
	 */
	public static LiquidEngine getEngine() {
		return engine;
	}
	
	/**
	 * Getter allows access to the GUI parts of the simulator.
	 * 
	 * @return	gui - the GUI component
	 */
	public static LiquidGUI getGUI() {
		return gui;
	}
	
	/**
	 * Getter allows access to the Logger parts of the simulator.
	 * 
	 * @return logger - the Logger component
	 */
	public static LiquidLogger getLogger() {
		return logger;
	}
}