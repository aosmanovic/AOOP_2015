package at.ac.tuwien.foop.domain;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
	private final String name;
	private Coordinates coordinates;
	// TODO: what is this used for?
	@JsonIgnore
	private ArrayList<Coordinates> visitedCoordinates = new ArrayList<>();

	@JsonCreator
	public Player(@JsonProperty("name") String name, @JsonProperty("coordinates") Coordinates coordinates) {
		this.name = name;
		this.coordinates = coordinates;
		this.visitedCoordinates.add(coordinates);
	}

	@JsonProperty("name")
	public String name() {
		return name;
	}

	@JsonProperty("coordinates")
	public Coordinates coordinates() {
		return coordinates;
	}

	public Player moveTo(int x, int y) {
		return new Player(name, new Coordinates(x, y));
	}

	public ArrayList<Coordinates> getVisitedCoordinates() {
		return visitedCoordinates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", coordinates=" + coordinates + "]";
	}
}
