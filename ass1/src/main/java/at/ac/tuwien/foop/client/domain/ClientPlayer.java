package at.ac.tuwien.foop.client.domain;

import java.util.HashMap;
import java.util.Map;

import at.ac.tuwien.foop.client.gui.utils.PlayerColor;
import at.ac.tuwien.foop.domain.Player;

public class ClientPlayer extends Player {
	private static Map<String, String> playerColors = new HashMap<>();

	private String color;

	public ClientPlayer(Player player) {
		super(player.name(), player.coordinates(), player.lastCoordinates(),player.state(), player.active());

		if (playerColors.containsKey(player.name())) {
			color = playerColors.get(player.name());
		} else {
			color = PlayerColor.getInstance().nextColor();
			playerColors.put(player.name(), color);
		}
	}

	public String color() {
		return color;
	}
}
