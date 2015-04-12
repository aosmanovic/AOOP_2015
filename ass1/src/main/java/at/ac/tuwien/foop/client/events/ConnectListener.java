package at.ac.tuwien.foop.client.events;

import at.ac.tuwien.foop.client.NettyClient;

@FunctionalInterface
public interface ConnectListener {
	public void onConnect(NettyClient client);
}
