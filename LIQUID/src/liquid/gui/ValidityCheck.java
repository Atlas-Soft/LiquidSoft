package liquid.gui;

import java.awt.Color;

import liquid.core.LiquidApplication;

/**
 * Class holds all of the methods to check the validity
 * of an input. Temperature values, for example, must be
 * a numerical value from -100 to 100, and run time values
 * must be a numerical value from 0 to 300.
 */
public class ValidityCheck {

	/**
	 * Method checks to ensure that the temperature value is
	 * actually a numerical value between -100 and 100 degrees Celsius.
	 * 
	 * @param  temp - value in the GUI text field
	 * @return true - when temp is between -100 and 100 degrees Celsius
	 *         false - when temp is not between -100 and 100 degrees Celsius
	 */
	public boolean tempHolds(String temp) {
		// first checks if value is actually numerical or not
		try {
			LiquidApplication.getGUI().variables.temperature = Float.parseFloat(temp);

			// next checks if value is between -100 and 100
			if (Float.parseFloat(temp) < -100 || Float.parseFloat(temp) > 100) {
				// sets the textbox red to signal to the user of an invalid input
				LiquidApplication.getGUI().param.temp.setBackground(new Color(255, 130, 130));
				LiquidApplication.getGUI().console.print_to_Console("Error: Temperature Value is Not "
						+ "Between -100 to 100 Degrees Celsius.\n");
				return false;
			}
			LiquidApplication.getGUI().param.temp.setBackground(new Color(255, 255, 255));
			return true;
		} catch (Exception e) {
			LiquidApplication.getGUI().param.temp.setBackground(new Color(255, 130, 130));
			LiquidApplication.getGUI().console.print_to_Console("Error: Temperature Value is Not "
					+ "Between -100 to 100 Degrees Celsius.\n");
			return false;
		}
	} // closes tempHolds(String)
	
	
	/**
	 * Method checks to ensure that the viscosity
	 * value is actually a numerical value.
	 * 
	 * @param  visc - value in the GUI text field
	 * @return true - when viscosity is a valid number
	 *         false - when viscosity is not a valid number
	 */
	public boolean viscosityHolds(String visc) {
		// checks if value is actually numerical or not
		try {
			LiquidApplication.getGUI().variables.viscosity = Float.parseFloat(visc);
			LiquidApplication.getGUI().param.visc.setBackground(new Color(255, 255, 255));
			return true;
		} catch (Exception e) {
			
			// sets the textbox red to signal to the user of an invalid input
			LiquidApplication.getGUI().param.visc.setBackground(new Color(255, 130, 130));
			LiquidApplication.getGUI().console.print_to_Console("Error: Viscosity is Not "
					+ "An Actual Number.\n");
			return false;
		}
	} // closes viscosityHolds(String)
	
	
	/**
	 * Method checks to ensure that the run time value is
	 * actually a numerical value between 0 and 300 seconds.
	 * 
	 * @param  runtime - value in the GUI text field
	 * @return true - when run time is between 0 and 300 seconds
	 *         false - when run time is not between 0 and 300 seconds
	 */
	public boolean runtimeHolds(String runtime) {
		// first checks if value is actually numerical or not
		try {
			LiquidApplication.getGUI().variables.runtime = Integer.parseInt(runtime);
			// next checks if value is between 0 and 300
			if (Integer.parseInt(runtime) < 0 || Integer.parseInt(runtime) > 300) {
				// sets the textbox red to signal to the user of an invalid input
				LiquidApplication.getGUI().param.time.setBackground(new Color(255, 130, 130));
				LiquidApplication.getGUI().console.print_to_Console("Error: Runtime is Not "
						+ "Between 0 and 300 Seconds.\n");
				return false;
			}
			LiquidApplication.getGUI().param.time.setBackground(new Color(255, 255, 255));
			return true;
		} catch (Exception e) {
			LiquidApplication.getGUI().param.time.setBackground(new Color(255, 130, 130));
			LiquidApplication.getGUI().console.print_to_Console("Error: Runtime is Not "
					+ "Between 0 and 300 Seconds.\n");
			return false;
		}
	} // closes runtimeHolds(String)
} // closes BoundaryCheck