package liquid.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import liquid.core.LiquidApplication;

/**
 * SimulationPanelRender class is a branch of SimulationPanel. Here, the size/color of both the environment & the individual particles are
 * set here. The previously-mentioned objects from the EditorPanel classes are initiated but defined in the SimulationPanelObjects class.
 */
public class SimulationPanelRender {
	private float x,y; // X-/Y-coordinates of the particles
	private SimulationPanelObjects simulationPanelObjects = new SimulationPanelObjects();
	
	/**
	 * Method calls more specific <code>render()</code> methods to setup items drawn in the simulation panel, including
	 * the environment itself, the details of a particle, and the various objects created from the EditorPanel classes.
	 * @param g2d         - same <code>Graphics2D</code>
	 * @param environment - same simulation panel
	 */
	public void render(Graphics2D g2d, Rectangle2D.Float environment) {
		renderEnvironment(g2d,environment);
        renderParticles(g2d,environment);
        
        // begins initialization process of object rendering
        simulationPanelObjects.renderObjects(g2d,environment);
	}
	
	/**
	 * Method creates the actual simulation panel, or environment, setting
	 * & redrawing the length/width based upon user parameter specifications. 
	 * @param g2d         - same <code>Graphics2D</code>
	 * @param environment - same simulation panel
	 */
	public void renderEnvironment(Graphics2D g2d, Rectangle2D.Float environment) {
		// X-/Y-Coordinates and Width/Height set dependent on application parameters
		environment.setRect(((500/2)-(LiquidApplication.getGUI().getFileVariables().getEnvironmentWidth()/2)),
							((400/2)-(LiquidApplication.getGUI().getFileVariables().getEnvironmentHeight()/2)),
							LiquidApplication.getGUI().getFileVariables().getEnvironmentWidth(),
							LiquidApplication.getGUI().getFileVariables().getEnvironmentHeight());
        g2d.setColor(Color.BLACK);
        g2d.draw(environment);
	}
	
	/**
	 * Method sets the color of the individual particles based on flow speed. Faster-moving particles
	 * portray a more reddish color, while slower-moving particles portray a more blueish color.
	 * @param g2d         - same <code>Graphics2D</code>
	 * @param environment - same simulation panel
	 */
	public void renderParticles(Graphics2D g2d, Rectangle2D.Float environment) {
		// loops through all present particles & sets a corresponding color
        if (LiquidApplication.getGUI().getFileVariables().getSimulating()) {
        	for (int i = 1; i < LiquidApplication.getGUI().getFileVariables().getParticles().length; i++) {
        		String[] tokens = LiquidApplication.getGUI().getFileVariables().getParticles()[i].split(" ");
        		
        		// begins in the top-left corner of the simulation panel, adds on the particle's X-/Y-coordinates
        		x = Float.parseFloat(tokens[1])+environment.x;
        		y = Float.parseFloat(tokens[2])+environment.y;
        		        		
        		// multiples velocity by a float in an attempt to simulate red = fast & blue = slow
        		g2d.setColor(new Color(1-(1/(Float.parseFloat(tokens[3])*1.3f)), 0.25f, (1/(Float.parseFloat(tokens[3])*1.3f))));
        		g2d.fill(new Ellipse2D.Float((x-2.5f),(y-2.5f),5,5));}	
        }
	}
}