package at.ac.tuwien.foop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.server.NettyServer;

public class App {
	private static Logger log = LoggerFactory.getLogger(App.class);

	private Thread server;

	public App() {
		log.info("start system");
		server = new Thread(new NettyServer());
	}

	public void run() {
		try {
			server.start();
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new App().run();
	}
}
