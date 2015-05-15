package at.ac.tuwien.foop.client.domain;

import org.junit.Assert;
import org.junit.Test;

import at.ac.tuwien.foop.domain.Board;
import at.ac.tuwien.foop.domain.Board.Field;

public class BoardTest {
	
	@Test(expected=NullPointerException.class)
	public void testGenerateFields_NullString_NullpointerException() {
		Board.createBoard(null, 123);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGenerateFields_0Width_IllegalArgumentException() {
		Board.createBoard("foo", 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGenerateFields_UnknownCharacters_IllegalArgumentException() {
		Board.createBoard("foo", 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGenerateFields_IllegalSize_IllegalArgumentException() {
		Board.createBoard("wwwww", 2);
	}
	
	public void testGenerateFields_onePointBoard_shouldSucceed() {
		Board b = Board.createBoard("w", 1);
		Field[][] f = b.fields();
		Assert.assertEquals(1, f.length);
		Assert.assertEquals(1, f[0].length);
		Assert.assertEquals(Field.wall, f[0][0]);
	}
	
//	public void testGenerateFields_smallBoard_shouldSucceed() {
//		Board b = Board.createBoard("wwwwwwC-mwwwwww", 1);
//		Field[][] f = b.fields();
//		Field[][] expected = new Field[] {new Field[]{Field.wall,Field.wall,Field.wall,Field.wall,Field.wall}};
//		Assert.assertEquals(1, f.length);
//		Assert.assertEquals(1, f[0].length);
//		Assert.assertEquals(Field.wall, f[0][0]);
//	}
}
