package at.ac.tuwien.foop.client.domain;

import java.util.Objects;

import org.apache.commons.lang3.Validate;

public class Board {
	public static enum Field { // TODO: just an example.. move to a separate
								// file
		wall, start, end
	}

	private Field[][] fields; // y|x

	private Board() {}
	
	/**
	 * 
	 * @param fieldString
	 * @param width
	 */
	private void generateFields(String fieldString, int width) {
		Objects.requireNonNull(fieldString);
		Validate.isTrue(width > 0, "the width must be > 0 but it is '%d'",
				width);

		if (fieldString.length() % width != 0) {
			throw new RuntimeException(
					"field length does not match board width!");
		}

		int i = 0;
		int j = 0;
		for (char c : fieldString.toCharArray()) {
			if (c == 'w') {
				fields[j][i] = Field.wall;
			} else if (c == 's') {
				fields[j][i] = Field.start;
			} else if (c == 'e') {
				fields[j][i] = Field.end;
			} else {
				throw new RuntimeException("unknown field type!");
			}
			i++;
			if (i == width - 1) {
				i = 0;
				j++;
			}
		}
	}

	public static Board createBoard(String fieldString, int width) {
		Board b = new Board();
		b.generateFields(fieldString, width);
		return b;
	}
}
