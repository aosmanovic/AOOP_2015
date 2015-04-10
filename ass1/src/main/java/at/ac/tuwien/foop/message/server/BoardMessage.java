package at.ac.tuwien.foop.message.server;

import java.util.UUID;

import at.ac.tuwien.foop.message.Message;

public class BoardMessage extends Message {
	public final String fields;
	public final UUID id;

	public BoardMessage(String fields, UUID id) {
		super(Type.S_BOARD);
		this.fields = fields;
		this.id = id;
	}
}
