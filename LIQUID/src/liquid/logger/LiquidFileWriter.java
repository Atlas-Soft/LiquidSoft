package liquid.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class is used to write the log files of a simulation. This includes the parameters as well
 * as the individual particles, flow meters, and breakpoints data, provided by the Engine.
 */
public class LiquidFileWriter {

	// changed to class level variable to keep state of BufferedWriter
	BufferedWriter bw;
	
	/**
	 * Method initializes the BufferedWriter component to begin writing
	 * a log file, all beginning with the name of the log file.
	 * @param fileName - name of log file to write to
	 */
	public void initLogFile(String fileName) {
		try {
			bw = new BufferedWriter(new FileWriter(new File(fileName)));
		} catch (IOException e) {
			e.printStackTrace();}
	}
	
	/**
	 * Method writes the contents of the String[] onto a text file. This represents
	 * the log file, which stores all parameter information for future replaying.
	 * @param fileName - name of log file to write to
	 * @param args     - the String[] of parameters
	 */
	public void writeLogParam(String args[]) {
		try {
			// loops through the list of parameters & prints out its contents
			for (int i = 0; i < args.length; i++) {
				bw.write(args[i]);
				bw.newLine();
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();}
	}
	
	/**
	 * Method used to add a string of data into the log file, all in one line. <p>PRECONDITION: initLogFile()
	 * method <b>MUST</b> be called successfully at some point prior to calling this function.</p>
	 * @param args - String[] to be written into the log file
	 */
	public void writeLogData(String args[]) {
		try {
			// loops through and prints out each part of the String[], all separated by a space
			for (int i = 0; i < args.length; i++) {
				bw.write(args[i] + " ");
			}
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();}
	}
	
	/**
	 * Method disposes of the BufferedWriter previously-opened for the simulation.
	 */
	public void dispose() {
		try {
			bw.close();
		} catch (Exception e) {}
	}
}