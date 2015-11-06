package liquid.logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LiquidFileLoader {
	
	public String[] loadLogFile(String fileName){
		String[] args = new String[1];
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			int i = 0;
			String line = br.readLine();
	        while (line != null) {
	            switch(i){
	            case 0:
	            	args[0] = line;
	            break;
	            }
	        	line = br.readLine();
	            i += 1;
	        }
	        br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return args;
	}
	
	public void loadConfigFile(){
		
	}
}
