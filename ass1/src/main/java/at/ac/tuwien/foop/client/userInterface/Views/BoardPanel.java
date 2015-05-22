package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.domain.Board.Field;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private FieldImages images;
	private Game game;

	public BoardPanel() {
		images = new FieldImages();
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (game == null) {
			return;
		}

		for (int i = 0; i < game.getBoard().fields().length; i++) {
			for (int j = 0; j < game.getBoard().fields()[i].length; j++) {
				Field field = game.getBoard().fields()[i][j];

				if (field == Field.wall) {
					g.drawImage(images.getWall(), j * 26, i * 26, null);
				} else if (field == Field.start) {
					g.drawImage(images.getWall(), j * 26, i * 26, null);
				} else if (field == Field.floor) {
					g.drawImage(images.getPath(), j * 26, i * 26, null);
				} else if (field == Field.end) {
					g.drawImage(images.getCheese(), j * 26, i * 26, null);
				}
			}
		}

		game.getPlayers().forEach(
				p -> g.drawImage(images.getMouse(), p.coordinates().x * 26,
						p.coordinates().y * 26, null));
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
