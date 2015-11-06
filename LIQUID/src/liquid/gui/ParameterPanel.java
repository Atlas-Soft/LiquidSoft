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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import liquid.core.LiquidApplication;

public class ParameterPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	JButton run;
	JButton pause;
	JButton step;
	JButton end;
	JList<String> liqs;
	JTextField temp;
	JTextField visc;
	JTextField time;
	
	public ParameterPanel(){
		super();
		initComponents();
		setLayout(null);
		setBackground(Color.lightGray);
		setBounds(500,0,300,640);
		setVisible(true);
	}
	
	private void initComponents(){
		Font font = new Font("Verdana", Font.BOLD, 12);
		setFont(font);
		
		JLabel l = new JLabel("Temperature:");
		l.setBounds(155,15,120,25);
		add(l);
		
		l = new JLabel("Viscocity:");
		l.setBounds(155,65,120,25);
		add(l);
		
		l = new JLabel("Environment Editor:");
		l.setBounds(25,165,140,25);
		add(l);
		
		l = new JLabel("Run For:");
		l.setBounds(25,475,75,25);
		add(l);
		
		l = new JLabel("Sec.");
		l.setBounds(225,475,50,25);
		add(l);
		
		run = new JButton("Run");
		run.setBounds(25,510,115,25);
		run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				pause.setEnabled(true);
				run.setEnabled(false);
			    step.setEnabled(false);
			}
        });
		add(run);
		
		pause = new JButton("Pause");
		pause.setBounds(155,510,115,25);
		pause.setEnabled(false);
		pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			    pause.setEnabled(false);
			    run.setEnabled(true);
			    step.setEnabled(true);
			}
        });
		add(pause);
		
		step = new JButton("Step");
		step.setBounds(25,545,115,25);
		step.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {

			}
        });
		add(step);
		
		end = new JButton("End");
		end.setBounds(155,545,115,25);
		end.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
			    pause.setEnabled(false);
			    run.setEnabled(true);
			    step.setEnabled(true);
			}
        });
		add(end);
		
		String[] options = {"Water","Glycerin"};
		liqs = new JList<String>(options);
		liqs.setBounds(25,15,120,150);
		liqs.setSelectedIndex(0);
		liqs.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                	LiquidApplication.getGUI().variables.liquid = (String) liqs.getSelectedValue();
                }
            }
        });
		add(liqs);
		
		temp = new JTextField("-100-100");
		temp.setBounds(155, 40, 120, 25);
		temp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.temperature = Float.parseFloat(temp.getText());
			    temp.transferFocus();
			}
        });
		add(temp);
		
		visc = new JTextField("0-?");
		visc.setBounds(155, 90, 120, 25);
		visc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.viscosity = Float.parseFloat(visc.getText());
			    visc.transferFocus();
			}
        });
		add(visc);
		
		time = new JTextField("0-300");
		time.setBounds(100, 475, 125, 25);
		time.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().variables.runtime = Integer.parseInt(temp.getText());
			    time.transferFocus();
			}
        });
		add(time);
	}
	
}