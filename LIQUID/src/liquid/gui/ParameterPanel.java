package liquid.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import liquid.core.LiquidApplication;
import liquid.engine.LiquidEngine;
import liquid.logger.LiquidLogger;

/**
 * Sets up some of the parameters to be used during the simulation,
 * all changeable by the user. This includes the type of liquid,
 * temperature, viscosity, and run time; but most importantly, it
 * creates the Run, Pause, Step, and End buttons.
 * 
 * A replay check-box has also been added to indicate when a
 * simulation is running a previously saved set of parameters.
 */
public class ParameterPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// declares some of the parameter components, such as temperature and viscosity,
	// as well as the buttons to allow the user to begin, pause, and end a simulation
	JComboBox<String> liqs; // a drop-down menu
	JComboBox<String> time;
	JComboBox<String> temp;
	JComboBox<String> visc;
	JCheckBox replay;
	JButton run;
	JButton pause;
	JButton step;
	JButton end;
	
	// used to set up the simulation when it's paused, a file name exists, or it's a new simulation
	enum SetSim{Paused, YesFile, NewSim};

	/**
	 * Constructor for the parameter panels. Currently, it is located on the right side
	 * of the simulator, excluding the details of the 'Environment Editor:' section.
	 */
	public ParameterPanel() {
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		setBounds(500, 0, 300, 640);
		setVisible(true);
	}

	/**
	 * Initializes various parts of the parameters, such as the liquid
	 * type and run time, to a previously-defined default setting. The
	 * buttons to begin and end a simulation are also created here.
	 */
	private void initComponents() {
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);

		// creates labels for the previously defined set of parameters
		JLabel l = new JLabel("Liquid Type:");
		l.setBounds(25, 15, 120, 25);
		add(l);
		
		l = new JLabel("Run For (Seconds):");
		l.setBounds(155, 15, 120, 25);
		add(l);
		
		l = new JLabel("Temperature (\u00b0C):");
		l.setBounds(25, 65, 120, 25);
		add(l);

		l = new JLabel("Viscocity:");
		l.setBounds(155, 65, 120, 25);
		add(l);

		l = new JLabel("Environment Editor:");
		l.setBounds(25, 125, 140, 25);
		add(l);

		// creates drop-downs and/or text-boxes for the user to
		// enter values for the previously-defined set of parameters
		String[] options = { "Water", "Glycerin" };
		liqs = new JComboBox<String>(options);
		liqs.setSelectedIndex(0);
		liqs.setBounds(25, 40, 120, 25);
		add(liqs);

		time = new JComboBox<String>();
		for (int i = 0; i <= 300; i++) {
			time.addItem(Integer.toString(i));}
		time.setSelectedIndex(300);
		time.setBounds(155, 40, 120, 25);
		add(time);
		
		temp = new JComboBox<String>();
		for (int i = -100; i <= 100; i++) {
			temp.addItem(Integer.toString(i));}
		temp.setSelectedIndex(170);
		temp.setBounds(25, 90, 120, 25);
		add(temp);

		visc = new JComboBox<String>();
		for (int i = 0; i <= 50; i++) {
			visc.addItem(Integer.toString(i));}
		visc.setSelectedIndex(1);
		visc.setBounds(155, 90, 120, 25);
		add(visc);

		replay = new JCheckBox("Run Replay?");
		replay.setBounds(95, 475, 100, 25);
		add(replay);

		// the 'Run' button works only when the text fields have been inputed
		// correctly and there is a log file present to record the data
		run = new JButton("Run");
		run.setBounds(25, 510, 115, 25);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					if (LiquidApplication.getGUI().variables.simulating) {
						// for a paused simulation, it is still technically on-
						// going. Parameters need to be set to continue simulating
						prepareSim(SetSim.Paused, null);
						return;}
					
					if (LiquidApplication.getGUI().variables.filename != null) {
						// sets various parameters for a previously-saved simulation
						prepareSim(SetSim.YesFile, null);
						return;}
					
					// obtains the file name from the save file dialog and repeats
					// the process when necessary (like with an invalid file name)
					JFileChooser fileDialog = new JFileChooser("../logs");
					fileDialog.setAcceptAllFileFilterUsed(false);
					fileDialog.setApproveButtonText("Save");
					fileDialog.setDialogTitle("Create Log File");
					fileDialog.setFileFilter(new FileNameExtensionFilter("Log File", "log"));
					boolean cancel = false;
					
					// loop continues as long as there is an invalid file name
					// (or until the user presses the cancel button to exit)
					do {
						int returnVal = fileDialog.showSaveDialog(LiquidApplication.getGUI().frame);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							// a valid file name (not empty or repeated) is required to
							// proceed, or when the Cancel/'X' button is pressed 
							String filename = fileDialog.getSelectedFile().getPath();
							if (filename == null || filename.equals("")) {
								JOptionPane.showMessageDialog(null,
										"Please enter a valid file name, no empty or repeated names.",
										"Invalid File Name", JOptionPane.ERROR_MESSAGE);
							} else {
								// a legitimate file name exists, so program
								// sets various parameters for a new simulation
								prepareSim(SetSim.NewSim, filename);}
						} else {
							// cancel button jumps out of this loop
							cancel = true;}
					} while (LiquidApplication.getGUI().variables.filename == null && cancel == false);			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		add(run);

		// the 'Pause' button basically stops the simulation. However,
		// no parameter configurations are allowed during this state
		pause = new JButton("Pause");
		pause.setBounds(155, 510, 115, 25);
		pause.setEnabled(false);
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				run.setEnabled(true);
				pause.setEnabled(false);
				step.setEnabled(true);
				LiquidApplication.getGUI().console.print_to_Console("Simulation Paused.\n");
				LiquidApplication.getGUI().send(LiquidApplication.getEngine(),LiquidEngine.PAUSESIM);
			}
		});
		add(pause);

		// the 'Step' button proceeds through the simulation one step at a time,
		// or one second at a time (since that's how the Logger records data)
		step = new JButton("Step");
		step.setBounds(25, 545, 115, 25);
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {

			}
		});
		add(step);

		// the 'End' button dismisses the simulation
		end = new JButton("End");
		end.setBounds(155, 545, 115, 25);
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
				LiquidApplication.getGUI().console.print_to_Console("Simulation Ended.\n");
				LiquidApplication.getGUI().send(LiquidApplication.getEngine(),LiquidEngine.ENDSIM);
			}
		});
		add(end);
	}

	/**
	 * Used for the Run button to extract setting various parameters when
	 * setting up a simulation. This includes button enabling/disabling and
	 * sending notices to the Logger/Engine to write the log file and run simulation.
	 * 
	 * @param route    - determines the route to take
	 * @param filename - to pass in the file name (NewSim only)
	 */
	public void prepareSim(SetSim route, String filename) {
		run.setEnabled(false);
		pause.setEnabled(true);
		step.setEnabled(false);
		end.setEnabled(true);
		LiquidApplication.getGUI().send(LiquidApplication.getEngine(), LiquidEngine.RUNSIM);

		// sets parameters for specific cases
		switch (route) {
		case YesFile: // when a file name is already present
			LiquidApplication.getGUI().setEnable(false);
			LiquidApplication.getGUI().variables.simulating = true;
			LiquidApplication.getGUI().console.print_to_Console("Simulation Started.\n");
			break;
		case NewSim: // when NO file name is present
			LiquidApplication.getGUI().setEnable(false);
			LiquidApplication.getGUI().variables.simulating = true;
			LiquidApplication.getGUI().variables.filename = filename;
			LiquidApplication.getGUI().console.print_to_Console("Simulation Started.\n");
			LiquidApplication.getGUI().send(LiquidApplication.getLogger(), LiquidLogger.WRITELOG);
			break;
		default:
		}
	}
	
	/**
	 * Method will enable/disable the parameters defined in the parameter panel.
	 */
	public void setEnabled(boolean enable) {
		liqs.setEnabled(enable);
		time.setEnabled(enable);
		visc.setEnabled(enable);
		temp.setEnabled(enable);
		replay.setEnabled(enable);
	}

	/**
	 * The parameters in the parameter panel will get their values
	 * updated based on the information from the log file.
	 */
	public void logUpdate() {
		liqs.setSelectedIndex(0);
		time.setSelectedIndex(300);
		temp.setSelectedIndex(170);
		visc.setSelectedIndex(1);
	}
	
	/**
	 * The simulation will revert the parameters back to the original default settings.
	 */
	public void reset() {
		liqs.setSelectedIndex(0);
		time.setSelectedIndex(300);
		temp.setSelectedIndex(170);
		visc.setSelectedIndex(1);
		replay.setSelected(false);
	}
}