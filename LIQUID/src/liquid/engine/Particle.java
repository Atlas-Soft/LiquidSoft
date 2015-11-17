/**
 * 
 */
package liquid.engine;

import java.awt.geom.Point2D;

/**
 * @author Rafael Zamora
 *
 */
public class Particle {

	Point2D loc;
	Point2D vel;
	
	public Particle(Point2D p){
		loc = p;
		vel = new Point2D.Float(.1f,.51f);
	}
	
	public String getData(){
		return "P " + loc.getX() + " " + loc.getY() + " " + ((float)vel.distance(0, 0)+1.0);
	}
}
