package at.ac.tuwien.foop.server.event;

//@FunctionalInterface
public interface GameEventListener {
	public void onUpdate(GameEvent e);
	public void onUpdate(NewPlayerEvent e);
	public void onUpdate(GameOverEvent e);
}
