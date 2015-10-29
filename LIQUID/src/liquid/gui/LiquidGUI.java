package liquid.gui;

import java.awt.*;
import java.awt.event.*;

public class LiquidGUI extends Frame{

	public LiquidGUI(){
		super("LIQUID - 2D Fluid Simulator");
		add(new UIPanel());
		add(new SimulationPanel());
		add(new ConsolePanel());
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

}
