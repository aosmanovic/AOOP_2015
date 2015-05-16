package at.ac.tuwien.foop.server.service;

import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;
import at.ac.tuwien.foop.domain.Board;
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
		Coordinates cheesCoordinates = Board.getCheesCoordinates();
		boolean end = false;
		Field[][] f = game.board().fields();

		for(int i =0; i<game.getPlayer().size();i++) {
			Player player =  game.getPlayer().get(i);

			while(!player.getCoordinates().equals(cheesCoordinates) && end==false)
			{
				Coordinates mouse = player.getCoordinates();
				int x = mouse.getX();
				int y = mouse.getY();
				
				//Getting closes neighbpurs of the specific player
				ArrayList<Coordinates> neighbourList= new ArrayList<>();
				neighbourList.add(new Coordinates(x,y-1));
				neighbourList.add(new Coordinates(x,y+1));
				neighbourList.add(new Coordinates(x-1,y));
				neighbourList.add(new Coordinates(x+1,y));

				ArrayList<Coordinates> floorList= new ArrayList<>();
				for(int j=0; j<neighbourList.size(); j++) {
					Coordinates neighbour = neighbourList.get(j);
					//Checked the boarders
					if(neighbour.getX()<f.length && neighbour.getX() >=0 && neighbour.getY()<f[1].length && neighbour.getY()>=0)
					{
						//Get path/floor neighbours
						if(f[neighbour.getX()][neighbour.getY()].equals(Field.floor )) {
							floorList.add(neighbour);
						} 
						else if(f[neighbour.getX()][neighbour.getY()].equals(Field.end))
						{
							player.setCoordinates(neighbour);
							end=true;
							break;
						}
					}
				}
				if(end==false)
				{
					double minDistance = calculateDistanceToCheese(cheesCoordinates, floorList.get(0));
					Coordinates closestNeigbour = floorList.get(0);
					for(int k =0; k<floorList.size();k++) {
						double distance = calculateDistanceToCheese(cheesCoordinates, floorList.get(k));
						if(distance<=minDistance)
						{
							minDistance = distance;
							closestNeigbour = floorList.get(k);
						}
					}
					player.setCoordinates(closestNeigbour);
				}

			}
		}
	}
	
	public double calculateDistanceToCheese(Coordinates c1, Coordinates c2) {

		double x = (c2.getX() - c1.getX())*(c2.getX() - c1.getX());
		double y = (c2.getY() - c1.getY())*(c2.getY() - c1.getY());
		double d = Math.sqrt(x+y);
		return d;
	}

}
