package at.ac.tuwien.foop.domain;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Handling of directions (map a navigation-map to a display):<br/>
 * NORTH = (x=0, y<0), angle = 0 (or 2 PI ..)<br/>
 * SOUTH = (x=0, y>0), angle = PI<br/>
 * WEST = (x<0, y=0), angle = -PI/2 (or 3 PI/2 ..)<br/>
 * EAST = (x>0, y=0), angle = PI/2 (or -3 PI/2 ..)<br/>
 */
public class Wind {
	@JsonIgnore
	public static final int MAX_STRENGTH = 4;
	
	public final double angle;
	public final double strength;
	public double x;
	public double y;

	private Wind(double angle, double strength, double x, double y) {
		this.angle = angle;
		this.strength = strength;
		this.x = x;
		this.y = y;
	}
	
	public static Wind fromCoordinates(double x, double y) {
		double angle = Math.atan2(x, -y);
		double strength = Math.sqrt(x*x + y*y);
		return fromAngle(angle, strength);
	}
	
	@JsonCreator
	public static Wind fromAngle(@JsonProperty("angle") double angle, @JsonProperty("strength") double strength) {
		Validate.isTrue(strength >= 0);

		strength = Math.min(MAX_STRENGTH, strength);
		double x = Math.sin(angle) * strength;
		double y = Math.cos(angle) * -strength;
		return new Wind(angle, strength, x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wind other = (Wind) obj;
		if (Double.doubleToLongBits(angle) != Double
				.doubleToLongBits(other.angle))
			return false;
		if (Double.doubleToLongBits(strength) != Double
				.doubleToLongBits(other.strength))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Wind [angle=" + angle + ", strength=" + strength + ", x=" + x
				+ ", y=" + y + "]";
	}
}
