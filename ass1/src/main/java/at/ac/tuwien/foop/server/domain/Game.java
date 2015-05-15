package at.ac.tuwien.foop.server.domain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.Board;
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
<<<<<<< HEAD
	private BoardString boardString;
	private Board board;
=======
	private BoardString board;
	private GameLogicService service = new GameLogicService();
>>>>>>> 66fdd7570821c40a1e142d132ce29d46032ad80f

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
		
		//call movementmethod here
		service.movement();
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
}
