package liquid.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import liquid.core.LiquidApplication;
import liquid.core.GlobalVar.ObjectType;

/**
 * SimulationPanelObjects is a branch of the SimulationPanel class. This is where the various objects are rendered, continued from the
 * SimulationPanelRender class. All objects obtained from the current simulation are broken down & separated by their parameters/shape, with
 * <code>Rectangle2D</code> representing rectangular shapes and <code>Ellipse2D</code> representing circular shapes. Various SimulationPanel's
 * <code>ArrayLists</code> are used to correctly reflect object movements using a mouse, with indexes coloring the current object green.
 */
public class SimulationPanelObjects {
	private float x,y,l,w,r;     // variable parameters for an object
	private int BID = 1,FID = 1; // labeling breakpoints and flowmeters
	
	/**
	 * Method creates the objects of the SimulationPanel class. It sets the parameters/color of the selected object under different methods,
	 * then creates the objects according to its shape (<code>Rectangle2D</code> or <code>Ellipse2D</code>). A list of rectangular & circular
	 * objects and its indexes are stored for mouse-moving purposes, allowing system detection of objects & current object updates.
	 * @param g2d         - uses same <code>Graphics2D</code>
	 * @param environment - the rectangular simulation panel
	 */
	public void renderObjects(Graphics2D g2d, Rectangle2D.Float environment) {
		LiquidApplication.getGUI().sim.setRectList(); LiquidApplication.getGUI().sim.setRectIndexList();
		LiquidApplication.getGUI().sim.setCircList(); LiquidApplication.getGUI().sim.setCircIndexList();
        
        // obtains the information of all existing objects from the GUI application
        for (int i = 0; i < LiquidApplication.getGUI().variables.objects.size(); i++) {
        	String[] tokens = LiquidApplication.getGUI().variables.objects.get(i).split(" ");
        	setParameters(tokens,environment);
        	setCurrentObjectColor(g2d,i);
        	
        	// creates a rectangular object, based on tokens[0]
        	if (tokens[0].equals(ObjectType.Rectangular.toString())    ||
        			tokens[0].equals(ObjectType.Rect_Drain.toString()) ||
        			tokens[0].equals(ObjectType.Breakpoints.toString())) {
        		LiquidApplication.getGUI().sim.getRectList().add(setRectangle(selectObjectType(tokens[0]),g2d));
        		LiquidApplication.getGUI().sim.getRectIndexList().add(Integer.toString(i));} // adds array index for moving rectangular object purposes
            	
        	// creates a circular object, based on tokens[0]
        	else if (tokens[0].equals(ObjectType.Circular.toString()) ||
        			tokens[0].equals(ObjectType.Circ_Drain.toString())) {
        		LiquidApplication.getGUI().sim.getCircList().add(setCircle(selectObjectType(tokens[0]),g2d));
        		LiquidApplication.getGUI().sim.getCircIndexList().add(Integer.toString(i));} // adds array index for moving circular object purposes
        	
        	// creates a circular object, based on tokens[0], that can be
        	// placed partially out of the panel to capture the corners
        	else if (tokens[0].equals(ObjectType.Sources.toString()) ||
        			tokens[0].equals(ObjectType.Flowmeters.toString())) {
        		LiquidApplication.getGUI().sim.getCircList().add(setCircle(selectObjectType(tokens[0]),g2d));
        		LiquidApplication.getGUI().sim.getCircIndexList().add("*"+Integer.toString(i));} // adds array index with '*' for differing circle shape
        }
        BID = 1; FID = 1; // resets the breakpoint/flowmeter counter after each loop
	}
	
	/**
	 * Method sets the parameter values X, Y, Length, Width, and Rotation to the class variables.
	 * @param tokens      - parameter values X, Y, Length, Width, & Rotation, <b>starting at index 1</b>:
	 *  		      <br>- tokens[1] = X
	 * 			      <br>- tokens[2] = Y
	 * 			      <br>- tokens[3] = Length
	 * 			      <br>- tokens[4] = Width
	 * 	    	      <br>- tokens[5] = Rotation
	 * @param environment - the rectangular simulation panel
	 */
	public void setParameters(String[] tokens, Rectangle2D.Float environment) {
		// sets the parameters according to the object being read
		for (int i = 0; i < tokens.length; i++) {
			switch (i) {
			case 1: x = Float.parseFloat(tokens[1])+environment.x; break;
			case 2: y = Float.parseFloat(tokens[2])+environment.y; break;
			case 3: l = Float.parseFloat(tokens[3]); break;
			case 4: w = Float.parseFloat(tokens[4]); break;
			case 5: r = Float.parseFloat(tokens[5]); break;
			default: break;}}
	}
	
