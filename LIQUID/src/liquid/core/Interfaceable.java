package liquid.core;

/**
 * When implemented, Interfaceable allows a component of
 * the simulation to interact with other components of the
 * simulation that have also implemented this interface.
 * 
 * @author Rafael Zamora
 */
public interface Interfaceable {
	
	// to make send/receive requests clearer with the use of enum and English words
	enum Request{	REQUEST_SET_LOG_PARAM, SET_LOG_PARAM,
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
					REQUEST_APPEND_LOG_DATA, APPEND_LOG_DATA
				};
	
	/**
	 * Method will be defined/overridden in the implemented class.
	 * The component should send encoded information into a String[] to be
	 * sent and received by another component through the receive method.
	 * 
	 * @param i    - determines which component to request an interaction with
	 * @param route - determines what interaction to request (see enum Request)
	 */
	public void send(Interfaceable i, Request route);
	
	/**
	 * Method will be defined/overridden in the implemented class.
	 * The component should receive decoded information from the sender
	 * in a String[] to be used by the receiving component in some way.
	 * 
	 * @param i    - determines which component is requesting an interaction
	 * @param route - determines what interaction is requested (see enum Request)
	 * @param args - information required to complete requested interaction
	 */
	public void receive(Interfaceable i, Request route, String[] args);
}