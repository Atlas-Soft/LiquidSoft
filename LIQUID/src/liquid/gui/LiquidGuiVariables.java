package liquid.gui;

import java.util.ArrayList;

public class LiquidGUIVariables {
	 
	boolean simulating = false;
	String filename;
	String liquid;
	float temperature;
	float viscosity;
	int runtime;
	int enviroLength = 500;
	int enviroWidth = 400;
	ArrayList<String> objects = new ArrayList<String>();
	int selectedObject;
	
	
	/**
	 * Stores all of the parameters into an array as a String,
	 * which can then be written into the log file.
	 * 
	 * @return
	 */
	public String[] storeArray() {
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(filename);
		list.add(liquid);
		list.add(Float.toString(temperature));
		list.add(Float.toString(viscosity));
		list.add(Integer.toString(runtime));
		list.add(Integer.toString(enviroLength) + " " + Integer.toString(enviroWidth));
		
		for (int i = 0; i < objects.size(); i++) {
			list.add(objects.get(i));
		}
		
		list.add("break");
		String[] var = list.toArray(new String[list.size()]);
		return var;
	}
	
	
	public void readArray(String[] arr) {
		liquid = arr[1];
		temperature = Float.parseFloat(arr[2]);
		viscosity = Float.parseFloat(arr[3]);
		runtime = Integer.parseInt(arr[4]);
		
		String[] tokens = arr[5].split(" ");
		enviroLength = Integer.parseInt(tokens[0]);
		enviroWidth = Integer.parseInt(tokens[1]);
		
		objects = new ArrayList<String>();
		for (int i = 6; i < arr.length; i++) {
			objects.add(arr[i]);
		}
		
		selectedObject = 0;
	}
}