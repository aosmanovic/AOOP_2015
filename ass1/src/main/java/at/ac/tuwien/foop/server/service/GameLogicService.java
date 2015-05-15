package at.ac.tuwien.foop.server.service;

import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;




import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.domain.Board.Field;
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


			int x = game.getPlayer().get(i).getCoordinates().getX();
			int y = game.getPlayer().get(i).getCoordinates().getY();

			ArrayList<Coordinates> ls= new ArrayList<>();
			ls.add(new Coordinates(x+1,y));
			ls.add(new Coordinates(x+1,y-1));
			ls.add(new Coordinates(x-1,y+1));
			ls.add(new Coordinates(x-1,y-1));
			ls.add(new Coordinates(x,y+1));
			ls.add(new Coordinates(x+1,y+1));
			ls.add(new Coordinates(x-1,y));
			ls.add(new Coordinates(x,y-1));


			/*if (field  == Field.wall) {
					w.drawImage(m.getWall(), j*26, i*26, null);
				} else if (field  == Field.start) {
					w.drawImage(m.getWall(), j*26, i*26, null);
				} else if (field == Field.floor) {
					w.drawImage(m.getPath(), j*26, i*26, null);
				} else if (field  == Field.end) {
					w.drawImage(m.getCheese(), j*26, i*26, null);
				}*/




			String fieldString = game.boardString().toString();
			int width = game.boardString().getWidth();
			Field[][] f = new Field[fieldString.length() / width][width];
			int count = 0;

			for(int j = 0; j<ls.size(); j++) {
				if(f[ls.get(j).getX()][ls.get(j).getY()] != Field.floor ) {
					ls.remove(j);
				} 

				// TODO game.getPlayer().get(i).setCoordinates(ls.get(j));

			}

			int o = 0;
			while(o !=ls.size()) {
				for(int k =0; k<ls.size();k++) {
					//if(compareCoordinates(ls.get(o),ls.get(k));
					
				}
				o++;
			}
		}
	}

	public double calculateDistanceToCheese(Coordinates c1, Coordinates c2) {

		double x = (c2.getX() - c1.getX())*(c2.getX() - c1.getX());
		double y = (c2.getY() - c1.getY())*(c2.getY() - c1.getY());
		double d = Math.sqrt(x-y);

		return d;
	}

	public void compareDistance(Coordinates c1, Coordinates c2) {
		
	}

}
