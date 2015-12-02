package liquid.engine;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Drain {
	private World myWorld;
	private PolygonShape myShape;
	
	public Drain(World theWorld, PolygonShape shape){
		myWorld = theWorld;
		myShape = shape;
	}
	
	public void update(){
		myWorld.destroyParticlesInShape(myShape, null);
	}
	
}
