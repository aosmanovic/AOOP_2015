package at.ac.tuwien.foop.domain.message.server;

import java.util.List;

import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.domain.message.Message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinedMessage extends Message {
	public final List<Player> players;

	@JsonCreator
	public JoinedMessage(@JsonProperty("players") List<Player> players) {
		super(Type.S_JOINED);
		this.players = players;
	}
}
