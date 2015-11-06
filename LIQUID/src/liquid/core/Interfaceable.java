package liquid.core;

/**
 * When implemented, Interfaceable allows a component
 * to interact with other components that have implemented
 * this interface.
 * 
 * @author Rafael Zamora
 *
 * @version 1.0
 */
public interface Interfaceable {

	/**
	 * Method will be defined in the implemented class.
	 * Send should encode information into a String[] to be sent
	 * via the receive method.
	 * 
	 * @param i		determines which component to request an interaction with.
	 * @param arg0	determines what interaction to request.
	 */
	public void send(Interfaceable i, int arg0);
	
	/**
	 * Method will be defined in the implemented class.
	 * Receive should decode information from args to be used in the
	 * interaction.
	 * 
	 * @param i 	determines which component is requesting an interaction.
	 * @param arg0 	determines what interaction is requested.
	 * @param args 	information required to complete requested interaction.
	 */
	public void receive(Interfaceable i, int arg0, String[] args);
	
}
