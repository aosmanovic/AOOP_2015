package at.ac.tuwien.foop.message;

import java.util.UUID;

public class BoardMessage extends Message {
	public final String fields;
	public final UUID id;

	public BoardMessage(String fields, UUID id) {
		super(Type.S_BOARD);
		this.fields = fields;
		this.id = id;
	}
}
