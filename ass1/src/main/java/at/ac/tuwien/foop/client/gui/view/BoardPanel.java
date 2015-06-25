package at.ac.tuwien.foop.client.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.ClientPlayer;
import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.gui.utils.ImageStore;
import at.ac.tuwien.foop.client.gui.utils.PlayerColor;
import at.ac.tuwien.foop.domain.Board.Field;

public class BoardPanel extends JPanel {
	private static Logger log = LoggerFactory.getLogger(BoardPanel.class);

	private static final long serialVersionUID = 1L;
	private ImageStore images;
	private PlayerColor colors;
	private Game game;

	public BoardPanel() {
		super();

		log.debug("create board panel");

		setLayout(new BorderLayout());
		images = ImageStore.getInstance();
		colors = PlayerColor.getInstance();
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
					g.drawImage(images.getWall(), j * ImageStore.IMAGE_SIZE, i
							* ImageStore.IMAGE_SIZE, null);
				} else if (field == Field.start) {
					g.drawImage(images.getPath(), j * ImageStore.IMAGE_SIZE, i
							* ImageStore.IMAGE_SIZE, null);
				} else if (field == Field.floor) {
					g.drawImage(images.getPath(), j * ImageStore.IMAGE_SIZE, i
							* ImageStore.IMAGE_SIZE, null);
				} else if (field == Field.end) {
					g.drawImage(images.getCheese(), j * ImageStore.IMAGE_SIZE,
							i * ImageStore.IMAGE_SIZE, null);
				}
			}
		}

		for (ClientPlayer p : game.getPlayers()) {
			if (!p.active()) {
				continue;
			}
			g.drawImage(images.getMouse(), p.coordinates().x
					* ImageStore.IMAGE_SIZE, p.coordinates().y
					* ImageStore.IMAGE_SIZE, ImageStore.IMAGE_SIZE,
					ImageStore.IMAGE_SIZE, colors.color(p.color()),
					null);
		}
		
		paintChildren(g);
	}

	public void setGame(Game game) {
		log.debug("set game");

		this.game = game;
		Field[][] fields = game.getBoard().fields();
		setPreferredSize(new Dimension(fields[0].length
				* ImageStore.IMAGE_SIZE, fields.length
				* ImageStore.IMAGE_SIZE));
		revalidate();
	}
}
