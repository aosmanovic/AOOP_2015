package at.ac.tuwien.foop.client.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.gui.utils.PlayerColor;
import at.ac.tuwien.foop.domain.Player;

public class BoardFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel label = new JLabel();
	private BoardPanel b;
	private static Logger log = LoggerFactory.getLogger(BoardFrame.class);


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

	}

	public void setLabel(Game game, Map<String, PlayerColor> map) {
		String result ="";

		for (Player p: game.getPlayers()) {
			result += "  NAME:   " + p.name() + "        COLOR:   ";
			result += map.get(p.name()).colorName;	       
		}
		log.info("RESULT: " + result);
		label.setText("Current players: "	+ result);
	}
}
