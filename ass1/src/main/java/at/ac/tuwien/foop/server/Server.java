package at.ac.tuwien.foop.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
	private static Logger log = LoggerFactory.getLogger(Server.class);

	public void run() {
		log.info("start system");
		new NettyServer().run();
	}

	public static void main(String[] args) {
		new Server().run();
	}

}
