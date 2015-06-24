package at.ac.tuwien.foop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.gui.controller.StartController;
import at.ac.tuwien.foop.server.Server;

public class App {
	private static Logger log = LoggerFactory.getLogger(App.class);

	public App() {
		log.info("start system");
		StartController sc = new StartController();
		sc.showStartFrame();
		new Server().setServerReadyListener(sc);
	}

	public static void main(String[] args) {
		new App();
	}
}
