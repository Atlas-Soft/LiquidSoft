package liquid.logger;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import liquid.core.GlobalVariables;
import liquid.core.LiquidApplication;

/**
 * Class extracts the process of file creation. It is set up to either create a new log file or load an old log file.
 */
public class FileSelection {

	/**
	 * Sets up the file name by first making sure the 'logs' directory is present and filtering all files
	 * expect ones that end in '.log'. Method is used both to set up a new file or load an existing one.
	 * @param set - determines to save or load a file
	 * @return    - the String name of the file
	 */
	public String setUpFile(GlobalVariables.FileAction set) {
		try {
			// sets the conditions for a log file:
			// - Folder is set to be 'logs' | - File must end in '.log'
			JFileChooser fileDialog = new JFileChooser("../logs");
			fileDialog.setAcceptAllFileFilterUsed(false);
			fileDialog.setFileFilter(new FileNameExtensionFilter("Log File", "log"));
			
			switch (set) {
			case Load: // case for when a log file needs to be loaded into the simulator
				fileDialog.setApproveButtonText("Load");
				fileDialog.setDialogTitle("Load Log File");
				return setUpFileToLoad(fileDialog);

			case Save: // case for when a new log file needs to be created for the simulator
				fileDialog.setApproveButtonText("Save");
				fileDialog.setDialogTitle("Create Log File");
				return setUpFileToSave(fileDialog);
			default:}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method used to set up to load a log file. It returns the whole file name, including directory.
	 * @param fileDialog - uses same JFileChooser to keep consistency
	 * @return           - String name of the file
	 */
	private String setUpFileToLoad(JFileChooser fileDialog) {
		// opens up a new dialog box to select the file, and proceeds only when it passes the '.log' ending
		if (fileDialog.showOpenDialog(LiquidApplication.getGUI().getFrame()) == JFileChooser.APPROVE_OPTION) {
			//LiquidApplication.getGUI().getFileVariables().setFileName(fileDialog.getSelectedFile().getName());
			return fileDialog.getSelectedFile().getPath();}
		return null;
	}
	
	/**
	 * Method used to set up a save log file. It also determines whether or not the log file
	 * is saved in the correct directory and whether or not it will override an existing file.
	 * @param fileDialog - uses same JFileChooser to keep consistency
	 * @return           - String name of the file
	 */
	private String setUpFileToSave(JFileChooser fileDialog) {
		File origFile = fileDialog.getCurrentDirectory();

		// when a file name exists and there are unsaved changes, the original file name is recommended
//		if (LiquidApplication.getGUI().getFileVariables().getFileName() != null)
//			fileDialog.setSelectedFile(new File(LiquidApplication.getGUI().getFileVariables().getFileName()));
		
		// continues through the loop only if "Save" has been pressed; otherwise exits
		if (fileDialog.showSaveDialog(LiquidApplication.getGUI().getFrame()) == JFileChooser.APPROVE_OPTION) {
			// checks if file directory is in the "..AtlasSoft\logs\" directory
			if (!fileDialog.getCurrentDirectory().getName().contains("logs")) {
				LiquidApplication.getGUI().getVariousMessages().changedFileDirectory();}

			String filename = origFile + "\\" + fileDialog.getSelectedFile().getName(); 
			if (filename.endsWith(".log") &&
					LiquidApplication.getGUI().getVariousMessages().fileOverride(fileDialog.getSelectedFile().getName()) == JOptionPane.YES_OPTION) {
				//LiquidApplication.getGUI().getFileVariables().onlyFileName = fileDialog.getSelectedFile().getName();
				return filename;
			} else if (!filename.endsWith(".log")) {
				//LiquidApplication.getGUI().getFileVariables().onlyFileName = fileDialog.getSelectedFile().getName()+".log";
				return filename + ".log";
			}
		}
		return null;
	}
}
