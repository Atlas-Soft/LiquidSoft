package liquid.gui;

import javax.swing.JFrame;

public class LiquidFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public LiquidFrame(){
		super("LIQUID - 2D Fluid Simulator");
		setSize(800,640);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
}
