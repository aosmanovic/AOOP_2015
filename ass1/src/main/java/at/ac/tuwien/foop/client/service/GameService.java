package at.ac.tuwien.foop.client.service;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.domain.Wind;

public class GameService {
	public void join(Game game, GameCore core) {
		if (game.joined()) {
			throw new IllegalStateException();
		}
		core.join();
	}

	public void leave(Game game, GameCore core) {
		if (!game.joined()) {
			throw new IllegalStateException();
		}
		core.join();
	}

	public void disconnect(Game game, GameCore core) {
		if (game.joined()) {
			core.leave();
		}
		core.disconnect();
	}

	public void sendWind(Game game, GameCore core, Wind wind) {
		if (!game.joined() && !game.running()) {
			throw new IllegalStateException();
		}
		core.sendWind(wind);
	}

	public void ping(GameCore gameCore) {
		gameCore.ping();
	}
}
