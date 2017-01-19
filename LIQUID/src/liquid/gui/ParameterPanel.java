package liquid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import liquid.core.GlobalVariables;
import liquid.core.LiquidApplication;

/**
 * Sets up some of the parameters to be used during the simulation, all changeable by the user. This includes the type of
 * liquid, temperature, viscosity, and run time; but most importantly, it creates the Run, Pause, Step, and End buttons.
 * 
 * <p>A replay check-box has also been added to indicate when a simulation is running a previously saved set of parameters.</p>
 */
public class ParameterPanel extends JPanel {

	// declares some of the parameter components, such as temperature and viscosity,
	// as well as the buttons to allow the user to begin, pause, and end a simulation
	private static final long serialVersionUID = 1L;
	ArrayList<String> liquidInfo;
	float tempMin = 0;
	float origTemp = 21;
	boolean actualChange = true;
	DecimalFormat df = new DecimalFormat("0.0000");
	
	JComboBox<String> liqs; // a drop-down menu
	JComboBox<Integer> time;
	JComboBox<String> tempVisc;
	JCheckBox replay;
	
	ParameterPanelButtons paramPanelButtons = new ParameterPanelButtons();

	/**
	 * Constructor for the parameter panels. Currently, it is located on the right side
	 * of the simulator, excluding the details of the 'Environment Editor:' section.
	 */
	public ParameterPanel() {
		super();
		liquidInfo = new ArrayList<String>();
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		setBounds(500,0,300,640);
		setVisible(true);
	}

