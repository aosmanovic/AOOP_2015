package at.ac.tuwien.foop.client.model;

import org.junit.Assert;
import org.junit.Test;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.events.GameEvent;
import at.ac.tuwien.foop.domain.Board;

public class GameTest {
	
	private final Board board = Board.createBoard("www", 1);
	@Test
	public void testSetBoard_whileRunning_shouldThrowRuntimeException() {
		Game g = new Game();
		g.start();
		try {
			g.setBoard(board);
			Assert.fail("expecting an exception!");
		} catch (RuntimeException e) {
			Assert.assertEquals("can't set board on a running game!",
					e.getMessage());
		}
	}

	@Test
	public void testSetBoard_whithListener_shouldFireEvent() {
		Game g = new Game();
		g.addGameEventListener(e -> Assert.assertEquals(GameEvent.Type.BOARD,
				e.type));
		g.setBoard(board);
	}
}
