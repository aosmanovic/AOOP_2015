package at.ac.tuwien.foop.userInterface.Maze;



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
		
		for(int y=0; y<25; y++)	{
			for(int x = 0; x<25; x++)	{
				
				if(m.getMap(x, y).equals("w"))
				{
					w.drawImage(m.getWall(), x*25, y*25, null);
				}
				
				else if(m.getMap(x, y).equals("-")) 
					w.drawImage(m.getPath(), x*25, y*25, null);
				
				if(m.getMap(x, y).equals("C"))
					w.drawImage(m.getCheese(), x*25, y*25, null);
				
			}
		}
	}

}
