package at.ac.tuwien.foop.server;

import java.util.Timer;
import java.util.TimerTask;

import at.ac.tuwien.foop.server.domain.Game;
import at.ac.tuwien.foop.server.network.NettyServer;

public class Server {

	private NettyServer server;
	
	public Server() {
		server = new NettyServer();
		new Thread(server).start();
		new Timer("gameLoop", true).scheduleAtFixedRate(new GameLoop(new Game()), 0, 1000);
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
