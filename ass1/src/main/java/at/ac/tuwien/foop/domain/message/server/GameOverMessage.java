package at.ac.tuwien.foop.domain.message.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.domain.message.Message;

public class GameOverMessage extends Message {
	
	public final Player player;

	@JsonCreator
	public GameOverMessage(@JsonProperty("name") Player player) {
		super(Type.S_OVER);
		this.player = player;
	}

}
