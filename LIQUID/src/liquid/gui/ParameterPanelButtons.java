package liquid.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import liquid.core.GlobalVariables;
import liquid.core.LiquidApplication;

/**
 * ParameterPanelButtons is a branch of the ParameterPanel class. Here, the buttons located near the
 * bottom quarter of the panel are declared. Users may choose to either run a simulation, pause midway
 * through a simulation, step through the simulation frame-by-frame, or end the simulation altogether. 
 */
public class ParameterPanelButtons {
	JButton run,pause,step,end;
	
	/**
	 * Method creates the Run button of the ParameterPanel class, which will begin a
	 * simulation when all of the conditions have been met, such as a correct log file.
	 */
	public JButton runButton() {
		// the 'Run' button works only when there is a log file present to record the data
		run = new JButton("Run");
		run.setBounds(25,510,115,25);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				
				if (!LiquidApplication.getGUI().getFileVariables().getFilename().endsWith(".log"))
					LiquidApplication.getGUI().send(LiquidApplication.getLogger(),GlobalVariables.Request.REQUEST_SAVE_FILE);
				
				if (LiquidApplication.getGUI().getApplicationState().getSavedStates().size() > 1)
					LiquidApplication.getGUI().getParameterPanel().recordParametersToLogFile(true);
				else
					LiquidApplication.getGUI().getParameterPanel().recordParametersToLogFile(false);
				
				LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVariables.Request.REQUEST_RUN_SIM);
				setEnabledButtons(false,true,false,true);
				LiquidApplication.getGUI().getConsolePanel().printToConsole("[Simulation Started.]\n");
		}});
		return run;
	}
	
	/**
	 * Method creates the Pause button, which pauses the simulation. Albeit paused, no parameters may be adjusted during this time period.
	 * Users may, however, perform observations about the liquid particle positions in regards to other objects & the simulation itself.
	 */
	public JButton pauseButton() {
		pause = new JButton("Pause");
		pause.setBounds(155,510,115,25);
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				
				// sets the buttons' enable statuses & prints a friendly user notification
				setEnabledButtons(true,false,true,true);
				LiquidApplication.getGUI().getConsolePanel().printToConsole("[Simulation Paused.]\n");
				LiquidApplication.getGUI().send(LiquidApplication.getEngine(),GlobalVariables.Request.REQUEST_PAUSE_SIM);
		}});
		return pause;
	}
	
	/**
	 * Method creates the Step button, which will proceed through the simulation one frame at a time.
	 * Once pressed, the simulation is technically running, so no parameter configurations are allowed. 
	 */
	public JButton stepButton() {
		// the 'Step' button proceeds through the simulation one frame at a time
		step = new JButton("Step");
		step.setBounds(25,545,115,25);
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// when the simulation is paused, simply steps through one frame at a time
				if (LiquidApplication.getGUI().getFileVariables().getSimulating()) {
					LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVariables.Request.REQUEST_STEP_SIM);
					
				// sets various parameters for a previously-saved simulation
				} else if (!LiquidApplication.getGUI().getFileVariables().getFilename().endsWith(".log") &&
						LiquidApplication.getGUI().getApplicationState().getSavedStates().size() >= 1) {
					LiquidApplication.getGUI().getParameterPanel().recordParametersToLogFile(false);
					LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVariables.Request.REQUEST_STEP_SIM);

				// for new simulations, path calls Logger to set a valid log file name
				} else {
					LiquidApplication.getGUI().send(LiquidApplication.getLogger(),GlobalVariables.Request.REQUEST_SAVE_FILE);
					if (LiquidApplication.getGUI().getFileVariables().getFilename() != null){
						LiquidApplication.getGUI().getParameterPanel().recordParametersToLogFile(true);
						LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVariables.Request.REQUEST_STEP_SIM);}
				}
				
				setEnabledButtons(true,false,true,true);
				LiquidApplication.getGUI().getConsolePanel().printToConsole("[Simulation Stepped Into.]\n");
		}});
		return step;
	}
	
	/**
	 * Method creates the End button, which ends the simulation. Users may load the completed log file to view that particular simulation again.
	 */
	public JButton endButton() {
		end = new JButton("End");
		end.setBounds(155,545,115,25);
		end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				
				// enables/disables various settings & clears the SimulationPanel, all preparing for a new simulation
				setEnabledButtons(true,false,true,false);
				LiquidApplication.getGUI().setEnable(true);
				LiquidApplication.getGUI().getFileVariables().resetSimulation();
				LiquidApplication.getGUI().getSimulationPanel().repaint();
				LiquidApplication.getGUI().getConsolePanel().printToConsole("[Simulation Ended.]\n");
				LiquidApplication.getGUI().send(LiquidApplication.getEngine(),GlobalVariables.Request.REQUEST_END_SIM);
		}});
		return end;
	}
	
	/**
	 * Method enables/disables each button according to the given parameter value.
	 * @param runEnabler   - Run button enabler
	 * @param pauseEnabler - Pause button enabler
	 * @param stepEnabler  - Step button enabler
	 * @param endEnabler   - End button enabler
	 */
	public void setEnabledButtons(boolean runEnabler, boolean pauseEnabler, boolean stepEnabler, boolean endEnabler) {
		run.setEnabled(runEnabler);
		pause.setEnabled(pauseEnabler);
		step.setEnabled(stepEnabler);
		end.setEnabled(endEnabler);
	}
	
	public JButton getRunButton() {return run;}
	public JButton getPauseButton() {return pause;}
	public JButton getStepButton() {return step;}
	public JButton getEndButton() {return end;}
}