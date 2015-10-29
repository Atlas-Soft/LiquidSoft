package liquid.core;

import liquid.engine.LiquidEngine;
import liquid.gui.LiquidGUI;
import liquid.logger.LiquidLogger;

public class LiquidApplication {
	
	private static LiquidGUI ui;
	private static LiquidLogger logger;
	private static LiquidEngine engine;
	
	public LiquidApplication(){
		ui = new LiquidGUI();
		logger = new LiquidLogger();
		engine = new LiquidEngine();
	}
	
	public static LiquidGUI getUI(){
		return ui;
	}
	
	public static LiquidLogger getLogger(){
		return logger;
	}
	
	public static LiquidEngine getEngine(){
		return engine;
	}
	
}
