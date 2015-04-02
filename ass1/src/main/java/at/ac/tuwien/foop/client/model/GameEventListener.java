package at.ac.tuwien.foop.client.model;

@FunctionalInterface
public interface GameEventListener {
	public void update(GameEvent e);
}
