package liquid.gui;

import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SimulationPanel extends Panel implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private Label xLabel;
	private Label yLabel;
	
	public SimulationPanel(){
		super();
		initComponents();
		setLayout(null);
		setBounds(0,45,500,365);
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);
	}
	
	private void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		xLabel = new Label("X: -");
		xLabel.setBounds(5,0,50,20);
		add(xLabel);
		
		yLabel = new Label("Y: -");
		yLabel.setBounds(5,20,50,20);
		add(yLabel);
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
		x = arg0.getX();
		y = arg0.getY();
		xLabel.setText("X: "+ x );
		yLabel.setText("Y: "+ y );	
	}
}
