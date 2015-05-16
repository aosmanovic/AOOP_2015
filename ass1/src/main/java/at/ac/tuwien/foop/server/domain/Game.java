package at.ac.tuwien.foop.server.domain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.Board;
import at.ac.tuwien.foop.domain.Board.Field;
import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.server.event.GameEvent;
import at.ac.tuwien.foop.server.event.GameEvent.Type;
import at.ac.tuwien.foop.server.event.GameEventListener;
import at.ac.tuwien.foop.server.service.GameLogicService;

public class Game {
	private static Logger log = LoggerFactory.getLogger(Game.class);
	private static final int MAX_PLAYERS = 4;

	private List<GameEventListener> listeners = new ArrayList<>();

	private boolean started = false;
	private List<Player> player = new ArrayList<>();
	private BoardString boardString;
	private Board board;
	private GameLogicService service = new GameLogicService();

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

		// call movementmethod here
		service.movement(this);
	}

	/**
	 * A player joins the game if the maximum amount of players is not reached
	 * yet and no other player with the same name joined before.
	 * 
	 * @return true if the player joined, false otherwise
	 */
	public synchronized boolean join(String name) {
		if (player.size() < MAX_PLAYERS && !player.stream().anyMatch(p -> p.name().equals(name))) {
			
			player.add(new Player(name, findFreeStartingCoordinates()));
			fireGameEvent(new GameEvent(Type.NEW_PLAYER));
			return true;
		}
		return false;
	}

	private Coordinates findFreeStartingCoordinates() {
		Field[][] f = board.fields();
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[i].length; j++) {
//				if (f[i][j] == Field.)
			}
		}
	}

	public void leave(Player p) {
		player.remove(p);
		if (player.size() == 0) {
			// TODO: stop game?
		}
		fireGameEvent(new GameEvent(Type.REMOVE_PLAYER));
	}

	public BoardString boardString() {
		return boardString;
	}

	public Board board() {
		return board;
	}

	public void setBoard(BoardString bs) {
		boardString = bs;
		board = Board.createBoard(bs.board, bs.width);
	}

	public List<Player> getPlayer() {
		return player;
	}

	public void movePlayer(String name, Coordinates coordinates) {
		player.stream().filter(e -> e.name().equals(name)).findFirst()
				.orElseThrow(IllegalArgumentException::new)
				.moveTo(coordinates.x, coordinates.y);
	}

	public Player getPlayer(String name) {
		return player.stream().filter(e -> e.name().equals(name)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
