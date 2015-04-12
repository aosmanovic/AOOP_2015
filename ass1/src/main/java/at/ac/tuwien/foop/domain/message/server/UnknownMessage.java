package at.ac.tuwien.foop.domain.message.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import at.ac.tuwien.foop.domain.message.Message;

public class UnknownMessage extends Message {
	public final String unknownType;

	@JsonCreator
	public UnknownMessage(@JsonProperty("unknownType") String unknownType) {
		super(Type.S_UNKNOWN);
		this.unknownType = unknownType;
	}
}
