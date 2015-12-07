package liquid.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import liquid.core.GlobalVar;
import liquid.core.LiquidApplication;

/**
 * Sets up some of the parameters to be used during the simulation, all changeable
 * by the user. This includes the type of liquid, temperature, viscosity, and run
 * time; but most importantly, it creates the Run, Pause, Step, and End buttons.
 * 
 * A replay check-box has also been added to indicate when a
 * simulation is running a previously saved set of parameters.
 */
public class ParameterPanel extends JPanel {

	// declares some of the parameter components, such as temperature and viscosity,
	// as well as the buttons to allow the user to begin, pause, and end a simulation
	private static final long serialVersionUID = 1L;
	String[] options = {"Water", "Glycerin"};
	float tempMin = 0;
	float origTemp = 21;
	boolean actualChange = true;
	DecimalFormat df = new DecimalFormat("#.#####");
	
	JComboBox<String> liqs; // a drop-down menu
	JComboBox<Integer> time;
	JComboBox<String> tempVisc;
	JCheckBox replay;
	JButton run;
	JButton pause;
	JButton step;
	JButton end;
	
	// used to set up the simulation when it's a new simulation, a file name exists, or it's paused
	enum SetSim{NEW_SIM, YES_FILE, PAUSED};

	/**
	 * Constructor for the parameter panels. Currently, it is located on the right side
	 * of the simulator, excluding the details of the 'Environment Editor:' section.
	 */
	public ParameterPanel() {
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		setBounds(500,0,300,640);
		setVisible(true);
	}

	/**
	 * Initializes various parts of the parameters, such as the liquid type and run time, to a
	 * previously-defined default setting. The buttons to begin and end a simulation are also created here.
	 */
	private void initComponents() {
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);

		// creates labels for the previously defined set of parameters
		JLabel l = new JLabel("Liquid Type:");
		l.setBounds(25,15,120,25);
		add(l);
		
		l = new JLabel("Run For (Seconds):");
		l.setBounds(155,15,120,25);
		add(l);
		
		l = new JLabel("Temperature (\u00b0C) \\ Viscosity:");
		l.setBounds(65,65,170,25);
		add(l);

		l = new JLabel("Environment Editor:");
		l.setBounds(25,125,140,25);
		add(l);
		
