package at.ac.tuwien.foop.client.userInterface.Views;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JPanel;



import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.domain.Board.Field;
import at.ac.tuwien.foop.domain.Coordinates;
import at.ac.tuwien.foop.domain.Player;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Map m;
	private Game game;
	
	
	public BoardPanel() {
		m = new Map();
	}

	public void actionPerformed(ActionEvent e){
		repaint();
	}

	public void paint(Graphics w) {
		
		super.paint(w);
		if(game==null)return;
	
		
		for(int i=0; i<game.getBoard().fields().length; i++) {
			for(int j=0; j<game.getBoard().fields()[i].length; j ++) {
				
				Field field = game.getBoard().fields()[i][j];
				
				if (field  == Field.wall) {
					w.drawImage(m.getWall(), j*26, i*26, null);
				} else if (field  == Field.start) {
					w.drawImage(m.getWall(), j*26, i*26, null);
				} else if (field == Field.floor) {
					w.drawImage(m.getPath(), j*26, i*26, null);
				} else if (field  == Field.end) {
					w.drawImage(m.getCheese(), j*26, i*26, null);
				}
				
			}
		}
		
		List<Player> player = game.getPlayers();
		for(int i = 0; i<player.size(); i++) {
			Coordinates coordinates = player.get(i).coordinates();
			w.drawImage(m.getMouse(), coordinates.y*26, coordinates.x*26, null);
		}
		
		
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
