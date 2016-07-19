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
 * Class creates all of the objects to be displayed in the SimulationPanel, including the obstacles, drains,
 * sources, flow meters, breakpoints, and the particles themselves. Here, the velocities of the individual
 * particles are also calculated and stored, then later sent to the Logger to be written into the log file.
 * @author Rafael Zamora
 */
public class FluidEnvironment {

	// variables used to create the environment and its various objects (for the Engine to compute)
	World world;
	Rectangle2D bounds;
	ArrayList<Drain> drains;
	ArrayList<Source> sources;
	ArrayList<Flowmeter> meters;
	ArrayList<Breakpoint> brkpts;
	ArrayList<String> dataList;
	DecimalFormat adj;
	float delta;
	float viscousStrength;
	
	/**
	 * Constructor creates a new fluid environment with the specified length and width.
	 * @param len - environment length
	 * @param wid - environment width
	 */
	public FluidEnvironment(float len, float wid) {
		world = new World(new Vec2(0,0));
		bounds = new Rectangle2D.Float(10,10,(len-10),(wid-10));
		drains = new ArrayList<Drain>();
		sources = new ArrayList<Source>();
		meters = new ArrayList<Flowmeter>();
		brkpts = new ArrayList<Breakpoint>();
		dataList = new ArrayList<String>(1500);
		adj = new DecimalFormat();
		adj.setMaximumFractionDigits(4);
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		Body b = world.createBody(bd);
		ChainShape shape = new ChainShape();
		Vec2[] vertices = new Vec2[]{new Vec2(0,0),new Vec2(len,0),new Vec2(len,wid),new Vec2(0,wid)};
		shape.createLoop(vertices,4);
		b.createFixture(shape,0.0f);
	}
	
	/**
	 * Initializes the particle details for the simulation. This includes the radius, max count, and viscous strength.
	 */
	public void init() {
		world.setParticleRadius(3.3f);
		world.setParticleMaxCount(3000);
		world.setParticleViscousStrength(viscousStrength);
	}
	
	/**
	 * Method runs one step of the simulation.
	 * @param delta - time, in milliseconds, since the last call to <code>update</code> 
	 */
	public void update(float delta) {
		this.delta = delta;
		world.step(delta,6,3);
		for (Source s: sources) s.update(delta);
		for (Drain d: drains)	d.update();
	}
	
	/**
	 * Method adds an obstacle of the specified shape at the specified coordinates and rotated <code>r</code> degrees
	 * @param s - shape of obstacle
	 * @param x - X-Coordinate of obstacle
	 * @param y - Y-Coordinate of obstacle
	 * @param r - rotation of obstacle, in degrees
	 */
	public void addObstacle(Shape s, float x, float y, float r) {
		Body b;
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		bd.position.set(x,y);
		bd.setAngle((float)Math.toRadians(r));
		b = world.createBody(bd);
		FixtureDef fd = new FixtureDef();
		fd.shape = s;
		b.createFixture(fd);
	}
	
	/**
	 * Method adds a drain with the specified coordinates based on <code>shape</code>.
	 * @param shape - shape of drain; must be generated such that the position of the top left corner's Vec2 value is X,Y
	 */
	public void addDrain(PolygonShape shape) {
		drains.add(new Drain(world,shape));
	}
	
	/**
	 * Method creates a particle source at the specified coordinates, and with the specified velocities and flow speed.
	 * @param x    - X-Coordinate of source
	 * @param y    - Y-Coordinate of source
	 * @param velx - X-velocity of source
	 * @param vely - Y-velocity of source
	 * @param flow - flow speed of source
	 */
	public void addSource(float x, float y, float velx, float vely, float flow) {
		sources.add(new Source(this,x,y,velx,vely,flow));
	}

	/**
	 * Method creates a flow meter at the specified coordinates and identifying number.
	 * @param x  - X-Coordinate of flow meter
	 * @param y  - Y-Coordinate of flow meter
	 * @param ID - identifying number of flow meter
	 */
	public void addFlowmeter(float x, float y, int ID) {
		meters.add(new Flowmeter(world,new Vec2(x,y),ID));
	}
	
	/**
	 * Method creates a breakpoint at the specified coordinates with the specified area.
	 * @param x  - X-Coordinate of breakpoint
	 * @param y  - Y-Coordinate of breakpoint
	 * @param l  - length of breakpoint area
	 * @param w  - width of breakpoint area
	 * @param ID - identifying number of breakpoint
	 */
	public void addBreakpoint(float x, float y, float l, float w, int ID) {
		brkpts.add(new Breakpoint(world,new Vec2(x,y),l,w,ID));
	}

	/**
	 * Method adds a new particle to the simulation.
	 * @param x	   - X-Coordinate of new particle
	 * @param y	   - Y-Coordinate of new particle
	 * @param velx - X-velocity of new particle
	 * @param vely - Y-velocity of new particle
	 */
	public void addParticle(float x, float y, float velx, float vely) {
		CircleShape shape = new CircleShape();
		shape.setRadius(5);
		ParticleGroupDef pd = new ParticleGroupDef();
		pd.position.set(x,y);
		pd.linearVelocity.set(velx,vely); 
		pd.flags = ParticleType.b2_waterParticle | ParticleType.b2_viscousParticle;
		pd.shape = shape;
		world.createParticleGroup(pd);
	}
	
	/**
	 * Method gets the position/length of the velocity vector for each particle.
	 * @return - array of particle data in the form:<br><code>'P (x) (y) (velocity)'</code>
	 */
	public String[] getParticleData() {
		String[] dataArray = new String[0];
		dataList.clear();
		dataList.add(""+adj.format(delta));
		Vec2[] particlePos = world.getParticlePositionBuffer();
		Vec2[] particleVel = world.getParticleVelocityBuffer();
		
		// used for displaying particles
		String data = "";
		try {
			for (int i = 0; i < particlePos.length; i++) {
				if ((particlePos[i].x+particlePos[i].y+particleVel[i].x+particleVel[i].y) != 0.0) {
					data = "P "+adj.format(particlePos[i].x)+" "+adj.format(particlePos[i].y)+" "+adj.format(particleVel[i].length()*3+1.0);
					dataList.add(data);}
			}
		} catch(Exception e) {}		
		dataArray = dataList.toArray(new String[dataList.size()]);
		return dataArray;
	}
}