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
		setDoubleBuffered(true);
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

		Field[][] f = game.getBoard().fields();
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[i].length; j++) {
				Field field = f[i][j];

				if (field == Field.wall) {
					g.drawImage(images.getWall(), j * FieldImages.IMAGE_SIZE, i * FieldImages.IMAGE_SIZE, null);
				} else if (field == Field.start) {
					g.drawImage(images.getPath(), j * FieldImages.IMAGE_SIZE, i * FieldImages.IMAGE_SIZE, null);
				} else if (field == Field.floor) {
					g.drawImage(images.getPath(), j * FieldImages.IMAGE_SIZE, i * FieldImages.IMAGE_SIZE, null);
				} else if (field == Field.end) {
					g.drawImage(images.getCheese(), j * FieldImages.IMAGE_SIZE, i * FieldImages.IMAGE_SIZE, null);
				}
			}
		}

		game.getPlayers().forEach(
				p -> g.drawImage(images.getMouse(), p.coordinates().x * FieldImages.IMAGE_SIZE,
						p.coordinates().y * FieldImages.IMAGE_SIZE, null));
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
