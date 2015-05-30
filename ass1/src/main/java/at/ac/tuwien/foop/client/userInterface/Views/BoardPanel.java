package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.domain.Board.Field;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private FieldImages images;
	private Game game;
	private static ArrayList<Color> colors = new ArrayList<>();
	private String result ="";
	private int counter= 0;
	private JLabel l = new JLabel();

	public BoardPanel() {
		images = new FieldImages();
		colors.add(Color.RED); colors.add(Color.BLUE); colors.add(Color.GREEN);
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	

	@Override
	public void paint(Graphics g) {
		super.paint(g);


		if (game == null) {
			return;
		}



		Field[][] f = game.getBoard().fields();
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[i].length; j++) {
				Field field = f[i][j];

				if (field == Field.wall) {
					g.drawImage(images.getWall(), j * FieldImages.IMAGE_SIZE, i * FieldImages.IMAGE_SIZE, null);
				} else if (field == Field.start) {
					g.drawImage(images.getPath(), j * FieldImages.IMAGE_SIZE, i * FieldImages.IMAGE_SIZE, null);
				} else if (field == Field.floor) {
					g.drawImage(images.getPath(), j * FieldImages.IMAGE_SIZE, i * FieldImages.IMAGE_SIZE, null);
				} else if (field == Field.end) {
					g.drawImage(images.getCheese(), j * FieldImages.IMAGE_SIZE, i * FieldImages.IMAGE_SIZE, null);
				}
			}
		}


		/*game.getPlayers().forEach(
				p -> g.drawImage(images.getMouse(), p.coordinates().x * FieldImages.IMAGE_SIZE,
						p.coordinates().y * FieldImages.IMAGE_SIZE, null));*/



		for (int i=0; i<game.getPlayers().size(); i++) {
			g.drawImage(images.getMouse(), game.getPlayers().get(i).coordinates().x * FieldImages.IMAGE_SIZE, game.getPlayers().get(i).coordinates().y * FieldImages.IMAGE_SIZE, 27, 27, colors.get(i), null);
			setResult(game.getPlayers().get(i).toString() + "COLOR: ");
			System.out.println("PLAYER" + result);
		} 
		
		
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getResult() {
		while(counter == 1)
			return result;
		return "";
	
	}

	public void setResult(String result) {
		this.result = result;
		counter = 1;
	}


	

	
		
}
