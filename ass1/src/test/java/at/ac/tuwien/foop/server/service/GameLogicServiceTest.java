package at.ac.tuwien.foop.server.service;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.server.domain.BoardString;
import at.ac.tuwien.foop.server.domain.Game;

public class GameLogicServiceTest {

	GameLogicService service = new GameLogicService();
	private static Logger log = LoggerFactory.getLogger(GameLogicServiceTest.class);

	@Test
	public void testMovement() {
		log.info("------------Test 1 ----------");
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
		Assert.assertEquals(new Coordinates(3, 1), game.getPlayer(actual.name()).coordinates());
	}
	
	@Test
	public void testMovement2() {
		log.info("------------Test 2 ----------");
		
		String b = 
				"wwwwwwww"
		      + "w-----ww"
			  + "w--m-Cww"
			  + "w---wwww"
			  + "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		game.join("Al");

		Player actual = game.getPlayers().get(0);
		service.movement(game);
		Assert.assertEquals(new Coordinates(4, 2), game.getPlayer(actual.name()).coordinates());
	}
	
	@Test
	public void testMovement3() {
		log.info("------------Test 3 ----------");
		
		String b = 
				"wwwmwwww"
		      + "w-----ww"
			  + "w--w-Cww"
			  + "w---wwww"
			  + "wwwwwwww";
		BoardString bs = new BoardString(b, 8);
		Game game = new Game(bs);
		game.join("Al");

		Player actual = game.getPlayers().get(0);
		service.movement(game);
		Assert.assertEquals(new Coordinates(3, 1), game.getPlayer(actual.name()).coordinates());
	}

	@Test
	public void testMovementWithDeadEnd() {
		log.info("------------Test 4 ----------");
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
