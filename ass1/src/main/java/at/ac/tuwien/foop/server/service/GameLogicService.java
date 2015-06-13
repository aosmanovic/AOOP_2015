package at.ac.tuwien.foop.server.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.Board.Field;
import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.domain.Player.State;
import at.ac.tuwien.foop.domain.Wind;
import at.ac.tuwien.foop.server.domain.BoardString;
import at.ac.tuwien.foop.server.domain.Game;

public class GameLogicService {

	private static Logger log = LoggerFactory.getLogger(GameLogicService.class);

	public BoardString loadBoard(String path) {
		try (Scanner s = new Scanner(Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(path))) {
			int width = 0;
			StringBuffer buffer = new StringBuffer();
			while (s.hasNextLine()) {
				String line = s.nextLine();
				if (width == 0)
					width = line.length();
				if (line.length() != width)
					throw new RuntimeException(String.format(
							"Board file '%s' has a bad format!", path));
				buffer.append(line);
			}
			return new BoardString(buffer.toString(), width);
		}
	}


	public void movement(Game game, Wind wind) {
		Coordinates nextNeighbor;
		Coordinates cheeseCoordinates = game.board().cheeseCoordinates();
		for (Player player : game.getPlayers()) {
			log.info("Position of the mouse '{}': {}", player.name(), player.coordinates());

			// get neighbors with paths
			List<Coordinates> floorList = calculateNeighbor(game.board().fields(),
					player.coordinates());
			log.debug("***WIND***"+wind.x+"  "+wind.y);
			// check for dead end
			if (floorList.size() == 1 && player.getLastCoordinates() != null) {
				player.setState(State.crazy);
				game.movePlayer(player.name(), player.getLastCoordinates());
				continue;
			}

			// mouse crashes
			if (player.getState().equals(State.crash)) {
				if (player.crash()) {
					moveRandomlyDifferentDirections(player, game, floorList);
				}
				continue;
			}

			// calculate player state changes and crazy movements
			if (!player.getState().equals(State.notCrazy)) {
				if (floorList.size() >= 3) {
					if (player.getState().equals(State.crazy)) {
						moveRandomly(player, game, floorList);
						continue;
					} else
						player.setState(State.notCrazy);
				} else {
					if((wind.x!=0 || wind.y!=0) && WindInfluencesValid(player, wind, game)==true)
					{
						game.movePlayer(player.name(), new Coordinates((int)(player.coordinates().x+wind.x), (int)(player.coordinates().y+wind.y)));
						wind.setWindToDefault();
					}
					else
					{
						game.movePlayer(
							player.name(),
							floorList
							.stream()
							.filter(z -> !z.equals(player
									.getLastCoordinates())).findFirst()
									.orElse(null));
					}
					continue;
				}
			}

			// -------------- Cod for normal moving / Not crazy state ----------------------------
			// TODO: just a hack: removes last cordinates from possible
			// neighbors
			if((wind.x!=0 || wind.y!=0) && WindInfluencesValid(player, wind, game)==true)
			{
				game.movePlayer(player.name(), new Coordinates((int)(player.coordinates().x+wind.x), (int)(player.coordinates().y+wind.y)));
				wind.setWindToDefault();
			}
			else
			{
				floorList = floorList.stream()
					.filter(z -> !z.equals(player.getLastCoordinates()))
					.collect(Collectors.toList());
				nextNeighbor = calculateNextNeighbor(floorList,
					cheeseCoordinates);
				game.movePlayer(player.name(), nextNeighbor);
			}

			// calculate next neighbor considering the wind
			nextNeighbor = calculateNextNeighbor(floorList,
					cheeseCoordinates);

			// check if cheese was found
			if (nextNeighbor.equals(cheeseCoordinates)) {
				game.stop(player);
				continue;
			}
		}
		wind.setWindToDefault();
	}




