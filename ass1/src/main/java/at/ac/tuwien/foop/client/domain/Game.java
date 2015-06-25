package at.ac.tuwien.foop.client.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import at.ac.tuwien.foop.client.events.GameEvent;
import at.ac.tuwien.foop.client.events.GameEventListener;
import at.ac.tuwien.foop.client.events.NewPlayerEvent;
import at.ac.tuwien.foop.domain.Board;
import at.ac.tuwien.foop.domain.Wind;

public class Game {
	private CopyOnWriteArrayList<GameEventListener> listeners = new CopyOnWriteArrayList<>();

	private boolean running = false;
	private boolean joined = false;
	private List<ClientPlayer> players = new ArrayList<>();
	private Board board;
	private Wind wind = null;
	private String winner = "";

	public Game() {
		wind = Wind.fromAngle(0, 0);
	}

	public void board(Board board) {
		this.board = board;
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

	public void start() {
		running = true;
		fireGameEvent(new GameEvent(GameEvent.Type.START));
	}

	public void stop() {
		running = false;
		fireGameEvent(new GameEvent(GameEvent.Type.STOP));
	}

	public void over(String name) {
		running = false;
		winner = name;
		fireGameEvent(new GameEvent(GameEvent.Type.OVER));
	}

	public void join() {
		joined = true;
		fireGameEvent(new GameEvent(GameEvent.Type.JOIN));
	}

	public void leave() {
		joined = false;
	}
	
	public boolean running() {
		return running;
	}

	public void update(List<ClientPlayer> players, Wind wind) {
		this.players = players;
		this.wind = wind;
		fireGameEvent(new GameEvent(GameEvent.Type.UPDATE));
	}

	public void addPlayer(ClientPlayer player) {
		if (players.stream().anyMatch(p -> p.equals(player)))
			return;
		players.add(player);
		listeners.forEach(e -> e.onUpdate(new NewPlayerEvent(player.name())));
	}

	public List<ClientPlayer> getPlayers() {
		return players;
	}

	public void setBoard(Board board) {
		this.board = board;
		fireGameEvent(new GameEvent(GameEvent.Type.BOARD));
	}

	public Board getBoard() {
		return board;
	}

	public Wind getWind() {
		return wind;
	}

	public boolean joined() {
		return joined;
	}

	public String winner() {
		return winner;
	}
	
	public void resetGame(Board board) {
		running = false;
		joined = false;
		wind = Wind.fromAngle(0, 0);
		winner = "";
		setBoard(board);
	}

}
