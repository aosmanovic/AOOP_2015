package at.ac.tuwien.foop.server.service;

import java.util.Scanner;

import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.server.domain.BoardString;
import at.ac.tuwien.foop.server.domain.Game;

public class GameLogicService {

	private static String BOARD_PATH = "Map.txt";
	
	public BoardString getBoard(Game game) {
		if (game.boardString() != null) {
			return game.boardString();
		}
		
		// TODO: load random board
		BoardString bs = loadBoard(BOARD_PATH);
		game.setBoard(bs);
		return bs;
	}
	
	private BoardString loadBoard(String path) {
		try (Scanner s = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream(path))) {
			int width = 0;
			StringBuffer buffer = new StringBuffer();
			while (s.hasNextLine()) {
				String line = s.nextLine();
				if (width == 0)
					width = line.length();
				if (line.length() != width)
					throw new RuntimeException(String.format("Board file '%s' has a bad format!", path));
				buffer.append(line);
			}
			return new BoardString(buffer.toString(), width);
		}
	}
	
	public void movement(Game game) {
		
		//game.getPlayer()
		
	}

}
