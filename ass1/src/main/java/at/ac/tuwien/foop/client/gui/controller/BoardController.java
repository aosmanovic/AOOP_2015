package at.ac.tuwien.foop.client.gui.controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.events.GameEvent;
import at.ac.tuwien.foop.client.events.GameEventListener;
import at.ac.tuwien.foop.client.events.NewPlayerEvent;
import at.ac.tuwien.foop.client.gui.utils.PlayerColor;
import at.ac.tuwien.foop.client.gui.view.BoardFrame;
import at.ac.tuwien.foop.client.gui.view.BoardPanel;
import at.ac.tuwien.foop.client.service.GameCore;
import at.ac.tuwien.foop.client.service.GameService;
import at.ac.tuwien.foop.domain.WindGust;
import at.ac.tuwien.foop.domain.WindGust.Direction;

public class BoardController implements GameEventListener, KeyListener {
	private static Logger log = LoggerFactory.getLogger(BoardController.class);

	private Game game;
	private GameCore core = null;
	private GameService service = new GameService();
	private BoardFrame boardFrame = new BoardFrame();

	public BoardController() {
		boardFrame.addKeyListener(this);
		boardFrame.setBoard(new BoardPanel());
//		game = new Game();
//		startFrame.setGame(game);
//		game.addGameEventListener(this);
	}

	private void showBoard() {

		boardFrame.setVisible(true);
	}

	@Override
	public void onUpdate(GameEvent e) {
		if (e.type == GameEvent.Type.UPDATE) {
			boardFrame.getBoard().repaint();
		} else if (e.type == GameEvent.Type.BOARD) {
			// game = nextGame;
			game.join();
			boardFrame.getBoard().setGame(game);
		} else if (e.type == GameEvent.Type.START) {
			// Map<String,PlayerColor> map = assignPlayerColor();
			// boardFrame.getBoard().setColor(map);
			// boardFrame.setLabel(game, map);
			showBoard();
		} else if (e.type == GameEvent.Type.JOIN) {
			setColor();
//			startFrame.showStartGamePanel();
		} else if (e.type == GameEvent.Type.OVER) {
			// nextGame = new Game();
			// game.setBoard(newGame.getBoard());
//			int gameover = startFrame.showGameOver(game);
//			if (gameover == 0) {
//				log.info("LOAD new level");
//				service.changeLevel(core);
//				// boardFrame.getBoard().setGame(game);
//			} else if (gameover == 1) {
//				log.info("Leave");
//				service.leave(game, core);
//				// TODO: close board and inform controller (that in turn informs the start-controller?!)
//			}
		}
	}

	
	private void setColor() {
		Map<String,PlayerColor> map = assignPlayerColor();
		boardFrame.getBoard().setColor(map);
		boardFrame.setLabel(game, map);		
	}
	private Map<String, PlayerColor> assignPlayerColor() {
		log.debug("new player, update colors");
		log.info("PLAYERS :" + game.getPlayers());

		Map<String, PlayerColor> playercolor = new HashMap<String, PlayerColor>();
		List<PlayerColor> colors = new ArrayList<>();
		colors.add(new PlayerColor(Color.RED, "Red"));
		colors.add(new PlayerColor(Color.BLUE, "Blue"));
		colors.add(new PlayerColor(Color.GREEN, "Green"));

		for (int i = 0; i < game.getPlayers().size(); i++) {
			playercolor.put(game.getPlayers().get(i).name(),
					colors.get(i % colors.size()));
		}
		return playercolor;
	}

	@Override
	public void onUpdate(NewPlayerEvent e) {
		setColor();
//		startFrame.printMessage(String.format("Player '%s' joined the game!",
//				e.name));
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			service.sendWind(game, core, new WindGust(Direction.NORTH, 1));
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			service.sendWind(game, core, new WindGust(Direction.SOUTH, 1));
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			service.sendWind(game, core, new WindGust(Direction.WEST, 1));
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			service.sendWind(game, core, new WindGust(Direction.EAST, 1));
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	public void showBoardFrame() {
		// TODO Auto-generated method stub
		
	}
}
