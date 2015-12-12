package liquid.engine;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.World;

/**
 * Class creates a drain object, which is designed to represent a "pipe" or an
 * "exit"; in other words, the particle gets destroyed/removed from the simulation.
 * @author William Steele
 */
public class Drain {
	
	// creates variables associated with removing a particle from the simulation
	private World myWorld;
	private PolygonShape myShape;
	private Transform t;
	
	/**
	 * Method constructs a new drain object at the location given in <code>shape</code>.
	 * @param theWorld - the world this drain will destroy particles from
	 * @param shape    - the shape and location of this drain
	 */
	public Drain(World theWorld, PolygonShape shape) {
		myWorld = theWorld;
		myShape = shape;
		t = new Transform();
	}
	
	/**
	 * Method checks for and destroys particles within <code>myShape</code>. Updates should
	 * be made once every frame of the simulation to remain up-to-date with the destruction. 
	 */
	public void update() {
		myWorld.destroyParticlesInShape(myShape, t);
	}	
}