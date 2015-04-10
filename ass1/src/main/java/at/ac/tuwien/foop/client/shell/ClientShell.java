package at.ac.tuwien.foop.client.shell;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;
import at.ac.tuwien.foop.client.ClientHandler;
import at.ac.tuwien.foop.client.NettyClient;
import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.domain.Wind;
import at.ac.tuwien.foop.client.domain.Wind.Direction;
import at.ac.tuwien.foop.client.service.GameService;

public class ClientShell {
	private static Logger log = LoggerFactory.getLogger(ClientShell.class);

	private NettyClient client = null;
	private Game game = null;
	private ClientHandler core = null;
	private GameService service = new GameService();

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
		core = new ClientHandler(game);
		client = new NettyClient(core, "localhost", 20150);
		new Thread(client, "Network-Layer-Thread").start();
	}

	@Command(description = "disconnect from server")
	public void disconnect() {
		if (client == null) {
			log.warn("not connected!");
			return;
		}
		log.debug("disconnect from server");
		service.disconnect(game, core);
		game = null;
		core = null;
		client = null;
	}

	@Command(description = "join a game")
	public void join(String name) {
		if (client == null) {
			log.warn("not connected!");
			return;
		}
		log.debug("join a game");
		service.join(game, core, name);
	}

	@Command(description = "leave the current game")
	public void leave() {
		if (client == null) {
			log.warn("not connected!");
			return;
		}
		log.debug("leave the current game");
		service.leave(game, core);
	}

	@Command(description = "send a wind")
	public void wind(String direction, int strength) {
		if (client == null) {
			log.warn("not connected!");
			return;
		}
		log.debug("send a wind");
		service.sendWind(game, core, new Wind(Direction.valueOf(direction.toUpperCase()), strength));
	}
	
	@Command(description = "show some system information")
	public void show() {
		log.info("show some system information");
		log.info("--- game -------------------");
		if (game != null) {
			log.info("running: {}", game.running());
			log.info("joined: {}", game.joined());
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
		service.ping(core);
	}

	public static void main(String[] args) throws IOException {
		log.debug("start client shell");

		ShellFactory.createConsoleShell("client shell", "client shell",
				new ClientShell()).commandLoop();
	}
}
