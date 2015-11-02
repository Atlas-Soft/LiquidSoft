package liquid.logger;

import java.io.File;

import liquid.core.Interfaceable;
import liquid.gui.LiquidGUI;

public class LiquidLogger implements Interfaceable{

	public static final int LOADLOG = 0;
	
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
			case LOADLOG:
				String[] args = new String[1];
				i.recieve(this, LiquidGUI.REQUEST_LOADLOG, args);
			}
		}
	}

	@Override
	public void recieve(Interfaceable i, int arg0, String[] args) {
		if(i instanceof LiquidGUI){
			switch(arg0){
			case LOADLOG:
				currentFile = args[0];
				fileLoader.loadLogFile(currentFile);
				send(i, LOADLOG);
			case 1:
					
			}
		}
	}
	
	private void init(){
		fileLoader = new LiquidFileLoader();
		fileWriter = new LiquidFileWriter();
		File f = new File("../logs");
		if (f.exists() && f.isDirectory()) {
			
		}
		else{
			f.mkdirs();
		}
		f = new File("../configs");
		if (f.exists() && f.isDirectory()) {
			
		}
		else{
			f.mkdirs();
		}
	}
}
