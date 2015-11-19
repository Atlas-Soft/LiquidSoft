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

import liquid.core.LiquidApplication;

public class SimulationPanel extends JPanel implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	private float x;
	private float y;
	private JLabel xLabel;
	private JLabel yLabel;
	
	private Rectangle2D.Float environment;
	
	public SimulationPanel(){
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.white);
		setBounds(0,0,500,400);
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);
	}
	
	private void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		environment = new Rectangle2D.Float();
		
		xLabel = new JLabel("X: -");
		xLabel.setBounds(5,0,50,20);
		add(xLabel);
		
		yLabel = new JLabel("Y: -");
		yLabel.setBounds(5,20,50,20);
		add(yLabel);
	}
	
	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		float len = LiquidApplication.getGUI().variables.enviroLength;
        float wid = LiquidApplication.getGUI().variables.enviroWidth;
        environment.setRect((500/2)-(len/2), (400/2)-(wid/2), len, wid);
        g2d.setColor(Color.black);
        g2d.draw(environment);
        
        float x,y,l,w;
        if(LiquidApplication.getGUI().variables.simulating){
        	for(int i = 0; i < LiquidApplication.getGUI().variables.particles.length; i++){
        		String[] tokens = LiquidApplication.getGUI().variables.particles[i].split(" ");
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		g2d.setColor(new Color(1-(1/Float.parseFloat(tokens[3])), 0, 1/Float.parseFloat(tokens[3])));
        		g2d.fill(new Ellipse2D.Float(x-2.5f,y-2.5f,5,5));
        	}
        }
        
        for(int i = 0; i < LiquidApplication.getGUI().variables.objects.size(); i++){
        	String[] tokens = LiquidApplication.getGUI().variables.objects.get(i).split(" ");
        	if(i == LiquidApplication.getGUI().variables.selectedObject && !LiquidApplication.getGUI().variables.simulating) g2d.setColor(Color.green);
        	else g2d.setColor(Color.black);
        	if(tokens[0].equals("Rectangular")){
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		l = Float.parseFloat(tokens[3]);
        		w = Float.parseFloat(tokens[4]);
        		g2d.fill(new Rectangle2D.Float(x,y,l,w));
        	}
        	if(tokens[0].equals("Circular")){
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		l = Float.parseFloat(tokens[3]);
        		w = Float.parseFloat(tokens[4]);
        		g2d.fill(new Ellipse2D.Float(x,y,l,w));
        	}
        	if(tokens[0].equals("Source")){
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		l = Float.parseFloat(tokens[3]);
        		w = Float.parseFloat(tokens[4]);
        		g2d.draw(new Ellipse2D.Float(x-10,y-10,20,20));
        		g2d.drawString("S", x-3, y+5);
        		g2d.draw(new Line2D.Float(x, y, x+l, y+w));
        	}
        	if(tokens[0].equals("Flowmeter")){
        		x = environment.x+Float.parseFloat(tokens[1]);
        		y = environment.y+Float.parseFloat(tokens[2]);
        		g2d.draw(new Ellipse2D.Float(x-10,y-10,20,20));
        		g2d.drawString("F", x-3, y+5);
        	}
        }
        
        
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g); 
		render(g);
    }

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {
		xLabel.setText("X: -");
		yLabel.setText("Y: -");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void mouseDragged(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(!environment.contains(arg0.getPoint())){
			xLabel.setText("X: -");
			yLabel.setText("Y: -");
		}else{
			x = arg0.getX() - environment.x ;
			y = arg0.getY() - environment.y ;
			xLabel.setText("X: "+ x );
			yLabel.setText("Y: "+ y );	
		}
	}
}
