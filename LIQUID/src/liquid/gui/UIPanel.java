package liquid.gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Panel;

public class UIPanel extends Panel {
	
	public UIPanel(){
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.lightGray);
		setBounds(500,0,300,600);
		setVisible(true);
	}
	
	private void initComponents(){
		Button b = new Button("Run");
		b.setBounds(40,500,90,40);
		add(b);
		
		b = new Button("Pause/Resume");
		b.setBounds(170,500,90,40);
		add(b);
		
		b = new Button("Step");
		b.setBounds(40,550,90,40);
		add(b);
		
		b = new Button("End");
		b.setBounds(170,550,90,40);
		add(b);
		
		b = new Button("Load");
		b.setBounds(200,50,90,40);
		add(b);
	}
}
