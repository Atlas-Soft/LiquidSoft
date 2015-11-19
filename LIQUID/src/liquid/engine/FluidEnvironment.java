/**
 * 
 */
package liquid.engine;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.particle.ParticleGroupDef;
import org.jbox2d.particle.ParticleType;

import java.util.ArrayList;

/**
 * @author Rafael Zamora
 *
 */
public class FluidEnvironment {

	World world;
	Vec2 bounds;
	
	ArrayList<Source> sources;
	ArrayList<Flowmeter> flowmeters;
	
	int particleCount;
	
	public FluidEnvironment(float len, float wid){
		world = new World(new Vec2(0, 0));
		bounds = new Vec2(len, wid);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		Body b = world.createBody(bd);
		ChainShape shape = new ChainShape();
        final Vec2[] vertices =
            new Vec2[] {new Vec2(0, 0), new Vec2(len, 0), new Vec2(len, wid), new Vec2(0, wid)};
        shape.createLoop(vertices, 4);
        b.createFixture(shape, 0.0f);
		
		sources = new ArrayList<Source>();
		flowmeters = new ArrayList<Flowmeter>();
		
		particleCount = 0;
	}
	
	public void init(){
		world.setParticleRadius(0.3f);
	}
	
	public void update(float delta){
		if (particleCount < 500) {
	    	PolygonShape shape = new PolygonShape();
	        shape.setAsBox(1,1, new Vec2(25, 75), 0);
	        ParticleGroupDef pd = new ParticleGroupDef();
	        pd.flags = ParticleType.b2_tensileParticle | ParticleType.b2_viscousParticle;
	        pd.shape = shape;
	        world.setParticleDensity(1.0f);
	        world.createParticleGroup(pd);
	        particleCount ++;
	    }
		world.step(delta, 6, 3);
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
	
	public void addSource(Source s){
		sources.add(s);
	}
	
	public void addFlowMeter(Flowmeter f){
		flowmeters.add(f);
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
			e.printStackTrace();
		}
		
		dataArray = dataList.toArray(new String[dataList.size()]);
		return dataArray;
	}
}
