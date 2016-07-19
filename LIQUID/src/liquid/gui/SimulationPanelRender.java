package liquid.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import liquid.core.LiquidApplication;

/**
 * SimulationPanelRender is a branch of the SimulationPanel class. This is where the specific items of the simulation panel are defined
 * and created. The size/color of the environment are set here, the size/color of the individual particles are set here, and the various
 * objects--obstacles, drains, sources, flowmeters, and breakpoints--are actually initiated but defined in the SimulationPanelObjects class.
 */
public class SimulationPanelRender {
	private float x,y; // setting the X-/Y-coordinates of the particles
	
	/**
	 * Method calls various other <code>render</code> methods to setup the items in the SimulationPanel class. This includes the
	 * environment itself, the details of a particle, and various objects--obstacles, drains, sources, flowmeters, and breakpoints.
	 * @param g2d         - uses same <code>Graphics2D</code>
	 * @param environment - the rectangular simulation panel
	 */
	public void render(Graphics2D g2d, Rectangle2D.Float environment) {
		renderEnvironment(g2d,environment);
        renderParticles(g2d,environment);
        
        SimulationPanelObjects simPanelObjects = new SimulationPanelObjects();
        simPanelObjects.renderObjects(g2d,environment);
	}
	
	/**
	 * Method creates the actual environment of the SimulationPanel class, setting and redrawing
	 * the length/width based upon the user specifications from the liquid application parameters. 
	 * @param g2d         - uses same <code>Graphics2D</code>
	 * @param environment - the rectangular simulation panel
	 */
	public void renderEnvironment(Graphics2D g2d, Rectangle2D.Float environment) {
		// creates the actual rectangular simulation panel
		environment.setRect(((500/2)-(LiquidApplication.getGUI().variables.enviroLength/2)),
							((400/2)-(LiquidApplication.getGUI().variables.enviroWidth/2)),
							LiquidApplication.getGUI().variables.enviroLength,
							LiquidApplication.getGUI().variables.enviroWidth);
        g2d.setColor(Color.BLACK);
        g2d.draw(environment);
	}
	
	/**
	 * Method is used to set the color of the individual particles based on the flow speed. Faster-moving
	 * particles portray a more reddish color, while slower-moving particles portray a more blueish color.
	 * @param g2d         - uses same <code>Graphics2D</code>
	 * @param environment - the rectangular simulation panel
	 */
	public void renderParticles(Graphics2D g2d, Rectangle2D.Float environment) {
		// sets the color of the particles depending on the flow speed
        if (LiquidApplication.getGUI().variables.simulating) {
        	for (int i = 1; i < LiquidApplication.getGUI().variables.particles.length; i++) {
        		String[] tokens = LiquidApplication.getGUI().variables.particles[i].split(" ");
        		// begins in the top-left corner of the simulation panel, adds on the particle's X-/Y-coordinates
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		
        		// multiples velocity by a float (currently 1.3f) in an attempt to simulate red = fast & blue = slow
        		g2d.setColor(new Color(1-(1/(Float.parseFloat(tokens[3])*1.3f)),0.25f,(1/(Float.parseFloat(tokens[3])*1.3f))));
        		g2d.fill(new Ellipse2D.Float((x-2.5f),(y-2.5f),5,5));}}
	}
}