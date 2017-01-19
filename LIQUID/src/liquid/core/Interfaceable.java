package liquid.core;

/**
 * Interfaceable class allows an implemented simulation component to interact with other implemented
 * simulation components. The system, for example, gains the ability to know when a simulation should
 * begin, export simulation data onto a text document, and extract <code>ArrayList</code> information.
 * @author Rafael Zamora (Interfaceable Idea)
 */
public interface Interfaceable {
	
	/**
	 * Method will be defined/overridden in the implemented class. When called, the component specifies which other
	 * component to attempt an interaction with as well as what type of action to take. A corresponding call to the
	 * receive method (within the send method) includes a <code>String[]</code> information to complete interaction.
	 * @param i     - component to request an interaction with
	 * @param route - type of interaction to request
	 */
	public void send(Interfaceable i, GlobalVariables.Request route);
	
	/**
	 * Method will be defined/overridden in the implemented class. When
	 * called, the component proceeds accordingly to the specified request
	 * and meaningfully uses the <code>String[]</code> information provided.
	 * @param i     - component that requested interaction
	 * @param route - type of interaction requested
	 * @param info  - information to complete requested interaction
	 */
	public void receive(Interfaceable i, GlobalVariables.Request route, String[] info);
}