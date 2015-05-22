package at.ac.tuwien.foop.domain.message.server;

import java.util.List;

import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.domain.Wind;
import at.ac.tuwien.foop.domain.message.Message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateMessage extends Message {
	public final List<Player> players;
	public final Wind wind;

	@JsonCreator
	public UpdateMessage(@JsonProperty("players") List<Player> players, @JsonProperty("wind") Wind wind) {
		super(Type.S_UPDATE);
		this.players = players;
		this.wind = wind;
	}
}
