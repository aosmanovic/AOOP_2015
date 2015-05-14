package at.ac.tuwien.foop.client.userInterface.Views;





import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

import at.ac.tuwien.foop.client.domain.Board;

public class BoardPanel extends JPanel implements ActionListener {
	
	private Timer timer;
	private Map m;
	private Board board;
	
	public BoardPanel() {
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
			for(int x = 0; x<37; x++)	{
				
				if(m.getMap(x, y).equals("w"))
				{
					w.drawImage(m.getWall(), x*25, y*25, null);
				}
				
				else if(m.getMap(x, y).equals("C")) {
					w.drawImage(m.getCheese(), x*25, y*25, null);
				}
				
				else if(m.getMap(x, y).equals("m")) {
					w.drawImage(m.getMouse(), x*25, y*25, null);
				}
				
			}
		}
		
	
		
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
