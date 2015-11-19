/**
 * 
 */
package liquid.engine;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
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
	ArrayList<Source> sources;
	
	public FluidEnvironment(float len, float wid){
		world = new World(new Vec2(0, 0));
		particles = new ParticleSystem(world);
		sources = new ArrayList<Source>();
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		Body b = world.createBody(bd);
		ChainShape shape = new ChainShape();
        Vec2[] vertices = new Vec2[] {new Vec2(0, 0), new Vec2(len, 0), new Vec2(len, wid), new Vec2(0, wid)};
        shape.createLoop(vertices, 4);
        b.createFixture(shape, 0.0f);
	}
	
	public void init(){
		world.setParticleRadius(5f);
		world.setParticleDensity(1.0f);
		world.setParticleMaxCount(1000);
	}
	
	public void update(float delta){
		world.step(delta, 6, 3);
		for(Source s: sources){
			s.update(delta);
		}
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
	
	public void addSource(float x, float y, float velx, float vely){
		sources.add(new Source(this, x, y, velx, vely));
	}
	
	public void addParticle(float x, float y, float velx, float vely){
	    	CircleShape shape = new CircleShape();
	    	shape.setRadius(6);
	        ParticleGroupDef pd = new ParticleGroupDef();
	        pd.position.set(x, y);
	        pd.flags = ParticleType.b2_waterParticle;
	        pd.shape = shape;
	        pd.linearVelocity.set(velx, vely); 
	        pd.strength = 1.0f;
	        world.createParticleGroup(pd);
	}
	
	public String[] getParticleData(){
		String[] dataArray = new String[0];
		ArrayList<String> dataList = new ArrayList<String>();
		Vec2[] particlePos = world.getParticlePositionBuffer();
		Vec2[] particleVel = world.getParticleVelocityBuffer();
		try{
			for(int i = 0; i < particlePos.length; i++){
				String data = "P " + particlePos[i].x + " " + particlePos[i].y + " " + (particleVel[i].length() + 1.0);
				dataList.add(data);
			}
		}catch(Exception e){
		}
		
		dataArray = dataList.toArray(new String[dataList.size()]);
		return dataArray;
	}
}
