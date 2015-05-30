package at.ac.tuwien.foop.client.userInterface.Views;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Panel;

import javax.swing.JPanel;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

public class BoardFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JLabel label = new JLabel();
	private BoardPanel b;
	
	public BoardFrame() {
		setTitle("Maze game");
		setSize(1100, 690);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		
	}

	public BoardPanel getBoard() {
		return b;
	}

	public void setBoard(BoardPanel b) {
		this.b = b;
		getContentPane().add(b);
		getContentPane().add(label, BorderLayout.PAGE_END);
		label.setText("Current players: " +b.getResult());
	}
}

