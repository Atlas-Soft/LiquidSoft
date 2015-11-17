/**
 * 
 */
package liquid.engine;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * @author Rafael Zamora
 *
 */
public class FluidEnvironment {

	Rectangle2D.Float bounds;
	
	ArrayList<Particle> particles;
	ArrayList<Obstacle> obstacles;
	ArrayList<Source> sources;
	ArrayList<Flowmeter> flowmeters;
	
	Area allObstacles;
	Area allParticles;
	
	public FluidEnvironment(float len, float wid){
		bounds = new Rectangle2D.Float(0,0,len,wid);
		particles = new ArrayList<Particle>();
		obstacles = new ArrayList<Obstacle>();
		sources = new ArrayList<Source>();
		flowmeters = new ArrayList<Flowmeter>();
	}
	
	public void init(){
		allObstacles = new Area();
		for(Obstacle o : obstacles){
			allObstacles.add(o.bounds);
		}
		
		for(int i = 0; i < 100; i++){
			for(int j = 0; j < 100; j++){
				Point2D.Float p = new Point2D.Float(i*5+5,j*5+5);
				if(!allObstacles.contains(p) && bounds.contains(p)){
					particles.add(new Particle(p));
				}
			}
		}
		
		particles.get(0).vel.setLocation(10, 0);
	}
	
	public void update(float delta){
		for(Particle p: particles){
			Point2D.Float point = new Point2D.Float((float)(p.loc.getX()+p.vel.getX()*delta), (float)(p.loc.getY()+p.vel.getY()*delta));
			if(!allObstacles.contains(point) && bounds.contains(point)){
				p.loc.setLocation(point);
			}
			else{
				p.vel.setLocation(-p.vel.getX(), -p.vel.getY());
			}
		}
	}
	
	public void addObstacle(Obstacle o){
		obstacles.add(o);
	}
	
	public void addSource(Source s){
		sources.add(s);
	}
	
	public void addFlowMeter(Flowmeter f){
		flowmeters.add(f);
	}
	
	public ArrayList<Particle> getParticles(){
		return particles;
	}
}
