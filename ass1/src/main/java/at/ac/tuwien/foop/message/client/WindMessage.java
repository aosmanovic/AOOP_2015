package at.ac.tuwien.foop.message.client;

import at.ac.tuwien.foop.client.domain.Wind;
import at.ac.tuwien.foop.message.Message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WindMessage extends Message {
	public final Wind wind;

	@JsonCreator
	public WindMessage(@JsonProperty("wind") Wind wind) {
		super(Type.C_WIND);
		this.wind = wind;
	}
}
