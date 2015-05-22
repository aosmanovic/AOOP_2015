package at.ac.tuwien.foop.domain.message.client;

import at.ac.tuwien.foop.domain.WindGust;
import at.ac.tuwien.foop.domain.message.Message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WindMessage extends Message {
	public final WindGust wind;

	@JsonCreator
	public WindMessage(@JsonProperty("wind") WindGust wind) {
		super(Type.C_WIND);
		this.wind = wind;
	}
}
