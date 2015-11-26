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
	ArrayList<Flowmeter> meters;
	
	ArrayList<String> particleLog;
	float delta;

	public FluidEnvironment(float len, float wid){
		world = new World(new Vec2(0, 0));
		sources = new ArrayList<Source>();
		meters = new ArrayList<Flowmeter>();
		bounds = new Rectangle2D.Float(10, 10, len-10, wid-10);
		particleLog = new ArrayList<String>();

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
	}

	public void update(float delta){
		this.delta = delta;
		world.step(delta, 6, 3);
		for(Source s: sources) s.update(delta);
		
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

	public void addSource(float x, float y, float velx, float vely, float flow){
		sources.add(new Source(this, x, y, velx, vely, flow));
	}

	/**
	 * Creates a Flow meter at the specified coordinates
	 * @param x x-position of the new flow meter
	 * @param y y-position of the new flow meter
	 */
	public void addFlowmeter(float x, float y, int ID){
		meters.add(new Flowmeter(world, new Vec2(x, y), ID));
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
	
	public void storeData(String[] particles){
		String data = delta + " ";
		for(int i = 0; i < particles.length; i++){
			data += particles[i] + " ";
		}
		particleLog.add(data);
	}
}
