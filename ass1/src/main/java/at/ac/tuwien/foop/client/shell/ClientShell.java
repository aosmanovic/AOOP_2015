package at.ac.tuwien.foop.client.shell;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;
import at.ac.tuwien.foop.client.NettyClient;
import at.ac.tuwien.foop.client.domain.Game;

public class ClientShell {
	private static Logger log = LoggerFactory.getLogger(ClientShell.class);

	private NettyClient client = null;
	private Game game = null;

	// public ClientShell() {
	// }

	@Command(description = "conntect to a server")
	public void connect(@Param(name = "host") String host,
			@Param(name = "port") String port) {
		if (client != null) {
			log.warn("client already connected!");
			return;
		}
		log.debug("connect to {}:{}", host, port);
		game = new Game();
		client = new NettyClient(game, "localhost", 20150);
		new Thread(client, "Network-Layer-Thread").start();
	}

	@Command(description = "disconnect from server")
	public void disconnect() {
		if (client == null) {
			log.warn("not connected!");
			return;
		}
		log.debug("disconnect from server");
		game.disconnect();
		game = null;
		client = null;
	}

	@Command(description = "join a game")
	public void join() {
		if (client == null) {
			log.warn("not connected!");
			return;
		}
		log.debug("join a game");
		throw new NotImplementedException();
	}

	@Command(description = "show some system information")
	public void show() {
		log.info("show some system information");
		log.info("--- game -------------------");
		if (game != null) {
			log.info("running: {}", game.isRunning());
		}
		log.debug("--- threads ----------------");
		Thread[] threads = new Thread[10];
		Thread.currentThread().getThreadGroup().enumerate(threads);
		for (Thread t : threads) {
			if (t != null) {
				log.info("thread {}", t);
			}
		}
	}

	@Command(description = "send a ping")
	public void ping() {
		if (client == null) {
			log.warn("not connected!");
			return;
		}
		log.debug("send a ping");
		game.ping();
	}

	public static void main(String[] args) throws IOException {
		log.debug("start client shell");

		ShellFactory.createConsoleShell("client shell", "client shell",
				new ClientShell()).commandLoop();
	}
}
