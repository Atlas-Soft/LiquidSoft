package liquid.engine;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * Class creates a flow meter object, which is designed to represent a tracker that shows flow speed and X/Y forces
 * at a particular specified location. This information is presented in the console section of the simulation.
 * @author William Steele - http://xkcd.com/1597/
 */
public class Flowmeter {
	
	// variables needed in creating a flow meter that will show runtime information
	private World myWorld;
	private Vec2 myLoc;
	private int myID;

	/**
	 * Method creates a new flow meter monitoring the specified world at the specified location.
	 * @param imHere - the world in which this flow meter resides
	 * @param loc	 - the location of the flow meter
	 * @param ID 	 - the identifying number of the flow meter
	 */
	public Flowmeter(World imHere, Vec2 loc, int ID) {
		myWorld = imHere;
		myLoc = loc;
		myID = ID;
	}
	
	/**
	 * Overrides the toString() method to represent the flow meter and its X/Y velocities. 
	 * @return - average velocity of all particles within a (20.0*20.0) area, centered around <code>loc</code>
	 */
	public String toString() {
		DecimalFormat adj = new DecimalFormat();
		adj.setMaximumFractionDigits(4);
		Vec2 curVel = pollVelocity();
		String send = " F"+myID+" ( X-Vel "+adj.format(curVel.x)+", Y-Vel "+adj.format(curVel.y)+")";
		return send;
	}

	/**
	 * <b>Helper Method</b>
	 * <p>Calculates the average X/Y velocities of the particles within 10.0 units of the flowmeter's position.</p>
	 * @return Vec2 - the average X/Y velocities of nearby particles
	 */
	private Vec2 pollVelocity() {
		ArrayList<Vec2> vel = new ArrayList<Vec2>();
		
		// to change boundaries to check, simply change parameters of constructor
		Vec2 bounds = new Vec2(10.0f,10.0f);
		Vec2[] pos = myWorld.getParticlePositionBuffer();
		if (pos != null) {
			for (int i = 0; i < pos.length; i++){
				if (almostEqual(myLoc, pos[i], bounds)) {
					// NOTE: may need to be changed to local variable, depending on performance
					vel.add(myWorld.getParticleVelocityBuffer()[i]);}
			}
		}

		// gathers velocities of all particles, then takes the average of them all
		float avgx = 0;
		float avgy = 0;
		if (vel.size() > 0) {
			for (int i = 0; i < vel.size(); i++) {
				avgx += vel.get(i).x;
				avgy += vel.get(i).y;
			}
			avgx = avgx/vel.size();
			avgy = avgy/vel.size();
		}
		return new Vec2(avgx, avgy);
	}

	/**
	 * Method determines if two Vec2 objects are within the bounds of one another.
	 * @param a 	 - first Vec2 object
	 * @param b		 - second Vec2 object
	 * @param bounds - object containing X/Y radius that b must be in relative to a
	 */
	public final static boolean almostEqual(Vec2 a, Vec2 b, Vec2 bounds) {
		boolean send = false;
		
		// checks if X-Coordinate of b is within X-Coordinate of a +/- X-Coordinate of bounds
		// as well as if Y-Coordinate of b is within Y-Coordinate of a +/- Y-Coordinate of bounds
		if (((a.x-bounds.x) <= b.x) && ((a.x+bounds.x) >= b.x) && ((a.y-bounds.y) <= b.y) && ((a.y+bounds.y) >= b.y))
				send = true;
		return send;
	}
}