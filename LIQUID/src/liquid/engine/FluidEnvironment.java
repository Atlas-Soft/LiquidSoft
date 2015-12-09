/**
 * 
 */
package liquid.engine;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.CircleShape;
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

import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
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
	ArrayList<Breakpoint> brkpts;
	ArrayList<Drain> drains;
	ArrayList<String> dataList;
	DecimalFormat adj;

	float delta;
	float viscousStrength;
	/**
	 * Creates a new fluid environment of the specified length and width
	 * @param len the length of the environment
	 * @param wid the width of the environment
	 */
	public FluidEnvironment(float len, float wid){
		world = new World(new Vec2(0, 0));
		sources = new ArrayList<Source>();
		meters = new ArrayList<Flowmeter>();
		brkpts = new ArrayList<Breakpoint>();
		drains = new ArrayList<Drain>();
		bounds = new Rectangle2D.Float(10, 10, len-10, wid-10);
		dataList = new ArrayList<String>(1500);
		adj = new DecimalFormat();
		adj.setMaximumFractionDigits(4);
		

		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		Body b = world.createBody(bd);
		ChainShape shape = new ChainShape();
		Vec2[] vertices = new Vec2[] {new Vec2(0, 0), new Vec2(len, 0), new Vec2(len, wid), new Vec2(0, wid)};
		shape.createLoop(vertices, 4);
		b.createFixture(shape, 0.0f);
	}
	/**
	 * initializes details about this world's particles
	 */
	public void init(){
		world.setParticleRadius(5f);
		world.setParticleMaxCount(1500);
		world.setParticleViscousStrength(viscousStrength);
	}
	/**
	 * runs one step of the simulation
	 * @param delta time, in milliseconds, since the last call to <code>update</code> 
	 */
	public void update(float delta){
		this.delta = delta;
		world.step(delta, 6, 3);
		for(Source s: sources) s.update(delta);
		for(Drain d: drains)	d.update();
		
	}
	/**
	 * Adds an obstacle of the specified shape at the specified coordinates and rotated <code>r</code> degrees
	 * @param s The shape of the new obstacle
	 * @param x the x-position of the new obstacle
	 * @param y the y-position of the new obstacle
	 * @param r the rotation of the new obstacle, in degrees
	 */
	public void addObstacle(Shape s, float x, float y, float r){
		Body b;
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		bd.position.set(x, y);
		bd.setAngle((float) Math.toRadians(r));
		b = world.createBody(bd);
		FixtureDef fd = new FixtureDef();
		fd.shape = s;
		b.createFixture(fd);
	}
	/**
	 * Creates a particle source at the specified coordinates, with the specified velocity and flow speed
	 * @param x x-position of the new source
	 * @param y y-position of the new source
	 * @param velx x-velocity of the new source
	 * @param vely y-velocity of the new source
	 * @param flow flow speed of the new source
	 */
	public void addSource(float x, float y, float velx, float vely, float flow){
		sources.add(new Source(this, x, y, velx, vely, flow));
	}

	/**
	 * Creates a Flow meter at the specified coordinates
	 * @param x x-position of the new flow meter
	 * @param y y-position of the new flow meter
	 * @param ID Identifying number of the new flow meter
	 */
	public void addFlowmeter(float x, float y, int ID){
		meters.add(new Flowmeter(world, new Vec2(x, y), ID));
	}
	/**
	 * Adds a breakpoint at the specified coordinates with the specified area
	 * @param x x-position of the new breakpoint
	 * @param y y-position of the new breakpoint
	 * @param l length of the area this breakpoint monitors
	 * @param w width of the area this breakpoint monitors
	 * @param ID Identifying number of the new breakpoint
	 */
	public void addBreakpoint(float x, float y, float l, float w, int ID){
		brkpts.add(new Breakpoint(world, new Vec2(x, y), l, w, ID));
	}
	/**
	 * Adds a new drain to the environment at coordinates specified in <code>shape</code>
	 * @param shape the shape of the new drain; must be generated such that the position of the top left corner's Vec2 value is x,y
	 */
	public void addDrain(PolygonShape shape){
		drains.add(new Drain(world, shape));
	}
	/**
	 * adds a new particle to the simulation
	 * @param x	x-position of the new particle
	 * @param y y-position of the new particle
	 * @param velx x-velocity of the new particle
	 * @param vely y-velocity of the new particle
	 */
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
	/**
	 * gets the position and length of the velocity vector for each particle
	 * @return array containing particle data in the form of:<br> <code>P (x) (y) (velocity)</code>
	 */
	public String[] getParticleData(){
		String[] dataArray = new String[0];
		dataList.clear();
		dataList.add(""+adj.format(delta));
		Vec2[] particlePos = world.getParticlePositionBuffer();
		Vec2[] particleVel = world.getParticleVelocityBuffer();
		String data = "";	//used for displaying particles
		try{
			for(int i = 0; i < particlePos.length; i++){
				if(particlePos[i].x + particlePos[i].y + particleVel[i].x + particleVel[i].y != 0.0 ){
					data = "P " + adj.format(particlePos[i].x) + " " + adj.format(particlePos[i].y) + " " + adj.format(particleVel[i].length() * 3 + 1.0);
					dataList.add(data);
				}
			}
		}catch(Exception e){}		
		dataArray = dataList.toArray(new String[dataList.size()]);
		return dataArray;
	}
	
}
