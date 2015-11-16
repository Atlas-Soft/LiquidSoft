package liquid.gui;

import java.util.ArrayList;

/**
 * Class will store all of the parameters associated with
 * the simulation. This includes file name, liquid type,
 * temperature, viscosity, and various others.
 */
public class LiquidGUIVariables {
	
	boolean simulating = false; // parameter not stored
	String filename;
	String liquid;
	float temperature;
	float viscosity;
	int runtime;
	int enviroLength = 500;
	int enviroWidth = 400;
	ArrayList<String> objects = new ArrayList<String>();
	int selectedObject = 0; // parameter not stored
	
	
	/**
	 * Stores all of the necessary parameters into an array list
	 * as String values, which will then be passed from the GUI
	 * to the Logger and written into the log file.
	 * 
	 * @return - the string array of all parameters
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
		String[] arr = list.toArray(new String[list.size()]);
		return arr;
	} // closes storeArray()
	
	
	/**
	 * Obtains an array list of necessary parameters from the
	 * Logger to be read. This method will then set them
	 * as their respective native variables of this class.
	 * 
	 * @param arr - array that holds all the parameter info
	 */
	public void readArray(String[] arr) {
		liquid = arr[1];
		temperature = Float.parseFloat(arr[2]);
		viscosity = Float.parseFloat(arr[3]);
		runtime = Integer.parseInt(arr[4]);
		
		// splits the sixth item into 2 parts, the length/width
		// of the environment. It is stored as "'length' 'width'"
		String[] tokens = arr[5].split(" ");
		enviroLength = Integer.parseInt(tokens[0]);
		enviroWidth = Integer.parseInt(tokens[1]);
		
		objects = new ArrayList<String>();
		for (int i = 6; i < arr.length; i++) {
			objects.add(arr[i]);
		}
	} // closes readArray(String[])
} // closes LiquidGUIVariables