package liquid.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LiquidFileWriter {

	public void appendtoLogFile(String fileName, String arg){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(arg);
			bw.close();		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
