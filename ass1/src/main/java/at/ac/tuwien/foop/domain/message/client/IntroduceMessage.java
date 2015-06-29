package at.ac.tuwien.foop.domain.message.client;

import at.ac.tuwien.foop.domain.message.Message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IntroduceMessage extends Message {
	public final String name;

	@JsonCreator
	public IntroduceMessage(@JsonProperty("name") String name) {
		super(Type.C_INTRODUCE);
		this.name = name;
	}
}
