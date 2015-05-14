package at.ac.tuwien.foop.domain.message.server;

import java.util.UUID;

import at.ac.tuwien.foop.domain.message.Message;

public class BoardMessage extends Message {
	public final String fields;
	public final int width;
	public final UUID id;

	public BoardMessage(UUID id, String fields, int width) {
		super(Type.S_BOARD);
		this.fields = fields;
		this.id = id;
		this.width = width;
	}
}
