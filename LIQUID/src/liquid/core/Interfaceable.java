package liquid.core;

/**
 * When implemented, Interfaceable allows a component of
 * the simulation to interact with other components of the
 * simulation that have also implemented this interface.
 * 
 * @author Rafael Zamora
 */
public interface Interfaceable {

	/**
	 * Method will be defined/overridden in the implemented class.
	 * The component should send encoded information into a String[]
	 * to be sent and received by the receiver by the receive method.
	 * 
	 * @param i    - determines which component to request an interaction with
	 * @param arg0 - determines what interaction to request
	 */
	public void send(Interfaceable i, int arg0);
	
	/**
	 * Method will be defined/overridden in the implemented class.
	 * The component should receive decoded information from String[]
	 * to be used by the receiving component in the interaction.
	 * 
	 * @param i    - determines which component is requesting an interaction
	 * @param arg0 - determines what interaction is requested
	 * @param args - information required to complete requested interaction
	 */
	public void receive(Interfaceable i, int arg0, String[] args);
}