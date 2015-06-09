package at.ac.tuwien.foop.client.userInterface.Controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.ClientHandler;
import at.ac.tuwien.foop.client.NettyClient;
import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.events.ConnectListener;
import at.ac.tuwien.foop.client.events.GameEvent;
import at.ac.tuwien.foop.client.events.GameEventListener;
import at.ac.tuwien.foop.client.events.NewPlayerEvent;
import at.ac.tuwien.foop.client.service.GameCore;
import at.ac.tuwien.foop.client.service.GameService;
import at.ac.tuwien.foop.client.userInterface.Views.BoardFrame;
import at.ac.tuwien.foop.client.userInterface.Views.BoardPanel;
import at.ac.tuwien.foop.client.userInterface.Views.PlayerColor;
import at.ac.tuwien.foop.client.userInterface.Views.StartFrame;
import at.ac.tuwien.foop.domain.WindGust;
import at.ac.tuwien.foop.domain.WindGust.Direction;

public class StartController implements ConnectListener, GameEventListener,
		KeyListener {

	private static Logger log = LoggerFactory.getLogger(StartController.class);
	private static final int DEFAULT_PORT = 20150;

	private Game game;
	private GameCore core = null;
	private StartFrame startFrame;
	private GameService service = new GameService();
	private BoardFrame boardFrame = new BoardFrame();

	public StartController() {
		startFrame = new StartFrame();

		boardFrame.addKeyListener(this);
		boardFrame.setBoard(new BoardPanel());

		startFrame.addJoinGameButtonListener(e -> service.join(game, core,
				startFrame.getPlayerName()));
		startFrame.addConnectButtonListener(e -> connect(
				startFrame.getServerAddress(), DEFAULT_PORT));
		startFrame.addDisconnectButtonListener(e -> service.disconnect(game,
				core));
		startFrame.addStartGameButtonListener(e -> service.start(game, core));
	}

	private void connect(String host, int port) {
		log.debug("connect to {}:{}", host, port);
		startFrame.printMessage(String.format("Connect to server '%s:%d'!",
				host, port));
		game = new Game();
		startFrame.setGame(game);
		game.addGameEventListener(this);

		NettyClient c = new NettyClient(game, host, port);
		c.addConnectListener(this);
		new Thread(c, "Network-Layer-Thread").start();
	}

	public void setCore(ClientHandler core) {
		this.core = core;
	}

	public void showStartView() {
		startFrame.setVisible(true);
	}

	private void showBoard() {
		
		boardFrame.setVisible(true);
	}

	@Override
	public void onConnect(NettyClient client) {
		core = client.getClientHandler();
		startFrame.printMessage("Connected to server!");
		startFrame.showJoinGamePanel();
		// service.join(game, core, "A");
		// startFrame.showNewGamePanel();
	}

	@Override
	public void onConnecitonFailure() {
		startFrame.printMessage("Connection to server failed!");
		startFrame.showFailure();
	}

	@Override
	public void onUpdate(GameEvent e) {
		if (e.type == GameEvent.Type.UPDATE) {
			boardFrame.getBoard().repaint();
		} else if (e.type == GameEvent.Type.BOARD) {
			boardFrame.getBoard().setGame(game);
		} else if (e.type == GameEvent.Type.START) {
			Map<String,PlayerColor> map = assignPlayerColor();
			boardFrame.getBoard().setColor(map);
			boardFrame.setLabel(game, map);
			showBoard();
		} else if (e.type == GameEvent.Type.JOIN) {
			startFrame.showStartGamePanel();
		} else if (e.type == GameEvent.Type.OVER) {
			int gameover = startFrame.showGameOver(game);
			if (gameover == 0) {
				log.info("LOAD new level");
				service.changeLevel(core);
				boardFrame.getBoard().setGame(game);
			} else if (gameover == 1) {
				log.info("Leave");
				service.leave(game, core);
				hideBoard();
			}
		}
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
		startFrame.printMessage(String.format("Player '%s' joined the game!",
				e.name));
	}

	public void hideBoard() {
		System.exit(0);
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
}
