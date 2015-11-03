package org.jbox2d.testbed.tests;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.particle.ParticleGroupDef;
import org.jbox2d.particle.ParticleType;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

public class LIQUID extends TestbedTest {
	private int m_count = 0;
	final int MAX_NUM = 1000;
	@Override
	public void initTest(boolean deserialized) {
		// TODO Auto-generated method stub
	    {
	        BodyDef bd = new BodyDef();
	        Body ground = m_world.createBody(bd);
	        m_world.setGravity(new Vec2(0, -10.0f));
	        

	        ChainShape shape = new ChainShape();
	        final Vec2[] vertices =
	            new Vec2[] {new Vec2(-20, 0), new Vec2(20, 0), new Vec2(20, 40), new Vec2(-20, 40)};
	        shape.createLoop(vertices, 4);
	        ground.createFixture(shape, 0.0f);

	      }

	      m_world.setParticleRadius(0.3f);
//	      {
//	        PolygonShape shape = new PolygonShape();
//	        shape.setAsBox(15, 4, new Vec2(0, 36), 0);
//	        ParticleGroupDef pd = new ParticleGroupDef();
//	        pd.flags = ParticleType.b2_tensileParticle | ParticleType.b2_viscousParticle;
//	        pd.shape = shape;
//	        getWorld().setParticleDensity(10.0f);
////	        getWorld().setParticleColorBuffer(buffer, capacity);
//	        getWorld().createParticleGroup(pd);
//	      }

	      {
	        BodyDef bd = new BodyDef();
	        Body body = m_world.createBody(bd);
	        EdgeShape shape = new EdgeShape();
	        shape.set(new Vec2(13, 32), new Vec2(-12, 35));
	        body.createFixture(shape, 0.1f);
	      }
	      
//	      {
//	        BodyDef bd = new BodyDef();
//	        Body body = m_world.createBody(bd);
//	        EdgeShape shape = new EdgeShape();
//	        shape.set(new Vec2(-11, 32), new Vec2(20, 32));
//	        body.createFixture(shape, 0.1f);
//	      }
//
//	      {
//	        BodyDef bd = new BodyDef();
//	        Body body = m_world.createBody(bd);
//	        EdgeShape shape = new EdgeShape();
//	        shape.set(new Vec2(-12, 32), new Vec2(-12, 28));
//	        body.createFixture(shape, 0.1f);
//	      }
//
//	      {
//	        BodyDef bd = new BodyDef();
//	        Body body = m_world.createBody(bd);
//	        EdgeShape shape = new EdgeShape();
//	        shape.set(new Vec2(-11, 32), new Vec2(-11, 28));
//	        body.createFixture(shape, 0.1f);
//	      }
//
//	      {
//	        BodyDef bd = new BodyDef();
//	        Body body = m_world.createBody(bd);
//	        EdgeShape shape = new EdgeShape();
//	        shape.set(new Vec2(-16, 24), new Vec2(8, 20));
//	        body.createFixture(shape, 0.1f);
//	      }
//
//	      {
//	        BodyDef bd = new BodyDef();
//	        Body body = m_world.createBody(bd);
//	        EdgeShape shape = new EdgeShape();
//	        shape.set(new Vec2(16, 16), new Vec2(-8, 12));
//	        body.createFixture(shape, 0.1f);
//	      }
//
//	      {
//	        BodyDef bd = new BodyDef();
//	        Body body = m_world.createBody(bd);
//	        EdgeShape shape = new EdgeShape();
//	        shape.set(new Vec2(-12, 8), new Vec2(-12, 0));
//	        body.createFixture(shape, 0.1f);
//	      }
//
//	      {
//	        BodyDef bd = new BodyDef();
//	        Body body = m_world.createBody(bd);
//	        EdgeShape shape = new EdgeShape();
//	        shape.set(new Vec2(-4, 8), new Vec2(-4, 0));
//	        body.createFixture(shape, 0.1f);
//	      }
//
//	      {
//	        BodyDef bd = new BodyDef();
//	        Body body = m_world.createBody(bd);
//	        EdgeShape shape = new EdgeShape();
//	        shape.set(new Vec2(4, 8), new Vec2(4, 0));
//	        body.createFixture(shape, 0.1f);
//	      }
//
//	      {
//	        BodyDef bd = new BodyDef();
//	        Body body = m_world.createBody(bd);
//	        EdgeShape shape = new EdgeShape();
//	        shape.set(new Vec2(12, 8), new Vec2(12, 0));
//	        body.createFixture(shape, 0.1f);
//	      }
	}

	  public synchronized void step(TestbedSettings settings) {
		    super.step(settings);

		    if (m_count < MAX_NUM) {
		    	PolygonShape shape = new PolygonShape();
		        shape.setAsBox(2, 2, new Vec2(0, 36), 0);
		        ParticleGroupDef pd = new ParticleGroupDef();
		        pd.flags = ParticleType.b2_tensileParticle | ParticleType.b2_viscousParticle;
		        pd.shape = shape;
		        getWorld().setParticleDensity(1.0f);
		        getWorld().createParticleGroup(pd);
		      ++m_count;
		    }
		    PolygonShape del = new PolygonShape();
		    del.setAsBox(1, 1, new Vec2(-15, 36), 0);
		    m_count -= getWorld().destroyParticlesInShape(del, new Transform());
		  }
	  
	@Override
	public String getTestName() {
		// TODO Auto-generated method stub
		return "Project LIQUID";
	}

}
