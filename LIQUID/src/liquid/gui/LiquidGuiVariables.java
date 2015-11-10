package liquid.gui;

import java.util.ArrayList;

public class LiquidGuiVariables {
	 
	String filename;
	String liquid;
	float temperature;
	float viscosity;
	float runtime;
	int enviroLength = 500;
	int enviroWidth = 400;
	ArrayList<String> objects = new ArrayList<String>();
	int selectedObjects;
	
	
	/**
	 * Stores all of the parameters into an array as a String,
	 * which can then be written into the log file.
	 * 
	 * @return
	 */
	public String[] storeArray() {
		String[] var = new String[10];
		
		var[0] = filename;
		var[1] = liquid;
		var[2] = Float.toString(temperature);
		var[3] = Float.toString(viscosity);
		var[4] = Float.toString(runtime);
		var[5] = Integer.toString(enviroLength) + " " + Integer.toString(enviroWidth);
		
		for (int i = 0; i < objects.size(); i++) {
			var[i+6] = objects.get(i);
		}
		return var;
	}
	
	
	public void readArray(String[] arr) {
		filename = arr[0];
		liquid = arr[1];
		temperature = Float.parseFloat(arr[2]);
		viscosity = Float.parseFloat(arr[3]);
		runtime = Float.parseFloat(arr[4]);
		
		String[] tokens = arr[5].split(" ");
		enviroLength = Integer.parseInt(tokens[0]);
		enviroWidth = Integer.parseInt(tokens[1]);
		
		for (int i = 6; i < arr.length; i++) {
			objects.add(arr[i]);
		}
	}
}