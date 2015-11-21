package liquid.logger;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LiquidFileWriter {

	public void writetoLogFile(String fileName, String args[]){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			
			for (int i = 0; i < args.length; i++) {
				System.out.println(args[i]);
				bw.write(args[i]);
				bw.newLine();
			}
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void appendtoLogFile(String fileName, String arg){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(arg);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String setUpFileToSave(JFileChooser fileDialog, Component frame) {
		File origFile = fileDialog.getCurrentDirectory();
		
		// continues through the loop only if "Save" has been pressed; otherwise exits
		int saveVal = fileDialog.showSaveDialog(frame);
		if (saveVal == JFileChooser.APPROVE_OPTION) {
		
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
		return null;
	}
}