	/**
	 * Method sets the currently-selected object the color green during non-simulation times.
	 * @param g2d   - uses same <code>Graphics2D</code>
	 * @param index - object index value from <code>ArrayList</code>
	 */
	public void setCurrentObjectColor(Graphics2D g2d, int index) {
    	if (index == LiquidApplication.getGUI().variables.selectedObject && !LiquidApplication.getGUI().variables.simulating)
			g2d.setColor(Color.GREEN);
		else
			g2d.setColor(Color.BLACK);
	}
	
	/**
	 * Method compares the currently selected object to the ObjectType list in order to convert the String to an ObjectType. This allows the
	 * <code>setRectangle()</code> & <code>setCircle()</code> method to effectively utilize ObjectType as its <code>switch</code> case expression.
	 * @param object - currently selected object (<code>tokens[0]</code>)
	 * @return       - corresponding ObjectType for current object
	 */
	public ObjectType selectObjectType(String object) {
		for (ObjectType objects : ObjectType.values()) {
			if (objects.toString().equals(object)) return objects;}
		
		return null; // returns null if the String is not in the ObjectType list
	}
	
	/**
	 * Method creates a new, specified <code>Rectangle2D</code> object using associated X-/Y-Coordinates, Length, Width, and Rotation.
	 * @param object - obstacle, drain, or breakpoint
	 * @param g2d	 - uses same <code>Graphics2D</code>
	 * @return       - newly-created object for mouse-moving purposes
	 */
	public Rectangle2D.Float setRectangle(ObjectType object, Graphics2D g2d) {
		switch (object) {
		
		// draws a filled-in rectangular obstacle with rotational functionalities
		case Rectangular:
			g2d.rotate(Math.toRadians(r),(x+(l/2)),(y+(w/2))); // sets the rotation in the positive direction
			g2d.fill(new Rectangle2D.Float(x,y,l,w));
			g2d.rotate(Math.toRadians(-r),(x+(l/2)),(y+(w/2))); // sets the rotation in the negative direction
			return new Rectangle2D.Float(x,y,l,w);
			
		// draws a non filled-in rectangular drain
		case Rect_Drain:
			g2d.rotate(Math.toRadians(r),(x+(l/2)),(y+(w/2)));
			g2d.draw(new Rectangle2D.Float(x,y,l,w));
			g2d.rotate(Math.toRadians(-r),(x+(l/2)),(y+(w/2)));
			return new Rectangle2D.Float(x,y,l,w);
			
		// draws a rectangular breakpoint with a "B"/# to indicate # of breakpoints
		case Breakpoints:
    		g2d.draw(new Rectangle2D.Float(x,y,l,w));
    		g2d.drawString("B",((x+(l/2))-6),((y+(w/2))+5));
    		g2d.drawString(((BID++)+""),((x+(l/2))-1),((y+(w/2))+5));
    		return new Rectangle2D.Float(x,y,l,w);
		default:
			return null;}
	}
	
	/**
	 * Method creates a new, specified <code>Ellipse2D</code> object using associated X-/Y-Coordinates, Length, Width, and Rotation.
	 * @param object - obstacle, drain, source, or flowmeter
	 * @param g2d	 - uses same <code>Graphics2D</code>
	 * @return       - newly-created object for mouse-moving purposes
	 */
	public Ellipse2D.Float setCircle(ObjectType object, Graphics2D g2d) {
		switch (object) {
		
		// draws a filled-in circular obstacle
		case Circular:
			g2d.fill(new Ellipse2D.Float(x,y,l,w));
			return new Ellipse2D.Float(x,y,l,w);
			
		// draws a non filled-in circular drain
		case Circ_Drain:
			g2d.draw(new Ellipse2D.Float(x,y,l,w));
			return new Ellipse2D.Float(x,y,l,w);
			
		// draws a circular liquid source with an "S"/line, all dependent on X/Y forces
		case Sources:
			g2d.draw(new Ellipse2D.Float((x-10),(y-10),20,20));
			g2d.drawString("S",(x-3),(y+5));
			g2d.draw(new Line2D.Float(x,y,(x+l),(y+w)));
			return new Ellipse2D.Float((x-10),(y-10),30,30);
		
		// draws a circular flowmeter with an "F"/# to indicate # of flowmeters
		case Flowmeters:
    		g2d.draw(new Ellipse2D.Float((x-10),(y-10),20,20));
    		g2d.drawString("F",(x-6),(y+5));
    		g2d.drawString(((FID++)+""),(x-1),(y+5));
    		return new Ellipse2D.Float((x-10),(y-10),30,30);
		default:
			return null;}
	}
}