		time = new JComboBox<Integer>();
		for (int i = 0; i <= 300; i++) {
			time.addItem(i);}
		time.setSelectedIndex(300);
		time.setBounds(155,40,120,25);
		time.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (actualChange) {
					LiquidApplication.getGUI().variables.runtime = (int)time.getSelectedItem();
					LiquidApplication.getGUI().variables.saveState();}
			}
		});
		add(time);
		
		replay = new JCheckBox("Run Replay?");
		replay.setSelected(false);
		replay.setBounds(95,475,100,25);
		add(replay);
		
		tempVisc = new JComboBox<String>();
		liqsParam(); // populates drop-down for liquid types
		tempAndViscParam(0,100,(float)1.787,(float)0.282); // populates drop-down for temperature/viscosity
		runButton(); // creates the Run button
		pauseButton(); // creates the Pause button
		stepButton(); // creates the Step button
		endButton(); // creates the End button
	}
	
	/**
	 * Method adjusts the liquid drop-down. It also gathers information from the config file if not already done so yet.
	 */
	public void liqsParam() {
		// creates drop-downs for the basic parameters
		liqs = new JComboBox<String>(options);
		//for (int i = 0; i <= LiquidApplication.getGUI().variables.liquidInfo.size(); i++) {
		//	String[] tokens = LiquidApplication.getGUI().variables.liquidInfo.get(i).split(" ");
		//	liqs.addItem(tokens[0]);
		//}
		liqs.setSelectedIndex(0);
		liqs.setBounds(25,40,120,25);
		liqs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				if (actualChange) {
					// sends a request to load the config file from the Logger if not already done so
					if (LiquidApplication.getGUI().variables.liquidInfo.size() == 0) {
						LiquidApplication.getGUI().send(LiquidApplication.getLogger(), GlobalVar.Request.REQUEST_LOAD_CONFIG_FILE);}
					
					// sets the liquid type as well as resets the values of the drop-down
					setLiquidType();
					LiquidApplication.getGUI().variables.liquid = (String)liqs.getSelectedItem();
					LiquidApplication.getGUI().variables.saveState();
				}
			}
		});
		add(liqs);
	}
	
	/**
	 * Method adjusts the temperature/viscosity parameters to be within the limitations of a liquid type, determined
	 * by the config file. This prevents the simulation from running when a liquid has become a solid or a gas.
	 * 
	 * @param tempMin - melting point of the liquid
	 * @param tempMax - boiling point of the liquid
	 * @param viscMin - viscosity at the melting point
	 * @param viscMax - viscosity at the boiling point
	 */
	public void tempAndViscParam(float tempMin, float tempMax, float viscMin, float viscMax) {
		// all items are first removed, then gets populated with items dependent on the melting/boiling points
		tempVisc.removeAllItems();
		for (int iTemp = (int)(tempMin+1); iTemp <= (tempMax-1); iTemp++) {
			if (viscMin > viscMax) {
				tempVisc.addItem(Float.valueOf(iTemp)+" \\ "+df.format(Float.valueOf(viscMin)));
				viscMin+=((viscMax-viscMin)/(tempMax-tempMin-2));}
		}
			
		if (origTemp >= tempMin && origTemp <= tempMax)
			tempVisc.setSelectedIndex((int)(origTemp-tempMin-1));
		else
			tempVisc.setSelectedIndex((int)(((tempMax-tempMin)/2)));
		tempVisc.setBounds(55,90,185,25);
		if (actualChange) {
			tempVisc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					if (actualChange) {
						String[] tokens = ((String)tempVisc.getSelectedItem()).split(" ");
						LiquidApplication.getGUI().variables.temperature = Float.parseFloat(tokens[0]);
						LiquidApplication.getGUI().variables.viscosity = Float.parseFloat(tokens[2]);
						LiquidApplication.getGUI().variables.saveState();}
				}
			});
		}
		add(tempVisc);
	}

	/**
	 * Method creates the Run button of the ParameterPanel class, which will begin a
	 * simulation when all of the conditions have been met, such as a correct log file.
	 */
	public void runButton() {
		// the 'Run' button works only when there is a log file present to record the data
		run = new JButton("Run");
		run.setBounds(25,510,115,25);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// paused simulations are technically on-going; specific parameters need to be set to continue simulating
				if (LiquidApplication.getGUI().variables.simulating) {
					run.setEnabled(false);
					pause.setEnabled(true);
					step.setEnabled(false);
					prepareSim(SetSim.PAUSED, null);
					LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVar.Request.REQUEST_RUN_SIM);
					
				// sets various parameters for a previously-saved simulation
				} else if (LiquidApplication.getGUI().variables.filename != null &&
						LiquidApplication.getGUI().variables.savedStates.size() <= 1) {
					run.setEnabled(false);
					pause.setEnabled(true);
					step.setEnabled(false);
					prepareSim(SetSim.YES_FILE, null);
					LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVar.Request.REQUEST_RUN_SIM);
				
				// for new simulations, path calls Logger to set a valid log file name
				} else {
					String filename = LiquidFileChooser.setUpFile("SAVE");
					if (filename != null) {
						run.setEnabled(false);
						pause.setEnabled(true);
						step.setEnabled(false);
						prepareSim(SetSim.NEW_SIM, filename);
						LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVar.Request.REQUEST_RUN_SIM);}
				}
			}
		});
		add(run);
	}
	
	/**
	 * Method creates the Pause button, which will pause the simulation. During
	 * this time, however, the user is unable to change any parameter details.
	 */
	public void pauseButton() {
		// the 'Pause' button stops the simulation, but no parameter configurations are allowed during this state
		pause = new JButton("Pause");
		pause.setBounds(155,510,115,25);
		pause.setEnabled(false);
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				run.setEnabled(true);
				pause.setEnabled(false);
				step.setEnabled(true);
				LiquidApplication.getGUI().console.print_to_Console("[Simulation Paused.]\n");
				LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVar.Request.REQUEST_PAUSE_SIM);
			}
		});
		add(pause);
	}
	
	/**
	 * Method creates the Step button, which will proceed through the simulation one frame at a time.
	 * Once pressed, the simulation is technically running, so no parameter configurations are allowed. 
	 */
	public void stepButton() {
		// the 'Step' button proceeds through the simulation one frame at a time
		step = new JButton("Step");
		step.setBounds(25,545,115,25);
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// when the simulation is paused, simply steps through one frame at a time
				if (LiquidApplication.getGUI().variables.simulating) {
					LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVar.Request.REQUEST_STEP_SIM);
					
				// sets various parameters for a previously-saved simulation
				} else if (LiquidApplication.getGUI().variables.filename != null &&
						LiquidApplication.getGUI().variables.savedStates.size() >= 1) {
					prepareSim(SetSim.YES_FILE, null);
					LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVar.Request.REQUEST_STEP_SIM);

				// for new simulations, path calls Logger to set a valid log file name
				} else {
					String filename = LiquidFileChooser.setUpFile("SAVE");
					if (filename != null){
						prepareSim(SetSim.NEW_SIM, filename);
						LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVar.Request.REQUEST_STEP_SIM);}
				}
			}
		});
		add(step);
	}
	
	/**
	 * Method creates the End button, which ends the simulation. Once pressed, the log file will be
	 * complete for replay, and any changes made will prompt the user to specify the log file name again.
	 */
	public void endButton() {
		// the 'End' button dismisses the simulation
		end = new JButton("End");
		end.setBounds(155,545,115,25);
		end.setEnabled(false);
		end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				run.setEnabled(true);
				pause.setEnabled(false);
				step.setEnabled(true);
				end.setEnabled(false);
				LiquidApplication.getGUI().setEnable(true);
				LiquidApplication.getGUI().variables.simulating = false;
				LiquidApplication.getGUI().variables.particles = new String[0];
				LiquidApplication.getGUI().sim.repaint();
				LiquidApplication.getGUI().console.print_to_Console("[Simulation Ended.]\n");
				LiquidApplication.getGUI().send(LiquidApplication.getEngine(), GlobalVar.Request.REQUEST_END_SIM);
			}
		});
		add(end);
	}

	/**
	 * Used for the Step/Run button to extract setting various parameters when setting up a simulation. This includes
	 * button enabling/disabling and sending notices to the Logger/Engine to write the log file and run simulation.
	 * 
	 * @param route    - determines the route to take
	 * @param filename - to pass in the file name (for new simulations only)
	 */
	public void prepareSim(SetSim route, String filename) {
		end.setEnabled(true);
		switch (route) {
		case PAUSED: // when the simulation has been paused
			return;
		case YES_FILE: // when a file name is already present
			setParamToRun();
			LiquidApplication.getGUI().send(LiquidApplication.getLogger(), GlobalVar.Request.REQUEST_WRITE_LOG_PARAM);
			break;
		case NEW_SIM: // when NO file name is present
			setParamToRun();
			LiquidApplication.getGUI().variables.filename = filename;
			LiquidApplication.getGUI().variables.savedStates.clear();
			LiquidApplication.getGUI().variables.saveState();
			LiquidApplication.getGUI().frame.setTitle(LiquidApplication.getGUI().variables.onlyFileName+GlobalVar.title);
			LiquidApplication.getGUI().send(LiquidApplication.getLogger(), GlobalVar.Request.REQUEST_WRITE_LOG_PARAM);
			break;
		default:
		}
		LiquidApplication.getGUI().console.print_to_Console("[Simulation Started.]\n");
	}
	
	/**
	 * Method gathers information based on the liquid type selected. It then promptly resets the drop-down
	 * values of the temperature/viscosity to prevent the simulation from running using a solid or a gas.
	 */
	public void setLiquidType() {
		// searches for a matching liquid type
		for (int i = 0; i < LiquidApplication.getGUI().variables.liquidInfo.size(); i++) {
			String[] tokens = LiquidApplication.getGUI().variables.liquidInfo.get(i).split(" ");
			String liquid;
			if (actualChange) 
				liquid = (String)liqs.getSelectedItem();
			else
				liquid = (String)LiquidApplication.getGUI().variables.liquid;
			
			// liquid type matches, so gathers its freezing/boiling points and viscosity values
			if (tokens[0].equals(liquid)) {
				
				// if an actual change was made to the temperature drop-down, it then gathers the temperature
				// set; otherwise, gets the previously-set temperature (when Undo/Redo has been pressed)
				if (actualChange) {
					tempMin = Float.parseFloat(tokens[1]);
					String[] tokens2 = ((String)tempVisc.getSelectedItem()).split(" ");
					origTemp = Float.parseFloat(tokens2[0]);}
				else {
					origTemp = LiquidApplication.getGUI().variables.temperature;}
				
				// resets the values of the temperature/viscosity based on the selected parameters
				actualChange = false;
				tempAndViscParam(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
						Float.parseFloat(tokens[3]), Float.parseFloat(tokens[4]));}
		}
		actualChange = true;
	}
	
	/**
	 * Method sets the parameters to the selected values after the Run button has been pressed.
	 */
	public void setParamToRun() {
		LiquidApplication.getGUI().setEnable(false);
		LiquidApplication.getGUI().variables.simulating = true;
		String[] tokens = ((String)tempVisc.getSelectedItem()).split(" ");
		LiquidApplication.getGUI().variables.liquid = (String) liqs.getSelectedItem();
		LiquidApplication.getGUI().variables.runtime = (int) time.getSelectedItem();
		LiquidApplication.getGUI().variables.temperature = Float.parseFloat(tokens[0]);
		LiquidApplication.getGUI().variables.viscosity = Float.parseFloat(tokens[2]);
	}
	
	/**
	 * Method sets the parameters after the user has pressed the Undo button.
	 */
	public void setUndoParam() {
		actualChange = false;
		liqs.setSelectedItem(LiquidApplication.getGUI().variables.liquid);
		time.setSelectedItem(LiquidApplication.getGUI().variables.runtime);
		setLiquidType();
		actualChange = true;
	}
	
	/**
	 * The parameters in the parameter panel will get their values updated based on the information from the log file.
	 */
	public void logUpdate() {
		liqs.setSelectedItem(LiquidApplication.getGUI().variables.liquid);
		time.setSelectedItem(LiquidApplication.getGUI().variables.runtime);
		tempVisc.setSelectedIndex((int)(LiquidApplication.getGUI().variables.temperature-tempMin-1));
	}
	
	/**
	 * Method will enable/disable the parameters defined in the parameter panel.
	 */
	public void setEnabled(boolean enable) {
		liqs.setEnabled(enable);
		time.setEnabled(enable);
		tempVisc.setEnabled(enable);
		replay.setEnabled(enable);
	}
	
	/**
	 * The simulation will revert the parameters back to the original default settings.
	 */
	public void reset() {
		actualChange = false;
		tempAndViscParam(0,100,(float)1.787,(float)0.282);
		liqs.setSelectedIndex(0);
		time.setSelectedIndex(300);
		tempVisc.setSelectedIndex(20);
		replay.setSelected(false);
		actualChange = true;
	}
}