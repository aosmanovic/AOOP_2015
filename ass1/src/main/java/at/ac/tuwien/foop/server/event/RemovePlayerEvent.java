package at.ac.tuwien.foop.server.event;

import at.ac.tuwien.foop.domain.Player;

public class RemovePlayerEvent {
	public final Player player;

	public RemovePlayerEvent(Player player) {
		this.player = player;
	}
}
