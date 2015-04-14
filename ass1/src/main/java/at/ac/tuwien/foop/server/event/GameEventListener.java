package at.ac.tuwien.foop.server.event;

@FunctionalInterface
public interface GameEventListener {
	public void onUpdate(GameEvent e);
}
