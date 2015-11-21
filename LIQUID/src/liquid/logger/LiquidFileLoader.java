package liquid.logger;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class LiquidFileLoader {
	
	public String[] loadLogFile(String fileName){
		ArrayList<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int i = 0;
			String line = br.readLine();
	        while (line != null) {
	            if(line.equals("break")) break;
	            list.add(line);
	        	line = br.readLine();
	            i += 1;
	        }
	        br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] args = list.toArray(new String[list.size()]);
		return args;
	}
	
	public void loadConfigFile(){
		
	}
	
	public String setUpFileToLoad(JFileChooser fileDialog, Component frame) {
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