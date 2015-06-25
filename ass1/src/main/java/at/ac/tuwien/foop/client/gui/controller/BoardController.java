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

		spectatorMode();
	}

	// game events
	
	@Override
	public void onUpdate(GameEvent e) {
		if (e.type == GameEvent.Type.UPDATE) {
			boardPanel.repaint();
			playerPanel.players(game.getPlayers());
			compassPanel.wind(game.getWind());
		} else if (e.type == GameEvent.Type.BOARD) {
			boardPanel.setGame(game);
			if (!boardFrame.isVisible()) {
				showBoardFrame();
			}
			boardFrame.pack();
		} else if (e.type == GameEvent.Type.START) {
			gameMode();
		} else if (e.type == GameEvent.Type.JOIN) {
			joindeMode();
		} else if (e.type == GameEvent.Type.OVER) {
			gameOverMode();
			game.leave();
		}
	}
	
	@Override
	public void onUpdate(NewPlayerEvent e) {
	}

	// messages

	private void spectatorMode() {
		messagePanel.setTopMessage("- SPECTATOR MODE -");
		messagePanel
				.setBottomMessage("- press ENTER to join or ESC to disconnect -");
	}

	private void joindeMode() {
		messagePanel.setTopMessage("- ready to play!? -");
		messagePanel
				.setBottomMessage("- press ENTER to start the game or ESC to leave -");
	}

	private void gameOverMode() {
		messagePanel.setTopMessage(String.format("- %s has won!!! -",
				game.winner()));
		messagePanel
				.setBottomMessage("- press ENTER to join or ESC to disconnect -");
	}
	
	private void gameMode() {
		messagePanel.setTopMessage("");
		messagePanel.setBottomMessage("");
	}

	// inputs
	
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
