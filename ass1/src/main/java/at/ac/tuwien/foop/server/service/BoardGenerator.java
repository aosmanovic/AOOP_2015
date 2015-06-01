package at.ac.tuwien.foop.server.service;

import java.util.Random;

import at.ac.tuwien.foop.domain.Board.Field;
import at.ac.tuwien.foop.server.domain.BoardString;

public class BoardGenerator {
	public BoardString generateBoard(int width, int height) {
		VisitableField[][] fields = new VisitableField[height][width];
		Random r = new Random();

		int x = r.nextInt(width);
		int y = r.nextInt(height);

		fields[y][x].field = Field.end;
		fields[y][x].visited = true;

		VisitableField n = getRandomNeighbor(fields, x, y);
		return null;
	}

	private VisitableField getRandomNeighbor(VisitableField[][] fields, int x, int y) {
//		return new VisitableField[] = {
		return null;
	}

	private class VisitableField {
		public Field field;
		public boolean visited;
		public VisitableField(Field field, boolean visited) {
			super();
			this.field = field;
			this.visited = visited;
		}
		
	}
}
