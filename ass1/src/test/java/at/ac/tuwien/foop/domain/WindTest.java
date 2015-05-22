package at.ac.tuwien.foop.domain;

import org.junit.Assert;
import org.junit.Test;

public class WindTest {
	@Test(expected = IllegalArgumentException.class)
	public void testFromAngle_negativeStrength_expectIllegalArgumentException() {
		Wind.fromAngle(1, -1);
	}

	@Test
	public void testFromAngle_negativeAngle_ok() {
		Wind wind = Wind.fromAngle(-1, 1);
		Assert.assertEquals(-1, wind.angle, 0.01);
		Assert.assertEquals(1, wind.strength, 0.01);
	}

	@Test
	public void testFromAngle_moreThanMaxStrength_expectMaxStrength() {
		Wind wind = Wind.fromAngle(0, Wind.MAX_STRENGTH + 10);
		Assert.assertEquals(Wind.fromAngle(0, Wind.MAX_STRENGTH), wind);
	}

	@Test
	public void testFromAngle_angle0Strength1_expectX0YMINUS1() {
		Wind wind = Wind.fromAngle(0, 1); // NORTH
		Assert.assertEquals(0, wind.x, 0.01);
		Assert.assertEquals(-1, wind.y, 0.01);
	}

	@Test
	public void testFromAngle_anglePIStrength1_expectX0Y1() {
		Wind wind = Wind.fromAngle(Math.PI, 1); // SOUTH
		Assert.assertEquals(0, wind.x, 0.01);
		Assert.assertEquals(1, wind.y, 0.01);
	}

	@Test
	public void testFromAngle_angleHalfPIStrength1_expectX1Y0() {
		Wind wind = Wind.fromAngle(Math.PI / 2, 1); // WEST
		Assert.assertEquals(1, wind.x, 0.01);
		Assert.assertEquals(0, wind.y, 0.01);
	}

	@Test
	public void testFromAngle_angle3HalfPIStrength1_expectXMINUS1Y0() {
		Wind wind = Wind.fromAngle(Math.PI * 3 / 2, 1); // EAST
		Assert.assertEquals(-1, wind.x, 0.01);
		Assert.assertEquals(0, wind.y, 0.01);
	}

}
