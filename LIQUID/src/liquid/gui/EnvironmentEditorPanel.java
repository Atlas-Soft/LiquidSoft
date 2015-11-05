package liquid.gui;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import liquid.core.LiquidApplication;

public class EnvironmentEditorPanel extends Panel{
	
	private static final long serialVersionUID = 1L;
	
	Panel enviro;
	Panel obstacles;
	Panel forces;
	Panel sensors;
	
	public EnvironmentEditorPanel() {
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.gray);
		setBounds(25,175,250,280);
		setVisible(true);
	}
	
	public void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		Choice select = new Choice();
		select.setBounds(5, 5, 240, 25);
		select.add("Environment");
		select.add("Obstacles");
		select.add("Forces");
		select.add("Sensors");
		select.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getItem().toString() == "Environment") enviro.setVisible(true);
				else enviro.setVisible(false);
				if(arg0.getItem().toString() == "Obstacles") obstacles.setVisible(true);
				else obstacles.setVisible(false);
				if(arg0.getItem().toString() == "Forces") forces.setVisible(true);
				else forces.setVisible(false);
				if(arg0.getItem().toString() == "Sensors") sensors.setVisible(true);
				else sensors.setVisible(false);
			}
        });
		add(select);
		
		enviro = new Panel();
		enviro.setBounds(5, 35, 240, 240);
		enviro.setBackground(Color.lightGray);
		enviro.setLayout(null);
		
		Label l = new Label("Length:");
		l.setBounds(5,0,110,25);
		enviro.add(l);
		
		l = new Label("Width:");
		l.setBounds(5,50,110,25);
		enviro.add(l);
		
		TextField enviroLen = new TextField("410");
		enviroLen.setBounds(5, 25, 110, 25);
		enviro.add(enviroLen);
		
		TextField enviroWid = new TextField("355");
		enviroWid.setBounds(5, 75, 110, 25);
		enviro.add(enviroWid);
		
		Button update = new Button("Update");
		update.setBounds(5,125,110,25);
		update.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				try{
					int l = Integer.parseInt(enviroLen.getText());
					if(l >= 0 && l <= 410 ){
						LiquidApplication.getGUI().variables.enviroLength = l;
						LiquidApplication.getGUI().sim.repaint();
					}else LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Length Is Not Between 0 and 410\n");
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Length Is Not An Integer.\n");
				}
				try{
					int w = Integer.parseInt(enviroWid.getText());
					if(w >= 0 && w <= 355 ){
						LiquidApplication.getGUI().variables.enviroWidth = w;
						LiquidApplication.getGUI().sim.repaint();
					}else LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Width Is Not Between 0 and 355\n");
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Width Is Not An Integer.\n");
				}
			}
        });
		enviro.add(update);
		
		add(enviro);
		
		obstacles = new Panel();
		obstacles.setBounds(5, 35, 240, 240);
		obstacles.setBackground(Color.lightGray);
		obstacles.setLayout(null);
		
		l = new Label("Object Type:");
		l.setBounds(5,0,110,25);
		obstacles.add(l);
		
		l = new Label("X:");
		l.setBounds(125,0,110,25);
		obstacles.add(l);
		
		l = new Label("Y:");
		l.setBounds(125,50,110,25);
		obstacles.add(l);
		
		l = new Label("Length:");
		l.setBounds(125,100,110,25);
		obstacles.add(l);
		
		l = new Label("Width:");
		l.setBounds(125,150,110,25);
		obstacles.add(l);
		
		Choice obstacleType = new Choice();
		obstacleType.setBounds(5, 25, 110, 25);
		obstacleType.add("Rectangular");
		obstacleType.add("Circular");
		obstacles.add(obstacleType);
			
		TextField obstacleX = new TextField("0");
		obstacleX.setBounds(125, 25, 110, 25);
		obstacles.add(obstacleX);
		
		TextField obstacleY = new TextField("0");
		obstacleY.setBounds(125, 75, 110, 25);
		obstacles.add(obstacleY);
		
		TextField obstacleL = new TextField("50");
		obstacleL.setBounds(125, 125, 110, 25);
		obstacles.add(obstacleL);
		
		TextField obstacleW = new TextField("50");
		obstacleW.setBounds(125, 175, 110, 25);
		obstacles.add(obstacleW);
		
		Button create = new Button("Create");
		create.setBounds(125,210,110,25);
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				try{
					String arg = obstacleType.getSelectedItem() + " ";
					arg += Integer.parseInt(obstacleX.getText()) + " ";
					arg += Integer.parseInt(obstacleY.getText()) + " ";
					arg += Integer.parseInt(obstacleL.getText()) + " ";
					arg += Integer.parseInt(obstacleW.getText());
					LiquidApplication.getGUI().variables.objects.add(arg);
					LiquidApplication.getGUI().variables.selectedObjects = LiquidApplication.getGUI().variables.objects.size()-1;
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		obstacles.add(create);
		
		Button selectUpdate = new Button("Update Selected");
		selectUpdate.setBounds(5,210,110,25);
		selectUpdate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				try{
					String arg = obstacleType.getSelectedItem() + " ";
					arg += Integer.parseInt(obstacleX.getText()) + " ";
					arg += Integer.parseInt(obstacleY.getText()) + " ";
					arg += Integer.parseInt(obstacleL.getText()) + " ";
					arg += Integer.parseInt(obstacleW.getText());
					LiquidApplication.getGUI().variables.objects.add(LiquidApplication.getGUI().variables.selectedObjects, arg);
					LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObjects+1);
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		obstacles.add(selectUpdate);
		
		add(obstacles);
		
		forces = new Panel();
		forces.setBounds(5, 35, 240, 240);
		forces.setLayout(null);
		forces.setBackground(Color.lightGray);
		
		l = new Label("Object Type:");
		l.setBounds(5,0,110,25);
		forces.add(l);
		
		l = new Label("X:");
		l.setBounds(125,0,110,25);
		forces.add(l);
		
		l = new Label("Y:");
		l.setBounds(125,50,110,25);
		forces.add(l);
		
		l = new Label("Force-X:");
		l.setBounds(125,100,110,25);
		forces.add(l);
		
		l = new Label("Force-Y:");
		l.setBounds(125,150,110,25);
		forces.add(l);
		
		Choice forceType = new Choice();
		forceType.setBounds(5, 25, 110, 25);
		forceType.add("Source");
		forces.add(forceType);
			
		TextField forceX = new TextField("0");
		forceX.setBounds(125, 25, 110, 25);
		forces.add(forceX);
		
		TextField forceY = new TextField("0");
		forceY.setBounds(125, 75, 110, 25);
		forces.add(forceY);
		
		TextField forceXComp = new TextField("10");
		forceXComp.setBounds(125, 125, 110, 25);
		forces.add(forceXComp);
		
		TextField forceYComp = new TextField("10");
		forceYComp.setBounds(125, 175, 110, 25);
		forces.add(forceYComp);
		
		create = new Button("Create");
		create.setBounds(125,210,110,25);
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				try{
					String arg = forceType.getSelectedItem() + " ";
					arg += Integer.parseInt(forceX.getText()) + " ";
					arg += Integer.parseInt(forceY.getText()) + " ";
					arg += Integer.parseInt(forceXComp.getText()) + " ";
					arg += Integer.parseInt(forceYComp.getText());
					LiquidApplication.getGUI().variables.objects.add(arg);
					LiquidApplication.getGUI().variables.selectedObjects = LiquidApplication.getGUI().variables.objects.size()-1;
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		forces.add(create);
		
		selectUpdate = new Button("Update Selected");
		selectUpdate.setBounds(5,210,110,25);
		selectUpdate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				try{
					String arg = forceType.getSelectedItem() + " ";
					arg += Integer.parseInt(forceX.getText()) + " ";
					arg += Integer.parseInt(forceY.getText()) + " ";
					arg += Integer.parseInt(forceXComp.getText()) + " ";
					arg += Integer.parseInt(forceYComp.getText());
					LiquidApplication.getGUI().variables.objects.add(LiquidApplication.getGUI().variables.selectedObjects, arg);
					LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObjects+1);
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		forces.add(selectUpdate);
		
		add(forces);
		
		sensors = new Panel();
		sensors.setBounds(5, 35, 240, 240);
		sensors.setLayout(null);
		sensors.setBackground(Color.lightGray);
		
		l = new Label("Object Type:");
		l.setBounds(5,0,110,25);
		sensors.add(l);
		
		l = new Label("X:");
		l.setBounds(125,0,110,25);
		sensors.add(l);
		
		l = new Label("Y:");
		l.setBounds(125,50,110,25);
		sensors.add(l);
		
		Choice sensorType = new Choice();
		sensorType.setBounds(5, 25, 110, 25);
		sensorType.add("Flowmeter");
		sensors.add(sensorType);
			
		TextField sensorX = new TextField("0");
		sensorX.setBounds(125, 25, 110, 25);
		sensors.add(sensorX);
		
		TextField sensorY = new TextField("0");
		sensorY.setBounds(125, 75, 110, 25);
		sensors.add(sensorY);
		
		create = new Button("Create");
		create.setBounds(125,210,110,25);
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				try{
					String arg = sensorType.getSelectedItem() + " ";
					arg += Integer.parseInt(sensorX.getText()) + " ";
					arg += Integer.parseInt(sensorY.getText());
					LiquidApplication.getGUI().variables.objects.add(arg);
					LiquidApplication.getGUI().variables.selectedObjects = LiquidApplication.getGUI().variables.objects.size()-1;
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		
		sensors.add(create);
		
		selectUpdate = new Button("Update Selected");
		selectUpdate.setBounds(5,210,110,25);
		selectUpdate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				try{
					String arg = sensorType.getSelectedItem() + " ";
					arg += Integer.parseInt(sensorX.getText()) + " ";
					arg += Integer.parseInt(sensorY.getText());
					LiquidApplication.getGUI().variables.objects.add(LiquidApplication.getGUI().variables.selectedObjects, arg);
					LiquidApplication.getGUI().variables.objects.remove(LiquidApplication.getGUI().variables.selectedObjects+1);
					LiquidApplication.getGUI().sim.repaint();
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid.\n");
				}
			}
        });
		sensors.add(selectUpdate);
		
		Button selectNext = new Button("Select Next");
		selectNext.setBounds(5,150,110,25);
		selectNext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				if(LiquidApplication.getGUI().variables.selectedObjects < LiquidApplication.getGUI().variables.objects.size()-1){
					LiquidApplication.getGUI().variables.selectedObjects += 1;
				}else{
					LiquidApplication.getGUI().variables.selectedObjects = 0;
				}
				String[] tokens = LiquidApplication.getGUI().variables.objects.get(LiquidApplication.getGUI().variables.selectedObjects).split(" ");
				if(tokens[0].equals("Rectangular") || tokens[0].equals("Circular")){
					obstacleType.select(tokens[0]);
					obstacleX.setText(tokens[1]);
					obstacleY.setText(tokens[2]);
					obstacleL.setText(tokens[3]);
					obstacleW.setText(tokens[4]);
				}
				if(tokens[0].equals("Source")){
					select.select("Forces");
					forces.setVisible(true);
					obstacles.setVisible(false);
					forceType.select(tokens[0]);
					forceX.setText(tokens[1]);
					forceY.setText(tokens[2]);
					forceXComp.setText(tokens[3]);
					forceYComp.setText(tokens[4]);
				}
				if(tokens[0].equals("Flowmeter")){
					select.select("Sensors");
					sensors.setVisible(true);
					obstacles.setVisible(false);
					sensorType.select(tokens[0]);
					sensorX.setText(tokens[1]);
					sensorY.setText(tokens[2]);
				}
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		obstacles.add(selectNext);
		
		Button selectPrev = new Button("Select Prev");
		selectPrev.setBounds(5,175,110,25);
		selectPrev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				if(LiquidApplication.getGUI().variables.selectedObjects > 0){
					LiquidApplication.getGUI().variables.selectedObjects -= 1;
				}else{
					LiquidApplication.getGUI().variables.selectedObjects = LiquidApplication.getGUI().variables.objects.size()-1;
				}
				String[] tokens = LiquidApplication.getGUI().variables.objects.get(LiquidApplication.getGUI().variables.selectedObjects).split(" ");
				if(tokens[0].equals("Rectangular") || tokens[0].equals("Circular")){
					obstacleType.select(tokens[0]);
					obstacleX.setText(tokens[1]);
					obstacleY.setText(tokens[2]);
					obstacleL.setText(tokens[3]);
					obstacleW.setText(tokens[4]);
				}
				if(tokens[0].equals("Source")){
					select.select("Forces");
					forces.setVisible(true);
					obstacles.setVisible(false);
					forceType.select(tokens[0]);
					forceX.setText(tokens[1]);
					forceY.setText(tokens[2]);
					forceXComp.setText(tokens[3]);
					forceYComp.setText(tokens[4]);
				}
				if(tokens[0].equals("Flowmeter")){
					select.select("Sensors");
					sensors.setVisible(true);
					obstacles.setVisible(false);
					sensorType.select(tokens[0]);
					sensorX.setText(tokens[1]);
					sensorY.setText(tokens[2]);
				}
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		obstacles.add(selectPrev);
		
		selectNext = new Button("Select Next");
		selectNext.setBounds(5,150,110,25);
		selectNext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				if(LiquidApplication.getGUI().variables.selectedObjects < LiquidApplication.getGUI().variables.objects.size()-1){
					LiquidApplication.getGUI().variables.selectedObjects += 1;
				}else{
					LiquidApplication.getGUI().variables.selectedObjects = 0;
				}
				String[] tokens = LiquidApplication.getGUI().variables.objects.get(LiquidApplication.getGUI().variables.selectedObjects).split(" ");
				if(tokens[0].equals("Rectangular") || tokens[0].equals("Circular")){
					select.select("Obstacles");
					obstacles.setVisible(true);
					forces.setVisible(false);
					obstacleType.select(tokens[0]);
					obstacleX.setText(tokens[1]);
					obstacleY.setText(tokens[2]);
					obstacleL.setText(tokens[3]);
					obstacleW.setText(tokens[4]);
				}
				if(tokens[0].equals("Source")){
					forceType.select(tokens[0]);
					forceX.setText(tokens[1]);
					forceY.setText(tokens[2]);
					forceXComp.setText(tokens[3]);
					forceYComp.setText(tokens[4]);
				}
				if(tokens[0].equals("Flowmeter")){
					select.select("Sensors");
					sensors.setVisible(true);
					forces.setVisible(false);
					sensorType.select(tokens[0]);
					sensorX.setText(tokens[1]);
					sensorY.setText(tokens[2]);
				}
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		forces.add(selectNext);
		
		selectPrev = new Button("Select Prev");
		selectPrev.setBounds(5,175,110,25);
		selectPrev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				if(LiquidApplication.getGUI().variables.selectedObjects > 0){
					LiquidApplication.getGUI().variables.selectedObjects -= 1;
				}else{
					LiquidApplication.getGUI().variables.selectedObjects = LiquidApplication.getGUI().variables.objects.size()-1;
				}
				String[] tokens = LiquidApplication.getGUI().variables.objects.get(LiquidApplication.getGUI().variables.selectedObjects).split(" ");
				if(tokens[0].equals("Rectangular") || tokens[0].equals("Circular")){
					select.select("Obstacles");
					obstacles.setVisible(true);
					forces.setVisible(false);
					obstacleType.select(tokens[0]);
					obstacleX.setText(tokens[1]);
					obstacleY.setText(tokens[2]);
					obstacleL.setText(tokens[3]);
					obstacleW.setText(tokens[4]);
				}
				if(tokens[0].equals("Source")){
					forceType.select(tokens[0]);
					forceX.setText(tokens[1]);
					forceY.setText(tokens[2]);
					forceXComp.setText(tokens[3]);
					forceYComp.setText(tokens[4]);
				}
				if(tokens[0].equals("Flowmeter")){
					select.select("Sensors");
					sensors.setVisible(true);
					forces.setVisible(false);
					sensorType.select(tokens[0]);
					sensorX.setText(tokens[1]);
					sensorY.setText(tokens[2]);
				}
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		forces.add(selectPrev);
		
		selectNext = new Button("Select Next");
		selectNext.setBounds(5,150,110,25);
		selectNext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				if(LiquidApplication.getGUI().variables.selectedObjects < LiquidApplication.getGUI().variables.objects.size()-1){
					LiquidApplication.getGUI().variables.selectedObjects += 1;
				}else{
					LiquidApplication.getGUI().variables.selectedObjects = 0;
				}
				String[] tokens = LiquidApplication.getGUI().variables.objects.get(LiquidApplication.getGUI().variables.selectedObjects).split(" ");
				if(tokens[0].equals("Rectangular") || tokens[0].equals("Circular")){
					select.select("Obstacles");
					obstacles.setVisible(true);
					sensors.setVisible(false);
					obstacleType.select(tokens[0]);
					obstacleX.setText(tokens[1]);
					obstacleY.setText(tokens[2]);
					obstacleL.setText(tokens[3]);
					obstacleW.setText(tokens[4]);
				}
				if(tokens[0].equals("Source")){
					select.select("Forces");
					forces.setVisible(true);
					obstacles.setVisible(false);
					forceType.select(tokens[0]);
					forceX.setText(tokens[1]);
					forceY.setText(tokens[2]);
					forceXComp.setText(tokens[3]);
					forceYComp.setText(tokens[4]);
				}
				if(tokens[0].equals("Flowmeter")){
					sensorType.select(tokens[0]);
					sensorX.setText(tokens[1]);
					sensorY.setText(tokens[2]);
				}
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		sensors.add(selectNext);
		
		selectPrev = new Button("Select Prev");
		selectPrev.setBounds(5,175,110,25);
		selectPrev.addActionListener(new ActionListener(){	
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.saveState();
				if(LiquidApplication.getGUI().variables.selectedObjects > 0){
					LiquidApplication.getGUI().variables.selectedObjects -= 1;
				}else{
					LiquidApplication.getGUI().variables.selectedObjects = LiquidApplication.getGUI().variables.objects.size()-1;
				}
				String[] tokens = LiquidApplication.getGUI().variables.objects.get(LiquidApplication.getGUI().variables.selectedObjects).split(" ");
				if(tokens[0].equals("Rectangular") || tokens[0].equals("Circular")){
					select.select("Obstacles");
					obstacles.setVisible(true);
					sensors.setVisible(false);
					obstacleType.select(tokens[0]);
					obstacleX.setText(tokens[1]);
					obstacleY.setText(tokens[2]);
					obstacleL.setText(tokens[3]);
					obstacleW.setText(tokens[4]);
				}
				if(tokens[0].equals("Source")){
					select.select("Forces");
					forces.setVisible(true);
					obstacles.setVisible(false);
					forceType.select(tokens[0]);
					forceX.setText(tokens[1]);
					forceY.setText(tokens[2]);
					forceXComp.setText(tokens[3]);
					forceYComp.setText(tokens[4]);
				}
				if(tokens[0].equals("Flowmeter")){
					sensorType.select(tokens[0]);
					sensorX.setText(tokens[1]);
					sensorY.setText(tokens[2]);
				}
				LiquidApplication.getGUI().sim.repaint();
			}
        });
		sensors.add(selectPrev);
		
		add(sensors);	
	}
	
}