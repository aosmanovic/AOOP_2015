package at.ac.tuwien.foop.client.service;

import at.ac.tuwien.foop.domain.WindGust;

public interface GameCore {
	public void join(String name);
	public void leave();
	public void disconnect();
	public void sendWind(WindGust wind);
	public void ping();
	public void start();
	public void over();
	public void newLevel();
}
