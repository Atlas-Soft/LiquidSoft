package liquid.engine;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import liquid.core.GlobalVar;
import liquid.core.Interfaceable;
import liquid.core.LiquidApplication;
import liquid.gui.LiquidGUI;
import liquid.logger.LiquidLogger;

public class LiquidEngine implements Interfaceable, Runnable {

	Thread loop;
	FluidEnvironment enviro;
	int runtime;
	int sec;
	boolean simulating;
	boolean wasPaused;
	boolean step;

	public LiquidEngine() {
		super();
	}


	@Override
	public void send(Interfaceable i, GlobalVar.Request request) {
		String[] args;
		
		if (i instanceof LiquidLogger) {
			switch (request) {
			case REQUEST_WRITE_LOG_DATA:
				args = enviro.particleLog.toArray(new String[enviro.particleLog.size()]);
				i.receive(this, GlobalVar.Request.WRITE_LOG_DATA, args);
				break;
			default:
				break;}
		}
		
		if (i instanceof LiquidGUI) {
			switch (request) {
			case REQUEST_DISPLAY_SIM:
				args = enviro.getParticleData();
//				enviro.storeData(args);
				i.receive(this, GlobalVar.Request.DISPLAY_SIM, args);
				break;
			case REQUEST_PRINT_SIM:
				args = new String[1];
				args[0] = "-Time " + (sec + 1) + ":\n";
				for (int f = 0; f < enviro.meters.size(); f++){
					args[0] += enviro.meters.get(f).toString();
					args[0] += "\n";
				}
				i.receive(this, GlobalVar.Request.PRINT_SIM, args);
				break;
			case REQUEST_SIM_HAS_ENDED:
				args = new String[0];
				i.receive(this, GlobalVar.Request.SIM_HAS_ENDED, args);
				break;
			default:
				break;}
		}
	}
	
	@Override
	public void receive(Interfaceable i, GlobalVar.Request request, String[] args) {
		if (i instanceof LiquidLogger) {
			switch (request) {

			default:
				break;}
		}
		
		
		if (i instanceof LiquidGUI) {
			switch (request) {
			case RUN_SIM:
				if (!simulating) {
					initSim(args);
					simulating = true;
					loop = new Thread(this);
					loop.start();
				} else loop.resume();
				break;
			case STEP_SIM:
				if (!simulating) {
					initSim(args);
					simulating = true;
					loop = new Thread(this);
					step = true;
					loop.start();
				} else{
					step = true;
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
		long now, updateLength;
		float delta;
		boolean logWrite = false;

		sec = 0;
		
		while (sec < runtime && simulating) {
			now = System.nanoTime();
			updateLength = now - lastLoopTime;
			lastLoopTime = now;
			if(wasPaused){
				if(step) updateLength = 100000000/6;
				else updateLength = 0;
				wasPaused = false;
			}
			
			delta = updateLength / ((float) OPTIMAL_TIME);
			lastFpsTime += updateLength;
			fps++;
			
			enviro.update(delta);
			send(LiquidApplication.getGUI(), GlobalVar.Request.REQUEST_DISPLAY_SIM);
			
			if (lastFpsTime >= 1000000000) {
				System.out.println("(FPS: " + fps + ")");
				send(LiquidApplication.getGUI(), GlobalVar.Request.REQUEST_PRINT_SIM);
				sec += 1;
				lastFpsTime = 0;
				fps = 0;
				logWrite = true;
			}
			
			if (sec % 30 == 0 && logWrite){
				logWrite = false;
				send(LiquidApplication.getLogger(), GlobalVar.Request.REQUEST_WRITE_LOG_DATA);
				enviro.particleLog.clear();
			}
			
			try { Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
			} catch (Exception e) {}
			
			if(step){		
				step = false;
				wasPaused = true;
				loop.suspend();
			}
		}
		send(LiquidApplication.getGUI(), GlobalVar.Request.REQUEST_SIM_HAS_ENDED);
		send(LiquidApplication.getLogger(), GlobalVar.Request.REQUEST_WRITE_LOG_DATA);
	}

	public void initSim(String[] args) {
		runtime = Integer.parseInt(args[5]);
		String[] tokens = args[6].split(" ");
		enviro = new FluidEnvironment(Float.parseFloat(tokens[0]),
				Float.parseFloat(tokens[1]));
		float x, y, l, w, r;
		int ID = 1;
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
			else if (tokens[0].equals("Circular")) {
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
			else if (tokens[0].equals("RectDrain")){
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				l = Float.parseFloat(tokens[3]);
				w = Float.parseFloat(tokens[4]);
				PolygonShape shape = new PolygonShape();
				shape.setAsBox(x + l/2, y + w/2);
				enviro.addDrain(shape);
			}
			else if (tokens[0].equals("CircDrain")){
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				l = Float.parseFloat(tokens[3]);
				w = Float.parseFloat(tokens[4]);
				PolygonShape shape = new PolygonShape();
				Vec2[] vertices = new Vec2[90];
				for(int t = 0; t < vertices.length; t ++){
					vertices[t] = new Vec2(x + ((l/2)*MathUtils.cos(t*(360.0f/vertices.length)))+(l/2), y + ((w/2)*MathUtils.sin(t*(360.0f/vertices.length))+(w/2))); 	
				}
				shape.set(vertices, vertices.length);
				enviro.addDrain(shape);
			}
			
			else if (tokens[0].equals("Source")) {
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				l = Float.parseFloat(tokens[3]);
				w = Float.parseFloat(tokens[4]);
				r = Float.parseFloat(tokens[5]);
				enviro.addSource(x, y, l, w, r);
			}
			else if (tokens[0].equals("Flowmeter")) {
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				enviro.addFlowmeter(x, y, ID++);
			}
		}
		enviro.init();
	}
	
	public void dispose(){
		simulating = false;
	}
}
