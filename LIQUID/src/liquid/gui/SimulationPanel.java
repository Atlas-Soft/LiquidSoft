package liquid.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import liquid.core.LiquidApplication;

public class SimulationPanel extends Panel implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private Label xLabel;
	private Label yLabel;
	
	private Rectangle enviroment;
	
	public SimulationPanel(){
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.white);
		setBounds(0,60,500,365);
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);
	}
	
	private void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		enviroment = new Rectangle(0,0);
		
		xLabel = new Label("X: -");
		xLabel.setBounds(5,0,40,20);
		add(xLabel);
		
		yLabel = new Label("Y: -");
		yLabel.setBounds(5,20,40,20);
		add(yLabel);
	}
	
	public void paint(Graphics g) {
        super.paint(g); 
              
        int len = LiquidApplication.getGUI().variables.enviroLength;
        int wid = LiquidApplication.getGUI().variables.enviroWidth;
        enviroment.setBounds((500/2)-(len/2), (365/2)-(wid/2), len, wid);
        g.drawRect(enviroment.x,enviroment.y,len,wid);
        
        int x,y,l,w;
        for(String obj : LiquidApplication.getGUI().variables.obstacles){
        	String[] tokens = obj.split(" ");      	
        	if(tokens[0].equals("Rectangular")){
        		x = enviroment.x+Integer.parseInt(tokens[1]);
        		y = enviroment.y+Integer.parseInt(tokens[2]);
        		l = Integer.parseInt(tokens[3]);
        		w = Integer.parseInt(tokens[4]);
        		g.fillRect(x,y,l,w);
        	}
        	if(tokens[0].equals("Circular")){
        		x = enviroment.x+Integer.parseInt(tokens[1]);
        		y = enviroment.y+Integer.parseInt(tokens[2]);
        		l = Integer.parseInt(tokens[3]);
        		w = Integer.parseInt(tokens[4]);
        		g.fillOval(x,y,l,w);
        	}
        }
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
		if(!enviroment.contains(arg0.getPoint())){
			xLabel.setText("X: -");
			yLabel.setText("Y: -");
		}else{
			x = arg0.getX() - enviroment.x ;
			y = arg0.getY() - enviroment.y ;
			xLabel.setText("X: "+ x );
			yLabel.setText("Y: "+ y );	
		}
	}
}
