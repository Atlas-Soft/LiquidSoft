package liquid.gui;

import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import liquid.core.GlobalVariables;
import liquid.core.LiquidApplication;

/**
 * SimulationPanelMouse class is a branch of SimulationPanel. Here, various mouse functionalities are defined/detailed, including the mouse dragging,
 * pressing, & releasing an object. The <code>ArrayLists</code> defined in other SimulationPanel-type classes are utilized for dragging purposes.
 */
public class SimulationPanelMouse {
	boolean pressedObject = false; // allows/disallows dragging capabilities
	float initialX,initialY;       // enhances more accurate mouse movements
	int index = 0;                 // locates currently-selected object index
	
	/**
	 * Method updates the object's coordinates within the <code>ArrayList</code> upon being dragged.
	 * @param environment - same simulation panel
	 * @return            - whether or not mouse successfully dragged
	 */
	public boolean mouseDragged(MouseEvent mouse, Rectangle2D.Float environment) {
		if (pressedObject) {
			updateCoordinates(mouse,environment);
			return true;}
		return false;
	}
	
	/**
	 * Method calls a check to see if the <b>pressed</b> location contains an object or not.
	 * @param environment        - same simulation panel
	 * @param rectangleList      - rectangular objects list
	 * @param rectangleIndexList - rectangular objects' indexes list
	 * @param circleList         - circular objects list
	 * @param circleIndexList    - circular objects' indexes list
	 * @return                   - whether or not mouse successfully pressed
	 */
	public boolean mousePressed(MouseEvent mouse, Rectangle2D.Float environment,
			ArrayList<Rectangle2D> rectangleList, ArrayList<String> rectangleIndexList,
			ArrayList<Ellipse2D> circleList, ArrayList<String> circleIndexList) {
		
		// per mouse press, changes selected object & allows dragging capabilities
		if (objectIsPresent(mouse,environment,rectangleList,rectangleIndexList,circleList,circleIndexList)) {
			LiquidApplication.getGUI().getEditorPanel().setSelectedObjectPanel();
			pressedObject = true;
			return true;}
		return false;
	}
	
	/**
	 * Method creates a new save state upon releasing an object. Setting the local
	 * <code>boolean</code> variable false prevents further dragging capabilities.
	 */
	public void mouseReleased(MouseEvent mouse) {
		if (pressedObject) {
			LiquidApplication.getGUI().getEditorPanel().setSelectedObjectPanel();
			LiquidApplication.getGUI().getApplicationState().saveState();
			pressedObject = false;}
	}
	
	/**
	 * Method searches through the <code>ArrayLists</code> of <code>Rectangle2D</code> and <code>Ellipse2D</code> objects to see if a given
	 * object contains the mouse's clicked X-/Y-Coordinates. If so, the user may begin dragging that object around in the simulation panel.
	 * @param environment        - same simulation panel
	 * @param rectangleList      - rectangular objects list
	 * @param rectangleIndexList - rectangular objects' indexes list
	 * @param circleList         - circular objects list
	 * @param circleIndexList    - circular objects' indexes list
	 * @return                   - whether or not the mouse's X-/Y-Coordinates contains an object
	 */
	public boolean objectIsPresent(MouseEvent mouse, Rectangle2D.Float environment,
			ArrayList<Rectangle2D> rectangleList, ArrayList<String> rectangleIndexList,
			ArrayList<Ellipse2D> circleList, ArrayList<String> circleIndexList) {
		
		// only allows mouse clicking during non-simulation times
		if (environment.contains(mouse.getPoint()) && !LiquidApplication.getGUI().getFileVariables().getSimulating()) {
					
			index = 0; // used to obtain rectangular object index from overall objects list
			for (Rectangle2D rectangle : rectangleList) {
				if (rectangle.contains(mouse.getPoint())) {
					// incorporating the shape, mouse, & environment coordinates allow the
					// user to press anywhere on a rectangular shape and drag it accordingly
					initialX = (float)(rectangle.getX()-mouse.getX()-environment.x);
					initialY = (float)(rectangle.getY()-mouse.getY()-environment.y);
					
					LiquidApplication.getGUI().getFileVariables().setSelectedObject(Integer.parseInt(rectangleIndexList.get(index)));
					return true;}
				else
					index++;}
				
			index = 0; // used to obtain circular object index from overall objects list
			for (Ellipse2D circle : circleList) {
				if (circle.contains(mouse.getPoint())) {
					// incorporating the shape, mouse, & environment coordinates allow the
					// user to press anywhere on a circular shape and drag it accordingly
					initialX = (float)(circle.getX()-mouse.getX()-environment.x);
					initialY = (float)(circle.getY()-mouse.getY()-environment.y);
					
					LiquidApplication.getGUI().getFileVariables().setSelectedObject(Integer.parseInt(circleIndexList.get(index)));
					return true;}
				else
					index++;}
		}
		return false;
	}
	
