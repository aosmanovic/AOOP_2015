package at.ac.tuwien.foop.client.service;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.domain.WindGust;

public interface GameCore {
	public void introduce(String name);
	public void join();
	public void leave();
	public void disconnect();
	public void sendWind(WindGust wind);
	public void ping();
	public void start();
	public void newLevel();
	public void setGame(Game game);
}
