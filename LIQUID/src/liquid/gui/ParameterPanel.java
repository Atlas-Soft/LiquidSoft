package liquid.gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import liquid.core.LiquidApplication;

public class ParameterPanel extends Panel {
	
	private static final long serialVersionUID = 1L;
	Button run;
	Button pause;
	Button step;
	Button end;
	List liqs;
	TextField temp;
	TextField visc;
	TextField time;
	
	public ParameterPanel(){
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.lightGray);
		setBounds(500,60,300,565);
		setVisible(true);
	}
	
	private void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		Label l = new Label("Temperature:");
		l.setBounds(155,0,120,25);
		add(l);
		
		l = new Label("Viscocity:");
		l.setBounds(155,50,120,25);
		add(l);
		
		l = new Label("Environment Editor:");
		l.setBounds(25,150,140,25);
		add(l);
		
		l = new Label("Run For:");
		l.setBounds(25,460,75,25);
		add(l);
		
		l = new Label("Sec.");
		l.setBounds(225,460,50,25);
		add(l);
		
		run = new Button("Run");
		run.setBounds(25,495,115,25);
		run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				pause.setEnabled(true);
				run.setEnabled(false);
			    step.setEnabled(false);
			}
        });
		add(run);
		
		pause = new Button("Pause");
		pause.setBounds(155,495,115,25);
		pause.setEnabled(false);
		pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			    pause.setEnabled(false);
			    run.setEnabled(true);
			    step.setEnabled(true);
			}
        });
		add(pause);
		
		step = new Button("Step");
		step.setBounds(25,530,115,25);
		step.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {

			}
        });
		add(step);
		
		end = new Button("End");
		end.setBounds(155,530,115,25);
		end.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			    pause.setEnabled(false);
			    run.setEnabled(true);
			    step.setEnabled(true);
			}
        });
		add(end);
		
		liqs = new List();
		liqs.setBounds(25,0,120,150);
		liqs.add("Water");
		liqs.add("Glycerin");
		liqs.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				LiquidApplication.getGUI().variables.liquid = arg0.getItem().toString();			
			}
        });
		liqs.select(0);
		add(liqs);
		
		temp = new TextField("-100-100");
		temp.setBounds(155, 25, 120, 25);
		temp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.temperature = Float.parseFloat(temp.getText());
			    temp.setFocusable(false);
			    temp.setFocusable(true);
			}
        });
		add(temp);
		
		visc = new TextField("0-?");
		visc.setBounds(155, 75, 120, 25);
		visc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.viscosity = Float.parseFloat(visc.getText());
			    visc.setFocusable(false);
			    visc.setFocusable(true);
			}
        });
		add(visc);
		
		time = new TextField("0-300");
		time.setBounds(100, 460, 125, 25);
		time.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.runtime = Integer.parseInt(temp.getText());
			    time.setFocusable(false);
			    time.setFocusable(true);
			}
        });
		add(time);
	}
	
}