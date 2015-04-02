package at.ac.tuwien.foop.message;

import java.util.UUID;

public class BoardMessage {
	public final String fields;
	public final UUID id;

	public BoardMessage(String fields, UUID id) {
		this.fields = fields;
		this.id = id;
	}
}
