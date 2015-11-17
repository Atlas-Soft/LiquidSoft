/**
 * 
 */
package liquid.engine;

import java.awt.Shape;
import java.awt.geom.Area;

/**
 * @author Rafael Zamora
 *
 */
public class Obstacle {

	Area bounds;
	
	public Obstacle(Shape s){
		bounds = new Area(s);
	}
	
}
