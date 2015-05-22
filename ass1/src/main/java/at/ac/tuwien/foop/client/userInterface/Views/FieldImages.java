package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class FieldImages {

	private Image wall, path, cheese, mouse;

	public FieldImages() {
		loadImages();
	}

	private void loadImages() {
		try {
			wall = new ImageIcon(ImageIO.read(loadStream("wall.png")))
					.getImage();
			path = new ImageIcon(ImageIO.read(loadStream("path.jpg")))
					.getImage();
			cheese = new ImageIcon(ImageIO.read(loadStream("cheese.jpg")))
					.getImage();
			mouse = new ImageIcon(ImageIO.read(loadStream("mouse.png")))
					.getImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private InputStream loadStream(String path) {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(path);
	}

	public Image getWall() {
		return wall;
	}

	public Image getPath() {
		return path;
	}

	public Image getCheese() {
		return cheese;
	}

	public Image getMouse() {
		return mouse;
	}

}
