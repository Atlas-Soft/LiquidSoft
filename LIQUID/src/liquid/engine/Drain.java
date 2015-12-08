package liquid.engine;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.World;

/**
 * V 1.0
 * @author William Steele
 */
public class Drain {
	private World myWorld;
	private PolygonShape myShape;
	private Transform t;
	/**
	 * Constructs a new drain at the location given in <code>shape</code>
	 * @param theWorld The world that this drain will destroy particles from
	 * @param shape The shape and location of this drain
	 */
	public Drain(World theWorld, PolygonShape shape){
		myWorld = theWorld;
		myShape = shape;
		t = new Transform();
	}
	/**
	 * Checks for and destroys particles within <code>myShape</code>
	 * Should be called once every frame of the simulation 
	 */
	public void update(){
		myWorld.destroyParticlesInShape(myShape, t);
	}
	
}
