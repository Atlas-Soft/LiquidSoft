package liquid.gui;

import java.util.ArrayList;

import liquid.core.GlobalVariables;

/**
 * FileVariables class stores all relevant information for the log file, including filename, liquid type, temperature,
 * viscosity, runtime, environment width/height, selected object index, & object parameters. It also writes/reads the
 * <code>ArrayList</code> sent to/taken from the Logger, storing all information locally for easier file arrangements.
 */
public class FileVariables {
	// declares log file parameters
	String filename,liquid;
	float  temperature,viscosity,environmentWidth,environmentHeight;
	int    runtime,selectedObject;
	ArrayList<String> objects = new ArrayList<String>();
	
	boolean simulating;                                   // indicates if application is running or not
	ArrayList<String> fileList = new ArrayList<String>(); // stores list of log file parameters
	ArrayList<GlobalVariables.LoggerItems> fileListInfo =
			new ArrayList<GlobalVariables.LoggerItems>(); // stores list of parameter name for easy file adjustments
	String[] particles = new String[0];                   // stores list of particle locations
	
	/**
	 * Constructor initializes the simulation parameter values.
	 */
	public FileVariables() {
		reset();          // 'resets' the log file parameter values
		writeArrayInfo(); // allows for flexible adjustments of log file layout
	}

	/**
	 * Method stores all necessary parameters into an <code>ArrayList</code> as <code>String</code>
	 * values. This list is then written into a log file for future simulations to load and view.
	 * @return - <code>String Array</code> of parameters
	 */
	public String[] writeArray() {
		fileList.clear();
		fileList.add(filename);
		fileList.add(liquid);
		fileList.add(Float.toString(temperature));
		fileList.add(Float.toString(viscosity));
		fileList.add(Integer.toString(runtime));
		fileList.add(Float.toString(environmentWidth));
		fileList.add(Float.toString(environmentHeight));
		fileList.add(Integer.toString(selectedObject));
		for (String obj : objects)
			fileList.add(obj);
		
		fileList.add("break"); // added to detect the end of a log file
		return fileList.toArray(new String[fileList.size()]);
	}
	
	/**
	 * Method imitates the <code>writeArray()</code> method's <code>ArrayList</code> parameter structure, except only using the actual name.
	 * This first step allows free alterations to the log file's layout without having to adjust new indexes when calling the log file list.
	 */
	public void writeArrayInfo() {
		fileListInfo.clear();
		fileListInfo.add(GlobalVariables.LoggerItems.filename);
		fileListInfo.add(GlobalVariables.LoggerItems.liquid);
		fileListInfo.add(GlobalVariables.LoggerItems.temperature);
		fileListInfo.add(GlobalVariables.LoggerItems.viscosity);
		fileListInfo.add(GlobalVariables.LoggerItems.runtime);
		fileListInfo.add(GlobalVariables.LoggerItems.environmentWidth);
		fileListInfo.add(GlobalVariables.LoggerItems.environmentHeight);
		fileListInfo.add(GlobalVariables.LoggerItems.selectedObject);
		fileListInfo.add(GlobalVariables.LoggerItems.objects);
	}
	
	/**
	 * Method first receives a <code>String Array</code> log file, then sets
	 * each part to their respective native variable for future adjustments.
	 * @param file - <code>String Array</code> of parameter
	 */
	public void readArray(String[] file) {
		liquid            = file[readArrayIndex(GlobalVariables.LoggerItems.liquid)];
		temperature       = Float.parseFloat(file[readArrayIndex(GlobalVariables.LoggerItems.temperature)]);
		viscosity         = Float.parseFloat(file[readArrayIndex(GlobalVariables.LoggerItems.viscosity)]);
		runtime           = Integer.parseInt(file[readArrayIndex(GlobalVariables.LoggerItems.runtime)]);
		environmentWidth  = Float.parseFloat(file[readArrayIndex(GlobalVariables.LoggerItems.environmentWidth)]);
		environmentHeight = Float.parseFloat(file[readArrayIndex(GlobalVariables.LoggerItems.environmentHeight)]);
		selectedObject    = Integer.parseInt(file[readArrayIndex(GlobalVariables.LoggerItems.selectedObject)]);
		
		// adds objects after the selected object index & prior to the "break" detector
		objects.clear();
		for (int i = readArrayIndex(GlobalVariables.LoggerItems.selectedObject)+1; i < file.length-1; i++)
			objects.add(file[i]);
	}
	
	/**
	 * Method compares the given value with the <code>ArrayList</code> of log file parameter names. This second step allows
	 * for free alterations to the log file's layout without having to adjust new indexes when calling the log file list.
	 * @param item - item to compare with
	 * @return     - index of item within <code>ArrayList</code>
	 */
	public int readArrayIndex(GlobalVariables.LoggerItems item) {
		for (int i = 0; i < fileListInfo.size(); i++) {
			if (item.toString().equals(fileListInfo.get(i).toString()))
				return i;}
		return -1;
	}
	
