package liquid.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import liquid.core.LiquidApplication;
import liquid.logger.LiquidLogger;

public class ParameterPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	JButton run;
	JButton pause;
	JButton step;
	JButton end;
	JCheckBox replay;
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
		
		String[] options = {"Water","Glycerin"};
		liqs = new JList<String>(options);
		liqs.setBounds(25,15,120,150);
		liqs.setSelectedIndex(0);
		add(liqs);
		
		temp = new JTextField("10");
		temp.setBounds(155, 40, 120, 25);
		add(temp);
		
		visc = new JTextField("1");
		visc.setBounds(155, 90, 120, 25);
		add(visc);
		
		time = new JTextField("300");
		time.setBounds(75, 475, 65, 25);
		add(time);
		
		replay = new JCheckBox("Run Replay");
		replay.setBounds(155, 475, 115, 25);
		add(replay);
		
		run = new JButton("Run");
		run.setBounds(25,510,115,25);
		run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try{
					if(!LiquidApplication.getGUI().variables.simulating){
						LiquidApplication.getGUI().variables.liquid = (String) liqs.getSelectedValue();
						LiquidApplication.getGUI().variables.temperature = Float.parseFloat(temp.getText());
						LiquidApplication.getGUI().variables.viscosity = Float.parseFloat(visc.getText());
						LiquidApplication.getGUI().variables.runtime = Integer.parseInt(time.getText());
						if(LiquidApplication.getGUI().variables.filename == null){
							String filename = JOptionPane.showInputDialog(LiquidApplication.getGUI().frame, "Save Log As:");
							if(filename == null || filename.equals("")) return;
							LiquidApplication.getGUI().variables.filename = "../logs/" + filename + ".log";
							LiquidApplication.getGUI().send(LiquidApplication.getLogger(), LiquidLogger.WRITELOG);
						}
						end.setEnabled(true);
						LiquidApplication.getGUI().setEnable(false);
						LiquidApplication.getGUI().variables.simulating = true;
						LiquidApplication.getGUI().console.print_to_Console("Simulation Started.\n");
					}
					pause.setEnabled(true);
					run.setEnabled(false);
					step.setEnabled(false);			
				}catch(Exception e){
					LiquidApplication.getGUI().console.print_to_Console("Error: Inputed Value is Not Valid "+ e.getMessage() + "\n");
				}
			}
        });
		add(run);
		
		pause = new JButton("Pause");
		pause.setBounds(155,510,115,25);
		pause.setEnabled(false);
		pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().console.print_to_Console("Simulation Paused.\n");
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
		end.setEnabled(false);
		end.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				LiquidApplication.getGUI().console.print_to_Console("Simulation Ended.\n");
				LiquidApplication.getGUI().variables.simulating = false;
				LiquidApplication.getGUI().setEnable(true);
				end.setEnabled(false);
			    pause.setEnabled(false);
			    run.setEnabled(true);
			    step.setEnabled(true);
			}
        });
		add(end);
	}
	
	public void reset(){
		liqs.setSelectedIndex(0);
		temp.setText("10");
		visc.setText("1");
		time.setText("300");
	}
	
	public void update(){
		liqs.setSelectedIndex(0);
		temp.setText(Float.toString(LiquidApplication.getGUI().variables.temperature));
		visc.setText(Float.toString(LiquidApplication.getGUI().variables.viscosity));
		time.setText(Integer.toString(LiquidApplication.getGUI().variables.runtime));
	}
	
}