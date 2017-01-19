package liquid.core;

import java.awt.Font;

/**
 * Class is a placeholder for all of the global variables of the project.
 */
public class GlobalVariables {
	
	// global variable for the title of the project
	public static String title = " - LIQUID : 2D Fluid Simulator";
	public static Font font = new Font("Verdana", Font.BOLD, 12);
	
	// global variable to make send/receive requests clearer with the use of English words
	public enum Request{// between log/gui
						REQUEST_LOAD_CONFIG_FILE, LOAD_CONFIG_FILE, // adjust naming, LOAD_CONFIG
						REQUEST_SET_CONFIG,       SET_CONFIG,
						REQUEST_LOAD_LOG_PARAM,   LOAD_LOG_PARAM, // LOAD_LOG be okay??
						REQUEST_SET_LOG_PARAM,    SET_LOG_PARAM, // SET_LOG be okay??
						REQUEST_SAVE_FILE,		  SAVE_FILE,
						REQUEST_LOAD_FILE,		  LOAD_FILE,
						REQUEST_SET_SAVE_FILE,	  SET_SAVE_FILE,
						REQUEST_SET_LOAD_FILE,	  SET_LOAD_FILE,
						REQUEST_INIT_WRITE_LOG,   INIT_WRITE_LOG, // check reason need this, remember problems without...
						REQUEST_WRITE_LOG_PARAM,  WRITE_LOG_PARAM, // WRITE_LOG be okay??
						
						// between engine/log
						//REQUEST_APPEND_LOG_DATA,  APPEND_LOG_DATA, // nothing calling??
						REQUEST_WRITE_LOG_DATA,   WRITE_LOG_DATA,
						
						// between gui/engine
						REQUEST_RUN_SIM,          RUN_SIM,
						REQUEST_PAUSE_SIM,        PAUSE_SIM,
						REQUEST_STEP_SIM,         STEP_SIM,
						REQUEST_END_SIM,          END_SIM,
						REQUEST_DISPLAY_SIM,      DISPLAY_SIM, 
						REQUEST_PRINT_SIM,        PRINT_SIM,
						REQUEST_SIM_HAS_ENDED,    SIM_HAS_ENDED // adjust naming, SIM_TIME_ENDED
						};
	
	public enum FileAction{Load,Save};
						
	public enum FileMenuItems{New,Load,Save,Save_As,Exit};
	public enum EditMenuItems{Undo,Redo};
	public enum HelpMenuItems{About};
	
	// global variable to set up the drop-downs of the EnvironmentEditorPanel
	public enum EnviroOptions{Environment, Obstacles, Drains, Sources, Flowmeters, Breakpoints};
	
	// global variable to set up the drop-downs of the Obstacles and Drains section
	public enum ObjectType{Rectangle, Circle, Rectangle_Drain, Circle_Drain, Sources, Flowmeters, Breakpoints};
	
	public enum LoggerItems{filename,liquid,temperature,viscosity,runtime,environmentWidth,environmentHeight,selectedObject,objects};
}
