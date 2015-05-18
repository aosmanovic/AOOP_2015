package at.ac.tuwien.foop.server.service;

import org.junit.Assert;
import org.junit.Test;

import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.server.domain.BoardString;
import at.ac.tuwien.foop.server.domain.Game;

public class GameLogicServiceTest {

	GameLogicService service = new GameLogicService();

	@Test
	public void testMovement() {
		String b = 
				"wwwwwwww"
		      + "w----Cww"
			  + "w-wmwwww"
			  + "w---wwww"
			  + "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		game.join("Al");

		Player actual = game.getPlayers().get(0);
		service.movement(game);
		Assert.assertEquals(new Coordinates(1, 5), actual.coordinates());
	}

	@Test
	public void testMovementWithDeadEnd() {
		String b = 
				  "wwwwwwww"
				+ "www-wCww"
				+ "w--mw-ww"
				+ "w-----ww"
				+ "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		game.join("Al");

		Player actual = game.getPlayers().get(0);
		service.movement(game);
		Assert.assertEquals(new Coordinates(0, 7), actual.coordinates());
	}
}
