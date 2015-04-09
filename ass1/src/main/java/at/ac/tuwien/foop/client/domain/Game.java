package at.ac.tuwien.foop.client.domain;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.foop.client.events.GameEvent;
import at.ac.tuwien.foop.client.events.GameEventListener;

public class Game {
	private List<GameEventListener> listeners = new ArrayList<>();

	private boolean running;
	private List<Player> players;
	private Board board;

	public void addGameEventListener(GameEventListener listener) {
		listeners.add(listener);
	}

	public void removeGameEventListener(GameEventListener listener) {
		listeners.remove(listener);
	}

	public void fireGameEvent(GameEvent event) {
		listeners.forEach(e -> e.update(event));
	}

	public void start() {
		running = true;
		fireGameEvent(new GameEvent(GameEvent.Type.START));
	}

	public void stop() {
		running = false;
		fireGameEvent(new GameEvent(GameEvent.Type.STOP));
	}

	// TODO: has actually nothing to do with the game but with the client, so move it there..!?
	public void disconnect() {
		running = false;
		fireGameEvent(new GameEvent(GameEvent.Type.DISCONNECT));
	}
	
	public boolean isRunning() {
		return running;
	}

	public void update(Update update) {
		// TODO: implement
	}

	public void addPlayer(Player player) {
		players.add(player);
		fireGameEvent(new GameEvent(GameEvent.Type.NEW_PLAYER));
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

	// TODO: not a ame method aswell but who cares atm :D
	public void ping() {
		fireGameEvent(new GameEvent(GameEvent.Type.PING));		
	}
}

// messages: add player, start game, gameover, player left