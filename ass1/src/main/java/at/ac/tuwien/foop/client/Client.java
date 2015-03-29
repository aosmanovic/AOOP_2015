package at.ac.tuwien.foop.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.model.Game;

public class Client {
	private static Logger log = LoggerFactory.getLogger(Client.class);

	public void run() {
		log.info("start system");
		Game game = new Game();
		new NettyClient(game).run();
	}

	public static void main(String[] args) {
		new Client().run();
	}

}
