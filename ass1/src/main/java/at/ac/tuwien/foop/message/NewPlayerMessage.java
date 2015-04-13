package at.ac.tuwien.foop.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewPlayerMessage {
	public final String name;

	@JsonCreator
	public NewPlayerMessage(@JsonProperty("name") String name) {
		this.name = name;
	}
}
