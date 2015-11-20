/**
 * 
 */
package liquid.engine;


import org.jbox2d.callbacks.ParticleQueryCallback;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
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
import org.jbox2d.particle.ParticleType;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * @author Rafael Zamora
 *
 */
public class FluidEnvironment {

	World world;
	Rectangle2D bounds;
	ArrayList<Source> sources;
	
	public FluidEnvironment(float len, float wid){
		world = new World(new Vec2(0, 0));
		sources = new ArrayList<Source>();
		bounds = new Rectangle2D.Float(10, 10, len-10, wid-10);
		
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
		world.setParticleMaxCount(1500);
		world.setParticleViscousStrength(0.0f);
		
		for(int i = 0; i < 100; i ++){
			for(int j = 0; j < 100; j++){
				ParticleQuery pq = new ParticleQuery();
				Vec2 p = new Vec2(i*12,j*12);
				AABB pb = new AABB();
				pb.lowerBound.set(p.x-10, p.y-10);
				pb.upperBound.set(p.x+10, p.y+10);
				world.queryAABB((QueryCallback)pq, (ParticleQueryCallback)pq, pb);
				if(pq.isOpen() && bounds.contains(p.x, p.y)){
					//addParticle(p.x,p.y,0,0);
				}
			}
		}
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
	    	shape.setRadius(5);
	        ParticleGroupDef pd = new ParticleGroupDef();
	        pd.position.set(x, y);
	        pd.linearVelocity.set(velx, vely); 
	        pd.flags = ParticleType.b2_waterParticle | ParticleType.b2_viscousParticle;
	        pd.shape = shape;
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
		}catch(Exception e){}		
		dataArray = dataList.toArray(new String[dataList.size()]);
		return dataArray;
	}
}
