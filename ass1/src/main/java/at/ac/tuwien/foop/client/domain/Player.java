package at.ac.tuwien.foop.client.domain;

public class Player {
	private final String name;
	private Coordinates coordinates;

	public Player(String name, Coordinates coordinates) {
		this.name = name;
		this.coordinates = coordinates;
	}

	public String name() {
		return name;
	}

	public Coordinates coordinates() {
		return coordinates;
	}
}
