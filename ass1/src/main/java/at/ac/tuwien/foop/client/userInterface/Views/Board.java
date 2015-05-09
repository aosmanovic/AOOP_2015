package at.ac.tuwien.foop.client.userInterface.Views;





import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

public class Board extends JPanel implements ActionListener {
	
	private Timer timer;
	private Map m;
	
	public Board() {
		m = new Map();
		timer = new Timer(25, this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e){
		repaint();
	}
	
	public void paint(Graphics w) {
		super.paint(w);
		
		for(int y=0; y<21; y++)	{
			for(int x = 0; x<49; x++)	{
				
				if(m.getMap(x, y).equals("w"))
				{
					w.drawImage(m.getWall(), x*20, y*30, null);
				}
				
				else if(m.getMap(x, y).equals("C")) {
					w.drawImage(m.getCheese(), x*20, y*30, null);
				}
				
				else if(m.getMap(x, y).equals("m")) {
					w.drawImage(m.getMouse(), x*20, y*30, null);
				}
				
			}
		}
	}

}
