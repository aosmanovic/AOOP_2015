package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.events.GameEvent;
import at.ac.tuwien.foop.client.events.GameEventListener;
import at.ac.tuwien.foop.client.events.NewPlayerEvent;
import at.ac.tuwien.foop.domain.Board.Field;
import at.ac.tuwien.foop.domain.Player;

public class BoardPanel extends JPanel {
	private static Logger log = LoggerFactory.getLogger(BoardPanel.class);

	private static final long serialVersionUID = 1L;
	private FieldImages images;
	private Game game;
	private static Map<String, PlayerColor> playercolor = new HashMap<>();
	private String result = "";
	private Consumer<Integer> onGameSet;

	public BoardPanel() {
		images = new FieldImages();
		// colors.put(Color.RED, "Red"); colors.put(Color.BLUE, "Blue");
		// colors.put(Color.GREEN, "Green");
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

		/*
		 * game.getPlayers().forEach( p -> g.drawImage(images.getMouse(),
		 * p.coordinates().x * FieldImages.IMAGE_SIZE, p.coordinates().y *
		 * FieldImages.IMAGE_SIZE, null));
		 */

		// for (Color key : colors.keySet()) {
		// System.out.println("Key = " + key);

		for (Player p : game.getPlayers()) {

			// Color key = colors.keySet().iterator().next();
			// System.out.println("Key = " + key);

			g.drawImage(images.getMouse(), p.coordinates().x
					* FieldImages.IMAGE_SIZE, p.coordinates().y
					* FieldImages.IMAGE_SIZE, 27, 27,
					playercolor.get(p.name()).color, null);

		}
	}

	//
	// System.out.println("COLOR" + colors.get(key));
	// g.drawImage(images.getMouse(), game.getPlayers().get(i).coordinates().x *
	// FieldImages.IMAGE_SIZE, game.getPlayers().get(i).coordinates().y *
	// FieldImages.IMAGE_SIZE, 27, 27, key, null);

	// }

	public void setGame(Game game) {
		log.debug("set game");
		this.game = game;
		game.addGameEventListener(new GameEventListener() {

			@Override
			public void onUpdate(NewPlayerEvent e) {
			}

			@Override
			public void onUpdate(GameEvent e) {
				log.debug("get game event********************" + e.type.toString());
				if (e.type.equals(GameEvent.Type.START)) {
					log.debug("new player, update colors");
					log.info("PLAYERS :"+ game.getPlayers());

					// TODO Auto-generated method stub
					System.out.println("IN");
					List<PlayerColor> colors = new ArrayList<>();
					colors.add(new PlayerColor(Color.RED, "Red"));
					colors.add(new PlayerColor(Color.BLUE, "Blue"));
					colors.add(new PlayerColor(Color.GREEN, "Green"));

					for (int i = 0; i < game.getPlayers().size(); i++) {
						playercolor.put(game.getPlayers().get(i).name(),
								colors.get(i % colors.size()));
					}
				}
			}
		});

		if (onGameSet != null)
			onGameSet.accept(0);
	}

	public void setOnGameSetCallback(Consumer<Integer> f) {
		onGameSet = f;
	}

	public String getResult() {

		/*
		 * for (Player p: game.getPlayers()) { // Color key =
		 * colors.keySet().iterator().next(); result += "   NAME: " + p.name() +
		 * "    COLOR: " + playercolor.get(p.name()).name + "\n"; }
		 */
		return result;
	}

	public class PlayerColor {

		public final Color color;
		public final String name;

		public PlayerColor(Color color, String name) {
			super();
			this.color = color;
			this.name = name;
		}

	}

}
