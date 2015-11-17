package liquid.gui;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class will store all of the parameters associated with
 * the simulation. This includes file name, liquid type,
 * temperature, viscosity, and various others.
 */
public class LiquidGuiVariables {

	LinkedList<String[]> savedStates;
	LinkedList<String[]> undoStates;
	
	boolean simulating;
	String filename;
	String liquid;
	float temperature;
	float viscosity;
	int runtime;
	float enviroLength;
	float enviroWidth;
	ArrayList<String> objects;
	int selectedObject;

	
	String[] particles;
	
	public LiquidGuiVariables(){
		savedStates = new LinkedList<String[]>();
		undoStates = new LinkedList<String[]>();
		simulating = false;
		enviroLength = 500;
		enviroWidth = 400;
		objects = new ArrayList<String>();
		particles = new String[0];
		saveState();
	}
	
	public void reset(){
		savedStates.clear();
		undoStates.clear();
		simulating = false;
		filename = null;
		liquid = null;
		temperature = 0;
		viscosity = 0;
		runtime = 0;
		enviroLength = 500;
		enviroWidth = 400;
		objects.clear();
		saveState();
	}
	
	/**
	 * Stores all of the necessary parameters into an array list
	 * as String values, which will then be passed from the GUI
	 * to the Logger and written into the log file.
	 * 
	 * @return - the string array of all parameters
	 */
	public String[] writeArray() {
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(filename);
		list.add(liquid);
		list.add(Integer.toString(selectedObject));
		list.add(Float.toString(temperature));
		list.add(Float.toString(viscosity));
		list.add(Integer.toString(runtime));
		list.add(Float.toString(enviroLength) + " " + Float.toString(enviroWidth));
		
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
	public void readArray(String[] args) {
		liquid = args[1];
		selectedObject = Integer.parseInt(args[2]);
		temperature = Float.parseFloat(args[3]);
		viscosity = Float.parseFloat(args[4]);
		runtime = Integer.parseInt(args[5]);
		
		// splits the sixth item into 2 parts, the length/width
		// of the environment. It is stored as "'length' 'width'"
		String[] tokens = args[6].split(" ");
		enviroLength = Float.parseFloat(tokens[0]);
		enviroWidth = Float.parseFloat(tokens[1]);
		
		objects = new ArrayList<String>();
		for (int i = 7; i < args.length; i++) {
			objects.add(args[i]);
		}
		
		
	}
	
	public void saveState(){
		savedStates.push(writeArray());
		undoStates.clear();
	}
	
	public void undo(){
		if(savedStates.size() > 1){
			undoStates.push(savedStates.pop());
			readArray(savedStates.peek());
		}
	}
	
	public void redo(){
		if(!undoStates.isEmpty()){
			savedStates.push(undoStates.pop());
			readArray(savedStates.peek());
		}
	}
}
