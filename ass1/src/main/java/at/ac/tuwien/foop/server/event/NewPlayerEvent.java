package at.ac.tuwien.foop.server.event;

import at.ac.tuwien.foop.domain.Player;

public class NewPlayerEvent {
	public final Player player;

	public NewPlayerEvent(Player player) {
		this.player = player;
	}
}
