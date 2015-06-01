package at.ac.tuwien.foop.client.events;

public class RemovePlayerEvent extends GameEvent {
	public final String name;

	public RemovePlayerEvent(String name) {
		super(Type.REMOVE_PLAYER);
		this.name = name;
	}
}
