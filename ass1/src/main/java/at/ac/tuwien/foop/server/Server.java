package at.ac.tuwien.foop.server;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.server.domain.Game;
import at.ac.tuwien.foop.server.domain.Game.GameState;
import at.ac.tuwien.foop.server.network.NettyServer;
import at.ac.tuwien.foop.server.service.GameLogicService;

public class Server {
	private static Logger log = LoggerFactory.getLogger(Server.class);

	private NettyServer server;
	private Timer timer;

	public Server() {
		log.debug("set up server!");
		Game game = new Game(new GameLogicService().loadBoard(GameLogicService.BOARD_PATH));
		server = new NettyServer(game);
		new Thread(server).start();
		timer = new Timer("gameLoop", true);
		timer.scheduleAtFixedRate(new GameLoop(game), 0, 1000);
	}

	public class GameLoop extends TimerTask {

		private Game game;

		public GameLoop(Game game) {
			this.game = game;
		}

		@Override
		public void run() {
			if (game.state() == GameState.running) {
				game.next();
			} else if (game.state() == GameState.over) {
				timer.cancel();
			}
		}
	}
}
