package at.ac.tuwien.foop.domain;

import java.util.Objects;

import org.apache.commons.lang3.Validate;

public class Board {

	public static enum Field { // TODO: just an example.. move to a separate
								// file
		wall, floor, start, end
	}

	private Field[][] fields; // y|x
	private Coordinates cheeseCoordinates;
//	private List<Coordinates> startCoordinates;

	private Board(Field[][] fields, Coordinates cheeseCoordinates) {
		this.fields = fields;
		this.cheeseCoordinates = cheeseCoordinates;
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
		Objects.requireNonNull(fieldString);
		Validate.isTrue(width > 0, "the width must be > 0 but it is '%d'",
				width);
		Validate.isTrue(fieldString.length() % width == 0,
				"field length does not match board width!");

		Field[][] f = new Field[fieldString.length() / width][width];
		Coordinates cc = null;

		int i = 0;
		int j = 0;

		for (char c : fieldString.toCharArray()) {
			if (c == 'w') {
				f[j][i] = Field.wall;
			} else if (c == '-') {
				f[j][i] = Field.floor;
			} else if (c == 'm') {
				f[j][i] = Field.start;
			} else if (c == 'C') {
				f[j][i] = Field.end;
				cc = new Coordinates(j, i);
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
		
		return new Board(f, cc);
	}

	public Field[][] fields() {
		return fields;
	}

	public Coordinates cheeseCoordinates() {
		return cheeseCoordinates;
	}
}
