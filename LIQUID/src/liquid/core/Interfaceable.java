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
	enum Request{REQUEST_SET_LOG_PARAM, SET_LOG_PARAM,
		REQUEST_LOAD_LOG, REQUEST_WRITE_LOG, LOAD_LOG, WRITE_LOG,
		REQUEST_RUN_SIM, REQUEST_PAUSE_SIM, REQUEST_END_SIM, RUN_SIM, PAUSE_SIM, END_SIM,
		REQUEST_DISPLAY_SIM, REQUEST_PRINT_SIM, DISPLAY_SIM, PRINT_SIM};
	
	/**
	 * Method will be defined/overridden in the implemented class.
	 * The component should send encoded information into a String[]
	 * to be sent and received by another component through the receive method.
	 * 
	 * @param i    - determines which component to request an interaction with
	 * @param route - determines what interaction to request (see class' enum)
	 */
	public void send(Interfaceable i, Request route);
	
	/**
	 * Method will be defined/overridden in the implemented class.
	 * The component should receive decoded information from the sender
	 * in a String[] to be used by the receiving component in some way.
	 * 
	 * @param i    - determines which component is requesting an interaction
	 * @param route - determines what interaction is requested (see class' enum)
	 * @param args - information required to complete requested interaction
	 */
	public void receive(Interfaceable i, Request route, String[] args);
}