	/**
	 * Method resets the properties of the FileVariables class.
	 */
	public void reset() {
		filename          = "Untitled"+GlobalVariables.title;
		liquid            = "Water";
		temperature       = 21;
		viscosity         = (float)1.47986;
		runtime           = 300;
		environmentWidth  = 500;
		environmentHeight = 400;
		selectedObject    = 0;
		objects.clear();
		
		// resets current simulation parameters via different method
		resetSimulation();
	}
	
	/**
	 * Method resets current simulation parameters.
	 */
	public void resetSimulation() {
		simulating = false;
		particles = new String[0];
	}
	
	/**
	 * Setter method sets the simulation status (running or not) with the given value.
	 * @param simulating - new simulating status
	 */
	public void setSimulating(boolean simulating) {this.simulating = simulating;}
	
	/**
	 * Getter method returns the simulation status (running or not).
	 * @return - simulation status
	 */
	public boolean getSimulating() {return simulating;}
	
	/**
	 * Setter method sets the filename with the given value.
	 * @param fileName - new filename
	 */
	public void setFilename(String filename) {this.filename = filename;}
	
	/**
	 * Getter method returns the whole filename directory.
	 * @return - whole filename directory
	 */
	public String getFilename() {return filename;}
	
	/**
	 * Getter method returns only the filename itself, no directory.
	 * @param title - whether or not to include title
	 * @return      - only the filename itself
	 */
	public String getFilenameOnlyAndTitle(boolean title) {
		// checks correct final directory & splits filename from that point
		if (filename.endsWith(".log")) {
			if (title)
				return (filename.split("logs")[1].substring(1,filename.split("logs")[1].length())+GlobalVariables.title);
			else
				return filename.split("logs")[1].substring(1,filename.split("logs")[1].length());}
		return filename;
	}
	
	/**
	 * Setter method sets the liquid type with the given value.
	 * @param liquid - new liquid type
	 */
	public void setLiquid(String liquid) {this.liquid = liquid;}
	
	/**
	 * Getter method returns the liquid type.
	 * @return - liquid type
	 */
	public String getLiquid() {return liquid;}
	
	/**
	 * Setter method sets the temperature with the given value.
	 * @param temperature - new temperature
	 */
	public void setTemperature(float temperature) {this.temperature = temperature;}
	
	/**
	 * Getter method returns the temperature value.
	 * @return - temperature value
	 */
	public float getTemperature() {return temperature;}
	
	/**
	 * Setter method sets the viscosity with the given value.
	 * @param viscosity - new viscosity
	 */
	public void setViscosity(float viscosity) {this.viscosity = viscosity;}
	
	/**
	 * Getter method returns the viscosity value.
	 * @return - viscosity value
	 */
	public float getViscosity() {return viscosity;}
	
	/**
	 * Setter method sets the runtime with the given value.
	 * @param runtime - new runtime
	 */
	public void setRuntime(int runtime) {this.runtime = runtime;}
	
	/**
	 * Getter method returns the runtime.
	 * @return - runtime
	 */
	public int getRuntime() {return runtime;}
	
	/**
	 * Setter method sets the environment's width with the given value.
	 * @param environmentWidth - new environment width
	 */
	public void setEnvironmentWidth(float environmentWidth) {this.environmentWidth = environmentWidth;}
	
	/**
	 * Getter method returns the environment's width.
	 * @return - environment's width
	 */
	public float getEnvironmentWidth() {return environmentWidth;}
	
	/**
	 * Setter method sets the environment's height with the given value.
	 * @param environmentHeight - new environment height
	 */
	public void setEnvironmentHeight(float environmentHeight) {this.environmentHeight = environmentHeight;}
	
	/**
	 * Getter method returns the environment's height.
	 * @return - environment's height
	 */
	public float getEnvironmentHeight() {return environmentHeight;}
	
	/**
	 * Setter method sets the currently-selected object index with the given value.
	 * @param selectedObject - new selected object index
	 */
	public void setSelectedObject(int selectedObject) {this.selectedObject = selectedObject;}
	
	/**
	 * Getter method returns the currently-selected object index.
	 * @return - currently-selected object index
	 */
	public int getSelectedObject() {return selectedObject;}
	
	/**
	 * Getter method returns the objects list.
	 * @return - objects list
	 */
	public ArrayList<String> getObjects() {return objects;}
	
	/**
	 * Setter method sets particles with the given <code>String Array</code>.
	 * @param particles - new <code>String Array</code> of particles
	 */
	public void setParticles(String[] particles) {this.particles = particles;}
	
	/**
	 * Getter method returns the particles list.
	 * @return - particles list
	 */
	public String[] getParticles() {return particles;}
}