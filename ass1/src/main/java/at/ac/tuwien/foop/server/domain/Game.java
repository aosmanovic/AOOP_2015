package at.ac.tuwien.foop.server.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.Board;
import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.domain.Player.State;
import at.ac.tuwien.foop.domain.Wind;
import at.ac.tuwien.foop.domain.WindGust;
import at.ac.tuwien.foop.domain.WindGust.Direction;
import at.ac.tuwien.foop.server.event.GameEvent;
import at.ac.tuwien.foop.server.event.GameEvent.Type;
import at.ac.tuwien.foop.server.event.GameEventListener;
import at.ac.tuwien.foop.server.event.GameOverEvent;
import at.ac.tuwien.foop.server.service.GameLogicService;

public class Game {
	public enum GameState {
		ready, running, paused, over
	}

	private static Logger log = LoggerFactory.getLogger(Game.class);

	private CopyOnWriteArrayList<GameEventListener> listeners = new CopyOnWriteArrayList<>();

	private GameState state = GameState.ready;

	private List<Player> players = new ArrayList<>();
	private BoardString boardString;
	private Board board;
	private Wind wind;
	private int levelCounter = 0;

	private GameLogicService service = new GameLogicService();

	public Game(BoardString bs) {
		reset(bs);
	}

	public void addGameEventListener(GameEventListener listener) {
		listeners.add(listener);
	}

	public void removeGameEventListener(GameEventListener listener) {
		listeners.remove(listener);
	}

	private void fireGameEvent(GameEvent event) {
		listeners.forEach(e -> e.onUpdate(event));
	}

	public void sendUpdate() {
		fireGameEvent(new GameEvent(Type.UPDATE));
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
	public void stop(Player winner) {
		if (state != GameState.running && state != GameState.paused) {
			throw new IllegalStateException(
					"game must be 'running' or 'paused' so it can be over");
		}

		state = GameState.over;
		listeners.forEach(l -> l.onUpdate(new GameOverEvent(winner)));
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

		// call movement method here
		service.movement(this, wind);
		wind = Wind.fromAngle(wind.angle,
				Math.max(wind.strength - (Wind.MAX_STRENGTH / 2), 0));

		sendUpdate();
	}

	/**
	 * A player joins the game if the maximum amount of players is not reached
	 * yet and no other player with the same name joined before.
	 * 
	 * @return a player if joined, null otherwise
	 */
	public boolean join(Player p) {
		Coordinates c = findFreeStartingCoordinates();
		if (c == null) {
			return false;
		}
		synchronized (players) {
			players.remove(p);
			players.add(new Player(p.name(), c, null, State.notCrazy, true));
		}
		return true;
	}

	private Coordinates findFreeStartingCoordinates() {
		synchronized (players) {
			return board
					.startCoordinates()
					.stream()
					.filter(c -> !players.stream().anyMatch(
							p -> p.coordinates().equals(c))).findFirst()
					.orElse(null);
		}
	}

	public void leave(Player p) {
		synchronized (p) {

			players.remove(p);
			players.add(new Player(p.name(), p.coordinates(), null,
					State.notCrazy, false));
		}
		// TODO: filter stream for a check...
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
		synchronized (players) {
			return players;
		}
	}

	public void movePlayer(String name, Coordinates coordinates) {
		synchronized (players) {

			Player player = getPlayer(name);

			log.debug("Move player '{}' to {}", name, coordinates);
			int i = 0;
			for (Player p : players) {
				if (p.name().equals(name)) {
					player = p.moveTo(coordinates.x, coordinates.y,
							p.coordinates(), p.state());
					players.set(i, player);
					break;
				}
				i++;
			}

			// 2 mouses crash
			Player other = service.checkCrash(player, this);
			if (other != null) {
				player.state(State.crash);
				other.state(State.crash);
			}
		}
	}

	public Player getPlayer(String name) {
		synchronized (players) {

			return players.stream().filter(e -> e.name().equals(name))
					.findFirst().orElseThrow(IllegalArgumentException::new);
		}
	}

	public GameState state() {
		return state;
	}

	public Wind wind() {
		return wind;
	}

	public int level() {
		return levelCounter;
	}

	public void level(int levelCounter) {
		this.levelCounter = levelCounter;
	}

	/**
	 * Creates a new player as spectator in the game.
	 * 
	 * @param name
	 */
	public Player newSpectator(String name) {
		synchronized (players) {

			if (players.stream().anyMatch(p -> p.name().equals(name))) {
				return null;
			}
			Player p = new Player(name, new Coordinates(0, 0), null,
					State.notCrazy, false);
			players.add(p);

			return p;
		}
	}

	public void removePlayer(Player player) {
		synchronized (player) {
			players.remove(player);
		}
	}

	public void reset(BoardString bs) {
		wind = Wind.fromCoordinates(0, 0);
		state = GameState.ready;
		setBoard(bs);

		synchronized (players) {
			players = players
					.stream()
					.map(p -> new Player(p.name(), new Coordinates(0, 0), null,
							State.notCrazy, false))
					.collect(Collectors.toList());
		}
		listeners.forEach(e -> e.onUpdate(new GameEvent(GameEvent.Type.NEW_LEVEL)));
	}
}
