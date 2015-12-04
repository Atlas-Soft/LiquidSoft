package liquid.logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LiquidFileLoader {
	
	public String[] loadLogParam(String fileName){
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
		return list.toArray(new String[list.size()]);
	}
	
	public String[] loadConfigFile(String fileName) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int i = 0;
			String line = br.readLine();
			while (line != null) {
				if (line.equals("break")) break;
				list.add(line);
				line = br.readLine();
				i += 1;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list.toArray(new String[list.size()]);
	}
}