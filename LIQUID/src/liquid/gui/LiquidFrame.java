package liquid.gui;

import javax.swing.JFrame;

import liquid.core.LiquidApplication;

/**
 * @version 1.0
 */
public class LiquidFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public LiquidFrame(){
		super("Untitled - LIQUID : 2D Fluid Simulator");
		setSize(800,640);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	@Override
	public void dispose(){
		super.dispose();
		LiquidApplication.dispose();
	}
}
