package at.ac.tuwien.foop.domain;

import at.ac.tuwien.foop.client.domain.Coordinates;



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

	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	
}
