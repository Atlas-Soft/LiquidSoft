/**
 * 
 */
package liquid.engine;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * V 1.0
 * @author William Steele
 */
public class Breakpoint {
	private World myWorld;
	private Vec2 myLoc;
	private float myWid;
	private float myHit;
	private int myID;

	/**
	 * Creates a new breakpoint monitoring the specified world in the specified area
	 * @param imHere the world this breakpoint is monitoring
	 * @param loc the center of the area this breakpoint monitors
	 * @param width the width of the area this breakpoint monitors
	 * @param ID The identifying number for this breakpoint
	 */
	public Breakpoint(World imHere, Vec2 loc, float width, float height, int ID){
		myWorld = imHere;
		myLoc = loc;
		myWid = width;
		myHit = height;
		myID = ID;
	}
	/**
	 * @return the average velocity of all particles within an area of width * height centered around loc
	 */
	public String toString(){
		DecimalFormat adj = new DecimalFormat();
		Vec2 curVel = pollVelocity();
		adj.setMaximumFractionDigits(4);
		String send = " B" + myID + " ( X-Vel " + adj.format(curVel.x) + ", Y-Vel " + adj.format(curVel.y) +")";
		return send;
	}

	/**
	 * Helper Method
	 * Gets the average x and y velocities of particles within an area of width * height centered around loc
	 * @return Vec2 object containing the average x and y velocities of nearby particles
	 */
	private Vec2 pollVelocity(){
		ArrayList<Vec2> vel = new ArrayList<Vec2>();
		Vec2[] pos = myWorld.getParticlePositionBuffer();
		Vec2 bounds = new Vec2(myWid/2, myHit/2);
		if (pos != null){
			for (int i = 0; i < pos.length; i++){
				if(almostEqual(myLoc, pos[i], bounds)){
					vel.add(myWorld.getParticleVelocityBuffer()[i]);	//may need to be changed to local variable depending on performance
				}
			}
		}

		float avgx = 0;
		float avgy = 0;
		if (vel.size() > 0){
			for (int i = 0; i < vel.size(); i++){
				avgx += vel.get(i).x;
				avgy += vel.get(i).y;
			}
			avgx = avgx/vel.size();
			avgy = avgy/vel.size();
		}

		return new Vec2(avgx, avgy);
	}

	/**
	 * Determines if two Vec2 objects are within the bounds of each other
	 * @param a the first Vec2 object
	 * @param b the second Vec2 object
	 * @param bounds Object containing x and y radius that b must be in relative to a
	 */
	public final static boolean almostEqual(Vec2 a, Vec2 b, Vec2 bounds){
		boolean send = false;
		if(a.x - bounds.x <= b.x && a.x + bounds.x >= b.x){ //checks if b.x is within a.x +/- bounds.x
			if(a.y - bounds.y <= b.y && a.y + bounds.y >= b.y)
				send = true;
		}
		return send;
	}
}
