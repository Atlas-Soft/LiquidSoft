package liquid.gui;

import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import liquid.core.LiquidApplication;

/**
 * SimulationPanelMouse is a branch of the SimulationPanel class. Various mouse functionality details are located here, including the mouse pressing,
 * dragging, & releasing an object. A <code>for</code> loop search is initiated for each mouse click within the panel, searching for an object with
 * matching X-/Y-Coordinates. When dragging, the parameter drop-downs are constantly updated until release, where the final location info is recorded.
 */
public class SimulationPanelMouse {
	boolean pressed = false;        // determines when object is pressed
	int index = 0,currentIndex = 0; // locates currently-selected object within system
	float prevX,prevY;              // previous coordinates allow accurate mouse movements
	
	/**
	 * Method calls a check to see if the <b>clicked</b> location contains an object or not.
	 * @param environment   - the rectangular simulation panel
	 * @param rectList      - list of rectangular objects
	 * @param rectIndexList - list of the rectangular objects' indexes
	 * @param circList      - list of circular objects
	 * @param circIndexList - list of the circular objects' indexes
	 */
	public boolean mouseClicked(MouseEvent mouse, Rectangle2D.Float environment, ArrayList<Rectangle2D> rectList, ArrayList<String> rectIndexList,
			ArrayList<Ellipse2D> circList, ArrayList<String> circIndexList) {
		if (objectIsPresent(mouse,environment,rectList,rectIndexList,circList,circIndexList)) {
			LiquidApplication.getGUI().enviroeditor.setSelectedObject();
			return true;}
		return false;
	}
	
	/**
	 * Method updates the location of the selected object in the <code>ArrayList</code> of objects.
	 * @return - whether to repaint simulation panel or not
	 */
	public boolean mouseDragged(MouseEvent mouse) {
		if (pressed) {
			updateObjectLocation(mouse);
			return true;}
		return false;
	}
	
	/**
	 * Method calls a check to see if the <b>pressed</b> location contains an object or not.
	 * @param environment   - the rectangular simulation panel
	 * @param rectList      - list of rectangular objects
	 * @param rectIndexList - list of the rectangular objects' indexes
	 * @param circList      - list of circular objects
	 * @param circIndexList - list of the circular objects' indexes
	 */
	public void mousePressed(MouseEvent mouse, Rectangle2D.Float environment, ArrayList<Rectangle2D> rectList, ArrayList<String> rectIndexList,
			ArrayList<Ellipse2D> circList, ArrayList<String> circIndexList) {
		if (objectIsPresent(mouse,environment,rectList,rectIndexList,circList,circIndexList)){
			LiquidApplication.getGUI().enviroeditor.setSelectedObject();
			pressed = true;}
	}
	
	/**
	 * Method sets the local variable <code>pressed</code> false when the mouse releases an object, preventing further
	 * dragging functionalities. The X-/Y-Coordinates of that object are also reflected in the EnvironmentEditorPanel.
	 */
	public void mouseReleased(MouseEvent mouse) {
		LiquidApplication.getGUI().enviroeditor.setSelectedObject();
		pressed = false;
	}
	
	/**
	 * Method searches through the <code>ArrayLists</code> of <code>Rectangle2D</code> and <code>Ellipse2D</code> objects to see if a given
	 * object contains the mouse's clicked X-/Y-Coordinates. If so, the user may begin dragging that object around in the simulation panel.
	 * @param environment   - the rectangular simulation panel
	 * @param rectList      - list of rectangular objects
	 * @param rectIndexList - list of the rectangular objects' indexes
	 * @param circList      - list of circular objects
	 * @param circIndexList - list of the circular objects' indexes
	 * @return              - whether a object is present in the mouse's clicked X-/Y-Coordinates or not
	 */
	public boolean objectIsPresent(MouseEvent mouse, Rectangle2D.Float environment, ArrayList<Rectangle2D> rectList, ArrayList<String> rectIndexList,
			ArrayList<Ellipse2D> circList, ArrayList<String> circIndexList) {
		// checks if the mouse is clicking in the simulation panel during non-simulation times
		if (environment.contains(mouse.getPoint()) && !LiquidApplication.getGUI().variables.simulating) {
					
			index = 0; // checks if a rectangular object contains the mouse's clicked X-/Y-Coordinates
			for (Rectangle2D rect : rectList) {
				if (rect.contains(mouse.getPoint())) {
					// sets the X-/Y-Coordinates accordingly as local variables to allow accurate dragging of objects at any point
					prevX = (float)(rect.getX()-mouse.getX()-environment.x);
					prevY = (float)(rect.getY()-mouse.getY()-environment.y);
					currentIndex = Integer.parseInt(rectIndexList.get(index));
					updateObjectLocation(mouse);
					return true;}
				else
					index++;
			}
				
			index = 0; // checks if a circular object contains the mouse's clicked X-/Y-Coordinates
			for (Ellipse2D circ : circList) {
				if (circ.contains(mouse.getPoint())) {
					// differentiates sources/flowmeters, which can go halfway out of the environment to capture the corners/sides
					if (circIndexList.get(index).startsWith("*")) {
						prevX = (float)(circ.getX()-mouse.getX()-environment.x+10);
						prevY = (float)(circ.getY()-mouse.getY()-environment.y+10);
						currentIndex = Integer.parseInt(circIndexList.get(index).substring(1,circIndexList.get(index).length()));}
					else {
						prevX = (float)(circ.getX()-mouse.getX()-environment.x);
						prevY = (float)(circ.getY()-mouse.getY()-environment.y);
						currentIndex = Integer.parseInt(circIndexList.get(index));}
					updateObjectLocation(mouse);
					return true;}
				else
					index++;
			}
		}
		return false;
	}
	
	/**
	 * Method updates the location of the mouse-selected object, showing the X-/Y-Coordinates prior/after dragging an object.
	 * These coordinates change based on the mouse's X-/Y-Coordinates, with the other components of an object remaining untouched.
	 */
	public void updateObjectLocation(MouseEvent mouse) {
		LiquidApplication.getGUI().variables.selectedObject = currentIndex; // sets current index as selected object
		String[] tokens = LiquidApplication.getGUI().variables.objects.get(currentIndex).split(" ");
		
		// adds the new X-/Y-Coordinates to the list of parameters & sets them to the selected object
		String newInfo = tokens[0]+" "+(prevX+mouse.getX())+" "+(prevY+mouse.getY())+" ";
		for (int i = 3; i < tokens.length; i++) newInfo += tokens[i]+" ";
		LiquidApplication.getGUI().variables.objects.set(currentIndex,newInfo);
	}
}