package liquid.engine;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import liquid.core.Interfaceable;
import liquid.core.LiquidApplication;
import liquid.gui.LiquidGUI;
import liquid.logger.LiquidLogger;

public class LiquidEngine implements Interfaceable, Runnable {

	Thread loop;
	FluidEnvironment enviro;
	int runtime;
	boolean simulating;
	boolean wasPaused;

	public LiquidEngine() {
		super();
	}

	@SuppressWarnings("static-access")
	@Override
	public void send(Interfaceable i, Request request) {
		String[] args;
		
		if (i instanceof LiquidLogger) {
			switch (request) {

			default:
				break;}
		}
		
		if (i instanceof LiquidGUI) {
			switch (request) {
			case REQUEST_DISPLAY_SIM:
				args = enviro.getParticleData();
				i.receive(this, request.DISPLAY_SIM, args);
				break;
			case REQUEST_PRINT_SIM:
				args = new String[1];
				args[0] = "1\n";
				i.receive(this, request.PRINT_SIM, args);
				break;
			default:
				break;}
		}
	}
	
	@Override
	public void receive(Interfaceable i, Request request, String[] args) {
		if (i instanceof LiquidLogger) {
			switch (request) {

			default:
				break;}
		}
		
		
		if (i instanceof LiquidGUI) {
			switch (request) {
			case RUN_SIM:
				if (!simulating) {
					initiateSim(args);
					simulating = true;
					loop = new Thread(this);
					loop.start();
				} else {
					loop.resume();
				}
				break;
			case PAUSE_SIM:
				loop.suspend();
				wasPaused = true;
				break;
			case END_SIM:
				simulating = false;
				break;
			default:
				break;}
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
			send(LiquidApplication.getGUI(), Interfaceable.Request.REQUEST_DISPLAY_SIM);
			
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
				shape.setAsBox(l/2, w/2);
				enviro.addObstacle(shape, (l/2)+x, (w/2)+y);
			}
			if (tokens[0].equals("Circular")) {
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				l = Float.parseFloat(tokens[3]);
				w = Float.parseFloat(tokens[4]);
				PolygonShape shape = new PolygonShape();
				Vec2[] vertices = new Vec2[90];
				for(int t = 0; t < vertices.length; t ++){
					vertices[t] = new Vec2(((l/2)*MathUtils.cos(t*(360.0f/vertices.length)))+(l/2),((w/2)*MathUtils.sin(t*(360.0f/vertices.length))+(w/2))); 	
				}
				shape.set(vertices, vertices.length);
				enviro.addObstacle(shape, x, y);
			}
			if (tokens[0].equals("Source")) {
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				l = Float.parseFloat(tokens[3]);
				w = Float.parseFloat(tokens[4]);
				enviro.addSource(x, y, l, w);
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
