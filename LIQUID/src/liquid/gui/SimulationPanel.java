package liquid.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import liquid.core.GlobalVar;
import liquid.core.LiquidApplication;

/**
 * Class creates a panel to display the simulation itself. All objects created in the
 * EnvironmentEditorPanel (if any present) will be drawn into the current simulation.
 */
public class SimulationPanel extends JPanel implements MouseListener, MouseMotionListener {
	
	// variables used to display and determine the X/Y location
	private static final long serialVersionUID = 1L;
	private float x;
	private float y;
	private JLabel xLabel;
	private JLabel yLabel;
	private Rectangle2D.Float environment;
	
	/**
	 * Constructor for the SimulationPanel. It is currently located on the lower left-hand side of the simulator. 
	 */
	public SimulationPanel() {
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.white);
		setBounds(0,0,501,401);
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);
	}
	
	/**
	 * Initializes the components of the SimulationPanel.
	 */
	private void initComponents() {
		Font font = new Font("Verdana",Font.BOLD,12);
		setFont(font);
		
		environment = new Rectangle2D.Float();
		
		// creates labels to show the user the current X/Y location
		xLabel = new JLabel("X: -");
		xLabel.setBounds(5,0,50,20);
		add(xLabel);
		
		yLabel = new JLabel("Y: -");
		yLabel.setBounds(5,20,50,20);
		add(yLabel);
	}
	
	/**
	 * Method creates all components in the simulation panel. This includes the color of a particle,
	 * as well as the various objects--obstacles, drains, sources, flow meters, and breakpoints
	 * @param g - graphics to be casted to a 2D graphics
	 */
	public void render(Graphics g) {
		// creates the actual rectangular simulation panel
		Graphics2D g2d = (Graphics2D) g;
		float len = LiquidApplication.getGUI().variables.enviroLength;
        float wid = LiquidApplication.getGUI().variables.enviroWidth;
        environment.setRect(((500/2)-(len/2)),((400/2)-(wid/2)),len,wid);
        g2d.setColor(Color.BLACK);
        g2d.draw(environment);
        
        // sets the color of the particles depending on the flow speed
        float x,y,l,w,r;
        if (LiquidApplication.getGUI().variables.simulating) {
        	for (int i = 1; i < LiquidApplication.getGUI().variables.particles.length; i++) {
        		String[] tokens = LiquidApplication.getGUI().variables.particles[i].split(" ");
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		g2d.setColor(new Color(1-(1/(Float.parseFloat(tokens[3])*1.3f)),0.25f,(1/(Float.parseFloat(tokens[3])*1.3f))));
        		g2d.fill(new Ellipse2D.Float((x-2.5f),(y-2.5f),5,5));
        	}
        }
        
        // creates objects when there are items in the String[] for objects
        int FID = 1;
        int BID = 1;
        for (int i = 0; i < LiquidApplication.getGUI().variables.objects.size(); i++) {
        	String[] tokens = LiquidApplication.getGUI().variables.objects.get(i).split(" ");
        	
        	// sets the currently selected object to be the color green
        	if (i == LiquidApplication.getGUI().variables.selectedObject && !LiquidApplication.getGUI().variables.simulating) g2d.setColor(Color.GREEN);
        	else g2d.setColor(Color.BLACK);
        	
        	// creates an object based on the type, in this instance a rectangular obstacle
        	if (tokens[0].equals(GlobalVar.ObsType.Rectangular.toString())) {
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		l = Float.parseFloat(tokens[3]);
        		w = Float.parseFloat(tokens[4]);
        		r = Float.parseFloat(tokens[5]);
        		g2d.rotate(Math.toRadians(r),x+(l/2),y+(w/2));
        		g2d.fill(new Rectangle2D.Float(x,y,l,w));
        		g2d.rotate(Math.toRadians(-r),x+(l/2),y+(w/2));    
        		
        	// in this instance, creates a rectangular drain
        	} else if (tokens[0].equals(GlobalVar.ObsType.Rect_Drain.toString())) {
           		x = environment.x+Float.parseFloat(tokens[1]);
           		y = environment.y+Float.parseFloat(tokens[2]);
           		l = Float.parseFloat(tokens[3]);
           		w = Float.parseFloat(tokens[4]);
           		r = Float.parseFloat(tokens[5]);
           		g2d.rotate(Math.toRadians(r),x+(l/2),y+(w/2));
           		g2d.draw(new Rectangle2D.Float(x,y,l,w));
           		g2d.rotate(Math.toRadians(-r),x+(l/2),y+(w/2)); 
            	
        	// in this instance, creates a circular obstacle
        	} else if (tokens[0].equals(GlobalVar.ObsType.Circular.toString())) {
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		l = Float.parseFloat(tokens[3]);
        		w = Float.parseFloat(tokens[4]);
        		r = Float.parseFloat(tokens[5]);
           		g2d.rotate(Math.toRadians(r),x+(l/2),y+(w/2));
        		g2d.fill(new Ellipse2D.Float(x,y,l,w));
        		g2d.rotate(Math.toRadians(-r),x+(l/2),y+(w/2)); 
        	
        	// in this instance, creates a circular drain
        	} else if (tokens[0].equals(GlobalVar.ObsType.Circ_Drain.toString())) {
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		l = Float.parseFloat(tokens[3]);
        		w = Float.parseFloat(tokens[4]);
        		r = Float.parseFloat(tokens[5]);
           		g2d.rotate(Math.toRadians(r),x+(l/2),y+(w/2));
        		g2d.draw(new Ellipse2D.Float(x,y,l,w));
        		g2d.rotate(Math.toRadians(-r),x+(l/2),y+(w/2)); 
        	
        	// in this instance, creates a source
        	} else if (tokens[0].equals(GlobalVar.EnviroOptions.Sources.toString())) {
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		l = Float.parseFloat(tokens[3]);
        		w = Float.parseFloat(tokens[4]);
        		g2d.draw(new Ellipse2D.Float((x-10),(y-10),20,20));
        		g2d.drawString("S",(x-3),(y+5));
        		g2d.draw(new Line2D.Float(x,y,(x+l),(y+w)));
        	
        	// in this instance, creates a flow meter
        	} else if (tokens[0].equals(GlobalVar.EnviroOptions.Flowmeters.toString())) {
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		g2d.draw(new Ellipse2D.Float((x-10),(y-10),20,20));
        		g2d.drawString("F",(x-6),(y+5));
        		g2d.drawString((FID++ +""),(x-1),(y+5));
        	
        	// in this instance, creates a breakpoint
        	} else if (tokens[0].equals(GlobalVar.EnviroOptions.Breakpoints.toString())) {
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		l = Float.parseFloat(tokens[3]);
        		w = Float.parseFloat(tokens[4]);
        		g2d.draw(new Rectangle2D.Float(x,y,l,w));
        		x += .5 * l;
        		y += .5 * w;
        		g2d.drawString("B",(x-6),(y+5));
        		g2d.drawString((BID++ +""),(x-1),(y+5));
        	}
        }
	}
	
	/**
	 * Method is the paint component, which does the actually drawing in the simulation panel.
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g); 
		render(g);
    }

	/**
	 * Method represents a placeholder for the implementation of mouse clicking.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	/**
	 * Method represents a placeholder for the implementation of when the mouse enters a component.
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	/**
	 * Method sets the X/Y location text to be "-" when the mouse moves out of the simulation panel.
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		xLabel.setText("X: -");
		yLabel.setText("Y: -");
	}

	/**
	 * Method represents a placeholder for the implementation of when the mouse presses something.
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {}

	/**
	 * Method represents a placeholder for the implementation of when the mouse releases something.
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {}

	/**
	 * Method represents a placeholder for the implementation of when the mouse drags something.
	 */
	@Override
	public void mouseDragged(MouseEvent arg0) {}

	/**
	 * Method sets the X/Y location text to be the X-/Y-Coordinates relative to the environment's size.
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// for instances when the mouse is outside the specified environment
		if (!environment.contains(arg0.getPoint())) {
			xLabel.setText("X: -");
			yLabel.setText("Y: -");
		} else {
			x = arg0.getX()-environment.x;
			y = arg0.getY()-environment.y;
			xLabel.setText("X: "+x);
			yLabel.setText("Y: "+y);	
		}
	}
}