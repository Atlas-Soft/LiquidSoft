package liquid.logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LiquidFileLoader {

	public LiquidFileLoader(){
		
	}
	
	public void loadLogFile(String filename){
		try {
			InputStream is = new FileInputStream("../logs/" +filename);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfigFile(){
		
	}
}
