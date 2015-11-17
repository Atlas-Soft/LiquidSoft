package liquid.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import liquid.core.LiquidApplication;
import liquid.engine.LiquidEngine;
import liquid.logger.LiquidLogger;

/**
 * Sets up some of the parameters to be used during the simulation, all
 * changeable by the user. This includes the type of liquid, temperature,
 * viscosity, and run time; but most importantly, it creates the Run, Pause,
 * Step, and End buttons.
 * 
 * A replay checkbox has also been added to indicate when a simulation is
 * running a previously saved set of parameters.
 */
public class ParameterPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// declares some of the parameter components, such as
	// temperature and viscosity, as well as buttons to
	// allow the user to begin, pause, and end a simulation
	JList<String> liqs;
	JTextField temp;
	JTextField visc;
	JTextField time;
	JCheckBox replay;
	JButton run;
	JButton pause;
	JButton step;
	JButton end;

	/**
	 * Constructor for the parameter panels. Currently, it is located on the
	 * right side of the simulator, excluding the 'Environment Editor:' section.
	 */
	public ParameterPanel() {
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.lightGray);
		setBounds(500, 0, 300, 640);
		setVisible(true);
	}

	/**
	 * Initializes various parts of the parameters, such as the liquid type and
	 * run time, to a previously- defined default setting. The buttons to begin
	 * and end a simulation are also created here.
	 */
	private void initComponents() {
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);

		// creates labels for the previously
		// defined set of parameters needed
		JLabel l = new JLabel("Temperature:");
		l.setBounds(155, 15, 120, 25);
		add(l);

		l = new JLabel("Viscocity:");
		l.setBounds(155, 65, 120, 25);
		add(l);

		l = new JLabel("Environment Editor:");
		l.setBounds(25, 165, 140, 25);
		add(l);

		l = new JLabel("Run For:");
		l.setBounds(25, 475, 75, 25);
		add(l);

		// creates a list for the types of liquid to be
		// used in the simulation, with the required
		// liquids of 'Water' and 'Glycerin'
		String[] options = { "Water", "Glycerin" };
		liqs = new JList<String>(options);
		liqs.setBounds(25, 15, 120, 150);
		liqs.setSelectedIndex(0);
		add(liqs);

		// creates textboxes for the user to enter values
		// for the previously-defined set of parameters
		temp = new JTextField("70");
		temp.setBounds(155, 40, 120, 25);
		add(temp);

		visc = new JTextField("1");
		visc.setBounds(155, 90, 120, 25);
		add(visc);

		time = new JTextField("300");
		time.setBounds(75, 475, 65, 25);
		add(time);

		replay = new JCheckBox("Run Replay");
		replay.setBounds(155, 475, 115, 25);
		add(replay);

		// the 'Run' button works only when the text fields have been inputed
		// correctly and there is a log file present to record the data
		run = new JButton("Run");
		run.setBounds(25, 510, 115, 25);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					if (!LiquidApplication.getGUI().variables.simulating) {
						// makes sure that the obtained parameter values, either
						// defaults or user-specified, are in the appropriate
						// ranges
						if (LiquidApplication.getGUI().check.tempHolds(temp.getText()) && LiquidApplication.getGUI().check.viscosityHolds(visc.getText())
							&& LiquidApplication.getGUI().check.runtimeHolds(time.getText())) {
							LiquidApplication.getGUI().variables.runtime = Integer .parseInt(time.getText());
							// if a log file is not already present, the user has
							// to define a valid log file name in order to proceed
							if (LiquidApplication.getGUI().variables.filename == null) {
								String filename = JOptionPane.showInputDialog(LiquidApplication.getGUI().frame,"Save Log As:");
								if (filename == null || filename.equals("")) return;
								LiquidApplication.getGUI().variables.filename = "../logs/"+ filename + ".log";
								LiquidApplication.getGUI().send(LiquidApplication.getLogger(),LiquidLogger.WRITELOG);
							}
							// when the user presses the Pause button, the simulation is
							// still technically
							// running so the other buttons must be turned on/off, as
							// appropriate
							run.setEnabled(false);
							pause.setEnabled(true);
							step.setEnabled(false);
							end.setEnabled(true);
							LiquidApplication.getGUI().setEnable(false);
							LiquidApplication.getGUI().variables.simulating = true;
							LiquidApplication.getGUI().console.print_to_Console("Simulation Started.\n");
							LiquidApplication.getGUI().send(LiquidApplication.getEngine(), LiquidEngine.RUNSIM);
						}
					}else{
						pause.setEnabled(true);
						step.setEnabled(false);
						end.setEnabled(true);
						LiquidApplication.getGUI().send(LiquidApplication.getEngine(), LiquidEngine.RUNSIM);
					}
						
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		add(run);

		// the 'Pause' button becomes enabled only after simulation runs
		pause = new JButton("Pause");
		pause.setBounds(155, 510, 115, 25);
		pause.setEnabled(false);
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().console.print_to_Console("Simulation Paused.\n");
				LiquidApplication.getGUI().send(LiquidApplication.getEngine(),LiquidEngine.PAUSESIM);
				pause.setEnabled(false);
				run.setEnabled(true);
				step.setEnabled(true);
				LiquidApplication.getGUI().console.print_to_Console("Simulation Paused.\n");
			}
		});
		add(pause);

		// the 'Step' button proceeds through the simulation
		// one step at a time, presumably one second at a time
		// (since that's how the Logger records the data)
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
				LiquidApplication.getGUI().console.print_to_Console("Simulation Ended.\n");
				LiquidApplication.getGUI().send(LiquidApplication.getEngine(),LiquidEngine.ENDSIM);
				LiquidApplication.getGUI().variables.simulating = false;
				LiquidApplication.getGUI().variables.particles = new String[0];
				run.setEnabled(true);
				pause.setEnabled(false);
				step.setEnabled(true);
				end.setEnabled(false);
				LiquidApplication.getGUI().setEnable(true);
				LiquidApplication.getGUI().sim.repaint();
				LiquidApplication.getGUI().console.print_to_Console("Simulation Ended.\n");
			}
		});
		add(end);
	} // closes initComponents()

	/**
	 * The simulation will revert back to the original default settings.
	 */
	public void reset() {
		liqs.setSelectedIndex(0);
		temp.setText("70");
		visc.setText("1");
		time.setText("300");
		replay.setSelected(false);
	}

	public void setEnabled(boolean enable) {
		liqs.setEnabled(enable);
		visc.setEnabled(enable);
		temp.setEnabled(enable);
		time.setEnabled(enable);
		replay.setEnabled(enable);
	}

	/**
	 * The main parameters will get their values updated when a feature has
	 * changed.
	 */
	public void update() {
		liqs.setSelectedIndex(0);
		temp.setText(Float.toString(LiquidApplication.getGUI().variables.temperature));
		visc.setText(Float.toString(LiquidApplication.getGUI().variables.viscosity));
		time.setText(Integer.toString(LiquidApplication.getGUI().variables.runtime));
	} // closes update()

} // closes ParameterPanel

