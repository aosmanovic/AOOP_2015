package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class FieldImages {

	public static final int IMAGE_SIZE = 26;
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

	/*public JLabel getMouse() {

		JLabel l;
		try {
			l = new JLabel(new ImageIcon(ImageIO.read(loadStream("mouse.png"))));
			return l;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}*/
	
	public Image getMouse() {
		return mouse;
	}

	public Image getPath() {
		return path;
	}

	public Image getWall() {
		return wall;
	}

	public Image getCheese() {
		return cheese;
	}
	
	

}
