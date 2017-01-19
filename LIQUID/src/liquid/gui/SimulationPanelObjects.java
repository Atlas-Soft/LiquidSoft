package liquid.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import liquid.core.GlobalVariables;
import liquid.core.LiquidApplication;

/**
 * SimulationPanelObjects class is a branch of SimulationPanel. Here, the various objects are now rendered, continuing
 * from the SimulationPanelRender class. All objects from the current simulation are separated accordingly through various
 * <code>ArrayLists</code>: one for rectangular shapes, one for circular shapes, and one for each respective indexes list.
 */
public class SimulationPanelObjects {
	private float x,y,w,h,r;     // object parameters, detailed within a method
	private int FID = 1,BID = 1; // flowmeter/breakpoint labeling
	
	/**
	 * Method creates/adds objects into specific <code>ArrayLists</code>, separated by type of object and its shape. Object
	 * attribute specifications, such as coordinates/rotation, via other methods allow for various mouse-moving capabilities.
	 * @param g2d         - same <code>Graphics2D</code>
	 * @param environment - same simulation panel
	 */
	public void renderObjects(Graphics2D g2d, Rectangle2D.Float environment) {
		LiquidApplication.getGUI().getSimulationPanel().clearAllLists();
        
        // obtains the information of all existing objects
        for (int i = 0; i < LiquidApplication.getGUI().getFileVariables().getObjects().size(); i++) {
        	String[] tokens = LiquidApplication.getGUI().getFileVariables().getObjects().get(i).split(" ");
        	setParameters(environment,tokens);
        	setColor(g2d,i);
        	
        	// creates a rectangular object, based on tokens[0], with indexes for mouse moving-purposes
        	if (tokens[0].equals(GlobalVariables.ObjectType.Rectangle.toString())           ||
        			tokens[0].equals(GlobalVariables.ObjectType.Rectangle_Drain.toString()) ||
        			tokens[0].equals(GlobalVariables.ObjectType.Breakpoints.toString())) {
        		LiquidApplication.getGUI().getSimulationPanel().getRectangleList().add(setRectangle(g2d,selectObjectType(tokens[0])));
        		LiquidApplication.getGUI().getSimulationPanel().getRectangleIndexList().add(Integer.toString(i));}
            	
        	// creates a circular object, based on tokens[0], with indexes for mouse-moving purposes
        	else if (tokens[0].equals(GlobalVariables.ObjectType.Circle.toString())      ||
        			tokens[0].equals(GlobalVariables.ObjectType.Circle_Drain.toString()) ||
        			tokens[0].equals(GlobalVariables.ObjectType.Sources.toString())      ||
        			tokens[0].equals(GlobalVariables.ObjectType.Flowmeters.toString())) {
        		LiquidApplication.getGUI().getSimulationPanel().getCircleList().add(setCircle(g2d,selectObjectType(tokens[0])));
        		LiquidApplication.getGUI().getSimulationPanel().getCircleIndexList().add(Integer.toString(i));}        	
        }
        FID = 1; BID = 1; // resets the flowmeter/breakpoint counter after each loop
	}
	
	/**
	 * Method sets the parameter values X-/Y-Coordinates, Width/Height, and Rotation to the class variables.
	 * @param environment - same simulation panel
	 * @param tokens      - <b>starting at index 1</b>, we have:
	 *  		      <br>- tokens[1] = X
	 * 			      <br>- tokens[2] = Y
	 * 			      <br>- tokens[3] = Width
	 * 			      <br>- tokens[4] = Height
	 * 	    	      <br>- tokens[5] = Rotation
	 */
	public void setParameters(Rectangle2D.Float environment, String[] tokens) {
		// when present, class variables are updated for current object
		for (int i = 0; i < tokens.length; i++) {
			switch (i) {
			case 1: x = Float.parseFloat(tokens[1])+environment.x; break;
			case 2: y = Float.parseFloat(tokens[2])+environment.y; break;
			case 3: w = Float.parseFloat(tokens[3]); break;
			case 4: h = Float.parseFloat(tokens[4]); break;
			case 5: r = Float.parseFloat(tokens[5]); break;
			default: break;}}
	}
	
	/**
	 * Method sets the currently-selected object the color green during non-simulation times.
	 * @param g2d   - same <code>Graphics2D</code>
	 * @param index - object index value from <code>ArrayList</code>
	 */
	public void setColor(Graphics2D g2d, int index) {
    	if (index == LiquidApplication.getGUI().getFileVariables().getSelectedObject() &&
    			!LiquidApplication.getGUI().getFileVariables().getSimulating())
			g2d.setColor(Color.GREEN);
		else
			g2d.setColor(Color.BLACK);
	}
	
