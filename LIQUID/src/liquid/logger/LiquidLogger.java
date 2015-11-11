package liquid.logger;

import java.io.File;

import liquid.core.Interfaceable;
import liquid.gui.LiquidGUI;

public class LiquidLogger implements Interfaceable{

	public static final int REQUEST_SETLOGPARAM = 0;
	public static final int LOADLOG = 0;
	public static final int WRITELOG = 1;
	
	
	private LiquidFileLoader fileLoader;
	private LiquidFileWriter fileWriter;
	
	private String currentFile;
	
	public LiquidLogger(){
		init();
	}

	@Override
	public void send(Interfaceable i, int arg0) {
		if(i instanceof LiquidGUI){
			switch(arg0){
			case REQUEST_SETLOGPARAM:
				String[] args = fileLoader.loadLogFile(currentFile);
				i.receive(this, LiquidGUI.SETLOGPARAM, args);
				break;
			}
		}
	}

	@Override
	public void receive(Interfaceable i, int arg0, String[] args) {
		if(i instanceof LiquidGUI){
			switch(arg0){
			case LOADLOG:
				currentFile = args[0];
				send(i, REQUEST_SETLOGPARAM);
				break;
			case WRITELOG:
				currentFile = args[0];
				fileWriter.writetoLogFile(currentFile, args);
				break;
			}
		}
	}
	
	private void init(){
		fileLoader = new LiquidFileLoader();
		fileWriter = new LiquidFileWriter();
		File f = new File("../logs");
		if (!(f.exists() && f.isDirectory())) f.mkdirs();
		
		f = new File("../configs");
		if (f.exists() && f.isDirectory()) {
			
		}
		else{
			f.mkdirs();
		}
	}
}
