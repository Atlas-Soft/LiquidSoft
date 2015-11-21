package liquid.logger;

import java.io.File;

import liquid.core.Interfaceable;
import liquid.gui.LiquidGUI;

public class LiquidLogger implements Interfaceable{
	
	private LiquidFileLoader fileLoader;
	private LiquidFileWriter fileWriter;
	
	private String currentFile;
	
	public LiquidLogger(){
		init();
	}

	@SuppressWarnings("static-access")
	@Override
	public void send(Interfaceable i, Request request) {
		if(i instanceof LiquidGUI){
			switch(request){
			case REQUEST_SET_LOG_PARAM:
				String[] args = fileLoader.loadLogFile(currentFile);
				i.receive(this, request.SET_LOG_PARAM, args);
				break;
			default:
				break;}
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void receive(Interfaceable i, Request request, String[] args) {
		if(i instanceof LiquidGUI){
			switch(request){
			case LOAD_LOG:
				currentFile = args[0];
				send(i, request.REQUEST_SET_LOG_PARAM);
				break;
			case WRITE_LOG:
				currentFile = args[0];
				fileWriter.writetoLogFile(currentFile, args);
				break;
			default:
				break;}
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
	
	public void dispose(){
		
	}
}
