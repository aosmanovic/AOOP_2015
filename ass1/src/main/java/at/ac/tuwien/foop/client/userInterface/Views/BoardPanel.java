package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import at.ac.tuwien.foop.client.domain.Board;
import at.ac.tuwien.foop.client.domain.Board.Field;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Map m;
	private Board board;
	
	
	public BoardPanel() {
		m = new Map();
	}

	public void actionPerformed(ActionEvent e){
		repaint();
	}

	public void paint(Graphics w) {
		super.paint(w);
		if(board==null)return;
		
		for(int i=0; i<board.fields().length; i++) {
			for(int j=0; j<board.fields()[i].length; j ++) {
				
				if (board.fields()[i][j] == Field.wall) {
					w.drawImage(m.getWall(), i*26, j*26, null);
				} else if (board.fields()[i][j] == Field.start) {
					w.drawImage(m.getMouse(), i*26, j*26, null);
				} else if (board.fields()[i][j] == Field.floor) {
					w.drawImage(m.getPath(), i*26, j*26, null);
				} else if (board.fields()[i][j] == Field.end) {
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
