package at.ac.tuwien.foop.domain;

import java.util.ArrayList;



public class Player {
	private final String name;
	private Coordinates coordinates;
	private ArrayList<Coordinates> visitedCoordinates = new ArrayList<>();

	public Player(String name, Coordinates coordinates) {
		this.name = name;
		this.coordinates = coordinates;
		this.visitedCoordinates.add(coordinates);
	}

	public String name() {
		return name;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void moveTo(int x, int y) {
		coordinates = new Coordinates(x, y);
	}
	
	public void setCoordinates(Coordinates c) {
		this.coordinates=c;
		this.visitedCoordinates.add(c);
	}

	public ArrayList<Coordinates> getVisitedCoordinates() {
		return visitedCoordinates;
	}

	/*public void setVisitedCoordinates(Coordinates visitedCoordinates) {
	
	}*/
	
	
	
}
