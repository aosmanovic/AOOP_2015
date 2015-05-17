package at.ac.tuwien.foop.server.domain;

import org.junit.Assert;
import org.junit.Test;

import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;

public class GameTest {

	@Test
	public void testjoin_noStartingCoordinate_expectNull() {
		String b = 
				  "wwwwwwww"
				+ "w----Cww"
				+ "w-w-wwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		Assert.assertNull(game.join("player1"));
	}

	@Test
	public void testjoin_1StartingCoordinate1Player_expectNull() {
		String b = 
				  "wwwwwwww"
				+ "wm---Cww"
				+ "w-w-wwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		game.join("player1");
		Assert.assertNull(game.join("player2"));
	}

	@Test
	public void testjoin_playerWithSameName_expectNull() {
		String b = 
				  "wwwwwwww"
				+ "wm---Cww"
				+ "w-wmwwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		game.join("player1");
		Assert.assertNull(game.join("player1"));
	}
	
	@Test
	public void testjoin_freeStartingField_expectPlayer() {
		String b = 
				  "wwwwwwww"
				+ "w----Cww"
				+ "w-wmwwww"
				+ "w---wwww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		Player actual = game.join("player1");
		Assert.assertEquals("player1", actual.name());
		Assert.assertEquals(new Coordinates(3, 2), actual.getCoordinates());
	}
}
