package at.ac.tuwien.foop.server.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.Board.Field;
import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.server.domain.BoardString;
import at.ac.tuwien.foop.server.domain.Game;

public class GameLogicService {

	public static String BOARD_PATH = "Map.txt";
	private static Logger log = LoggerFactory.getLogger(GameLogicService.class);

	public BoardString loadBoard(String path) {
		log.debug("load board with path '{}'!", path);
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

		for(int i =0; i<game.getPlayers().size();i++) {
			Player player =  game.getPlayers().get(i);

			Field[][] f = game.board().fields();
			Coordinates mouse = player.coordinates();
			int x = mouse.getX();
			int y = mouse.getY();
			log.info("Position of the mouse: " + mouse.toString());

			List<Coordinates> floorList= calculateNeighbor(f, x, y);


			double minDistance = calculateDistanceToCheese(cheesCoordinates, floorList.get(0));
			log.info("Size: " + floorList.size());
			Coordinates closestNeigbour = floorList.get(0);
			// Dead end
			if (floorList.size() == 1) {
				//goBack();
			}

			for(int k =0; k<floorList.size();k++) {
				double distance = calculateDistanceToCheese(cheesCoordinates, floorList.get(k));
				if(distance<=minDistance) {
					minDistance = distance;
					closestNeigbour = floorList.get(k);
					log.info("Clos ne:" + closestNeigbour);
				}
			}

			game.movePlayer(player.name(), closestNeigbour);



			/*ArrayList<Coordinates> floorList= new ArrayList<>();
			for(int j=0; j<neighbourList.size(); j++) {
				Coordinates neighbour = neighbourList.get(j);

				//Checked the boarders
				if(neighbour.getX()<f.length && neighbour.getX() >=0 && neighbour.getY()<f[1].length && neighbour.getY()>=0) {
					//Get path/floor neighbors
					if(f[neighbour.getX()][neighbour.getY()].equals(Field.floor )) {
						floorList.add(neighbour);
						log.info(" Neighbours with path: " + neighbour);
					} 
					//Checked if cheese/end is found
					else if(f[neighbour.getX()][neighbour.getY()].equals(Field.end)) {

						// field will never be changed!
						//							f[player.getCoordinates().getX()][player.getCoordinates().getY()] = Field.floor;
						//							game.board().setFields(f);

						game.movePlayer(player.name(), neighbour);
						//player.setVisitedCoordinates(player.getCoordinates());							

						end=true;
						break;
					}
				}
			}

			//Algorithm for mouse moving in cheese direction
			if(end==false) {	
				double minDistance = calculateDistanceToCheese(cheesCoordinates, floorList.get(0));
				log.info("Size:" + floorList.size());
				Coordinates closestNeigbour = floorList.get(0);

				for(int k =0; k<floorList.size();k++) {
					double distance = calculateDistanceToCheese(cheesCoordinates, floorList.get(k));
					if(distance<=minDistance) {
						minDistance = distance;
						closestNeigbour = floorList.get(k);
					}
				}
				// field will never be changed!
				//					f[player.getCoordinates().getX()][player.getCoordinates().getY()] = Field.floor;
				//					game.board().setFields(f);

				game.movePlayer(player.name(), closestNeigbour);

				Coordinates lastVisitedPath = player.getVisitedCoordinates().get(player.getVisitedCoordinates().size()-2);
				// when he is in a dead end
				if(countNeighbourWals(f,player.coordinates())==3 && pathIsVisited(lastVisitedPath,player.coordinates()) == true) {	
					log.info("OK");
					//player.setCoordinates(new Coordinates(2,5));
					end = true;
					break;
				} 

			}*/

			//}
		}
	}

	private List<Coordinates> calculateNeighbor(Field[][] f, int x, int y) {
		return Arrays.asList(new Coordinates[] {new Coordinates(x, y-1), new Coordinates(x, y+1), new Coordinates(x-1, y), new Coordinates(x+1, y)})
				.stream().filter(neighbour -> {
					if(neighbour.getX()<f[0].length 
							&& neighbour.getX() >=0 
							&& neighbour.getY()<f.length 
							&& neighbour.getY()>=0 
							&& !f[neighbour.getY()][neighbour.getX()].equals(Field.wall)) {
						return true;
					}
					return false;
				}).collect(Collectors.toList());
	}

	public double calculateDistanceToCheese(Coordinates c1, Coordinates c2) {
		//double x = 1 << (c2.getX() - c1.getX());
		//double y = 1 << (c2.getY() - c1.getY());
		double x = (c2.getX() - c1.getX())*(c2.getX() - c1.getX());
		double y = (c2.getY() - c1.getY())*(c2.getY() - c1.getY());
		return Math.sqrt(x+y);
	}

	public int countNeighbourWals(Field[][] f, Coordinates c) {
		int i = 0;

		if(f[c.getX()][c.getY()-1]== Field.wall) i++;
		if(f[c.getX()][c.getY()+1]== Field.wall) i++;
		if(f[c.getX()-1][c.getY()]== Field.wall) i++;
		if(f[c.getX()+1][c.getY()]== Field.wall) i++;

		return i;
	}

	/*public boolean pathIsVisited(Coordinates lastPath, Coordinates mousePosition) {
		boolean isVisited = false;

		Coordinates n1 = new Coordinates(mousePosition.getX(),mousePosition.getY()-1);
		Coordinates n2 = new Coordinates(mousePosition.getX(),mousePosition.getY()+1);
		Coordinates n3 = new Coordinates(mousePosition.getX()-1,mousePosition.getY());
		Coordinates n4 = new Coordinates(mousePosition.getX()+1,mousePosition.getY());

		if(lastPath.equals(n1) || lastPath.equals(n2)|| lastPath.equals(n3)|| lastPath.equals(n4)) 
			isVisited = true;


		return isVisited;
	}*/



}
