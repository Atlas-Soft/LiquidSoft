package liquid.logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class is used to load the config file information for the current program. It also loads
 * the parameters of log files to, in a sense, "replay" that particular saved simulation.
 */
public class LiquidFileLoader {
	
	/**
	 * Method attempts to read the specified config file, then stores all of
	 * the information onto an ArrayList and sent out as a String[] to read.
	 * @param fileName - name of config file to read
	 * @return		   - String[] of liquid type information
	 */
	public String[] loadConfigFile(String fileName) {
		ArrayList<String> list = new ArrayList<String>();
		
		// tries to find the text document with the given file name
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			while (line != null) {
				if (line.equals("break")) break;
				list.add(line);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list.toArray(new String[list.size()]);
	}
	
	/**
	 * Method attempts to read the specified log file, then stores all of
	 * the information onto an ArrayList and sent out as a String[] to read.
	 * @param fileName - name of log file to read
	 * @return		   - String[] of parameter information
	 */
	public String[] loadLogParam(String fileName) {
		ArrayList<String> list = new ArrayList<String>();
		
		// tries to find the text document with the given file name
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
	        while (line != null) {
	            if (line.equals("break")) break;
	            list.add(line);
	        	line = br.readLine();
	        }
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list.toArray(new String[list.size()]);
	}
}