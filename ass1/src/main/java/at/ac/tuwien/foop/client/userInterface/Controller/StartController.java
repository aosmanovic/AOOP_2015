package at.ac.tuwien.foop.client.userInterface.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

		boardFrame.setBoard(new BoardPanel());
		boardFrame.addKeyListener(this);

		startFrame.addJoinGameButtonListener(e -> join(startFrame
				.getPlayerName()));
		startFrame.addConnectButtonListener(e -> connect(
				startFrame.getServerAddress(), DEFAULT_PORT));
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

	private void join(String playerName) {
		log.debug("join game");
		service.join(game, core, playerName);
	}

	private void startGame() {
		service.start(game, core);
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
			showBoard();
		} else if (e.type == GameEvent.Type.JOIN) {
			// TODO: implement
		}
	}

	@Override
	public void onUpdate(NewPlayerEvent e) {
		startFrame.printMessage(String.format("Player '%s' joined the game!",
				e.name));
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
