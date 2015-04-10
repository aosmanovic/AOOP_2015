package at.ac.tuwien.foop.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinMessage {
	public final String name;

	@JsonCreator
	public JoinMessage(@JsonProperty("name") String name) {
		this.name = name;
	}
}
