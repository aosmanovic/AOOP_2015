package at.ac.tuwien.foop.client.service;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.domain.WindGust;

public class GameService {
	public void join(Game game, GameCore core, String name) {
		if (game.joined()) {
			throw new IllegalStateException("Can't join if joined already!");
		}
		core.join();
	}

	public void leave(Game game, GameCore core) {
		if (!game.joined()) {
			throw new IllegalStateException("Can't leave if not joined first!");
		}
		core.leave();
	}

	public void disconnect(Game game, GameCore core) {
		if (game.joined()) {
			core.leave();
		}
		core.disconnect();
	}

	public void sendWind(Game game, GameCore core, WindGust wind) {
		if (!game.joined() && !game.running()) {
			throw new IllegalStateException("Can't send wind if not joined and game is not running!");
		}
		core.sendWind(wind);
	}

	public void start(Game game, GameCore core) {
		if (!game.joined()) {
			throw new IllegalStateException("Can't start if not joinde first!");
		}
		core.start();
	}

	public void ping(GameCore gameCore) {
		gameCore.ping();
	}
	
	public void changeLevel(GameCore gameCore) {
		gameCore.newLevel();
	}
}
