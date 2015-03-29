package at.ac.tuwien.foop.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
	private static Logger log = LoggerFactory.getLogger(Client.class);

	public void run() {
		log.info("start system");
		new NettyClient().run();
	}

	public static void main(String[] args) {
		new Client().run();
	}

}
