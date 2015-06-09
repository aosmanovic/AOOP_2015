package at.ac.tuwien.foop.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

public class Board {

	public static enum Field {
		wall, floor, start, end
	}

	private Field[][] fields; // f[y][x]
	private Coordinates cheeseCoordinates;
	private List<Coordinates> startCoordinates;



	public Board(Field[][] fields, Coordinates cheeseCoordinates,
			List<Coordinates> startCoordinates) {
		this.fields = fields;
		this.cheeseCoordinates = cheeseCoordinates;
		this.startCoordinates = Collections.unmodifiableList(startCoordinates);
	}

	/**
	 * Create a board instance using a string where every character represents a
	 * field type.<br/>
	 * Allowed fields are:<br/>
	 * <code>w</code>: wall, unpassable field<br/>
	 * <code>-</code>: floor, passable field<br/>
	 * <code>m</code>: mouse, starting field<br/>
	 * <code>C</code>: cheese, goal field<br/>
	 * 
	 * @param width
	 *            the width of the board
	 */
	public static Board createBoard(String fieldString, int width) {
//		System.out.println("create Board with string: "+ fieldString);
		Objects.requireNonNull(fieldString);
		Validate.isTrue(width > 0, "the width must be > 0 but it is '%d'",
				width);
		Validate.isTrue(fieldString.length() % width == 0,
				"field length does not match board width!");

		Field[][] f = new Field[fieldString.length() / width][width];
		Coordinates cc = null;
		List<Coordinates> startCoordinates = new ArrayList<>();

		int i = 0;
		int j = 0;

		for (char c : fieldString.toCharArray()) {
			if (c == 'w') {
				f[j][i] = Field.wall;
			} else if (c == '-') {
				f[j][i] = Field.floor;
			} else if (c == 'm') {
				f[j][i] = Field.start;
				startCoordinates.add(new Coordinates(i, j));
			} else if (c == 'C') {
				f[j][i] = Field.end;
				cc = new Coordinates(i, j);
			} else {
				throw new IllegalArgumentException("unknown field type!");
			}
			i++;
			if (i == width) {
				i = 0;
				j++;
			}
		}
		Validate.notNull(cc, "no cheese found on map!");
		
		return new Board(f, cc, startCoordinates);
	}

	public Field[][] fields() {
		return fields;
	}

	public Coordinates cheeseCoordinates() {
		return cheeseCoordinates;
	}
	
	public List<Coordinates> startCoordinates() {
		return startCoordinates;
	}
}
