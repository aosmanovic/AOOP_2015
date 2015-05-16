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
		Coordinates cheesCoordinates = new Coordinates(1,5);
		boolean end = false;
		Field[][] f = game.board().fields();

		for(int i =0; i<game.getPlayer().size();i++) {
			Coordinates c = game.getPlayer().get(i).getCoordinates();
			int x = c.getX();
			int y = c.getY();

			do
			{
			ArrayList<Coordinates> ls= new ArrayList<>();
			ls.add(new Coordinates(x,y-1));
			ls.add(new Coordinates(x+1,y-1));
			ls.add(new Coordinates(x-1,y+1));
			ls.add(new Coordinates(x-1,y-1));
			ls.add(new Coordinates(x,y+1));
			ls.add(new Coordinates(x+1,y+1));
			ls.add(new Coordinates(x-1,y));
			ls.add(new Coordinates(x+1,y));
			
			//ArrayList<Coordinates> floorList= new ArrayList<>();
			for(int j=0; j<ls.size(); j++) {
				if(ls.get(j).getX()<f.length && ls.get(j).getX() >=0 && ls.get(j).getY()<f[1].length && ls.get(j).getY()>=0)
				{
					System.out.println((ls.get(j).toString()+" "+f[ls.get(j).getX()][ls.get(j).getY()].toString()));
				if(f[ls.get(j).getX()][ls.get(j).getY()].equals(Field.floor )) {
					//floorList.add(ls.get(j));
					x=ls.get(j).getX();
					y=ls.get(j).getY();
				} 
				else if(f[ls.get(j).getX()][ls.get(j).getY()].equals(Field.end))
				{
					end=true;
					break;
				}
				}

				// TODO game.getPlayer().get(i).setCoordinates(ls.get(j));
			}
			/*if(end==false)
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
				x=closestNeigbour.getX();
				y=closestNeigbour.getY();
				
			}*/
			if(end==true) break;
			}while(c.getX()!=cheesCoordinates.getX() && c.getY()!=cheesCoordinates.getY());
		}
		
	}

	public double calculateDistanceToCheese(Coordinates c1, Coordinates c2) {

		double x = (c2.getX() - c1.getX())*(c2.getX() - c1.getX());
		double y = (c2.getY() - c1.getY())*(c2.getY() - c1.getY());
		double d = Math.pow(2,x-y);
		return d;
	}

}
