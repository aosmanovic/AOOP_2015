package at.ac.tuwien.foop.client.model;

public class GameEvent {
	public enum Type {
		close
	}

	public final Type type;

	public GameEvent(Type type) {
		this.type = type;
	}
}
