package at.ac.tuwien.foop.domain.message.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import at.ac.tuwien.foop.domain.Update;
import at.ac.tuwien.foop.domain.message.Message;

public class UpdateMessage extends Message {
	public final Update update;

	@JsonCreator
	public UpdateMessage(@JsonProperty("update") Update update) {
		super(Type.S_UPDATE);
		this.update = update;
	}
}
