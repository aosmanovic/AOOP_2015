package at.ac.tuwien.foop.client.gui.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class PlayerColor {

	private static PlayerColor instance;

	private SortedMap<String, Color> colors = new TreeMap<>();
	private ArrayList<String> colorList = new ArrayList<>();
	private int count = 0;

	private PlayerColor() {
		colors.put("red", Color.RED);
		colors.put("green", Color.GREEN);
		colors.put("blue", Color.BLUE);
		colors.put("yellow", Color.YELLOW);
		colorList.addAll(colors.keySet());
	}

	public String nextColor() {
		return colorList.get(count++ % colorList.size());
	}

	public Color color(String color) {
		return colors.get(color);
	}

	public static PlayerColor getInstance() {
		if (instance == null) {
			instance = new PlayerColor();
		}
		return instance;
	}
}
