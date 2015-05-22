package at.ac.tuwien.foop.server.domain;

import org.apache.commons.lang3.Validate;

/**
 * Handling of directions (map a navigation-map to a display):<br/>
 * NORTH = (x=0, y<0), angle = 0 (or 2 PI ..)<br/>
 * SOUTH = (x=0, y>0), angle = PI<br/>
 * WEST = (x<0, y=0), angle = -PI/2 (or 3 PI/2 ..)<br/>
 * EAST = (x>0, y=0), angle = PI/2 (or -3 PI/2 ..)<br/>
 */
public class Wind {
	public static final int MAX_STRENGTH = 4;
	
	public final double angle;
	public final double strength;
	public final double x;
	public final double y;

	private Wind(double angle, double strength, double x, double y) {
		this.angle = angle;
		this.strength = strength;
		this.x = x;
		this.y = y;
	}
	
//	public Wind(double angle, double strength) {
//		this.angle = angle;
//		this.strength = strength;
//		x = Math.sin(angle);
//		y = Math.cos(angle);
//	}
//	
//	public Wind(double x, double y) {
//		this.x = x;
//		this.y = y;
//		angle = Math.atan2(y, x);
//		strength = Math.sqrt(x*x + y*y);
//	}

	public static Wind fromCoordinates(double x, double y) {
		double angle = Math.atan2(x, -y);
		double strength = Math.sqrt(x*x + y*y);
//		return new Wind(angle, strength, x, y);
		return fromAngle(angle, strength);
	}
	public static Wind fromAngle(double angle, double strength) {
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
