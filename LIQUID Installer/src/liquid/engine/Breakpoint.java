package liquid.engine;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * Class creates a breakpoint object, which is designed to represent a flow meter--to
 * display the X/Y forces, only spread across a specified area of the simulation.
 * @author William Steele
 */
public class Breakpoint {
	
	// variables needed in creating a breakpoint
	private World myWorld;
	private Vec2 myLoc;
	private float myWid;
	private float myHit;
	private int myID;

	/**
	 * Method creates a new breakpoint, monitoring the specified world in the specified area.
	 * @param imHere - world the breakpoint is monitoring in
	 * @param loc	 - center of the breakpoint area
	 * @param width	 - width of the breakpoint area
	 * @param height - height of theh breakpoint area
	 * @param ID	 - identifying number of the breakpoint
	 */
	public Breakpoint(World imHere, Vec2 loc, float width, float height, int ID) {
		myWorld = imHere;
		myLoc = loc;
		myWid = width;
		myHit = height;
		myID = ID;
	}
	
	/**
	 * Overrides the toString() method to represent the breakpoint and its X/Y velocities. 
	 * @return - average velocity of all particles within the (width*height) area, centered around <code>loc</code>
	 */
	public String toString() {
		DecimalFormat adj = new DecimalFormat();
		adj.setMaximumFractionDigits(4);
		Vec2 curVel = pollVelocity();
		String send = " B"+myID+" ( X-Vel "+adj.format(curVel.x)+", Y-Vel "+adj.format(curVel.y)+")";
		return send;
	}

	/**
	 * <b>Helper Method</b>
	 * <p>Calculates the average X/Y velocities of particles in the area (width*height), centered around <code>loc</code>.</p>
	 * @return Vec2 - the average X/Y velocities of nearby particles
	 */
	private Vec2 pollVelocity() {
		ArrayList<Vec2> vel = new ArrayList<Vec2>();
		Vec2 bounds = new Vec2((myWid/2),(myHit/2));
		Vec2[] pos = myWorld.getParticlePositionBuffer();
		if (pos != null) {
			for (int i = 0; i < pos.length; i++) {
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
	 * @param a		 - first Vec2 object
	 * @param b 	 - second Vec2 object
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