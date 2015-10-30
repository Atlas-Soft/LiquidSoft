package liquid.gui;

import java.awt.*;
import java.awt.event.*;

public class LiquidGUI extends Frame{
	
	private ParameterPanel param;
	private SimulationPanel sim;
	private ConsolePanel console;
	private LiquidMenuBar menubar;
	
	public LiquidGUI(){
		super("LIQUID - 2D Fluid Simulator");
		initComponents();
		setSize(800,610);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                dispose();
            }
        });
	}
	
	private void initComponents(){
		add(console = new ConsolePanel());
		add(param = new ParameterPanel());
		add(sim = new SimulationPanel());
		setMenuBar(menubar = new LiquidMenuBar());
	}
	
	public ConsolePanel getConsolePanel(){
		return console;
	}
	
	public ParameterPanel getParameterPanel(){
		return param;
	}
	
	public SimulationPanel getSimulationPanel(){
		return sim;
	}
	
	public LiquidMenuBar getLiquidMenuBar(){
		return menubar;
	}

}
