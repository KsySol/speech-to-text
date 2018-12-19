package model;

import javax.swing.*;
import java.awt.*;
 
public class MyDrawPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paintComponent (Graphics g) {
		Image image = new ImageIcon("123.jpg").getImage();
        g.drawImage(image, 3, 4, this);
    }
}