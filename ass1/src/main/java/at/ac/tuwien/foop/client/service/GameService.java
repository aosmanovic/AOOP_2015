package at.ac.tuwien.foop.client.service;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.domain.Wind;

public class GameService {
	public void join(Game game, GameCore core, String name) {
		if (game.joined()) {
			throw new IllegalStateException("Can't join if joined already!");
		}
		core.join(name);
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

	public void sendWind(Game game, GameCore core, Wind wind) {
		if (!game.joined() && !game.running()) {
			throw new IllegalStateException("Can't send wind if not joined and game is not running!");
		}
		core.sendWind(wind);
	}

	public void ping(GameCore gameCore) {
		gameCore.ping();
	}
}
