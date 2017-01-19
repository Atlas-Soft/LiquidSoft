package liquid.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import liquid.core.LiquidApplication;

/**
 * Frame class creates the overall application frame.
 */
public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor sets the frame specifications, such as the size and resizeable factors.
	 */
	public Frame() {
		setSize(800,640);
		setResizable(false);
		
		// allows programmer-defined parameter specifications
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Method disposes the simulator itself, by first confirming this action with the user.
	 */
	@Override
	public void dispose() {
		if (LiquidApplication.getGUI().getVariousMessages().exitSimulation() == JOptionPane.YES_OPTION) {
			super.dispose();
			LiquidApplication.dispose();}
	}
}