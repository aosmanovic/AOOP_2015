package at.ac.tuwien.foop.client.domain;

import org.junit.Test;

public class BoardTest {
	
	@Test
	public void testGenerateFields_NullString_NullpointerException() {
		Board.createBoard(null, 123);
	}

	@Test
	public void testGenerateFields_0Width_IllegalArgumentException() {
		Board.createBoard("foo", 0);
	}
}
