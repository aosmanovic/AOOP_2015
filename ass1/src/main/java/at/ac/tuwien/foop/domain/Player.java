package at.ac.tuwien.foop.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
	private static final int MAX_CRASH_TIME = 5;

	private final String name;
	private Coordinates coordinates;
	private Coordinates lastCoordinates;
	private State state;
	private int crashTime = 0;
	private boolean active;

	public static enum State {
		crazy, notSoCrazy, notCrazy, crash
	};

	@JsonCreator
	public Player(@JsonProperty("name") String name,
			@JsonProperty("coordinates") Coordinates coordinates,
			@JsonProperty("lastCoordinates") Coordinates lastCoordinates,
			@JsonProperty("state") State crazyState, @JsonProperty("active") boolean active) {
		this.name = name;
		this.coordinates = coordinates;
		this.lastCoordinates = lastCoordinates;
		this.state = crazyState;
		this.active = active;
	}

	public Player(String name, Coordinates coordinates) {
		this(name, coordinates, null, State.notCrazy, true);
	}

	public Player(String name, Coordinates coordinates, boolean active) {
		this(name, coordinates, null, State.notCrazy, true);
		
	}
	
	@JsonProperty("name")
	public String name() {
		return name;
	}

	@JsonProperty("coordinates")
	public Coordinates coordinates() {
		return coordinates;
	}

	public Player moveTo(int x, int y, Coordinates lastCoordinates, State state) {
		return new Player(name, new Coordinates(x, y), lastCoordinates, state, true);
	}

	public Player moveTo(int x, int y) {
		return new Player(name, new Coordinates(x, y));
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

	public Coordinates lastCoordinates() {
		return lastCoordinates;
	}

	public State state() {
		return state;
	}

	public void state(State state) {
		this.state = state;
	}

	public boolean crash() {
		lastCoordinates = coordinates;
		return (++crashTime % MAX_CRASH_TIME) == 0;

	}

	@JsonProperty("active")
	public boolean active() {
		return active;
	}

	public void active(boolean state) {
		active = state;
	}
}
