package at.ac.tuwien.foop.server.domain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.server.event.GameEvent;
import at.ac.tuwien.foop.server.event.GameEvent.Type;
import at.ac.tuwien.foop.server.event.GameEventListener;

public class Game {
	private static Logger log = LoggerFactory.getLogger(Game.class);
	private static final int MAX_PLAYERS = 4;

	private List<GameEventListener> listeners = new ArrayList<>();

	private boolean started = false;
	private List<Player> player = new ArrayList<>();

	public void addGameEventListener(GameEventListener listener) {
		listeners.add(listener);
	}

	public void removeGameEventListener(GameEventListener listener) {
		listeners.remove(listener);
	}

	public void fireGameEvent(GameEvent event) {
		listeners.forEach(e -> e.onUpdate(event));
	}

	/**
	 * Start the game.
	 */
	public void start() {
		log.info("start game");
		started = true;
		fireGameEvent(new GameEvent(Type.START));
	}

	/**
	 * Calculates the game progress.
	 */
	public void next() { // maybe use a delta later on
		if (!started) {
			return;
		}
		// calculate next step
		fireGameEvent(new GameEvent(Type.UPDATE));
	}

	/**
	 * A player joins the game if the maximum amount of players is not reached
	 * yet.
	 * 
	 * @return true if the player joined, false otherwise
	 */
	public synchronized boolean join(Player newPlayer) {
		if (player.size() < MAX_PLAYERS) {
			player.add(newPlayer);
			fireGameEvent(new GameEvent(Type.NEW_PLAYER));
			return true;
		}
		return false;
	}

	public void leave(Player p) {
		player.remove(p);
		if (player.size() == 0) {
			// TODO: stop game?
		}
		fireGameEvent(new GameEvent(Type.REMOVE_PLAYER));
	}
}
