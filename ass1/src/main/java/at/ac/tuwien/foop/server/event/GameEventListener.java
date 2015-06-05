package at.ac.tuwien.foop.server.event;

//@FunctionalInterface
public interface GameEventListener {
	void onUpdate(GameEvent e);
	void onUpdate(NewPlayerEvent e);
	void onUpdate(GameOverEvent e);
	void onUpdate(RemovePlayerEvent e);
}
