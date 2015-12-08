/**
 * 
 */
package liquid.engine;

import org.jbox2d.common.Vec2;

import liquid.core.LiquidApplication;

/**
 * V 1.0
 * @author Rafael Zamora
 *
 */
public class Source {

	private FluidEnvironment enviro;
	private Vec2 pos;
	private Vec2 force;
	private float flowspeed;
	private float timer;
	/**
	 * Creates a new particle source in the specified environment, at the specified coordinates, and with the specified velocity and flow speed
	 * @param enviro The environment this source exists in
	 * @param x x-position of this source
	 * @param y y-position of this source
	 * @param velx x-velocity of this source
	 * @param vely y-velocity of this source
	 * @param flow flow speed of this source, in particles per second
	 */
	public Source(FluidEnvironment enviro, float x, float y, float velx, float vely, float flow){
		this.enviro = enviro;
		pos = new Vec2(x, y);
		force = new Vec2(velx,vely);
		flowspeed = flow;
		timer = 0;
	}
	/**
	 * runs one step of the simulation, generating a particle if the time since the last particle is less than <code>60/flowspeed</code>
	 * @param delta The time in milliseconds since the last call to <code>update()</code>
	 */
	public void update(float delta){
		timer += delta;
		if(timer >= 60/flowspeed){
			timer = 0;
			enviro.addParticle(pos.x, pos.y, force.x, force.y);
		}
	}
}
