package liquid.gui;

import java.awt.Color;

import javax.swing.BorderFactory;

import liquid.core.LiquidApplication;

public class BoundaryCheck {

	/**
	 * Method checks to ensure that the temperature value is
	 * actually a numerical value and is between -100 and 100.
	 * 
	 * @param  temp - value in the GUI text field
	 * @return true - when temp holds conditions
	 *         false - when temp does not hold conditions
	 */
	public static boolean tempHolds(String temp) {
		// first checks if value is actually numerical or not
		try {
			LiquidApplication.getGUI().variables.temperature = Float.parseFloat(temp);
		
			// next checks if value is between -100 and 100
			if ((Float.parseFloat(temp) - 100) > 0 || (Float.parseFloat(temp) + 100) < 0) {
				LiquidApplication.getGUI().param.temp.setBorder(BorderFactory.createLineBorder(Color.RED));
				LiquidApplication.getGUI().console.print_to_Console("Error: Temperature Value is Not Between -100 to 100.\n");
				return false;
			}
			LiquidApplication.getGUI().param.temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			return true;
		} catch (Exception e) {
			LiquidApplication.getGUI().param.temp.setBorder(BorderFactory.createLineBorder(Color.RED));
			LiquidApplication.getGUI().console.print_to_Console("Error: Temperature Value is Not Between -100 to 100.\n");
			return false;
		}
	} // closes tempHolds(String)
} // closes BoundaryCheck