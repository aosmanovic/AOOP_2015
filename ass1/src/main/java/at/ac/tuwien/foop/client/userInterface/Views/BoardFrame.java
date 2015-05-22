package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class BoardFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	private BoardPanel b;
	
	public BoardFrame() {
		setTitle("Maze game");
		setSize(1100, 690);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.addKeyListener();
	}

	public BoardPanel getBoard() {
		return b;
	}

	public void setBoard(BoardPanel b) {
		this.b = b;
		getContentPane().add(b);
	}
}

