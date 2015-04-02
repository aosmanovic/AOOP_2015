package at.ac.tuwien.foop.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
	public static enum Type {
		C_PING, S_PONG, S_NEWPLAYER, S_UPDATE, S_BOARD, C_JOIN, S_JOINED, S_ALREADY_FULL
	}

	public final Type type;

	@JsonCreator
	public Message(@JsonProperty("type") Type type) {
		this.type = type;
	}
}
