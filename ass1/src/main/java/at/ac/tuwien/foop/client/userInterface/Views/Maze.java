package at.ac.tuwien.foop.client.userInterface.Views;




import javax.swing.*;


public class Maze {

	public Maze() {
		JFrame f = new JFrame();
		f.setTitle("Maze game");
		Board b = new Board();
		f.add(b);
		f.setSize(1100, 690);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

