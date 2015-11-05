package liquid.gui;

import java.util.ArrayList;
import java.util.LinkedList;

public class LiquidGUIVariables {
	
	LinkedList<LiquidGUIVariables> history = new LinkedList<LiquidGUIVariables>();
	LinkedList<LiquidGUIVariables> undohistory = new LinkedList<LiquidGUIVariables>();
	
	String liquid;
	float temperature;
	float viscosity;
	float runtime;
	int enviroLength = 410;
	int enviroWidth = 355;
	ArrayList<String> objects = new ArrayList<String>();
	int selectedObjects;
	
	public LiquidGUIVariables(){
		
	}
	
	public LiquidGUIVariables(LiquidGUIVariables v){
		liquid = v.liquid;
		temperature = v.temperature;
		viscosity = v.viscosity;
		runtime = v.runtime;
		enviroLength = v.enviroLength;
		enviroWidth = v.enviroWidth;
		objects = new ArrayList<String>(v.objects);
		selectedObjects = v.selectedObjects;
	}
	
	public void saveState(){
		history.push(new LiquidGUIVariables(this));
		System.out.println(history.size());
	}
	
	public void undo(){
		if(history.size()>1){
			undohistory.push(new LiquidGUIVariables(history.pop()));
			LiquidGUIVariables last = history.getFirst();
			liquid = last.liquid;
			temperature = last.temperature;
			viscosity = last.viscosity;
			runtime = last.runtime;
			enviroLength = last.enviroLength;
			enviroWidth = last.enviroWidth;
			objects = last.objects;
			selectedObjects = last.selectedObjects;
		}
	}
	
	public void redo(){
		if(undohistory.size()>0){
			LiquidGUIVariables lastundo = undohistory.pop();
			liquid = lastundo.liquid;
			temperature = lastundo.temperature;
			viscosity = lastundo.viscosity;
			runtime = lastundo.runtime;
			enviroLength = lastundo.enviroLength;
			enviroWidth = lastundo.enviroWidth;
			objects = lastundo.objects;
			selectedObjects = lastundo.selectedObjects;
			saveState();
		}
	}
	
}
