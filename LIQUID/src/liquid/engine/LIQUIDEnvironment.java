package liquid.engine;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.particle.ParticleColor;
import org.jbox2d.particle.ParticleGroupDef;
import org.jbox2d.particle.ParticleType;

public class LIQUIDEnvironment extends TempEnvironment {
//	private int m_count = 0;
	final int MAX_NUM = 7500;
	final float PRADIUS = 0.3f;
	
	PolygonShape create = new PolygonShape();
	PolygonShape del = new PolygonShape();
	ParticleGroupDef gen;
	long rateOfFlow = 20;
	
	//Need some sort of constructor here to set up environment
	//for the time being, leave it out while integrating
	
	@Override
	//Call this after creating the instance of the test
	public void initTest(boolean deserialized) {
		// TODO Auto-generated method stub
	    {
	        BodyDef bd = new BodyDef();
	        Body ground = m_world.createBody(bd);
	        gen = new ParticleGroupDef();
	        m_world.setGravity(new Vec2(0, 0f));
	        
//	        create.setAsBox(2, 2, new Vec2(0, 4), 0);
	        create.setAsBox(PRADIUS*4, PRADIUS, new Vec2(0, 4), 0);
	        del.setAsBox(40, 1, new Vec2(-20, 41), 0);
	        
	        
	        ChainShape shape = new ChainShape();
	        final Vec2[] vertices =
	            new Vec2[] {new Vec2(-5, 40), new Vec2(-20, 40), new Vec2(-20, 0), new Vec2(20, 0), new Vec2(20, 40), new Vec2(5, 40)};
	        shape.createChain(vertices, 6);
	        ground.createFixture(shape, 0.0f);

	      }
	    
	      getWorld().setParticleRadius(PRADIUS);
	      
	      {
	        BodyDef bd = new BodyDef();
	        Body body = m_world.createBody(bd);
	        CircleShape shape = new CircleShape();
	        shape.setRadius(.5f);
	        body.setTransform(new Vec2(0.0f, 7.0f), 0.0f);
	        body.createFixture(shape, 0.1f);
	        getWorld().setParticleMaxCount(MAX_NUM);
	      }
	}
	//call this every time the test is updated
	  public synchronized void step() {
		    super.step();

		    if (System.currentTimeMillis() % rateOfFlow <= 10) {
		        gen.flags = ParticleType.b2_waterParticle | ParticleType.b2_tensileParticle | ParticleType.b2_viscousParticle;
		        gen.shape = create;
		        gen.linearVelocity.addLocal(0.0f, 3.0f);
		        getWorld().setParticleDensity(1.0f);
		        getWorld().createParticleGroup(gen);
		    }
		    
		    getWorld().destroyParticlesInShape(del, new Transform());
		  }
	  
	@Override
	public String getTestName() {
		// TODO Auto-generated method stub
		return "Project LIQUID";
	}

}