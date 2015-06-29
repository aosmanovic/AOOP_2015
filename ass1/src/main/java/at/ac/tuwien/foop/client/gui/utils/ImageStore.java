package at.ac.tuwien.foop.client.gui.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageStore {
	public static final int IMAGE_SIZE = 26;

	private static ImageStore instance;

	private BufferedImage wall, path, cheese, mouse, compass, compassArrow;

	private ImageStore() {
		loadImages();
	}

	private void loadImages() {
		try {
			wall = ImageIO.read(loadStream("wall.png"));
			path = ImageIO.read(loadStream("path.jpg"));
			cheese = ImageIO.read(loadStream("cheese.jpg"));
			mouse = ImageIO.read(loadStream("mouse.png"));
			compass = ImageIO.read(loadStream("compass_bg.png"));
			compassArrow = ImageIO.read(loadStream("compass_arrow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private InputStream loadStream(String path) {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(path);
	}

	public BufferedImage getMouse() {
		return mouse;
	}

	public BufferedImage getPath() {
		return path;
	}

	public BufferedImage getWall() {
		return wall;
	}

	public BufferedImage getCheese() {
		return cheese;
	}

	public BufferedImage getCompass() {
		return compass;
	}

	public BufferedImage getCompassArrow() {
		return compassArrow;
	}

	public static ImageStore getInstance() {
		if (instance == null) {
			instance = new ImageStore();
		}
		return instance;
	}
}
