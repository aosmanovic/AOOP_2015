package at.ac.tuwien.foop.client.userInterface.Views;




import java.awt.Color;

import javax.swing.*;


public class Maze {

	private BoardPanel b;
	
	public Maze() {
		JFrame f = new JFrame();
		f.setTitle("Maze game");
		b = new BoardPanel();
		b.setBackground(Color.WHITE);
		f.getContentPane().add(b);
		f.setSize(1100, 690);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public BoardPanel getBoard() {
		return b;
	}

	public void setBoard(BoardPanel b) {
		this.b = b;
	}

	
	
}

