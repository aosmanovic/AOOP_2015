package at.ac.tuwien.foop.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game extends Observable {
	private static Logger log = LoggerFactory.getLogger(Game.class);
	private static final int MAX_PLAYERS = 4;

	private List<Player> player = new ArrayList<>();
	private boolean started = false;

	/**
	 * Start the game.
	 */
	public void start() {
		log.info("start game");
		started = true;
		notifyObservers();
	}

	/**
	 * Calculates the game progress.
	 */
	public void next() { // maybe use a delta later on
		if (!started) {
			return;
		}
		// calculate next step
		notifyObservers();
	}

	/**
	 * Let's a player join the game if aspot is left.
	 * 
	 * @return true if the player joined, fals otherwise
	 */
	public synchronized boolean join(Player newPlayer) {
		if (player.size() < MAX_PLAYERS) {
			player.add(newPlayer);
			notifyObservers();
			return true;
		}
		return false;
	}
}
