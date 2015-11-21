package liquid.gui;

import java.util.ArrayList;
import java.util.LinkedList;

import liquid.core.LiquidApplication;

/**
 * Class will store all of the parameters associated with
 * the simulation. This includes file name, liquid type,
 * temperature, viscosity, and various others.
 */
public class LiquidGuiVariables {
	
	// declares various parameters regarding the GUI
	boolean simulating;
	String filename;
	String liquid;
	int selectedObject;
	float temperature;
	float viscosity;
	int runtime;
	float enviroLength;
	float enviroWidth;
	ArrayList<String> objects;

	// declares various parameters regarding the Engine
	LinkedList<String[]> savedStates;
	LinkedList<String[]> undoStates;
	String[] particles;
	
	/**
	 * Constructor initializes some initial values of the parameters declared.
	 */
	public LiquidGuiVariables() {
		// initializes parameters regarding the GUI
		simulating = false;
		enviroLength = 500;
		enviroWidth = 400;
		objects = new ArrayList<String>();
		selectedObject = 0;
		
		// initializes parameters regarding the Engine
		savedStates = new LinkedList<String[]>();
		undoStates = new LinkedList<String[]>();
		particles = new String[0];
		saveState();
	}
	
	/**
	 * Resets the program to its default settings.
	 */
	public void reset() {
		// resets the GUI parameters
		simulating = false;
		filename = null;
		liquid = null;
		enviroLength = 500;
		enviroWidth = 400;
		objects.clear();
		
		// resets the Engine parameters
		savedStates.clear();
		undoStates.clear();
		particles = new String[0];
		saveState();
	}
	
	/**
	 * Stores all of the necessary parameters into an array list
	 * as String values, which will then be passed from the GUI
	 * to the Logger and written into the log file.
	 * 
	 * @return - the string array of parameters
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
		
		// adds information of all objects, like X-/Y-Coordinates
		for (int i = 0; i < objects.size(); i++) {
			list.add(objects.get(i));}
		
		list.add("break");
		String[] arr = list.toArray(new String[list.size()]);
		return arr;
	}	

	/**
	 * Obtains an array list of necessary parameters from the
	 * Logger to be read. This method will then set them
	 * as their respective native variables of this class.
	 * 
	 * @param args - array that holds parameter info
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
			objects.add(args[i]);}
	}
	
	public void saveState(){
		savedStates.push(writeArray());
		undoStates.clear();
		if (savedStates.size() > 1) {
			LiquidApplication.getGUI().frame.setTitle("*"+filename + " - LIQUID : 2D Fluid Simulator   ");
			if (filename == null) {
				LiquidApplication.getGUI().frame.setTitle("*Untitled - LIQUID : 2D Fluid Simulator   ");
			}
		}
	}
	
	public void undo(){
		if(savedStates.size() > 1){
			undoStates.push(savedStates.pop());
			readArray(savedStates.peek());}
	}
	
	public void redo(){
		if(!undoStates.isEmpty()){
			savedStates.push(undoStates.pop());
			readArray(savedStates.peek());}
	}
}