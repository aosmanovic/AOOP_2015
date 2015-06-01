package at.ac.tuwien.foop.domain.message.server;

import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.domain.message.Message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RemovePlayerMessage extends Message {
	public final Player player;

	@JsonCreator
	public RemovePlayerMessage(@JsonProperty("name") Player player) {
		super(Type.S_REMOVEPLAYER);
		this.player = player;
	}
}
