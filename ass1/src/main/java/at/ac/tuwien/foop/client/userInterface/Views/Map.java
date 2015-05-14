package at.ac.tuwien.foop.client.userInterface.Views;





import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Map {

	private Scanner m;
	private String Map[] = new String[500];
	private Image wall, path, cheese, mouse;

	public Map() {
		loadImages();
		openFile();
		readFile();
		closeFile();
	}
	
	private void loadImages() {
		try {
			wall = new ImageIcon(ImageIO.read(loadStream("wall.png"))).getImage();
			path = new ImageIcon(ImageIO.read(loadStream("path.jpg"))).getImage();
			cheese = new ImageIcon(ImageIO.read(loadStream("cheese.jpg"))).getImage();
			mouse = new ImageIcon(ImageIO.read(loadStream("mouse.png"))).getImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMap(int x, int y) {
		String index = Map[y].substring(x,x + 1);
		return index;
	}


	public void openFile() {
		try {
			m = new Scanner(loadStream("Map.txt"));
		} catch(Exception e) {
			System.out.println("Error loading map!");
			// TODO: handle failure!
		}
	}

	private InputStream loadStream(String path) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}

	public void readFile() 	{

		while(m.hasNext())	{
			for(int i=0; i<21; i++){
				Map[i] = m.next();
			}	
		}
	}

	public void closeFile(){
		m.close();
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
