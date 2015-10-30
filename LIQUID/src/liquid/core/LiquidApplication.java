package liquid.core;

import liquid.engine.LiquidEngine;
import liquid.gui.LiquidGUI;
import liquid.logger.LiquidLogger;

public class LiquidApplication {
	
	private static LiquidGUI gui;
	private static LiquidLogger logger;
	private static LiquidEngine engine;
	
	public LiquidApplication(){
		logger = new LiquidLogger();
		engine = new LiquidEngine();
		gui = new LiquidGUI();
	}
	
	public static LiquidGUI getGUI(){
		return gui;
	}
	
	public static LiquidLogger getLogger(){
		return logger;
	}
	
	public static LiquidEngine getEngine(){
		return engine;
	}
	
}
