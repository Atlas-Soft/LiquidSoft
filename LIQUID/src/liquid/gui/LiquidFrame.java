package liquid.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
		int exitSim = JOptionPane.showConfirmDialog(LiquidApplication.getGUI().frame,
				"Are you sure you want to exit the simulator?", "Exit Simulator?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (exitSim == JOptionPane.YES_OPTION) {
			super.dispose();
			LiquidApplication.dispose();
		}
		
	}
}
