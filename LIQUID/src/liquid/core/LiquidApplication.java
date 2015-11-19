package liquid.core;

import liquid.engine.LiquidEngine;
import liquid.gui.LiquidGUI;
import liquid.logger.LiquidLogger;

/**
 * The Liquid Application class initializes the GUI,
 * Logger, and Fluid Engine components. It also provides
 * static access to the components.
 * 
 *
 */
public class LiquidApplication {
	
	private static LiquidGUI gui;
	private static LiquidLogger logger;
	private static LiquidEngine engine;
	
	/**
	 * Constructor initializes components of application.
	 */
	public LiquidApplication(){
		logger = new LiquidLogger();
		engine = new LiquidEngine();
		gui = new LiquidGUI();
	}
	
	public static void dispose(){
		logger.dispose();
		engine.dispose();
		gui.dispose();
	}
	
	/**
	 * 
	 * @return	gui
	 */
	public static LiquidGUI getGUI(){
		return gui;
	}
	
	/**
	 * 
	 * @return logger
	 */
	public static LiquidLogger getLogger(){
		return logger;
	}
	
	/**
	 * 
	 * @return engine
	 */
	public static LiquidEngine getEngine(){
		return engine;
	}
	
}
