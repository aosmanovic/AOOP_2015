package at.ac.tuwien.foop.domain.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
	public static enum Type {
		C_PING, C_INTRODUCE, C_JOIN, C_WIND, C_START, C_LEAVE,
		S_PONG, S_NEWPLAYER, S_UPDATE, S_BOARD, S_JOINED, S_ALREADY_FULL, S_UNKNOWN, S_START, S_OVER, S_REMOVEPLAYER
	}

	public final Type type;

	@JsonCreator
	public Message(@JsonProperty("type") Type type) {
		this.type = type;
	}
}
