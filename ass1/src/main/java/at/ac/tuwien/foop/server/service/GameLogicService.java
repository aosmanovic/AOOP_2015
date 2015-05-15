package at.ac.tuwien.foop.server.service;

import java.util.Scanner;

import at.ac.tuwien.foop.client.domain.Coordinates;
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


		for(int i =0; i<game.getPlayer().size();i++) {
			Coordinates c = game.getPlayer().get(i).getCoordinates();

			//while (c.getX()!=9 && c.getY()!=18) {
				int x = game.getPlayer().get(i).getCoordinates().getX();
				int y = game.getPlayer().get(i).getCoordinates().getY();

				Coordinates c1 = new Coordinates(x+1,y);
				Coordinates c2 = new Coordinates(x+1,y-1);
				Coordinates c3 = new Coordinates(x-1,y+1);
				Coordinates c4 = new Coordinates(x-1,y-1);
				Coordinates c5 = new Coordinates(x,y+1);
				Coordinates c6 = new Coordinates(x+1,y+1);
				Coordinates c7 = new Coordinates(x-1,y);
				Coordinates c8 = new Coordinates(x,y-1);

				
				
			

		}

	}

}
