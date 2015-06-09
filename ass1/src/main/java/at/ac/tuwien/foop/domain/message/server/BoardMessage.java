package at.ac.tuwien.foop.domain.message.server;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.domain.message.Message;

public class BoardMessage extends Message {
	public final String fields;
	public final int width;
	public final UUID id;
	public final List<Player> list;

	@JsonCreator
	public BoardMessage(@JsonProperty("id") UUID id, @JsonProperty("fields") String fields, @JsonProperty("width") int width, @JsonProperty("list") List<Player> list) {
		super(Type.S_BOARD);
		this.fields = fields;
		this.id = id;
		this.width = width;
		this.list = list;
	}
}
