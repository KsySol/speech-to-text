package model;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.border.Border;



public class Frame {
	
	//створюэмо комспоненти в памяті
			JButton start = new JButton("Start");
			JButton stop = new JButton("Stop");
			JButton resume = new JButton("Resume");
			JButton clear = new JButton("Clear");
			JButton choice = new JButton("Сhoice of language");
			JButton exit = new JButton("Exit");
			static JTextArea area1 = new JTextArea(30,30);
			MyDrawPanelMain panel = new MyDrawPanelMain();
			Border etched = BorderFactory.createEtchedBorder();
          
			
	public  void FrameCreate (){
		 
		//задаємо фрейм+панель 
		JFrame frame = new JFrame("Speech to Text");
		
	//створення менеджера для нової панелі
		//FlowLayout f1 = new FlowLayout();
		//myPanel.setLayout(f1);
		
		
		
		start.setBackground(new Color(230, 230, 250));
		stop.setBackground(new Color(230, 230, 250));
		resume.setBackground(new Color(230, 230, 250));
		clear.setBackground(new Color(255, 228, 225));
		choice.setBackground(new Color(34, 238,230));
		exit.setBackground(new Color(0, 0, 0));
		exit.setForeground(new Color(255, 255, 255));
		
		start.setCursor(new Cursor(Cursor.HAND_CURSOR));
		stop.setCursor(new Cursor(Cursor.HAND_CURSOR));
		resume.setCursor(new Cursor(Cursor.HAND_CURSOR));
		clear.setCursor(new Cursor(Cursor.HAND_CURSOR));
		choice.setCursor(new Cursor(Cursor.HAND_CURSOR));
		exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		frame.getContentPane().add(panel);

     
		Border titled = BorderFactory.createTitledBorder(etched, "Press \"Start\" and begin to talk...");
        panel.setBorder(titled);
		
		//додавання компонентів на панель 
		panel.add(start);
        panel.add(stop);
        panel.add(resume);
        panel.add(area1);
        panel.add(clear);
        panel.add(exit);
		
		
		
		
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		
		//розміри і видимвсть стиль 
		frame.setSize(370, 620);
		frame.setVisible(true);
		
		stop.setEnabled(false);
		resume.setEnabled(false);
		
		ActionListener actionListenerStart = new StartPressStart();
		start.addActionListener(actionListenerStart);
		
		ActionListener actionListenerStop = new StartPressStop();
		stop.addActionListener(actionListenerStop);
		
		ActionListener actionListenerResume = new StartPressResume();
		resume.addActionListener(actionListenerResume);
		
		ActionListener actionListenerClear = new StartPressClear();
		clear.addActionListener(actionListenerClear);
		
		ActionListener actionListenerExit = new StartPressExit();
		exit.addActionListener(actionListenerExit);
	}
	
	
	public class StartPressStart implements ActionListener {
	     public void actionPerformed(ActionEvent e) {
	    	
	         
	    	 if(FrameLanguage.numberLang == 1){
	    		 
	    		 Border titled = BorderFactory.createTitledBorder(etched, "Please speak in english language ...");
	    		 panel.setBorder(titled);
		         
	    		 new Main();
	    		 start.setEnabled(false);
	    		 resume.setEnabled(false);
	    		 stop.setEnabled(true);
	    		
	    		 
	         }
	         else if(FrameLanguage.numberLang == 2){
	        	 
	        	 Border titled = BorderFactory.createTitledBorder(etched, "Please speak in russian language ...");
	        	 panel.setBorder(titled);
		         
	        	 new RusMain();
	        	 start.setEnabled(false);
	        	 resume.setEnabled(false);
	        	 stop.setEnabled(true);
	        	
	         }
	         else {
	        	 
	        	 Border titled = BorderFactory.createTitledBorder(etched, "Please speak in english-russian language ...");
	        	 panel.setBorder(titled);
		         
	        	 new AutoMain();
	        	 start.setEnabled(false);
	        	 resume.setEnabled(false);
	        	 stop.setEnabled(true);
	         }
	     }
	}
	
	public class StartPressStop implements ActionListener {
	     public void actionPerformed(ActionEvent e) {
	    	 
	    	 stop.setEnabled(false);
    		 resume.setEnabled(true);
    		 IgnoreSpeech.ignoreSpeechRecognitionResults();
	    	
	     }
	     
	}
	
	public class StartPressResume implements ActionListener {
	     public void actionPerformed(ActionEvent e) {
	    	 
	    	 stop.setEnabled(true);
    		 resume.setEnabled(false);
	    	 //відновити
    		 IgnoreSpeech.stopIgnoreSpeechRecognitionResults();
	     }
	}
	
	
	public class StartPressClear implements ActionListener {
	     public void actionPerformed(ActionEvent e) {
	    	 
	    	 area1.setText("");
	    }
	}
	
	public class StartPressExit implements ActionListener {
	     public void actionPerformed(ActionEvent e) {
	    	
	    	 System.exit(1); 
	    }
	}
	
	public static void printspeachResult(String SRR) {
	
		area1.append(SRR+"\n");
		}
}
