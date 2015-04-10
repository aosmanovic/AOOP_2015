package at.ac.tuwien.foop.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinMessage extends Message {
	public final String name;

	@JsonCreator
	public JoinMessage(@JsonProperty("name") String name) {
		super(Type.C_JOIN);
		this.name = name;
	}
}
