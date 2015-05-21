package at.ac.tuwien.foop.server.domain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.Board;
import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.server.event.GameEvent;
import at.ac.tuwien.foop.server.event.GameEvent.Type;
import at.ac.tuwien.foop.server.event.GameEventListener;
import at.ac.tuwien.foop.server.service.GameLogicService;

public class Game {
	private static Logger log = LoggerFactory.getLogger(Game.class);

	private List<GameEventListener> listeners = new ArrayList<>();

	private boolean started = false;
	private List<Player> players = new ArrayList<>();
	private BoardString boardString;
	private Board board;
	private GameLogicService service = new GameLogicService();

	public Game(BoardString bs) {
		setBoard(bs);
	}

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
	 * @return a player if joined, null otherwise
	 */
	public synchronized Player join(String name) {
		// TODO: maybe handle full and name already used differently!
		Coordinates c;
		if (!players.stream().anyMatch(p -> p.name().equals(name))
				&& (c = findFreeStartingCoordinates()) != null) {
			Player p = new Player(name, c);
			players.add(p);
			fireGameEvent(new GameEvent(Type.NEW_PLAYER));
			return p;
		}
		return null;
	}

	private Coordinates findFreeStartingCoordinates() {
		return board
				.startCoordinates()
				.stream()
				.filter(c -> !players.stream().anyMatch(
						p -> p.coordinates().equals(c))).findFirst()
				.orElse(null);
	}

	public void leave(Player p) {
		players.remove(p);
		if (players.size() == 0) {
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

	private void setBoard(BoardString bs) {
		boardString = bs;
		board = Board.createBoard(bs.board, bs.width);
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void movePlayer(String name, Coordinates coordinates) {
		Player player = getPlayer(name);
		players.replaceAll(p -> p.equals(player) ? p.moveTo(coordinates.x, coordinates.y, p.coordinates()) : p);
		
	}

	public Player getPlayer(String name) {
		return players.stream().filter(e -> e.name().equals(name)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
