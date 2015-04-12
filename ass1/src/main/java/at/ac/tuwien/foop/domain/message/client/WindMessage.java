package at.ac.tuwien.foop.domain.message.client;

import at.ac.tuwien.foop.domain.Wind;
import at.ac.tuwien.foop.domain.message.Message;

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
