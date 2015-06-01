package at.ac.tuwien.foop.client.domain;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.foop.client.events.GameEvent;
import at.ac.tuwien.foop.client.events.GameEventListener;
import at.ac.tuwien.foop.client.events.NewPlayerEvent;
import at.ac.tuwien.foop.client.events.RemovePlayerEvent;
import at.ac.tuwien.foop.domain.Board;
import at.ac.tuwien.foop.domain.Player;

public class Game {
	private List<GameEventListener> listeners = new ArrayList<>();

	private boolean running = false;
	private boolean joined = false;
	private List<Player> players = new ArrayList<>();
	private Board board;
	private String winner = "";

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

	public boolean running() {
		return running;
	}

	public void update(List<Player> players) {
		this.players = players;
		fireGameEvent(new GameEvent(GameEvent.Type.UPDATE));
	}

	public void addPlayer(Player player) {
		players.add(player);
		listeners.forEach(e -> e.onUpdate(new NewPlayerEvent(player.name())));
	}

	public void removePlayer(Player player) {
		players.remove(player);
		listeners.forEach(e -> e.onUpdate(new RemovePlayerEvent(player.name())));
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	public void setBoard(Board board) {
		if (running) {
			throw new RuntimeException("can't set board on a running game!");
		}
		this.board = board;
		fireGameEvent(new GameEvent(GameEvent.Type.BOARD));
	}

	public Board getBoard() {
		return board;
	}

	public boolean joined() {
		return joined;
	}

	public String winner() {
		return winner;
	}
}
