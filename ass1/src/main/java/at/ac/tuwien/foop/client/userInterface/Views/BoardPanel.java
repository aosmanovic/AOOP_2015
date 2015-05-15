package at.ac.tuwien.foop.client.userInterface.Views;





import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

import at.ac.tuwien.foop.client.domain.Board;
import at.ac.tuwien.foop.client.domain.Board.Field;
import at.ac.tuwien.foop.client.domain.Fields;
import at.ac.tuwien.foop.domain.message.Message.Type;

public class BoardPanel extends JPanel implements ActionListener {

	private Timer timer;
	private Map m;
	private Board board;
	
	
	public BoardPanel() {
		m = new Map();
		timer = new Timer(25, this);
		timer.start();
		
		//Board.createBoard(getBoard().toString(), 3);
	}

	public void actionPerformed(ActionEvent e){
		repaint();
	}

	public void paint(Graphics w) {
		super.paint(w);
//	System.out.println(board + "out" + this);	
		if(board==null)return;
		
		/*for(int y=0; y<21; y++)	{
			for(int x = 0; x<37; x++)	{

				if(m.getMap(x, y).equals("w"))
				{
					w.drawImage(m.getWall(), x*26, y*26, null);
				}

				else if(m.getMap(x, y).equals("C")) {
					w.drawImage(m.getCheese(), x*26, y*26, null);
				}

				else if(m.getMap(x, y).equals("m")) {
					w.drawImage(m.getMouse(), x*26, y*26, null);
				}

			}
		}*/
		/*for(int y=0; y<21; y++) {
			for(int x = 0; x<37; x++)	{
				if(getBoard().toString().startsWith("w")) {
					w.drawImage(m.getWall(), x*26, y*26, null);
				}
			}
		}*/
		
		for(int i=0; i<board.getFields().length; i++) {
System.out.println("in");			for(int j=0; j<board.getFields()[i].length; j ++) {
				
				
				if (board.getFields()[i][j] == Field.wall) {
					w.drawImage(m.getWall(), i*26, j*26, null);
				} else if (board.getFields()[i][j] == Field.start) {
					w.drawImage(m.getMouse(), i*26, j*26, null);
				} else if (board.getFields()[i][j] == Field.floor) {
					w.drawImage(m.getPath(), i*26, j*26, null);
				} else if (board.getFields()[i][j] == Field.end) {
					w.drawImage(m.getCheese(), i*26, j*26, null);
				}
				
			}
		}
		
		



	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		System.out.println(board + "***********************");
		this.board = board;
	}
}
