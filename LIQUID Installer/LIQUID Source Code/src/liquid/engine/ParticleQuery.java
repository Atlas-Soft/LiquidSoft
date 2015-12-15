package liquid.engine;

import org.jbox2d.callbacks.ParticleQueryCallback;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.dynamics.Fixture;

public class ParticleQuery implements QueryCallback, ParticleQueryCallback {

	boolean b;
	
	public ParticleQuery() {
		b = true;
	}

	@Override
	public boolean reportFixture(Fixture arg0) {
		b = false;
		return false;
	}
	
	public boolean isOpen(){
		return b;
	}

	@Override
	public boolean reportParticle(int arg0) {
		b = false;
		return false;
	}

}
