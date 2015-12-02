package liquid.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class is used to write the actual log and config files for the simulation.
 * It also defines a method to set up a new file to save the simulation in.
 */
public class LiquidFileWriter {

	BufferedWriter bw;	//changed to class level variable to keep state of bw
	
	public void initLogFile(String fileName){
		try{	
			bw = new BufferedWriter(new FileWriter(new File(fileName).getAbsoluteFile()));
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Method writes the contents of a String[] onto a text file. This'll
	 * represent the log file, which stores all parameter information.
	 * 
	 * @param fileName - the file's name to store in
	 * @param args    - the String[] of parameters
	 */
	public void writeLogParam(String args[]) {
		try {
			// loops through the list of parameters & prints out its contents
			for (int i = 0; i < args.length; i++) {
				System.out.println(args[i]);
				bw.write(args[i]);
				bw.newLine();
			}
			bw.flush();
			//bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Used to add strings in args to one line of the log file
	 * PRECONDITION: writetoLogFile() <b>MUST</b> be called successfully at some point prior to calling this function
	 * @param args an array of strings to be written to the log file
	 */
	public void writeLogData(String args[]){
		try {
			for (int i = 0; i < args.length; i++) {
//				System.out.println(args[i]);
				bw.write(args[i] + " ");
//				bw.newLine();
			}
			bw.newLine();
			bw.flush();
//			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes file opened in writetoLogFile()
	 */
	public void dispose(){
		try{
			bw.close();
		}
		catch (Exception e){}
	}
}