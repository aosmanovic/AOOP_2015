package at.ac.tuwien.foop.client.events;

public interface GameEventListener {
	void onUpdate(GameEvent e);

	void onUpdate(NewPlayerEvent e);
}
