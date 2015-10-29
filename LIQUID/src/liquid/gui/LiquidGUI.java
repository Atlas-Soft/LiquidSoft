package liquid.gui;

import java.awt.*;
import java.awt.event.*;

public class LiquidGUI extends Frame{
	
	private UIPanel ui;
	private SimulationPanel sim;
	private ConsolePanel console;
	
	public LiquidGUI(){
		super("LIQUID - 2D Fluid Simulator");
		initComponents();
		setSize(800,600);
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
		add(ui = new UIPanel(this));
		add(sim = new SimulationPanel());
		add(console = new ConsolePanel(this));
	}
	
	public ConsolePanel getConsolePanel(){
		return console;
	}

}
