package liquid.gui;

import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

public class LiquidMenuBar extends MenuBar{

	public LiquidMenuBar(){
		super();
		initComponents();
	}
	
	private void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		Menu m = new Menu("File");
		MenuItem mi = new MenuItem("New");
		m.add(mi);
		mi = new MenuItem("Load...");
		m.add(mi);
		mi = new MenuItem("Exit");
		m.add(mi);
		add(m);
		
		m = new Menu("Edit");
		mi = new MenuItem("Undo");
		m.add(mi);
		mi = new MenuItem("Redo");
		m.add(mi);
		add(m);
		
		m = new Menu("Help");
		mi = new MenuItem("About");
		m.add(mi);
		add(m);
	}
}
