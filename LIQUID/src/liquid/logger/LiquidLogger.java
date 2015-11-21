package liquid.logger;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import liquid.core.Interfaceable;
import liquid.gui.LiquidGUI;

/**
 * LiquidLogger is the main class of the Logger component. Here,
 * the methods to write or load a log file are initialized.
 * 
 * This class interacts with both the GUI and Engine.
 */
public class LiquidLogger implements Interfaceable {
	
	// initializes the variables to write or load a log file,
	// as well as the file name of the current simulation
	private LiquidFileLoader fileLoader;
	private LiquidFileWriter fileWriter;
	private String currentFile;
	
	/**
	 * Constructor initializes the Logger component of the simulation.
	 */
	public LiquidLogger() {
		init();
	}
	
	/**
	 * Initializes the necessary parts to write or load a log file.
	 * Method also checks that the log and config files are located
	 * in the correct directory and contains the correct ending.
	 */
	private void init(){
		fileLoader = new LiquidFileLoader();
		fileWriter = new LiquidFileWriter();
		File f = new File("../logs");
		if (!(f.exists() && f.isDirectory())) f.mkdirs();
			
		f = new File("../configs");
		if (f.exists() && f.isDirectory()) {
			
		}
		else{
			f.mkdirs();
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void send(Interfaceable i, Request request) {
		if(i instanceof LiquidGUI){
			switch(request){
			case REQUEST_SET_LOG_PARAM:
				String[] args = fileLoader.loadLogFile(currentFile);
				i.receive(this, request.SET_LOG_PARAM, args);
				break;
			default:
				break;}
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void receive(Interfaceable i, Request request, String[] args) {
		if(i instanceof LiquidGUI){
			switch(request){
			case LOAD_LOG:
				currentFile = args[0];
				send(i, request.REQUEST_SET_LOG_PARAM);
				break;
			case WRITE_LOG:
				currentFile = args[0];
				fileWriter.writetoLogFile(currentFile, args);
				break;
			default:
				break;}
		}
	}
	
	public String setUpFile(JFileChooser fileDialog, String set, Component frame) {
		// sets the conditions for a log file:
		//  - Folder is set to be 'logs'
		//  - File must end in '.log'
		fileDialog.setAcceptAllFileFilterUsed(false);
		fileDialog.setFileFilter(new FileNameExtensionFilter("Log File", "log"));
		
		switch (set) {
		case "SAVE":
			fileDialog.setApproveButtonText("Save");
			fileDialog.setDialogTitle("Create Log File");
			File origFile = fileDialog.getCurrentDirectory();
			
			// continues through the loop only if "Save" has been pressed; otherwise exits
			int returnVal = fileDialog.showSaveDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				// checks if file directory is in the "..AtlasSoft\logs\" directory
				File currFile = fileDialog.getCurrentDirectory();
				if (!currFile.getName().contains("logs")) {
					JOptionPane.showMessageDialog(frame, "The directory of your log file has been changed to be under " +
						"AtlasSoft's 'logs'\nfolder to preserve uniformity. Sorry for any inconveniences!",
						"WARNING: Directory Change!!", JOptionPane.WARNING_MESSAGE);}
				
				String filename = origFile + "\\" + fileDialog.getSelectedFile().getName();
				if (filename.endsWith(".log")) {
					int run = JOptionPane.showConfirmDialog(frame, "Are you sure you want to overwrite this log file?",
							"Overwrite File?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (run == JOptionPane.YES_OPTION)
						return filename;
				} else {
					return filename + ".log";}
			}
			break;
		case "LOAD":
			fileDialog.setApproveButtonText("Load");
			fileDialog.setDialogTitle("Load Log File");
			break;
		default:}
		return null;
	}
	
	public void dispose(){
		
	}
}