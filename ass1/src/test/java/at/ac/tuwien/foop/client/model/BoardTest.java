package at.ac.tuwien.foop.client.model;

import org.junit.Test;

public class BoardTest {
	
	@Test
	public void testGenerateFields_NullString_NullpointerException() {
		new Board().generateFields(null, 123);
	}

	@Test
	public void testGenerateFields_0Width_IllegalArgumentException() {
		new Board().generateFields("foo", 0);
	}
}
