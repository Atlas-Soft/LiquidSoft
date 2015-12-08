/**
 * 
 */
package liquid.engine;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
//http://xkcd.com/1597/
/**
 * V 1.0
 * @author William Steele
 */
public class Flowmeter {
	private World myWorld;
	private Vec2 myLoc;
	private int myID;

	/**
	 * Creates a new flow meter monitoring the specified world at the specified location
	 * @param imHere the world this flow meter is monitoring
	 * @param loc the location this flow meter monitors
	 * @param ID the identifying number for this flow meter
	 */
	public Flowmeter(World imHere, Vec2 loc, int ID){
		myWorld = imHere;
		myLoc = loc;
		myID = ID;
	}
	/**
	 * @return the average velocity of all particles within an area of 20.0 * 20.0 centered around loc
	 */
	public String toString(){
		DecimalFormat adj = new DecimalFormat();
		Vec2 curVel = pollVelocity();
		adj.setMaximumFractionDigits(4);
		String send = " F" + myID + " ( X-Vel " + adj.format(curVel.x) + ", Y-Vel " + adj.format(curVel.y) +")";
		return send;
	}
	
	/**
	 * Helper Method
	 * Gets the average x and y velocities of particles within 10.0 units of this flowmeter's position
	 * @return Vec2 object containing the average x and y velocities of nearby particles
	 */
	private Vec2 pollVelocity(){
		ArrayList<Vec2> vel = new ArrayList<Vec2>();
		Vec2[] pos = myWorld.getParticlePositionBuffer();
		Vec2 bounds = new Vec2(10.0f, 10.0f);	//To change boundaries to check, simply change parameters of constructor
		for (int i = 0; i < pos.length; i++){
			if(almostEqual(myLoc, pos[i], bounds)){
				vel.add(myWorld.getParticleVelocityBuffer()[i]);	//may need to be changed to local variable depending on performance
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