	/**
	 * Method updates the location of the mouse-selected object, showing the X-/Y-Coordinates prior/after dragging an object.
	 * @param environment - same simulation panel
	 */
	public void updateCoordinates(MouseEvent mouse, Rectangle2D.Float environment) {
		boolean validDragging = false; // limits the user when dragging an object
		String[] tokens = LiquidApplication.getGUI().getFileVariables().getObjects().get(
				LiquidApplication.getGUI().getFileVariables().getSelectedObject()).split(" ");
		
		// allows objects to go slightly out of the environment, without consequence
    	if ((tokens[0].equals(GlobalVariables.ObjectType.Rectangle.toString())          ||
    			tokens[0].equals(GlobalVariables.ObjectType.Circle.toString())          ||
    			tokens[0].equals(GlobalVariables.ObjectType.Rectangle_Drain.toString()) ||
    			tokens[0].equals(GlobalVariables.ObjectType.Circle_Drain.toString()))   &&
    			((initialX+mouse.getX()) > (9-Float.parseFloat(tokens[3])))             &&
    			((initialY+mouse.getY()) > (9-Float.parseFloat(tokens[4])))             &&
    			((initialX+mouse.getX()) < (environment.width-9))                       &&
				((initialY+mouse.getY()) < (environment.height-9)))
    		validDragging = true;
		
    	// ensures the objects remain inside the environment to avoid any consequences
    	if ((tokens[0].equals(GlobalVariables.ObjectType.Flowmeters.toString())               ||
    			tokens[0].equals(GlobalVariables.ObjectType.Breakpoints.toString()))          &&
    			((initialX+mouse.getX()) > -1)                                                &&
    			((initialY+mouse.getY()) > -1)                                                &&
    			((initialX+mouse.getX()) < (environment.width-Float.parseFloat(tokens[3])+1)) &&
    			((initialY+mouse.getY()) < (environment.height-Float.parseFloat(tokens[4])+1)))
    		validDragging = true;
    	
    	// ensures the source's X-/Y-Forces also remain inside the environment
    	if (tokens[0].equals(GlobalVariables.ObjectType.Sources.toString())                   &&
    			((initialX+mouse.getX()) > -1)                                                &&
    			((initialY+mouse.getY()) > -1)                                                &&
    			((initialX+mouse.getX() < (environment.width-Float.parseFloat(tokens[3])-9))) &&
    			((initialY+mouse.getY() < (environment.height-Float.parseFloat(tokens[4])-9))))
    		validDragging = true;
    	
    	// when requirements are met for a certain object, new X-/Y-Coordinates get added to the
    	// object's original list of parameters, with the other parameters remaining constant
		if (validDragging) {
			String newInfo = tokens[0]+" "+(initialX+mouse.getX())+" "+(initialY+mouse.getY())+" ";
			for (int i = 3; i < tokens.length; i++)
				newInfo += tokens[i]+" ";
			
			LiquidApplication.getGUI().getFileVariables().getObjects().set(
					LiquidApplication.getGUI().getFileVariables().getSelectedObject(),newInfo);
			validDragging = false;}
	}
}