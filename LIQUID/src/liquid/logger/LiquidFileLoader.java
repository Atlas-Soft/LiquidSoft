package liquid.logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LiquidFileLoader {

	public LiquidFileLoader(){
		
	}
	
	public String[] loadLogFile(String filename){
		String[] args = new String[1];
		try {
			InputStream is = new FileInputStream("../logs/" +filename);
			///
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return args;
	}
	
	public void loadConfigFile(){
		
	}
}
