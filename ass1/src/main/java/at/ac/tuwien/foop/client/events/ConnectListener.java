package at.ac.tuwien.foop.client.events;

import at.ac.tuwien.foop.client.network.NettyClient;

public interface ConnectListener {
	public void onConnect(NettyClient client);
	public void onConnecitonFailure();
}
