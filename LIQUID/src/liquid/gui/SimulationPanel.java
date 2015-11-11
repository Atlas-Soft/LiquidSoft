package liquid.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import liquid.core.LiquidApplication;

public class SimulationPanel extends JPanel implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private JLabel xLabel;
	private JLabel yLabel;
	
	private Rectangle enviroment;
	
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
		
		enviroment = new Rectangle(0,0);
		
		xLabel = new JLabel("X: -");
		xLabel.setBounds(5,0,40,20);
		add(xLabel);
		
		yLabel = new JLabel("Y: -");
		yLabel.setBounds(5,20,40,20);
		add(yLabel);
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g); 
			
        int len = LiquidApplication.getGUI().variables.enviroLength;
        int wid = LiquidApplication.getGUI().variables.enviroWidth;
        enviroment.setBounds((500/2)-(len/2), (400/2)-(wid/2), len, wid);
        g.setColor(Color.black);
        g.drawRect(enviroment.x,enviroment.y,len,wid);
        
        int x,y,l,w;
        for(int i = 0; i < LiquidApplication.getGUI().variables.objects.size(); i++){
        	String[] tokens = LiquidApplication.getGUI().variables.objects.get(i).split(" ");
        	if(i == LiquidApplication.getGUI().variables.selectedObject) g.setColor(Color.blue);
        	else g.setColor(Color.black);
        	
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
        	if(tokens[0].equals("Source")){
        		x = enviroment.x+Integer.parseInt(tokens[1]);
        		y = enviroment.y+Integer.parseInt(tokens[2]);
        		l = Integer.parseInt(tokens[3]);
        		w = Integer.parseInt(tokens[4]);
        		g.drawOval(x-10,y-10,20,20);
        		g.drawString("S", x-3, y+5);
        		g.drawLine(x, y, x+l, y+w);
        	}
        	if(tokens[0].equals("Flowmeter")){
        		x = enviroment.x+Integer.parseInt(tokens[1]);
        		y = enviroment.y+Integer.parseInt(tokens[2]);
        		g.drawOval(x-10,y-10,20,20);
        		g.drawString("F", x-3, y+5);
        	}
        }
    }

	//@Override
	public void mouseClicked(MouseEvent arg0) {}

	//@Override
	public void mouseEntered(MouseEvent arg0) {}

	//@Override
	public void mouseExited(MouseEvent arg0) {
		xLabel.setText("X: -");
		yLabel.setText("Y: -");
	}

	//@Override
	public void mousePressed(MouseEvent arg0) {}

	//@Override
	public void mouseReleased(MouseEvent arg0) {}

	//@Override
	public void mouseDragged(MouseEvent arg0) {}

	//@Override
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
