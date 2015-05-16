package at.ac.tuwien.foop.server.service;

import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.Board;
import at.ac.tuwien.foop.domain.Board.Field;
import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.server.domain.BoardString;
import at.ac.tuwien.foop.server.domain.Game;

public class GameLogicService {

	private static String BOARD_PATH = "Map.txt";
	private static Logger log = LoggerFactory.getLogger(GameLogicService.class);

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
		Coordinates cheesCoordinates = game.board().cheeseCoordinates();
		boolean end = false;

		for(int i =0; i<game.getPlayer().size();i++) {
			Player player =  game.getPlayer().get(i);

			while(!player.getCoordinates().equals(cheesCoordinates) && end==false)
			{
				Field[][] f = game.board().fields();
				Coordinates mouse = player.getCoordinates();
				int x = mouse.getX();
				int y = mouse.getY();
				log.info("Pozicija misa" + mouse.toString());
				
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
							log.info(" komsije put" + neighbour);
						} //Checked if chees/end is found
						else if(f[neighbour.getX()][neighbour.getY()].equals(Field.end))
						{
							
							// field will never be changed!
//							f[player.getCoordinates().getX()][player.getCoordinates().getY()] = Field.floor;
//							game.board().setFields(f);
							
							player.setCoordinates(neighbour);
							//player.setVisitedCoordinates(player.getCoordinates());							
							
							end=true;
							break;
						}
					}
				}
				if(end==false)
				{	//Algorithm for mouse moving in chees direction
					double minDistance = calculateDistanceToCheese(cheesCoordinates, floorList.get(0));
					System.out.println(floorList.size());
					Coordinates closestNeigbour = floorList.get(0);
					for(int k =0; k<floorList.size();k++) {
						double distance = calculateDistanceToCheese(cheesCoordinates, floorList.get(k));
						if(distance<=minDistance)
						{
							minDistance = distance;
							closestNeigbour = floorList.get(k);
						}
					}
					// field will never be changed!
//					f[player.getCoordinates().getX()][player.getCoordinates().getY()] = Field.floor;
//					game.board().setFields(f);
					
					player.setCoordinates(closestNeigbour);
					
					
					Coordinates lastVisitedPath = player.getVisitedCoordinates().get(player.getVisitedCoordinates().size()-2);
					// when he is in a dead end
					if(countNeighbourWals(f,player.getCoordinates())==3 && pathIsVisited(lastVisitedPath,player.getCoordinates()) == true) {	
						log.info("OK");
						//player.setCoordinates(new Coordinates(2,5));
						end = true;
						break;
					} 
		
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
	
	public int countNeighbourWals(Field[][] f, Coordinates c) {
		int i = 0;
		
		if(f[c.getX()][c.getY()-1]== Field.wall) i++;
		if(f[c.getX()][c.getY()+1]== Field.wall) i++;
		if(f[c.getX()-1][c.getY()]== Field.wall) i++;
		if(f[c.getX()+1][c.getY()]== Field.wall) i++;
			
		return i;
	}

	public boolean pathIsVisited(Coordinates lastPath, Coordinates mousePosition) {
		boolean isVisited = false;
		
		Coordinates n1 = new Coordinates(mousePosition.getX(),mousePosition.getY()-1);
		Coordinates n2 = new Coordinates(mousePosition.getX(),mousePosition.getY()+1);
		Coordinates n3 = new Coordinates(mousePosition.getX()-1,mousePosition.getY());
		Coordinates n4 = new Coordinates(mousePosition.getX()+1,mousePosition.getY());
		
		if(lastPath.equals(n1) || lastPath.equals(n2)|| lastPath.equals(n3)|| lastPath.equals(n4)) 
			isVisited = true;

		
		return isVisited;
	}
	
	
}
