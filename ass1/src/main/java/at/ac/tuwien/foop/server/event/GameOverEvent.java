package at.ac.tuwien.foop.server.event;

import at.ac.tuwien.foop.domain.Player;

public class GameOverEvent {

	public final Player player;

	public GameOverEvent(Player player) {
		this.player = player;
	}
	
}
