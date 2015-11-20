/**
 * 
 */
package liquid.engine;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * @author Rafael Zamora
 *
 */
public class Flowmeter {
	private World myWorld;
	private Vec2 myLoc;
	
	public Flowmeter(World imHere, Vec2 loc){
		myWorld = imHere;
		myLoc = loc;
	}
	
	public Vec2 pollVelocity(){
		Vec2[] pos = myWorld.getParticlePositionBuffer();
		ArrayList<Vec2> vel = new ArrayList<Vec2>();
		for (int i = 0; i < pos.length; i++){
			//implement almostEqual() to get values from near flowmeter
		}
	}
	
	  /**
	   * Determines if two Vec2 objects are within the bounds of each other
	   * @param a the first Vec2 object
	   * @param b the second Vec2 object
	   * @param bounds Object containing x and y radius that b must be in relative to a
	   */
	  public final static boolean almostEqual(Vec2 a, Vec2 b, Vec2 bounds){
		  return false;
	  }
}
