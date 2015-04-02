package at.ac.tuwien.foop.client.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private List<GameEventListener> listeners = new ArrayList<>();

	private boolean running;
	// TODO: think about immutable list types here, as the client can request the list and modify its elements
	private List<Fields> fields;
	private List<Player> players;
	
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
	
	public void setFields(String fieldsString) {
		//TODO: move convert to a distinct file
	}
	
	public void updateBoard(String updateString) {
		//TODO: move convert to a distinct file
		
	}
	
	public List<Fields> getFields() {
		return fields;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
}

// messages: add player, start game, gameover, player left