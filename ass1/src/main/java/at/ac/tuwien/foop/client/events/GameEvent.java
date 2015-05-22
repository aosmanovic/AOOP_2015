package at.ac.tuwien.foop.client.events;

public class GameEvent {
	public enum Type {
		NEW_PLAYER, BOARD, START, STOP, UPDATE
	}

	public final Type type;

	public GameEvent(Type type) {
		this.type = type;
	}
}
