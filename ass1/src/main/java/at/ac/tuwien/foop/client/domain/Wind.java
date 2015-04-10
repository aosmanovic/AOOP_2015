package at.ac.tuwien.foop.client.domain;

public class Wind {
	public enum Direction {
		NORTH, SOUTH, WEST, EAST
	}
	
	public final Direction direction;
	public final int strength;

	public Wind(Direction direction, int strength) {
		this.direction = direction;
		this.strength = strength;
	}
}
