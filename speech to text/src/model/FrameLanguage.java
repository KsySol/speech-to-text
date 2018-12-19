package model;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;


public class FrameLanguage {
	
	static public int numberLang; 

	JButton continueButton = new JButton("Continue >>>");
    JRadioButton Eng = new JRadioButton("     Eng        ");
    JRadioButton Rus = new JRadioButton("     Rus        ");
    JRadioButton Auto = new JRadioButton("    Auto       ");
    ButtonGroup buttongroup = new ButtonGroup();
    JLabel labelText = new JLabel("         Select speech language:                     ");
    
    public  void frameLang() {
    	
    	//створення панелі
    	//JPanel myPanelLang = new JPanel();
    	
    	JFrame frame = new JFrame("Speech to Text");
    	//frame.setContentPane(myPanelLang);
    	
    	MyDrawPanel panel = new MyDrawPanel();
    	frame.getContentPane().add(panel);
    	
        frame.setResizable(false);

    			
    	//створення менеджера для нової панелі
    	//FlowLayout f1 = new FlowLayout();
    	//myPanelLang.setLayout(f1);
    	
    	
    	
    	buttongroup.add(Eng);
		buttongroup.add(Rus);
		buttongroup.add(Auto);
		Eng.setSelected(true);
    	
		//Border etched = BorderFactory.createEtchedBorder();
        //Border titled = BorderFactory.createTitledBorder(etched, "Select speech language");
        //panel.setBorder(titled);
		continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
        continueButton.setBounds(185, 75, 0, 0);
        continueButton.setBackground(new Color(230, 230, 250));
        labelText.setForeground(new Color(25, 25, 112));
        Eng.setForeground(new Color(147, 112, 219));
        Rus.setForeground(new Color(147, 112, 219));
        Auto.setForeground(new Color(147, 112, 219));
        
        panel.add(labelText);
		//myPanel.add(choice);
        panel.add(Eng);
        panel.add(Rus);
        panel.add(Auto);
        panel.add(continueButton);
	    
	  //задаємо фрейм+панель 
	  		
	  		
	  		//розміри і видимвсть стиль 
	  		frame.setSize(300, 170);
	  		frame.setVisible(true);
	  		
	  		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  		
	  		ActionListener actionListenercontinueButton = new StartPressContinueButton();
	  		continueButton.addActionListener(actionListenercontinueButton);
			
 }
    
class StartPressContinueButton implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	
    	if(Eng.isSelected()){
   		 
   		 numberLang = 1;
   		 
        }
        else if(Rus.isSelected()){
       	 
       	 numberLang = 2;
       	
        }
        else {
       	 
       	 numberLang = 3;
        }
   	 
    	
    	continueButton.setEnabled(false);
   	    
    	Frame Frame = new Frame();
 	    Frame.FrameCreate();
   	
    }
    
}
}

