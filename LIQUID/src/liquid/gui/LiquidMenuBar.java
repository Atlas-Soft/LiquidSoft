package liquid.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import liquid.core.LiquidApplication;

public class LiquidMenuBar extends JMenuBar{

	private static final long serialVersionUID = 1L;
	
	JMenuItem New;
	JMenuItem load;
	JMenuItem exit;
	JMenuItem undo;
	JMenuItem redo;
	JMenuItem about;
	
	String log_filename;

	public LiquidMenuBar(){
		super();
		initComponents();
	}
	
	private void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		JMenu m = new JMenu("File");
		
		New = new JMenuItem("New");
		New.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().reset();
			}
        });
		m.add(New);
		
		load = new JMenuItem("Load...");
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				JFileChooser  fileDialog = new JFileChooser("../logs");
				fileDialog.setDialogTitle("Load Log File");
				fileDialog.setFileFilter(new FileNameExtensionFilter("Log File", "log"));
				int returnVal = fileDialog.showOpenDialog(LiquidApplication.getGUI().frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					LiquidApplication.getGUI().variables.filename = fileDialog.getSelectedFile().getPath();
		            LiquidApplication.getGUI().send(LiquidApplication.getLogger(), LiquidGUI.REQUEST_LOADLOG);
		        }
			}
        });
		m.add(load);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().frame.dispose();
				System.exit(0);
			}
        });
		m.add(exit);
		add(m);
		
		m = new JMenu("Edit");
		
		undo = new JMenuItem("Undo");
		undo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		m.add(undo);
		
		redo = new JMenuItem("Redo");
		m.add(redo);
		redo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		add(m);
		
		m = new JMenu("Help");
		
		about = new JMenuItem("About");
		m.add(about);
		add(m);
	}
}
