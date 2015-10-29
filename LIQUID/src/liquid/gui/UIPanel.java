package liquid.gui;

import java.awt.Button;
import java.awt.Font;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UIPanel extends Panel {
	
	private LiquidGUI parent;
	
	public Button load;
	public Button run;
	public Button pause;
	public Button step;
	public Button end;
	public List liquids;
	public TextField file;
	public TextField flow;
	public TextField temp;
	public TextField visc;
	public TextField time;
	
	public UIPanel(LiquidGUI gui){
		super();
		parent = gui;
		initComponents();
		setLayout(null);
		setBounds(500,0,300,600);
		setVisible(true);
	}
	
	private void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		Label l = new Label("Load From File:");
		l.setBounds(25,35,90,25);
		add(l);
		
		l = new Label("Flow Speed:");
		l.setBounds(160,75,115,25);
		add(l);
		
		l = new Label("Temperature:");
		l.setBounds(160,125,115,25);
		add(l);
		
		l = new Label("Viscocity:");
		l.setBounds(160,175,115,25);
		add(l);
		
		l = new Label("Environment:");
		l.setBounds(25,225,90,25);
		add(l);
		
		l = new Label("Run For:");
		l.setBounds(25,490,75,25);
		add(l);
		
		l = new Label("Sec.");
		l.setBounds(225,490,50,25);
		add(l);
		
		run = new Button("Run");
		run.setBounds(25,525,115,25);
		run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			      parent.getConsolePanel().print_to_Console("RUN\n");
			}
        });
		add(run);
		
		pause = new Button("Pause/Resume");
		pause.setBounds(155,525,115,25);
		pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			      parent.getConsolePanel().print_to_Console("Paused\n");
			}
        });
		add(pause);
		
		step = new Button("Step");
		step.setBounds(25,560,115,25);
		step.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			      parent.getConsolePanel().print_to_Console("Step\n");
			}
        });
		add(step);
		
		end = new Button("End");
		end.setBounds(155,560,115,25);
		end.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			      parent.getConsolePanel().print_to_Console("end\n");
			}
        });
		add(end);
		
		load = new Button("Load");
		load.setBounds(205,35,70,25);
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			      parent.getConsolePanel().print_to_Console(file.getText());
			}
        });
		add(load);
		
		liquids = new List();
		liquids.setBounds(25,75,125,150);
		add(liquids);
		
		file = new TextField("file name");
		file.setBounds(115, 35, 90, 25);
		add(file);
		
		flow = new TextField("0 to ?");
		flow.setBounds(160, 100, 115, 25);
		flow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			      parent.getConsolePanel().print_to_Console(flow.getText());
			      flow.setFocusable(false);
			      flow.setFocusable(true);
			}
        });
		add(flow);
		
		temp = new TextField("-100 to 100");
		temp.setBounds(160, 150, 115, 25);
		temp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			      parent.getConsolePanel().print_to_Console(temp.getText());
			      temp.setFocusable(false);
			      temp.setFocusable(true);
			}
        });
		add(temp);
		
		visc = new TextField("0 to ?");
		visc.setBounds(160, 200, 115, 25);
		visc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			      parent.getConsolePanel().print_to_Console(visc.getText());
			      visc.setFocusable(false);
			      visc.setFocusable(true);
			}
        });
		add(visc);
		
		time = new TextField("0 to 300");
		time.setBounds(100, 490, 125, 25);
		time.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			      parent.getConsolePanel().print_to_Console(time.getText());
			      time.setFocusable(false);
			      time.setFocusable(true);
			}
        });
		add(time);
		
	}
}
