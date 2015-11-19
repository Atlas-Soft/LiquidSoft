package liquid.engine;

import org.jbox2d.collision.shapes.PolygonShape;

import liquid.core.Interfaceable;
import liquid.core.LiquidApplication;
import liquid.gui.LiquidGUI;
import liquid.logger.LiquidLogger;

public class LiquidEngine implements Interfaceable, Runnable {

	public static final int REQUEST_DISPLAYSIM = 3;
	public static final int REQUEST_PRINTSIM = 4;
	public static final int RUNSIM = 2;
	public static final int PAUSESIM = 5;
	public static final int ENDSIM = 6;

	Thread loop;
	FluidEnvironment enviro;
	int runtime;
	boolean simulating;
	boolean wasPaused;

	public LiquidEngine() {
		super();
	}

	@Override
	public void send(Interfaceable i, int arg0) {
		String[] args;
		if (i instanceof LiquidLogger) {
			switch (arg0) {

			}
		}
		if (i instanceof LiquidGUI) {
			switch (arg0) {
			case REQUEST_DISPLAYSIM:
				args = enviro.getParticleData();
				i.receive(this, LiquidGUI.DISPLAYSIM, args);
			break;
			case REQUEST_PRINTSIM:
				args = new String[1];
				args[0] = "1\n";
				i.receive(this, LiquidGUI.PRINTSIM, args);
			break;
			}
		}
	}

	@Override
	public void receive(Interfaceable i, int arg0, String[] args) {
		if (i instanceof LiquidLogger) {
			switch (arg0) {

			}
		}
		if (i instanceof LiquidGUI) {
			switch (arg0) {
			case RUNSIM:
				if(!simulating){
					initiateSim(args);
					simulating = true;
					loop = new Thread(this);
					loop.start();
				}else{
					loop.resume();
				}
			break;
			case PAUSESIM:
				loop.suspend();
				wasPaused = true;
			break;
			case ENDSIM:
				simulating = false;
			break;
			}
		}
	}

	@Override
	public void run() {
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
		int fps = 0;
		float lastFpsTime = 0;

		int sec = 0;

		while (sec < runtime && simulating) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			if(wasPaused){
				updateLength = 0;
				wasPaused = false;
			}
			float delta = updateLength / ((float) OPTIMAL_TIME);

			lastFpsTime += updateLength;
			fps++;
			
			enviro.update(delta);
			send(LiquidApplication.getGUI(), LiquidGUI.DISPLAYSIM);
			
			if (lastFpsTime >= 1000000000) {
				System.out.println("(FPS: " + fps + ")");
				//send(LiquidApplication.getGUI(), LiquidGUI.PRINTSIM);
				sec += 1;
				lastFpsTime = 0;
				fps = 0;
			}
			
			try {
				Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
			} catch (Exception e) {
			}
		}
	}

	public void initiateSim(String[] args) {
		runtime = Integer.parseInt(args[5]);
		String[] tokens = args[6].split(" ");
		enviro = new FluidEnvironment(Float.parseFloat(tokens[0]),
				Float.parseFloat(tokens[1]));
		float x, y, l, w;
		for (int i = 7; i < args.length; i++) {
			tokens = args[i].split(" ");
			if (tokens[0].equals("Rectangular")) {
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				l = Float.parseFloat(tokens[3]);
				w = Float.parseFloat(tokens[4]);
				PolygonShape shape = new PolygonShape();
				shape.setAsBox(l, w);
				enviro.addObstacle(shape, x, y);
			}
			if (tokens[0].equals("Circular")) {
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				l = Float.parseFloat(tokens[3]);
				w = Float.parseFloat(tokens[4]);
			}
			if (tokens[0].equals("Source")) {

			}
			if (tokens[0].equals("Flowmeter")) {

			}
		}
		enviro.init();
	}
	
	public void dispose(){
		simulating = false;
	}
}
