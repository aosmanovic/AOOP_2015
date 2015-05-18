package at.ac.tuwien.foop.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates {
	public final int x;
	public final int y;

	@JsonCreator
	public Coordinates(@JsonProperty("x") int x, @JsonProperty("y") int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coordinates [x=" + x + ", y=" + y + "]";
	}
}