	/**
	 * Method creates a newly specified <code>Rectangle2D</code> object using the given X-/Y-Coordinates, Width, Height, & Rotation.
	 * @param g2d	 - same <code>Graphics2D</code>
	 * @param object - obstacle, drain, or breakpoint
	 * @return       - newly-created object for mouse-moving purposes
	 */
	public Rectangle2D.Float setRectangle(Graphics2D g2d, GlobalVariables.ObjectType object) {
		switch (object) {
		
		// draws a filled in rectangular obstacle with rotational functionalities
		case Rectangle:
			g2d.rotate(Math.toRadians(r),(x+(w/2)),(y+(h/2))); // sets the rotation in the positive direction
			g2d.fill(new Rectangle2D.Float(x,y,w,h));
			g2d.rotate(Math.toRadians(-r),(x+(w/2)),(y+(h/2))); // sets the rotation in the negative direction
			return new Rectangle2D.Float(x,y,w,h);
			
		// draws a non-filled in rectangular drain
		case Rectangle_Drain:
			g2d.rotate(Math.toRadians(r),(x+(w/2)),(y+(h/2)));
			g2d.draw(new Rectangle2D.Float(x,y,w,h));
			g2d.rotate(Math.toRadians(-r),(x+(w/2)),(y+(h/2)));
			return new Rectangle2D.Float(x,y,w,h);
			
		// draws a rectangular breakpoint with a "B"/# to indicate # of breakpoints
		case Breakpoints:
    		g2d.draw(new Rectangle2D.Float(x,y,w,h));
    		g2d.drawString("B",((x+(w/2))-6),((y+(h/2))+5));
    		g2d.drawString(((BID++)+""),((x+(w/2))-1),((y+(h/2))+5));
    		return new Rectangle2D.Float(x,y,w,h);
		default:
			return null;}
	}
	
	/**
	 * Method creates a newly specified <code>Ellipse2D</code> object using the given X-/Y-Coordinates,
	 * Width, Height, & Rotation. For source objects, Width/Height are the X-/Y-Forces, respectively.
	 * @param g2d	 - same <code>Graphics2D</code>
	 * @param object - obstacle, drain, source, or flowmeter
	 * @return       - newly-created object for mouse-moving purposes
	 */
	public Ellipse2D.Float setCircle(Graphics2D g2d, GlobalVariables.ObjectType object) {
		switch (object) {
		
		// draws a filled in circular obstacle
		case Circle:
			g2d.fill(new Ellipse2D.Float(x,y,w,h));
			return new Ellipse2D.Float(x,y,w,h);
			
		// draws a non-filled in circular drain
		case Circle_Drain:
			g2d.draw(new Ellipse2D.Float(x,y,w,h));
			return new Ellipse2D.Float(x,y,w,h);
			
		// draws a circular liquid source with an "S"/line, all dependent on X/Y forces
		case Sources:			
			g2d.draw(new Ellipse2D.Float(x,y,20,20));
			g2d.drawString("S",(x+7),(y+15));
			g2d.draw(new Line2D.Float((x+10),(y+10),(x+w+10),(y+h+10)));
			return new Ellipse2D.Float(x,y,20,20);
		
		// draws a circular flowmeter with an "F"/# to indicate # of flowmeters
		case Flowmeters:
    		g2d.draw(new Ellipse2D.Float(x,y,w,h));
    		g2d.drawString("F",(x+4),(y+15));
    		g2d.drawString(((FID++)+""),(x+9),(y+15));
    		return new Ellipse2D.Float(x,y,w,h);
		default:
			return null;}
	}
	
	/**
	 * Method compares the currently-selected object to the ObjectType list via a <code>for</code> loop. This allows the
	 * <code>setRectangle()/setCircle()</code> methods to utilize ObjectType as its <code>switch</code> case expression.
	 * @param object - currently selected object (<code>tokens[0]</code>)
	 * @return       - corresponding ObjectType for current object
	 */
	public GlobalVariables.ObjectType selectObjectType(String object) {
		for (GlobalVariables.ObjectType type : GlobalVariables.ObjectType.values()) {
			if (type.toString().equals(object))
				return type;}
		return null; // returns null if the String is not in the ObjectType list
	}
}