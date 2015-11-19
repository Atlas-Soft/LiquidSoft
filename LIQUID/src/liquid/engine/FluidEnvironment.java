/**
 * 
 */
package liquid.engine;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.MathUtils;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.particle.ParticleDef;
import org.jbox2d.particle.ParticleGroupDef;
import org.jbox2d.particle.ParticleSystem;
import org.jbox2d.particle.ParticleType;

import java.util.ArrayList;

/**
 * @author Rafael Zamora
 *
 */
public class FluidEnvironment {

	World world;
	ParticleSystem particles;
	int particleCount;
	
	public FluidEnvironment(float len, float wid){
		world = new World(new Vec2(0, 0));
		particles = new ParticleSystem(world);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		Body b = world.createBody(bd);
		ChainShape shape = new ChainShape();
        final Vec2[] vertices =
            new Vec2[] {new Vec2(0, 0), new Vec2(len, 0), new Vec2(len, wid), new Vec2(0, wid)};
        shape.createLoop(vertices, 4);
        b.createFixture(shape, 0.0f);
		
		particleCount = 0;
	}
	
	public void init(){
		world.setParticleRadius(5f);
	}
	
	public void update(float delta){
		
		world.step(delta, 6, 3);
		addParticle(25,75,10,0);
	}
	
	public void addObstacle(Shape s,float x, float y){
		Body b;
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		bd.position.set(x, y);
		b = world.createBody(bd);
		FixtureDef fd = new FixtureDef();
		fd.shape = s;
		b.createFixture(fd);
	}
	
	private void addParticle(float x, float y, float velx, float vely){
		if (particleCount < 1000) {
	    	CircleShape shape = new CircleShape();
	    	shape.setRadius(5);
	        ParticleGroupDef pd = new ParticleGroupDef();
	        pd.position.set(x, y);
	        pd.flags = ParticleType.b2_waterParticle;
	        pd.shape = shape;
	        pd.linearVelocity.set(velx, vely);
	        world.setParticleDensity(.5f);
	        world.createParticleGroup(pd);
	        particleCount ++;
	    }
	}
	
	public String[] getParticleData(){
		String[] dataArray = new String[0];
		ArrayList<String> dataList = new ArrayList<String>();
		Vec2[] particlePos = world.getParticlePositionBuffer();
		Vec2[] particleVel = world.getParticleVelocityBuffer();
		
		try{
			System.out.println(particlePos.length);
			for(int i = 0; i < particlePos.length; i++){
				String data = "P " + particlePos[i].x + " " + particlePos[i].y + " " + (particleVel[i].length() + 1.0);
				dataList.add(data);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		dataArray = dataList.toArray(new String[dataList.size()]);
		return dataArray;
	}
}
