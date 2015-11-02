package liquid.gui;

import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import liquid.core.LiquidApplication;

public class LiquidMenuBar extends MenuBar{

	private static final long serialVersionUID = 1L;
	
	private String logfile;

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
		mi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				 JFileChooser  fileDialog = new JFileChooser("../logs");
				 fileDialog.setDialogTitle("Load Log File");
				 fileDialog.setFileFilter(new FileNameExtensionFilter("Log File", "log"));
				 int returnVal = fileDialog.showOpenDialog(LiquidApplication.getGUI().getLiquidFrame());
				 if (returnVal == JFileChooser.APPROVE_OPTION) {
		               logfile = fileDialog.getSelectedFile().getName();
		               LiquidApplication.getGUI().send(LiquidApplication.getLogger(), LiquidGUI.REQUEST_LOADLOG);
		            }
			}
        });
		m.add(mi);
		mi = new MenuItem("Exit");
		mi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				 LiquidApplication.getGUI().getLiquidFrame().dispose();
				 System.exit(0);
			}
        });
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
	
	public String getLoadLogFile(){
		return logfile;
	}
}
