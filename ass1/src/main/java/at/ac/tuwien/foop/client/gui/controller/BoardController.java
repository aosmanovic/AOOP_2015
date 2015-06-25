package at.ac.tuwien.foop.client.gui.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.events.BoardControllerListener;
import at.ac.tuwien.foop.client.events.GameEvent;
import at.ac.tuwien.foop.client.events.GameEventListener;
import at.ac.tuwien.foop.client.events.NewPlayerEvent;
import at.ac.tuwien.foop.client.gui.view.BoardFrame;
import at.ac.tuwien.foop.client.gui.view.BoardPanel;
import at.ac.tuwien.foop.client.gui.view.CompassPanel;
import at.ac.tuwien.foop.client.gui.view.MessagePanel;
import at.ac.tuwien.foop.client.gui.view.PlayerPanel;
import at.ac.tuwien.foop.client.service.GameCore;
import at.ac.tuwien.foop.client.service.GameService;
import at.ac.tuwien.foop.domain.WindGust;
import at.ac.tuwien.foop.domain.WindGust.Direction;

public class BoardController implements GameEventListener, KeyListener {
	private static Logger log = LoggerFactory.getLogger(BoardController.class);

	private Game game;
	private GameCore core;
	private GameService service = new GameService();
	private BoardFrame boardFrame;

	private BoardPanel boardPanel;
	private MessagePanel messagePanel;
	private PlayerPanel playerPanel;
	private CompassPanel compassPanel;

	private List<BoardControllerListener> listeners = new ArrayList<>();

	public BoardController(Game game, GameCore core) {
		log.debug("create board controller");

		this.game = game;
		this.core = core;

		boardFrame = new BoardFrame();
		boardPanel = new BoardPanel();
		messagePanel = new MessagePanel();
		playerPanel = new PlayerPanel();
		compassPanel = new CompassPanel(game.getWind());

		boardFrame.addKeyListener(this);
		boardFrame
				.setBoard(boardPanel, playerPanel, compassPanel, messagePanel);

		game.addGameEventListener(this);

		// TODO: remove after tests
		spectatorMode();
	}

	@Override
	public void onUpdate(GameEvent e) {
		if (e.type == GameEvent.Type.UPDATE) {
			boardPanel.repaint();
			playerPanel.setPlayer(game.getPlayers());
		} else if (e.type == GameEvent.Type.BOARD) {
			boardPanel.setGame(game);
			if (!boardFrame.isVisible()) {
				showBoardFrame();
			}
			// game.join();
		} else if (e.type == GameEvent.Type.START) {
			// Map<String,PlayerColor> map = assignPlayerColor();
			// boardFrame.getBoard().setColor(map);
			// boardFrame.setLabel(game, map);
			// showBoard();
		} else if (e.type == GameEvent.Type.JOIN) {
			joindeMode();
		} else if (e.type == GameEvent.Type.OVER) {
			// nextGame = new Game();
			// game.setBoard(newGame.getBoard());
			// int gameover = startFrame.showGameOver(game);
			// if (gameover == 0) {
			// log.info("LOAD new level");
			// service.changeLevel(core);
			// // boardFrame.getBoard().setGame(game);
			// } else if (gameover == 1) {
			// log.info("Leave");
			// service.leave(game, core);
			// // TODO: close board and inform controller (that in turn informs
			// the start-controller?!)
			// }
		}
	}

	// private void setColor() {
	// Map<String,PlayerColor> map = assignPlayerColor();
	// boardFrame.getBoard().setColor(map);
	// boardFrame.setLabel(game, map);
	// }
	// private Map<String, PlayerColor> assignPlayerColor() {
	// log.debug("new player, update colors");
	// log.info("PLAYERS :" + game.getPlayers());
	//
	// Map<String, PlayerColor> playercolor = new HashMap<String,
	// PlayerColor>();
	// List<PlayerColor> colors = new ArrayList<>();
	// colors.add(new PlayerColor(Color.RED, "Red"));
	// colors.add(new PlayerColor(Color.BLUE, "Blue"));
	// colors.add(new PlayerColor(Color.GREEN, "Green"));
	//
	// for (int i = 0; i < game.getPlayers().size(); i++) {
	// playercolor.put(game.getPlayers().get(i).name(),
	// colors.get(i % colors.size()));
	// }
	// return playercolor;
	// }

	private void spectatorMode() {
		messagePanel.setTopMessage("- SPECTATOR MODE -");
		messagePanel
				.setBottomMessage("- press ENTER to join or ESC to disconnect -");
	}

	private void joindeMode() {
		messagePanel
				.setBottomMessage("- press SPACE to start the game or ESC to leave -");
		messagePanel.setTopMessage("- ready to play!? -");
	}

	@Override
	public void onUpdate(NewPlayerEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// wind
		if (game.running()) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				service.sendWind(game, core, new WindGust(Direction.NORTH, 1));
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				service.sendWind(game, core, new WindGust(Direction.SOUTH, 1));
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				service.sendWind(game, core, new WindGust(Direction.WEST, 1));
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				service.sendWind(game, core, new WindGust(Direction.EAST, 1));
			}
		} else {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (!game.joined()) {
					core.join();
				} else {
					core.start();
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (game.joined()) {
				game.leave();
				core.leave();
				spectatorMode();
			} else {
				game.removeGameEventListener(this);
				listeners.forEach(l -> l.onDisconnectRequest());
				boardFrame.dispose();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void showBoardFrame() {
		log.debug("show board");
		boardFrame.pack();
		boardFrame.setVisible(true);
		boardFrame.setLocationRelativeTo(null);
	}

	public void addBoardControllerListener(BoardControllerListener listener) {
		listeners.add(listener);
	}
}
