package liquid.engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.callbacks.DestructionListener;
import org.jbox2d.callbacks.ParticleDestructionListener;
import org.jbox2d.callbacks.ParticleQueryCallback;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.Collision;
import org.jbox2d.collision.Collision.PointState;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.Profile;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;
import org.jbox2d.particle.ParticleGroup;


public abstract class TempEnvironment 
implements
ContactListener
{
	public static final int MAX_CONTACT_POINTS = 4048;
	public static final float ZOOM_SCALE_DIFF = .05f;
	public static final int TEXT_LINE_SPACE = 13;
	public static final int TEXT_SECTION_SPACE = 3;
	public static final int MOUSE_JOINT_BUTTON = 1;
	public static final int BOMB_SPAWN_BUTTON = 10;

	protected static final long GROUND_BODY_TAG = 1897450239847L;
	protected static final long BOMB_TAG = 98989788987L;
	protected static final long MOUSE_JOINT_TAG = 4567893364789L;


	/**
	 * Only visible for compatibility. Should use {@link #getWorld()} instead.
	 */
	protected World m_world;
	protected Body groundBody;
	private MouseJoint mouseJoint;

	private Body bomb;
	private final Vec2 bombMousePoint = new Vec2();
	private final Vec2 bombSpawnPoint = new Vec2();
	private boolean bombSpawning = false;

	protected boolean mouseTracing;
	private Vec2 mouseTracerPosition = new Vec2();
	private Vec2 mouseTracerVelocity = new Vec2();

	private final Vec2 mouseWorld = new Vec2();
	private int pointCount;
	private int stepCount;

	protected DestructionListener destructionListener;
	protected ParticleDestructionListener particleDestructionListener;


	private String title = null;
	protected int m_textLine;
	private final LinkedList<String> textList = new LinkedList<String>();


	private final Transform identity = new Transform();

	public TempEnvironment() {
		identity.setIdentity();
		for (int i = 0; i < MAX_CONTACT_POINTS; i++) {
		}
		destructionListener = new DestructionListener() {
			public void sayGoodbye(Fixture fixture) {
				fixtureDestroyed(fixture);
			}

			public void sayGoodbye(Joint joint) {
				if (mouseJoint == joint) {
					mouseJoint = null;
				} else {
					jointDestroyed(joint);
				}
			}
		};

		particleDestructionListener = new ParticleDestructionListener() {
			@Override
			public void sayGoodbye(int index) {
				particleDestroyed(index);
			}

			@Override
			public void sayGoodbye(ParticleGroup group) {
				particleGroupDestroyed(group);
			}
		};
	}

	public void init() {

		Vec2 gravity = new Vec2(0, -10f);
		m_world = new World(gravity);
		m_world.setParticleGravityScale(0.4f);
		m_world.setParticleDensity(1.2f);
		bomb = null;
		mouseJoint = null;

		mouseTracing = false;
		mouseTracerPosition.setZero();
		mouseTracerVelocity.setZero();

		BodyDef bodyDef = new BodyDef();
		groundBody = m_world.createBody(bodyDef);

		init(m_world, false);
	}

	public void init(World world, boolean deserialized) {
		m_world = world;
		pointCount = 0;
		stepCount = 0;
		bombSpawning = false;

		world.setDestructionListener(destructionListener);
		world.setParticleDestructionListener(particleDestructionListener);
		world.setContactListener(this);
		title = getTestName();
		initTest(deserialized);
	}



	/**
	 * Gets the current world
	 */
	public World getWorld() {
		return m_world;
	}

	/**
	 * Gets the ground body of the world, used for some joints
	 */
	public Body getGroundBody() {
		return groundBody;
	}

	/**
	 * Gets the world position of the mouse
	 */
	public Vec2 getWorldMouse() {
		return mouseWorld;
	}

	public int getStepCount() {
		return stepCount;
	}

	/**
	 * The number of contact points we're storing
	 */
	public int getPointCount() {
		return pointCount;
	}

	/**
	 * Gets the 'bomb' body if it's present
	 */
	public Body getBomb() {
		return bomb;
	}

	/**
	 * Override for a different default camera position
	 */
	public Vec2 getDefaultCameraPos() {
		return new Vec2(0, 20);
	}

	/**
	 * Override for a different default camera scale
	 */
	public float getDefaultCameraScale() {
		return 10;
	}

	public boolean isMouseTracing() {
		return mouseTracing;
	}

	public Vec2 getMouseTracerPosition() {
		return mouseTracerPosition;
	}

	public Vec2 getMouseTracerVelocity() {
		return mouseTracerVelocity;
	}

	/**
	 * Gets the filename of the current test. Default implementation uses the test name with no
	 * spaces".
	 */
	public String getFilename() {
		return getTestName().toLowerCase().replaceAll(" ", "_") + ".box2d";
	}

	/**
	 * Initializes the current test.
	 * 
	 * @param deserialized if the test was deserialized from a file. If so, all physics objects are
	 *        already added.
	 */
	public abstract void initTest(boolean deserialized);

	/**
	 * The name of the test
	 */
	public abstract String getTestName();

	/**
	 * Adds a text line to the reporting area
	 */
	public void addTextLine(String line) {
		textList.add(line);
	}

	/**
	 * called when the tests exits
	 */
	public void exit() {}

	private final Color3f color1 = new Color3f(.3f, .95f, .3f);
	private final Color3f color2 = new Color3f(.3f, .3f, .95f);
	private final Color3f color3 = new Color3f(.9f, .9f, .9f);
	private final Color3f color4 = new Color3f(.6f, .61f, 1);
	private final Color3f color5 = new Color3f(.9f, .9f, .3f);
	private final Color3f mouseColor = new Color3f(0f, 1f, 0f);
	private final Vec2 p1 = new Vec2();
	private final Vec2 p2 = new Vec2();
	private final Vec2 tangent = new Vec2();
	private final List<String> statsList = new ArrayList<String>();

	private final Vec2 acceleration = new Vec2();
	private final CircleShape pshape = new CircleShape();
	private final ParticleVelocityQueryCallback pcallback = new ParticleVelocityQueryCallback();
	private final AABB paabb = new AABB();

	public void step() {
		float hz = 60;	//sets max fps at 60, as it is most common refresh rate
		float timeStep = hz > 0f ? 1f / hz : 0;


		m_world.setAllowSleep(true);	//settings have been arbitrarily set for experimentation
		m_world.setWarmStarting(true);	//currently matches default settings in the testbed
		m_world.setSubStepping(true);
		m_world.setContinuousPhysics(false);
		pointCount = 0;

		m_world.step(timeStep, 4, 5);

		m_world.drawDebugData();

		if (timeStep > 0f) {
			++stepCount;
		}

		if (mouseTracing && mouseJoint == null) {
			float delay = 0.1f;
			acceleration.x =
					2 / delay * (1 / delay * (mouseWorld.x - mouseTracerPosition.x) - mouseTracerVelocity.x);
			acceleration.y =
					2 / delay * (1 / delay * (mouseWorld.y - mouseTracerPosition.y) - mouseTracerVelocity.y);
			mouseTracerVelocity.x += timeStep * acceleration.x;
			mouseTracerVelocity.y += timeStep * acceleration.y;
			mouseTracerPosition.x += timeStep * mouseTracerVelocity.x;
			mouseTracerPosition.y += timeStep * mouseTracerVelocity.y;
			pshape.m_p.set(mouseTracerPosition);
			pshape.m_radius = 2;
			pcallback.init(m_world, pshape, mouseTracerVelocity);
			pshape.computeAABB(paabb, identity, 0);
			m_world.queryAABB(pcallback, paabb);
		}
	}

	/************ INPUT ************/

	/**
	 * Called for mouse-up
	 */
	public void mouseUp(Vec2 p, int button) {
		mouseTracing = false;
		if (button == MOUSE_JOINT_BUTTON) {
			destroyMouseJoint();
		}
		completeBombSpawn(p);
	}

	public void keyPressed(char keyChar, int keyCode) {}

	public void keyReleased(char keyChar, int keyCode) {}

	public void mouseDown(Vec2 p, int button) {
		mouseWorld.set(p);
		mouseTracing = true;
		mouseTracerVelocity.setZero();
		mouseTracerPosition.set(p);

		if (button == BOMB_SPAWN_BUTTON) {
			beginBombSpawn(p);
		}

		if (button == MOUSE_JOINT_BUTTON) {
			spawnMouseJoint(p);
		}
	}

	public void mouseMove(Vec2 p) {
		mouseWorld.set(p);
	}

	public void mouseDrag(Vec2 p, int button) {
		mouseWorld.set(p);
		if (button == MOUSE_JOINT_BUTTON) {
			updateMouseJoint(p);
		}
		if (button == BOMB_SPAWN_BUTTON) {
			bombMousePoint.set(p);
		}
	}

	/************ MOUSE JOINT ************/

	private final AABB queryAABB = new AABB();
	private final TestQueryCallback callback = new TestQueryCallback();

	private void spawnMouseJoint(Vec2 p) {
		if (mouseJoint != null) {
			return;
		}
		queryAABB.lowerBound.set(p.x - .001f, p.y - .001f);
		queryAABB.upperBound.set(p.x + .001f, p.y + .001f);
		callback.point.set(p);
		callback.fixture = null;
		m_world.queryAABB(callback, queryAABB);

		if (callback.fixture != null) {
			Body body = callback.fixture.getBody();
			MouseJointDef def = new MouseJointDef();
			def.bodyA = groundBody;
			def.bodyB = body;
			def.collideConnected = true;
			def.target.set(p);
			def.maxForce = 1000f * body.getMass();
			mouseJoint = (MouseJoint) m_world.createJoint(def);
			body.setAwake(true);
		}
	}

	private void updateMouseJoint(Vec2 target) {
		if (mouseJoint != null) {
			mouseJoint.setTarget(target);
		}
	}

	private void destroyMouseJoint() {
		if (mouseJoint != null) {
			m_world.destroyJoint(mouseJoint);
			mouseJoint = null;
		}
	}

	/********** BOMB ************/

	private final Vec2 p = new Vec2();
	private final Vec2 v = new Vec2();

	public void lanchBomb() {
		p.set((float) (Math.random() * 30 - 15), 30f);
		v.set(p).mulLocal(-5f);
		launchBomb(p, v);
	}

	private final AABB aabb = new AABB();

	private void launchBomb(Vec2 position, Vec2 velocity) {
		if (bomb != null) {
			m_world.destroyBody(bomb);
			bomb = null;
		}
		// todo optimize this
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(position);
		bd.bullet = true;
		bomb = m_world.createBody(bd);
		bomb.setLinearVelocity(velocity);

		CircleShape circle = new CircleShape();
		circle.m_radius = 0.3f;

		FixtureDef fd = new FixtureDef();
		fd.shape = circle;
		fd.density = 20f;
		fd.restitution = 0;

		Vec2 minV = new Vec2(position);
		Vec2 maxV = new Vec2(position);

		minV.subLocal(new Vec2(.3f, .3f));
		maxV.addLocal(new Vec2(.3f, .3f));

		aabb.lowerBound.set(minV);
		aabb.upperBound.set(maxV);

		bomb.createFixture(fd);
	}

	private void beginBombSpawn(Vec2 worldPt) {
		bombSpawnPoint.set(worldPt);
		bombMousePoint.set(worldPt);
		bombSpawning = true;
	}

	private final Vec2 vel = new Vec2();

	private void completeBombSpawn(Vec2 p) {
		if (bombSpawning == false) {
			return;
		}

		float multiplier = 30f;
		vel.set(bombSpawnPoint).subLocal(p);
		vel.mulLocal(multiplier);
		launchBomb(bombSpawnPoint, vel);
		bombSpawning = false;
	}

	/************ SERIALIZATION *************/

	/**
	 * Override to enable saving and loading. Remember to also override the {@link ObjectListener} and
	 * {@link ObjectSigner} methods if you need to
	 * 
	 * @return
	 */
	public boolean isSaveLoadEnabled() {
		return false;
	}

	public void fixtureDestroyed(Fixture fixture) {}

	public void jointDestroyed(Joint joint) {}

	public void beginContact(Contact contact) {}

	public void endContact(Contact contact) {}

	public void particleDestroyed(int particle) {}

	public void particleGroupDestroyed(ParticleGroup group) {}

	public void postSolve(Contact contact, ContactImpulse impulse) {}

	private final PointState[] state1 = new PointState[Settings.maxManifoldPoints];
	private final PointState[] state2 = new PointState[Settings.maxManifoldPoints];
	private final WorldManifold worldManifold = new WorldManifold();

	public void preSolve(Contact contact, Manifold oldManifold) {
		Manifold manifold = contact.getManifold();

		if (manifold.pointCount == 0) {
			return;
		}

		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		Collision.getPointStates(state1, state2, oldManifold, manifold);

		contact.getWorldManifold(worldManifold);

//		for (int i = 0; i < manifold.pointCount && pointCount < MAX_CONTACT_POINTS; i++) {
//			ContactPoint cp = points[pointCount];
//			cp.fixtureA = fixtureA;
//			cp.fixtureB = fixtureB;
//			cp.position.set(worldManifold.points[i]);
//			cp.normal.set(worldManifold.normal);
//			cp.state = state2[i];
//			cp.normalImpulse = manifold.points[i].normalImpulse;
//			cp.tangentImpulse = manifold.points[i].tangentImpulse;
//			cp.separation = worldManifold.separations[i];
//			++pointCount;
//		}
	}
}


class TestQueryCallback implements QueryCallback {

	public final Vec2 point;
	public Fixture fixture;

	public TestQueryCallback() {
		point = new Vec2();
		fixture = null;
	}

	public boolean reportFixture(Fixture argFixture) {
		Body body = argFixture.getBody();
		if (body.getType() == BodyType.DYNAMIC) {
			boolean inside = argFixture.testPoint(point);
			if (inside) {
				fixture = argFixture;

				return false;
			}
		}

		return true;
	}
}


class ParticleVelocityQueryCallback implements ParticleQueryCallback {
	World world;
	Shape shape;
	Vec2 velocity;
	final Transform xf = new Transform();

	public ParticleVelocityQueryCallback() {
		xf.setIdentity();
	}

	public void init(World world, Shape shape, Vec2 velocity) {
		this.world = world;
		this.shape = shape;
		this.velocity = velocity;
	}

	@Override
	public boolean reportParticle(int index) {
		Vec2 p = world.getParticlePositionBuffer()[index];
		if (shape.testPoint(xf, p)) {
			Vec2 v = world.getParticleVelocityBuffer()[index];
			v.set(velocity);
		}
		return true;
	}
}

