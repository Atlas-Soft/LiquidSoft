package liquid.gui;

import liquid.core.Interfaceable;
import liquid.logger.LiquidLogger;

public class LiquidGUI implements Interfaceable{
	
	public static final int REQUEST_LOADLOG = 0;
	public static final int SETLOGPARAM = 0;
	
	private LiquidFrame frame;
	private LiquidMenuBar menubar;
	private ParameterPanel param;
	private EnvironmentEditorPanel eeditor;
	private SimulationPanel sim;
	private ConsolePanel console;
	
	public LiquidGUI(){
		initComponents();
	}
	
	@Override
	public void send(Interfaceable i, int arg0) {
		if(i instanceof LiquidLogger){
			switch(arg0){
			case REQUEST_LOADLOG:
				String[] args = new String[1];
				args[0] = menubar.getLoadLogFile();
				i.recieve(this, LiquidLogger.LOADLOG, args);
			}
		}
	}

	@Override
	public void recieve(Interfaceable i, int arg0, String[] args) {
		if(i instanceof LiquidLogger){
			switch(arg0){
			case SETLOGPARAM:
				console.print_to_Console("Log File Loaded.\n");
			}
		}
	}
	
	private void initComponents(){
		frame = new LiquidFrame();
		frame.setMenuBar(menubar = new LiquidMenuBar());
		frame.add(console = new ConsolePanel());
		frame.add(param = new ParameterPanel());
		param.add(eeditor = new EnvironmentEditorPanel());
		frame.add(sim = new SimulationPanel());	
	}
	
	LiquidFrame getLiquidFrame(){
		return frame;
	}
	
	LiquidMenuBar getLiquidMenuBar(){
		return menubar;
	}
	
	ParameterPanel getParameterPanel(){
		return param;
	}
	
	EnvironmentEditorPanel getEnvironmentEditorPanel(){
		return eeditor;
	}
	
	ConsolePanel getConsolePanel(){
		return console;
	}
	
	SimulationPanel getSimulationPanel(){
		return sim;
	}
	
}
