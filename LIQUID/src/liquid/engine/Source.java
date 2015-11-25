/**
 * 
 */
package liquid.engine;

import org.jbox2d.common.Vec2;

import liquid.core.LiquidApplication;

/**
 * @author Rafael Zamora
 *
 */
public class Source {

	private FluidEnvironment enviro;
	private Vec2 pos;
	private Vec2 force;
	private float flowspeed;
	private float timer;
	
	public Source(FluidEnvironment enviro, float x, float y, float velx, float vely){
		this.enviro = enviro;
		pos = new Vec2(x, y);
		force = new Vec2(velx,vely);
		flowspeed = force.length();
		timer = 0;
	}
	
	public void update(float delta){
		timer += delta;
		if(timer >= 60/flowspeed){
			timer = 0;
			enviro.addParticle(pos.x, pos.y, force.x, force.y);
		}
	}
}
