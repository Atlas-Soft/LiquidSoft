package liquid.core;

/**
 * Class is a placeholder for all of the global variables of the project.
 */
public class GlobalVar {
	
	// global variable for the title of the project
	public static String title = " - LIQUID : 2D Fluid Simulator";
	
	// global variable to make send/receive requests clearer with the use of English words
	public enum Request{REQUEST_SET_LOG_PARAM, SET_LOG_PARAM,
						REQUEST_LOAD_LOG_PARAM, LOAD_LOG_PARAM,
						REQUEST_WRITE_LOG_PARAM, WRITE_LOG_PARAM,
						REQUEST_WRITE_LOG_DATA, WRITE_LOG_DATA,
						REQUEST_RUN_SIM, RUN_SIM,
						REQUEST_STEP_SIM, STEP_SIM,
						REQUEST_PAUSE_SIM, PAUSE_SIM,
						REQUEST_END_SIM, END_SIM,
						REQUEST_DISPLAY_SIM, DISPLAY_SIM, 
						REQUEST_PRINT_SIM, PRINT_SIM,
						REQUEST_SIM_HAS_ENDED, SIM_HAS_ENDED,
						REQUEST_APPEND_LOG_DATA, APPEND_LOG_DATA,
						REQUEST_INIT_WRITE_LOG, INIT_WRITE_LOG
						};
	
	// global variable to set up the drop-downs of the EnvironmentEditorPanel
	public enum EnviroOptions{Environment, Obstacles_And_Drains, Sources, Flowmeters, Breakpoint};
	
	// global variable to set up the drop-downs of the Obstacles and Drains section
	public enum ObsType{Rectangular, Circular, Rect_Drain, Circ_Drain};
}
