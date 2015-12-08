package liquid.gui;

import java.util.ArrayList;
import java.util.LinkedList;

import liquid.core.GlobalVar;
import liquid.core.LiquidApplication;

/**
 * Class will store all of the parameters associated with the simulation. This
 * includes file name, liquid type, temperature, viscosity, and various others.
 */
public class LiquidGuiVariables {
	
	// declares various parameters regarding the GUI
	boolean simulating;
	String filename;
	String onlyFileName; // the actual file name itself; is not stored
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
		onlyFileName = "Untitled";
		liquid = "Water";
		selectedObject = 0;
		temperature = 21;
		viscosity = (float)1.47986;
		runtime = 300;
		enviroLength = 500;
		enviroWidth = 400;
		objects = new ArrayList<String>();
		
		// initializes parameters regarding the Engine
		savedStates = new LinkedList<String[]>();
		undoStates = new LinkedList<String[]>();
		particles = new String[0];
		saveState();
	}
	
	/**
	 * Stores all of the necessary parameters into an array list as String values, which
	 * will then be passed from the GUI to the Logger and written into the log file.
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
		list.add(Float.toString(enviroLength)+" "+Float.toString(enviroWidth));
		
		// adds information of all objects, like X-/Y-Coordinates
		for (int i = 0; i < objects.size(); i++) {
			list.add(objects.get(i));}
		
		list.add("break");
		String[] arr = list.toArray(new String[list.size()]);
		return arr;
	}	

	/**
	 * Obtains an array list of necessary parameters from the Logger to be read. This
	 * method will then set them as their respective native variables of this class.
	 * 
	 * @param args - array that holds parameter info
	 */
	public void readArray(String[] args) {
		liquid = args[1];
		selectedObject = Integer.parseInt(args[2]);
		temperature = Float.parseFloat(args[3]);
		viscosity = Float.parseFloat(args[4]);
		runtime = Integer.parseInt(args[5]);
		
		// splits the sixth item into 2 parts, the length/width of the environment, stored as "'length' 'width'"
		String[] tokens = args[6].split(" ");
		enviroLength = Float.parseFloat(tokens[0]);
		enviroWidth = Float.parseFloat(tokens[1]);
		
		objects = new ArrayList<String>();
		for (int i = 7; i < args.length; i++) {
			objects.add(args[i]);}
	}
	
	/**
	 * Checks whether there are unsaved parameter changes to the current simulation or not. If so,
	 * sets the simulator file name to include a '*' (to imitate current software functionalities).
	 */
	public void saveState() {
		// saves the entire list of parameters onto the linked list to be able to see the history of changes
		savedStates.push(writeArray());
		undoStates.clear();
		if (savedStates.size() > 1) {
			LiquidApplication.getGUI().frame.setTitle("*"+onlyFileName+GlobalVar.title);}
	}
	
	/**
	 * Un-does the latest change made by the user.
	 */
	public void undo() {

		// if there are unsaved changes, the latest changes will get be popped
		// from the linked list, and the newest set of parameters will be set
		if (savedStates.size() > 1) {
			undoStates.push(savedStates.pop());
			readArray(savedStates.peek());
			LiquidApplication.getGUI().param.setUndoParam();}
		
		// eliminates the '*' from the title when there are no more unsaved changes
		if (savedStates.size() == 1) {
			LiquidApplication.getGUI().frame.setTitle(onlyFileName+GlobalVar.title);}
	}
	
	/**
	 * Re-does the latest change made by the user.
	 */
	public void redo() {
		// if a set of un-did changes is present, then those set of parameters
		// will be pushed back onto the saved state of parameters and be set
		if (!undoStates.isEmpty()) {
			savedStates.push(undoStates.pop());
			readArray(savedStates.peek());
			LiquidApplication.getGUI().param.setUndoParam();
			LiquidApplication.getGUI().frame.setTitle("*"+onlyFileName+GlobalVar.title);}
	}
	
	/**
	 * Resets the program to its default settings.
	 */
	public void reset() {
		simulating = false;
		filename = null;
		onlyFileName = "Untitled";
		liquid = "Water";
		selectedObject = 0;
		temperature = 21;
		viscosity = (float)1.47986;
		runtime = 300;
		selectedObject = 0;
		enviroLength = 500;
		enviroWidth = 400;
		objects.clear();
		savedStates.clear();
		undoStates.clear();
		particles = new String[0];
		saveState();
	}
}