package at.ac.tuwien.foop.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
	public static enum Type {
		ping, pong, join, joined, already_full
	}

	public final Type type;

	@JsonCreator
	public Message(@JsonProperty("type") Type type) {
		this.type = type;
	}
}