	/**
	 * Calculate the closest neighbor to the cheese
	 * 
	 * @param neighbors
	 * @param cheeseCoordinates
	 * @return Coordinates of the neighbor
	 */
	private Coordinates calculateClosestNeighborToCheese(List<Coordinates> neighbors, Coordinates cheeseCoordinates) {
		double minDistance = Double.POSITIVE_INFINITY;
		Coordinates closestNeighbor = null;

		for (Coordinates neighbor : neighbors) {
			double distance = calculateDistanceToCheese(cheeseCoordinates,neighbor);
			if (distance <= minDistance) {
				minDistance = distance;
				closestNeighbor = neighbor;
			}
		}

		return closestNeighbor;

	} 
	/**
	 * Calculate the next neighbor, considering the wind direction
	 * @param neighbors
	 * @param cheeseCoordinates
	 * @param wind
	 * @param field
	 * @param c
	 * @return
	 */
	private Coordinates calculateNextNeighbor(List<Coordinates> neighbors, Coordinates cheeseCoordinates) {
			Coordinates nextNeighbor = null;
			nextNeighbor = calculateClosestNeighborToCheese(neighbors,cheeseCoordinates);		

		return nextNeighbor;
	}


	/**
	 * Calculate neighbor fields of {@code position} that are not of type wall.
	 */
	private List<Coordinates> calculateNeighbor(Field[][] fields,
			Coordinates position) {
		int x = position.x;
		int y = position.y;

		return Arrays
				.asList(new Coordinates[] { new Coordinates(x, y - 1),
						new Coordinates(x, y + 1), new Coordinates(x - 1, y),
						new Coordinates(x + 1, y) })
						.stream()
						.filter(neighbour -> {
							if (neighbour.x < fields[0].length
									&& neighbour.x >= 0
									&& neighbour.y < fields.length
									&& neighbour.y >= 0
									&& !fields[neighbour.y][neighbour.x]
											.equals(Field.wall)) {
								return true;
							}
							return false;
						}).collect(Collectors.toList());
	}


	/**
	 * Calculate the distance between {@code position} and {@code cheese}.
	 */
	private double calculateDistanceToCheese(Coordinates position,
			Coordinates cheese) {

		double x = (cheese.x - position.x) * (cheese.x - position.x);
		double y = (cheese.y - position.y) * (cheese.y - position.y);
		return Math.sqrt(x + y);
		
	}


	public static String getBoardPath(int lvl) {
		return String.format("Map%d.txt", lvl);
	}


	public Player checkCrash(Player player, Game game) {
		int x = player.coordinates().x;
		int y = player.coordinates().y;

		for (Player p : game.getPlayers()) {
			if (p.equals(player)) {
				continue;
			}
			if (new Coordinates(x - 1, y).equals(p.coordinates())
					|| new Coordinates(x + 1, y).equals(p.coordinates())
					|| new Coordinates(x, y - 1).equals(p.coordinates())
					|| new Coordinates(x, y + 1).equals(p.coordinates())) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Randomly movement for the mouse in case of a dead end
	 * @param player
	 * @param game
	 * @param floorList
	 */
	public void moveRandomly(Player player, Game game, List<Coordinates> floorList) {
		player.setState(State.notSoCrazy);
		List<Coordinates> p = floorList.stream()
				.filter(z -> !z.equals(player.getLastCoordinates()))
				.collect(Collectors.toList());
		game.movePlayer(player.name(), p.get(new Random().nextInt(p.size())));
		
	}


	/**
	 * Randomly movement in different directions for the mouses in case of a mouse crash
	 * @param player
	 * @param game
	 * @param floorList
	 */
	public void moveRandomlyDifferentDirections(Player player, Game game, List<Coordinates> floorList) {
		player.setState(State.notSoCrazy);
		List<Coordinates> p = floorList.stream()
				.filter(z -> !game.getPlayers().stream().anyMatch(pl -> pl.coordinates().equals(z)))
				.collect(Collectors.toList());
		game.movePlayer(player.name(), p.get(new Random().nextInt(p.size())));
	}
	
	private boolean WindInfluencesValid(Player p, Wind w, Game g)
	{
		double signX=1, signY=1;
		if(w.x<0) signX=-1;
		if(w.y<0) signY=-1;

		for(int i=0; i<Math.abs((int)w.x)+1; i++)
		{
			for(int j=0; j<Math.abs((int)w.y)+1; j++)
			{
				if(g.board().fields()[(int) (p.coordinates().y+j*signY)][(int) (p.coordinates().x+i*signX)]==Field.wall) 
					{
						return false;
					}
			}
		}
		return true;
	}


}
