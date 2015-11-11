package liquid.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
}
