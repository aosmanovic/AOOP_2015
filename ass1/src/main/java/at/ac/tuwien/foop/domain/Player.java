package at.ac.tuwien.foop.domain;



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
	
	public void setCoordinates(Coordinates c) {
		this.coordinates=c;
	}
	
}
