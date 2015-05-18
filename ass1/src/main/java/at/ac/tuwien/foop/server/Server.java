package at.ac.tuwien.foop.server;

import java.util.Timer;
import java.util.TimerTask;

import at.ac.tuwien.foop.server.domain.Game;
import at.ac.tuwien.foop.server.network.NettyServer;
import at.ac.tuwien.foop.server.service.GameLogicService;

public class Server {

	private NettyServer server;
	
	public Server() {
		Game game = new Game(new GameLogicService().loadBoard(GameLogicService.BOARD_PATH));
		server = new NettyServer(game);
		new Thread(server).start();
		new Timer("gameLoop", true).scheduleAtFixedRate(new GameLoop(game), 0, 1000);
	}

	public class GameLoop extends TimerTask {

		private Game game;
		
		public GameLoop(Game game) {
			this.game = game;
		}
		
		@Override
		public void run() {
			game.next();
		}
	}
}
