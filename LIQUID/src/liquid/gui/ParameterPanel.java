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

import liquid.core.LiquidApplication;

public class ParameterPanel extends Panel {
	
	private static final long serialVersionUID = 1L;
	private Button run;
	private Button pause;
	private Button step;
	private Button end;
	private List liqs;
	private TextField flow;
	private TextField temp;
	private TextField visc;
	private TextField time;
	
	public ParameterPanel(){
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.lightGray);
		setBounds(500,45,300,565);
		setVisible(true);
	}
	
	private void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		Label l = new Label("Flow Speed:");
		l.setBounds(160,5,115,25);
		add(l);
		
		l = new Label("Temperature:");
		l.setBounds(160,55,115,25);
		add(l);
		
		l = new Label("Viscocity:");
		l.setBounds(160,105,115,25);
		add(l);
		
		l = new Label("Environment:");
		l.setBounds(25,155,90,25);
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
				LiquidApplication.getGUI().getConsolePanel().print_to_Console(getParameters()[0]);
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
				LiquidApplication.getGUI().getConsolePanel().print_to_Console("Paused\n");
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
				LiquidApplication.getGUI().getConsolePanel().print_to_Console("Step\n");
			}
        });
		add(step);
		
		end = new Button("End");
		end.setBounds(155,530,115,25);
		end.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().getConsolePanel().print_to_Console("End\n");
			    pause.setEnabled(false);
			    run.setEnabled(true);
			    step.setEnabled(true);
			}
        });
		add(end);
		
		liqs = new List();
		liqs.setBounds(25,5,125,150);
		liqs.add("Water");
		liqs.add("Glycerin");
		liqs.select(0);
		add(liqs);
		
		flow = new TextField("0 to ?");
		flow.setBounds(160, 30, 115, 25);
		flow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			    flow.setFocusable(false);
			    flow.setFocusable(true);
			}
        });
		add(flow);
		
		temp = new TextField("-100 to 100");
		temp.setBounds(160, 80, 115, 25);
		temp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			    temp.setFocusable(false);
			    temp.setFocusable(true);
			}
        });
		add(temp);
		
		visc = new TextField("0 to ?");
		visc.setBounds(160, 130, 115, 25);
		visc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			    visc.setFocusable(false);
			    visc.setFocusable(true);
			}
        });
		add(visc);
		
		time = new TextField("0 to 300");
		time.setBounds(100, 460, 125, 25);
		time.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			    time.setFocusable(false);
			    time.setFocusable(true);
			}
        });
		add(time);
		
	}
	
	public String[] getParameters(){
		String[] args = {liqs.getSelectedItem(),flow.getText(),temp.getText(),visc.getText(),time.getText()};
		return args;
	}
}
