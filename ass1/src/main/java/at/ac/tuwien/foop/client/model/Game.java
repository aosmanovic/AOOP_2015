package at.ac.tuwien.foop.client.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private List<GameEventListener> listeners = new ArrayList<>();

	private boolean running;
	// TODO: think about immutable list types here, as the client can request the list and modify its elements
	private List<Player> players;
	private Board board;
	
	public void addGameEventListener(GameEventListener listener) {
		listeners.add(listener);
	}
	
	public void removeGameEventListener(GameEventListener listener) {
		listeners.remove(listener);
	}
	
	public void start() {
		running = true;
	}
	
	public void stop() {
		running = false;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	public void setBoard(Board board) {
		// TODO: should not be possible while the game is still running
		this.board = board;
	}
	
	public Board getBoard() {
		return board;
	}
}

// messages: add player, start game, gameover, player left