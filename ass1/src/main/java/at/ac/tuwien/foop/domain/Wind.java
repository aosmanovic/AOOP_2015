package at.ac.tuwien.foop.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Wind {
	public enum Direction {
		NORTH, SOUTH, WEST, EAST
	}

	public final Direction direction;
	public final int strength;

	@JsonCreator
	public Wind(@JsonProperty("direction") Direction direction,
			@JsonProperty("strength") int strength) {
		this.direction = direction;
		this.strength = strength;
	}
}
