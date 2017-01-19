package liquid.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import liquid.core.GlobalVariables;

/**
 * SimulationPanel class creates a panel to display the simulation itself, currently located in the upper lefthand corner. All objects created
 * from the EditorPanel classes--if any present--will be drawn into the current simulation, such as obstacles, drains, sources, flowmeters, and
 * breakpoints. Once a simulation begins, liquid particles of various colors appear from the source(s) & the various objects function according.
 */
public class SimulationPanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private Rectangle2D.Float environment;
	private Graphics2D g2d;
	private SimulationPanelRender simulationPanelRender = new SimulationPanelRender();
	private SimulationPanelMouse  simulationPanelMouse  = new SimulationPanelMouse();

	// ArrayLists store all rectangular/circular shapes & their indexes, respectively
	ArrayList<Rectangle2D> rectangleList      = new ArrayList<Rectangle2D>();
	ArrayList<String>      rectangleIndexList = new ArrayList<String>();
	ArrayList<Ellipse2D>   circleList         = new ArrayList<Ellipse2D>();
	ArrayList<String>      circleIndexList    = new ArrayList<String>();
	
	/**
	 * Constructor sets various settings for the simulation panel, such as mouse capabilities.
	 */
	public SimulationPanel() {
		super();
		initComponents();
		
		// allows manual setup of layout settings
		setLayout(null);
		setBackground(Color.WHITE);
		setBounds(0,0,501,401);

		// allows user to interact with & move objects using a mouse
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);
	}
	
	/**
	 * Method initializes the overall environment of a simulation.
	 */
	private void initComponents() {
		setFont(GlobalVariables.font);
		environment = new Rectangle2D.Float();
	}
	
	/**
	 * Method represents the paint component, which does the actually drawing of the environment, particles, & objects.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D) g;

		// begins initialization process of various item rendering
		simulationPanelRender.render(g2d,environment);
    }
	
	/**
	  * Method represents an implementation placeholder for the mouse clicking the simulation panel.
	  * Per testing results, <code>mouseClicked()</code> events also triggers <code>mousePressed()</code>
	  * events; all <code>mouseClicked()</code> relevant code have been removed to reduce redundancy.
	  */
	@Override
	public void mouseClicked(MouseEvent mouse) {}

	/**
	 * Method repaints the simulation panel whenever the mouse drags an object.
	 */
	@Override
	public void mouseDragged(MouseEvent mouse) {
		if (simulationPanelMouse.mouseDragged(mouse,environment))
			repaint();
	}
	
	/*** Method represents an implementation placeholder for the mouse entering the simulation panel. */
	@Override
	public void mouseEntered(MouseEvent mouse) {}

	/*** Method represents an implementation placeholder for the mouse exiting the simulation panel. */
	@Override
	public void mouseExited(MouseEvent mouse) {}
	
	/*** Method represents an implementation placeholder for the mouse moving in the simulation panel. */
	@Override
	public void mouseMoved(MouseEvent mouse) {}
	
	/**
	 * Method repaints the simulation panel whenever the mouse presses an object.
	 */
	@Override
	public void mousePressed(MouseEvent mouse) {
		if (simulationPanelMouse.mousePressed(mouse,environment,rectangleList,rectangleIndexList,circleList,circleIndexList))
			repaint();
	}

	/**
	 * Method represents resulted actions whenever the mouse releases an object.
	 */
	@Override
	public void mouseReleased(MouseEvent mouse) {
		simulationPanelMouse.mouseReleased(mouse);
	}
	
	/*
	 * Method clears all class lists (rectangles, circles, indexes).
	 */
	public void clearAllLists() {
		rectangleList.clear();
		rectangleIndexList.clear();
		circleList.clear();
		circleIndexList.clear();
	}
	
	/**
	 * Getter method returns the rectangular objects list.
	 * @return - rectangular objects list
	 */
	public ArrayList<Rectangle2D> getRectangleList() {return rectangleList;}
	
	/**
	 * Getter method returns the rectangular objects' indexes list.
	 * @return - rectangular objects' indexes list
	 */
	public ArrayList<String> getRectangleIndexList() {return rectangleIndexList;}
	
	/**
	 * Getter method returns the circular objects list.
	 * @return - circular objects list
	 */
	public ArrayList<Ellipse2D> getCircleList() {return circleList;}
	
	/**
	 * Getter method returns the circular objects' indexes list.
	 * @return - circular objects' indexes list
	 */
	public ArrayList<String> getCircleIndexList() {return circleIndexList;}
}