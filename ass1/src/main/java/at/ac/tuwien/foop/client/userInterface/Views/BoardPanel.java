package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.domain.Board.Field;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private FieldImages images;
	private Game game;
	private static Map<Color,String> colors = new HashMap<>();
	private String result ="";


	public BoardPanel() {
		images = new FieldImages();
		colors.put(Color.RED, "Red"); colors.put(Color.BLUE, "Blue"); colors.put(Color.GREEN, "Green");
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
			Color key = colors.keySet().iterator().next();
			g.drawImage(images.getMouse(), game.getPlayers().get(i).coordinates().x * FieldImages.IMAGE_SIZE, game.getPlayers().get(i).coordinates().y * FieldImages.IMAGE_SIZE, 27, 27, key, null);
		} 

	}



	public void setGame(Game game) {
		this.game = game;
	}

	public String getResult() {

		for (int i=0; i<game.getPlayers().size(); i++) {
			Color key = colors.keySet().iterator().next();
			result += "   NAME: " + game.getPlayers().get(i).name() + "    COLOR: " + colors.get(key) +        "\n";
		} 

		return result;
	}

	public void getClor() {

	}

}
