package at.ac.tuwien.foop.client.events;

public class GameEvent {
	public enum Type {
		DISCONNECT, NEW_PLAYER, BOARD, START, STOP, PING
	}

	public final Type type;

	public GameEvent(Type type) {
		this.type = type;
	}
}
