package at.ac.tuwien.foop.server.event;

public class GameEvent {
	public enum Type {
		NEW_PLAYER, START, UPDATE, REMOVE_PLAYER, PAUSE, OVER, NEW_LEVEL
	}

	public final Type type;

	public GameEvent(Type type) {
		this.type = type;
	}
}
