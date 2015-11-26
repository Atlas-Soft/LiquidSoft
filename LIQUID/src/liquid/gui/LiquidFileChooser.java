package liquid.gui;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

class LiquidFileChooser {

	/**
	 * Sets up the file name by first making sure the 'logs' directory is
	 * present and filtering all files expect ones that end in '.log'. Method
	 * can be used both to set up a new file or load an existing one.
	 * 
	 * @param set
	 *            - determines to save or load a file
	 * @param frame
	 *            - the frame with which JFileChooser will appear in
	 * @return - the String name of the file
	 */
	public static String setUpFile(String set, Component frame) {
		try {
			// sets the conditions for a log file:
			// - Folder is set to be 'logs'
			// - File must end in '.log'
			JFileChooser fileDialog = new JFileChooser("../logs");
			fileDialog.setAcceptAllFileFilterUsed(false);
			fileDialog.setFileFilter(new FileNameExtensionFilter("Log File",
					"log"));

			switch (set) {
			case "LOAD": // case for when a log file needs to be loaded into the
							// simulator
				fileDialog.setApproveButtonText("Load");
				fileDialog.setDialogTitle("Load Log File");
				return setUpFileToLoad(fileDialog, frame);

			case "SAVE": // case for when a new log file needs to be created for
							// the simulator
				fileDialog.setApproveButtonText("Save");
				fileDialog.setDialogTitle("Create Log File");
				return setUpFileToSave(fileDialog, frame);
			default:
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method used to set up a save log file. It also determines whether or not
	 * the log file is saved in the correct directory and whether or not it will
	 * override an existing file.
	 * 
	 * @param fileDialog
	 *            - uses same JFileChooser to keep consistency
	 * @param frame
	 *            - the frame with which to display
	 * @return - String name of the file
	 */
	private static String setUpFileToSave(JFileChooser fileDialog,
			Component frame) {
		File origFile = fileDialog.getCurrentDirectory();

		// continues through the loop only if "Save" has been pressed; otherwise
		// exits
		int saveVal = fileDialog.showSaveDialog(frame);
		if (saveVal == JFileChooser.APPROVE_OPTION) {

			// checks if file directory is in the "..AtlasSoft\logs\" directory
			if (!fileDialog.getCurrentDirectory().getName().contains("logs")) {
				JOptionPane.showMessageDialog(frame,
								"The directory of your log file has been changed to be under "
								+ "AtlasSoft's 'logs'\nfolder to preserve uniformity. Sorry for any inconveniences!",
								"WARNING: Directory Change!!",
								JOptionPane.WARNING_MESSAGE);
			}

			String filename = origFile + "\\"
					+ fileDialog.getSelectedFile().getName();
			System.out.println(filename);
			if (filename.endsWith(".log")) {
				int run = JOptionPane.showConfirmDialog(frame,
						"Are you sure you want to overwrite this log file?",
						"Overwrite File?", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (run == JOptionPane.YES_OPTION)
					return filename;
			} else {
				return filename + ".log";
			}
		}
		return null;
	}

	private static String setUpFileToLoad(JFileChooser fileDialog,
			Component frame) {
		// opens up a new dialog box to select the file,
		// and proceeds only when it passes the '.log' ending
		int loadVal = fileDialog.showOpenDialog(frame);
		if (loadVal == JFileChooser.APPROVE_OPTION) {
			// sets filename to be the chosen file's name, and calls
			// the Logger to obtain and set the necessary parameters
			return fileDialog.getSelectedFile().getPath();
		}
		return null;
	}

}
