package liquid.gui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LiquidFrame extends Frame {

	private static final long serialVersionUID = 1L;
	
	public LiquidFrame(){
		super("LIQUID - 2D Fluid Simulator");
		setSize(800,625);
		setBackground(Color.lightGray);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                dispose();
                System.exit(0);
            }
        });
	}
	
}
