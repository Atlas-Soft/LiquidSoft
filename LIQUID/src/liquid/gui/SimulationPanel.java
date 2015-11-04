package liquid.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import liquid.core.LiquidApplication;

public class SimulationPanel extends Panel implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	private Rectangle anchor;
	private int x;
	private int y;
	private Label xLabel;
	private Label yLabel;
	
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
		
		anchor = new Rectangle(0,0);
		
		xLabel = new Label("X: -");
		xLabel.setBounds(5,0,50,20);
		add(xLabel);
		
		yLabel = new Label("Y: -");
		yLabel.setBounds(5,20,50,20);
		add(yLabel);
	}
	
	public void paint(Graphics g) {
        super.paint(g);       
        int len = LiquidApplication.getGUI().variables.enviroLength;
        int wid = LiquidApplication.getGUI().variables.enviroWidth;
        anchor.setBounds((500/2)-(len/2), (365/2)-(wid/2), len, wid);
        g.drawRect(anchor.x,anchor.y,len,wid);
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
		if(!anchor.contains(arg0.getPoint())){
			xLabel.setText("X: -");
			yLabel.setText("Y: -");
		}else{
			x = arg0.getX() - anchor.x ;
			y = arg0.getY() - anchor.y ;
			xLabel.setText("X: "+ x );
			yLabel.setText("Y: "+ y );	
		}
	}
}