	/**
	 * Initializes various parts of the parameters, such as the liquid type and run time, to a previously-defined
	 * default setting once the config file has been read. The buttons to begin/end simulations are also created here.
	 */
    void initComponents() {
		//Font font = new Font("Verdana",Font.BOLD,10);
		setFont(GlobalVariables.font);

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
					LiquidApplication.getGUI().getFileVariables().setRuntime((int)time.getSelectedItem());
					LiquidApplication.getGUI().getApplicationState().saveState();}
			}
		});
		add(time);
		
		replay = new JCheckBox("Run Replay?");
		replay.setSelected(false);
		replay.setBounds(95,475,100,25);
		add(replay);
		
		// populates the drop-down info for liquid types/temperature/viscosity as well as creating buttons
		tempVisc = new JComboBox<String>();
		liqsParam();
		tempAndViscParam(0,100,(float)1.787,(float)0.282);
		add(paramPanelButtons.runButton());
		add(paramPanelButtons.pauseButton());
		add(paramPanelButtons.stepButton());
		add(paramPanelButtons.endButton());
		paramPanelButtons.setEnabledButtons(true,false,true,false);
		reset();
	}
	
	/**
	 * Method adjusts the liquid drop-down, which then adjusts the temperature/viscosity drop-down accordingly.
	 */
	public void liqsParam() {
		// creates drop-downs for the basic parameters
		liqs = new JComboBox<String>();
		for (int i = 0; i < liquidInfo.size(); i++) {
			String[] tokens = liquidInfo.get(i).split(" ");
			liqs.addItem(tokens[0]);}
		liqs.setBounds(25,40,120,25);
		liqs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				if (actualChange) {
					// sets the liquid type as well as resets the values of the temperature/viscosity drop-down
					setLiquidType();
					LiquidApplication.getGUI().getFileVariables().setLiquid((String)liqs.getSelectedItem());
					LiquidApplication.getGUI().getApplicationState().saveState();
				}
			}
		});
		add(liqs);
	}
	
	/**
	 * Method gathers information based on the liquid type selected. It then promptly resets the drop-down
	 * values of the temperature/viscosity to prevent the simulation from running using a solid or a gas.
	 */
	public void setLiquidType() {
		// searches for a matching liquid type
		for (int i = 0; i < liquidInfo.size(); i++) {
			String[] tokens = liquidInfo.get(i).split(" ");
			String liquid;
			if (actualChange) 
				liquid = (String)liqs.getSelectedItem();
			else
				liquid = (String)LiquidApplication.getGUI().getFileVariables().getLiquid();
			
			// liquid type matches, so gathers its freezing/boiling points and viscosity values
			if (tokens[0].equals(liquid)) {
				// if an actual change was made to the temperature drop-down, it then gathers the temperature
				// set; otherwise, gets the previously-set temperature (when Undo/Redo has been pressed)
				if (actualChange) {
					tempMin = Float.parseFloat(tokens[1]);
					origTemp = Float.parseFloat(((String)tempVisc.getSelectedItem()).split(" ")[0]);}
				else {
					origTemp = LiquidApplication.getGUI().getFileVariables().getTemperature();}
				
				// resets the values of the temperature/viscosity based on the selected parameters
				actualChange = false;
				tempAndViscParam(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
						Float.parseFloat(tokens[3]), Float.parseFloat(tokens[4]));
				actualChange = true;
			}
		}
	}
	
	/**
	 * Method adjusts the temperature/viscosity parameters to be within the limitations of a liquid type, determined
	 * by the config file. This prevents the simulation from running when a liquid has become a solid or a gas.
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
		
		if (origTemp >= tempMin && origTemp < tempMax)
			tempVisc.setSelectedIndex((int)(origTemp-tempMin-1));
		else if (origTemp < tempMin)
			tempVisc.setSelectedIndex(0);
		else if (origTemp >= tempMax)
			tempVisc.setSelectedIndex((int)(tempMax-tempMin-2));
		tempVisc.setBounds(55,90,185,25);
		
		for (ActionListener actionListener : tempVisc.getActionListeners())
			tempVisc.removeActionListener(actionListener);
		
		tempVisc.addActionListener(tempAndViscActionListener());
		add(tempVisc);
	}
	
	public ActionListener tempAndViscActionListener() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// sets the internal temperature/viscosity values when an actual change occurs
				if (actualChange) {
					String[] tokens = ((String)tempVisc.getSelectedItem()).split(" ");
					LiquidApplication.getGUI().getFileVariables().setTemperature(Float.parseFloat(tokens[0]));
					LiquidApplication.getGUI().getFileVariables().setViscosity(Float.parseFloat(tokens[2]));
					LiquidApplication.getGUI().getApplicationState().saveState();}
		}};
		return actionListener;
	}

	/**
	 * Used for the Step/Run button to extract setting various parameters when setting up a simulation. This includes
	 * button enabling/disabling and sending notices to the Logger/Engine to write the log file and run simulation.
	 * @param route    - determines the route to take
	 * @param filename - to pass in the file name (for new simulations only)
	 */
	public void recordParametersToLogFile(boolean newParameters) {
		
		if (newParameters)
			LiquidApplication.getGUI().getApplicationState().reset();
		
		LiquidApplication.getGUI().send(LiquidApplication.getLogger(), GlobalVariables.Request.REQUEST_WRITE_LOG_PARAM);
	}
	
	/**
	 * Method sets the parameters to the selected values after the Run button has been pressed.
	 */
	public void setParamToRun() {
		LiquidApplication.getGUI().setEnable(false);
		LiquidApplication.getGUI().getFileVariables().setSimulating(true);
		LiquidApplication.getGUI().getFileVariables().setLiquid((String)liqs.getSelectedItem());
		LiquidApplication.getGUI().getFileVariables().setRuntime((int)time.getSelectedItem());
		String[] tokens = ((String)tempVisc.getSelectedItem()).split(" ");
		LiquidApplication.getGUI().getFileVariables().setTemperature(Float.parseFloat(tokens[0]));
		LiquidApplication.getGUI().getFileVariables().setViscosity(Float.parseFloat(tokens[2]));
	}
	
	/**
	 * Method sets the parameters after the user has pressed the Undo button.
	 */
	public void setUndoParam() {
		actualChange = false;
		liqs.setSelectedItem(LiquidApplication.getGUI().getFileVariables().getLiquid());
		time.setSelectedItem(LiquidApplication.getGUI().getFileVariables().getRuntime());
		setLiquidType();
		actualChange = true;
	}
	
	/**
	 * The parameters in the parameter panel will get their values updated based on the information from the log file.
	 */
	public void logUpdate() {
		liqs.setSelectedItem(LiquidApplication.getGUI().getFileVariables().getLiquid());
		time.setSelectedItem(LiquidApplication.getGUI().getFileVariables().getRuntime());
		tempVisc.setSelectedIndex((int)(LiquidApplication.getGUI().getFileVariables().getTemperature()-tempMin-1));
	}
	
	/**
	 * Method will enable/disable the parameters defined in the parameter panel.
	 * @param enable - to enable/disable components
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
		origTemp = 21;
		tempAndViscParam(0,100,(float)1.787,(float)0.282);
		liqs.setSelectedIndex(0);
		time.setSelectedIndex(300);
		tempVisc.setSelectedIndex((int)origTemp-1);
		replay.setSelected(false);
		actualChange = true;
	}
	
	public ParameterPanelButtons getParameterPanelButtons() {return paramPanelButtons;}
}