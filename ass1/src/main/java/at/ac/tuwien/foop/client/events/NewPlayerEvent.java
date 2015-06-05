package at.ac.tuwien.foop.client.events;

public class NewPlayerEvent extends GameEvent {
	public final String name;

	public NewPlayerEvent(String name) {
		super(Type.NEW_PLAYER);
		this.name = name;
	}
}
