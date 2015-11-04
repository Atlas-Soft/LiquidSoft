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
	
	private MenuItem New;
	private MenuItem load;
	private MenuItem exit;
	private MenuItem undo;
	private MenuItem redo;
	private MenuItem about;
	
	private String logfile;

	public LiquidMenuBar(){
		super();
		initComponents();
	}
	
	private void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		Menu m = new Menu("File");
		
		New = new MenuItem("New");
		m.add(New);
		
		load = new MenuItem("Load...");
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				JFileChooser  fileDialog = new JFileChooser("../logs");
				fileDialog.setDialogTitle("Load Log File");
				fileDialog.setFileFilter(new FileNameExtensionFilter("Log File", "log"));
				int returnVal = fileDialog.showOpenDialog(LiquidApplication.getGUI().frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		              logfile = fileDialog.getSelectedFile().getName();
		              LiquidApplication.getGUI().send(LiquidApplication.getLogger(), LiquidGUI.REQUEST_LOADLOG);
		        }
			}
        });
		m.add(load);
		
		exit = new MenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().frame.dispose();
				System.exit(0);
			}
        });
		m.add(exit);
		add(m);
		
		m = new Menu("Edit");
		
		undo = new MenuItem("Undo");
		m.add(undo);
		
		redo = new MenuItem("Redo");
		m.add(redo);
		add(m);
		
		m = new Menu("Help");
		
		about = new MenuItem("About");
		m.add(about);
		add(m);
	}
	
	public String getLoadLogFile(){
		return logfile;
	}
}
