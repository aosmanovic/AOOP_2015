package at.ac.tuwien.foop.client.gui.view;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.gui.utils.FieldImages;
import at.ac.tuwien.foop.client.gui.utils.PlayerColor;
import at.ac.tuwien.foop.domain.Board.Field;
import at.ac.tuwien.foop.domain.Player;

public class BoardPanel extends JPanel {
	private static Logger log = LoggerFactory.getLogger(BoardPanel.class);

	private static final long serialVersionUID = 1L;
	private FieldImages images;
	private Game game;
	private static Map<String, PlayerColor> playercolor = new HashMap<>();

	public BoardPanel() {
		super();
		images = new FieldImages();
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
					g.drawImage(images.getWall(), j * FieldImages.IMAGE_SIZE, i
							* FieldImages.IMAGE_SIZE, null);
				} else if (field == Field.start) {
					g.drawImage(images.getPath(), j * FieldImages.IMAGE_SIZE, i
							* FieldImages.IMAGE_SIZE, null);
				} else if (field == Field.floor) {
					g.drawImage(images.getPath(), j * FieldImages.IMAGE_SIZE, i
							* FieldImages.IMAGE_SIZE, null);
				} else if (field == Field.end) {
					g.drawImage(images.getCheese(), j * FieldImages.IMAGE_SIZE,
							i * FieldImages.IMAGE_SIZE, null);
				}
			}
		}

		for (Player p : game.getPlayers()) {
			g.drawImage(images.getMouse(), p.coordinates().x
					* FieldImages.IMAGE_SIZE, p.coordinates().y
					* FieldImages.IMAGE_SIZE, 27, 27,
					playercolor.get(p.name()).color, null);
		}
	}


	public void setGame(Game game) {
		log.debug("set game");
		this.game = game;
	}
	
	public void setColor(Map<String, PlayerColor> map) {
		playercolor = map;
	}
	
	

}
