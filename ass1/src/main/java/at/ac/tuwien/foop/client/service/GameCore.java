package at.ac.tuwien.foop.client.service;

import at.ac.tuwien.foop.domain.Wind;

public interface GameCore {
	public void join(String name);
	public void leave();
	public void disconnect();
	public void sendWind(Wind wind);
	public void ping();
}
