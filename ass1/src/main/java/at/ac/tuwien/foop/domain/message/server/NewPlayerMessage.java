package at.ac.tuwien.foop.domain.message.server;

import at.ac.tuwien.foop.domain.message.Message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewPlayerMessage extends Message {
	public final String name;

	@JsonCreator
	public NewPlayerMessage(@JsonProperty("name") String name) {
		super(Type.S_NEWPLAYER);
		this.name = name;
	}
}
