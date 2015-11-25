// DONE!
package liquid.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import liquid.core.LiquidApplication;

/**
 * Class creates the overall frame of the simulator.
 */
public class LiquidFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor sets the parameters of the frame, such as the size.
	 */
	public LiquidFrame() {
		super("Untitled - LIQUID : 2D Fluid Simulator");
		setSize(800, 640);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Method disposes the GUI, or exits the entire simulator
	 * by first confirming this action with the user.
	 */
	@Override
	public void dispose() {
		int exitSim = JOptionPane.showConfirmDialog(LiquidFrame.this,
				"Are you sure you want to exit the simulator?", "Exit Simulator?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (exitSim == JOptionPane.YES_OPTION) {
			super.dispose();
			LiquidApplication.dispose();}		
	}
}