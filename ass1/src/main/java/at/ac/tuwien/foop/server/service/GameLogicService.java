package at.ac.tuwien.foop.server.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.Board.Field;
import at.ac.tuwien.foop.domain.Player.State;
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
		

		for(int i =0; i<game.getPlayers().size();i++) {
			final Player player =  game.getPlayers().get(i);

			Field[][] f = game.board().fields();
			int x = player.coordinates().x;
			int y = player.coordinates().y;
			log.info("Position of the mouse: " + player.coordinates().toString());

			// get neighbors with paths
			List<Coordinates> floorList= calculateNeighbor(f, x, y);


			Coordinates closestNeigbour = floorList.get(0);
			//player = new Player(player.name(), player.coordinates(), closestNeigbour);

			// dead end
			/*if (countNeighbourWals(f,player.coordinates()) == 3) {
				/*player.getLastCoordinates();
				log.info("UUUUUU" + player.getLastCoordinates());
				game.movePlayer(player.name(), player.getLastCoordinates());
				log.info("AAAA" + player.coordinates());*/

			
			if(countNeighbourWals(f,player.coordinates()) == 3 && player.getLastCoordinates() != null) {
				log.info("INNNNNNNNNNNN 11111111111");
				player.setState(State.crazy);
				game.movePlayer(player.name(), player.getLastCoordinates());
				break;
				
			}
			
			if(!player.getState().equals(State.notCrazy))  {
				log.info("INFOOOOOO 22222222222");
				if(floorList.size()>=3) {
					if(player.getState().equals(State.crazy)) {
						player.setState(State.notSoCrazy);
						List<Coordinates> p = floorList.stream().filter(z -> !z.equals(player.getLastCoordinates())).collect(Collectors.toList());
						game.movePlayer(player.name(), p.get(new Random().nextInt(p.size())));
						break;
					}else
						player.setState(State.notCrazy);
				}else {
					game.movePlayer(player.name(), floorList.stream().filter(z -> !z.equals(player.getLastCoordinates())).findFirst().orElse(null));
					break;
				}
			}
				
			
			// TODO: just a hack!
			floorList = floorList.stream().filter(z -> !z.equals(player.getLastCoordinates())).collect(Collectors.toList());

			// calculate cheese distance
			double minDistance = calculateDistanceToCheese(cheesCoordinates, floorList.get(0));
			for(int k =0; k<floorList.size();k++) {
				double distance = calculateDistanceToCheese(cheesCoordinates, floorList.get(k));
				if(distance<=minDistance) {
					minDistance = distance;
					closestNeigbour = floorList.get(k);
					log.info("Closest neighbor:" + closestNeigbour);
				}
			}

			// move the player
			game.movePlayer(player.name(), closestNeigbour);
			//player = new Player(player.name(), player.coordinates(), closestNeigbour);
			log.info("Last Coordinates: " + player.getLastCoordinates());
		}
	}

	/*private int goBack(Player player) {
		// TODO Auto-generated method stub
		
		
		
		return 0;
	}*/

	// calculates the neighbors that are not a wall
	private List<Coordinates> calculateNeighbor(Field[][] f, int x, int y) {
		return Arrays.asList(new Coordinates[] {new Coordinates(x, y-1), new Coordinates(x, y+1), new Coordinates(x-1, y), new Coordinates(x+1, y)})
				.stream().filter(neighbour -> {
					if(neighbour.x<f[0].length 
							&& neighbour.x >=0 
							&& neighbour.y<f.length 
							&& neighbour.y>=0 
							&& !f[neighbour.y][neighbour.x].equals(Field.wall)) {
						return true;
					}
					return false;
				}).collect(Collectors.toList());
	}

	// calculates distance to cheese
	public double calculateDistanceToCheese(Coordinates c1, Coordinates c2) {
		double x = (c2.x - c1.x)*(c2.x - c1.x);
		double y = (c2.y - c1.y)*(c2.y - c1.y);
		return Math.sqrt(x+y);
	}

	// counts neigbors that are walls
	public int countNeighbourWals(Field[][] f, Coordinates c) {
		int i = 0;

		if(c.x-1>=0 && c.y-1 >=0) {
			if(f[c.y-1][c.x]== Field.wall) i++;
			if(f[c.y+1][c.x]== Field.wall) i++;
			if(f[c.y][c.x-1]== Field.wall) i++;
			if(f[c.y][c.x+1]== Field.wall) i++;
		}

		return i;
	}
}
