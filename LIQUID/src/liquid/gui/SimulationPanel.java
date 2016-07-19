package liquid.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * SimulationPanel class creates a panel (currently located in the upper lefthand corner) to display the simulation itself. All objects created
 * in the EnvironmentEditorPanels--if any present--will be drawn into the current simulation, such as obstacles, drains, sources, breakpoints &
 * flowmeters. Once a simulation begins, liquid particles of various colors appear starting from the source(s) & function according to an object.
 */
public class SimulationPanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private Rectangle2D.Float environment;
	private Graphics2D g2d;
	SimulationPanelMouse simPanelMouse = new SimulationPanelMouse();	

	// ArrayLists store all rectangular/circular shapes & their indexes respectively
	ArrayList<Rectangle2D> rectList; ArrayList<String> rectIndexList;
	ArrayList<Ellipse2D> circList;   ArrayList<String> circIndexList;
	
	/**
	 * Constructor for the SimulationPanel sets up various settings for the panel, currently located in the upper lefthand corner of the simulator. 
	 */
	public SimulationPanel() {
		super(); initComponents();
		
		// allows manual setup of layout settings
		setLayout(null);
		setBackground(Color.WHITE); setBounds(0,0,501,401);

		// allows user to interact with & move objects using a mouse
		addMouseListener(this); addMouseMotionListener(this);
		setVisible(true);
	}
	
	/**
	 * Method initializes the basic components of the SimulationPanel class.
	 */
	private void initComponents() {
		setFont(new Font("Verdana",Font.BOLD,12));
		environment = new Rectangle2D.Float();
	}
	
	/**
	 * Method represents the paint component, which does the actually drawing in the SimulationPanel class.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D) g;

		// begins initialization process of environment, particles & objects
		SimulationPanelRender sumPanelRender = new SimulationPanelRender();
		sumPanelRender.render(g2d,environment);
    }
	
	/**
	  * Method repaints the simulation panel whenever the mouse merely clicks on an object.
	  * The details have been extracted into another class to prevent clustering of code.
	  */
	@Override
	public void mouseClicked(MouseEvent mouse) {
		if (simPanelMouse.mouseClicked(mouse,environment,rectList,rectIndexList,circList,circIndexList)) repaint();}

	/**
	 * Method repaints the simulation panel whenever the mouse begins to drag an object.
	 * The details have been extracted into another class to prevent clustering of code.
	 */
	@Override
	public void mouseDragged(MouseEvent mouse) {
		if (simPanelMouse.mouseDragged(mouse)) repaint();}
	
	/*** Method represents a placeholder for the implementation of the mouse entering the SimulationPanel. */
	@Override
	public void mouseEntered(MouseEvent mouse) {}

	/*** Method represents a placeholder for the implementation of the mouse exiting the SimulationPanel. */
	@Override
	public void mouseExited(MouseEvent mouse) {}
	
	/*** Method represents a placeholder for the implementation of the mouse moving in the SimulationPanel. */
	@Override
	public void mouseMoved(MouseEvent mouse) {}
	
	/**
	 * Method represents resulted actions whenever the mouse presses an object. The
	 * details have been extracted into another class to prevent clustering of code.
	 */
	@Override
	public void mousePressed(MouseEvent mouse) {
		simPanelMouse.mousePressed(mouse,environment,rectList,rectIndexList,circList,circIndexList);}

	/**
	 * Method represents resulted actions whenever the mouse releases an object. The
	 * details have been extracted into another class to prevent clustering of code.
	 */
	@Override
	public void mouseReleased(MouseEvent mouse) {
		simPanelMouse.mouseReleased(mouse);}
	
	/**
	 * Setter method creates a new instance of the rectangular objects list.
	 */
	public void setRectList() {rectList = new ArrayList<Rectangle2D>();}
	
	/**
	 * Getter method returns the rectangular objects list.
	 * @return - the rectangular objects list
	 */
	public ArrayList<Rectangle2D> getRectList() {return rectList;}
	
	/**
	 * Setter method creates a new instance of the rectangular objects' indexes list.
	 */
	public void setRectIndexList() {rectIndexList = new ArrayList<String>();}
	
	/**
	 * Getter method returns the rectangular objects' indexes list.
	 * @return - the rectangular objects' indexes list
	 */
	public ArrayList<String> getRectIndexList() {return rectIndexList;}
	
	/**
	 * Setter method creates a new instance of the circular objects list.
	 */
	public void setCircList() {circList = new ArrayList<Ellipse2D>();}
	
	/**
	 * Getter method returns the circular objects list.
	 * @return - the circular objects list
	 */
	public ArrayList<Ellipse2D> getCircList() {return circList;}
	
	/**
	 * Setter method creates a new instance of the circular objects' indexes list.
	 */
	public void setCircIndexList() {circIndexList = new ArrayList<String>();}
	
	/**
	 * Getter method returns the circular objects' indexes list.
	 * @return - the circular objects' indexes list
	 */
	public ArrayList<String> getCircIndexList() {return circIndexList;}
}