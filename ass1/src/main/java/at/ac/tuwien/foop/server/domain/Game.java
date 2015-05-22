package at.ac.tuwien.foop.server.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.Board;
import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.domain.Wind;
import at.ac.tuwien.foop.domain.WindGust;
import at.ac.tuwien.foop.domain.WindGust.Direction;
import at.ac.tuwien.foop.server.event.GameEvent;
import at.ac.tuwien.foop.server.event.GameEvent.Type;
import at.ac.tuwien.foop.server.event.GameEventListener;
import at.ac.tuwien.foop.server.event.NewPlayerEvent;
import at.ac.tuwien.foop.server.service.GameLogicService;

public class Game {
	public enum GameState {
		ready, running, paused, over
	}

	private static Logger log = LoggerFactory.getLogger(Game.class);

	private List<GameEventListener> listeners = new ArrayList<>();

	private GameState state = GameState.ready;

	private List<Player> players = new ArrayList<>();
	private BoardString boardString;
	private Board board;
	private Wind wind = Wind.fromCoordinates(0, 0);

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
		if (state != GameState.ready && state != GameState.paused) {
			throw new IllegalStateException(
					"game must be 'ready' or 'paused' so it can be started");
		}

		state = GameState.running;
		fireGameEvent(new GameEvent(Type.START));
	}

	/**
	 * Stops the game so it's over.
	 */
	public void stop() {
		if (state != GameState.running && state != GameState.paused) {
			throw new IllegalStateException(
					"game must be 'running' or 'paused' so it can be over");
		}

		state = GameState.over;
		fireGameEvent(new GameEvent(Type.OVER));
	}

	/**
	 * Pause the game.
	 */
	public void pause() {
		if (state != GameState.running) {
			throw new IllegalStateException(
					"game must be 'running' so it can be paused");
		}

		state = GameState.paused;
		fireGameEvent(new GameEvent(Type.PAUSE));
	}

	/**
	 * Calculates the game progress.
	 * 
	 * <p>
	 * This method will be called periodically to upgrade the players on the
	 * board as well as the game state if a player reached the goal.
	 */
	public void next() {
		if (state != GameState.running) {
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
			listeners.forEach(e -> e.onUpdate(new NewPlayerEvent(p)));
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

	public synchronized void sendGust(WindGust gust) {
		Validate.notNull(gust);

		Coordinates c;
		if (gust.direction == Direction.NORTH) {
			c = new Coordinates(0, -1 * gust.strength);
		} else if (gust.direction == Direction.SOUTH) {
			c = new Coordinates(0, 1 * gust.strength);
		} else if (gust.direction == Direction.WEST) {
			c = new Coordinates(-1 * gust.strength, 0);
		} else {
			c = new Coordinates(1 * gust.strength, 0);
		}
		// System.out.println(String.format(" %d + %d , %d + %d", wind.x , c.x,
		// wind.y , c.y));
		wind = Wind.fromCoordinates(wind.x + c.x, wind.y + c.y);
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
		players.replaceAll(p -> p.equals(player) ? p.moveTo(coordinates.x,
				coordinates.y, p.coordinates(), p.getState()) : p);
	}

	public Player getPlayer(String name) {
		return players.stream().filter(e -> e.name().equals(name)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

	public GameState state() {
		return state;
	}
	
	public void setState(GameState state) {
		this.state = state;
	}

	public Wind wind() {
		return wind;
	}
}
