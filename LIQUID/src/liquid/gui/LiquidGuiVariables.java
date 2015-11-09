package liquid.gui;

import java.util.ArrayList;

/**
 * V1.0
 *
 */
public class LiquidGUIVariables {
	 
	String filename;
	String liquid;
	float temperature; String temp = Float.toString(temperature);
	float viscosity; String vis = Float.toString(viscosity);
	float runtime; String run = Float.toString(runtime);
	int enviroLength = 500; String envLen = Integer.toString(enviroLength);
	int enviroWidth = 400; String envWid = Integer.toString(enviroWidth);
	ArrayList<String> objects = new ArrayList<String>();
	int selectedObjects;
	
	String[] var;
	var[0] = filename;
	var[1] = liquid;
	var[2] = temp;
	var[3] = vis;
	var[4] = run;
	var[5] = envLen;
	var[6] = envWid;
	for (i = 0; i < objects.size(); i++) {
		String obj = objects.get(i);
		var[i] = objects.get(i);
	}
}
