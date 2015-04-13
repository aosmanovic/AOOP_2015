package at.ac.tuwien.foop.server.shell;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asg.cliche.Command;
import asg.cliche.ShellFactory;
//import at.ac.tuwien.foop.client.ClientHandler;
//import at.ac.tuwien.foop.client.NettyClient;
//import at.ac.tuwien.foop.client.domain.Game;
//import at.ac.tuwien.foop.client.service.GameService;
//import at.ac.tuwien.foop.domain.Wind;
//import at.ac.tuwien.foop.domain.Wind.Direction;
import at.ac.tuwien.foop.server.NettyServer;

public class ServerShell {
	private static Logger log = LoggerFactory.getLogger(ServerShell.class);

	private NettyServer server;

//	private Game game = null;
//	private GameHandler core = null;
//	private GameService service = new GameService();


	public ServerShell() {
		server = new NettyServer();
		new Thread(server).start();
	}
	
//	@Command(description = "conntect to a server")
//	public void connect(@Param(name = "host") String host,
//			@Param(name = "port") String port) {
//		if (core != null) {
//			log.warn("client already connected!");
//			return;
//		}
//		log.debug("connect to {}:{}", host, port);
//		game = new Game();
//		NettyClient c = new NettyClient(game, host, Integer.parseInt(port));
//		c.addConnectListener(e -> core = e.getClientHandler());
//		new Thread(c, "Network-Layer-Thread").start();
//	}

	@Command(description = "disconnect all clients and ")
	public void disconnect() {
		server.shutDown();
//		if (core == null) {
//			log.warn("not connected!");
//			return;
//		}
//		log.debug("disconnect from server");
//		service.disconnect(game, core);
//		game = null;
//		core = null;
	}

	
	@Command(description = "show some system information")
	public void show() {
		log.info("show some system information");
//		log.info("--- game -------------------");
//		log.info("connected: {}", core != null);
//		if (game != null) {
//			log.info("joined: {}", game.joined());
//			log.info("running: {}", game.running());
//		}
//		log.debug("--- threads ----------------");
//		Thread[] threads = new Thread[10];
//		Thread.currentThread().getThreadGroup().enumerate(threads);
//		for (Thread t : threads) {
//			if (t != null) {
//				log.info("thread {}", t);
//			}
//		}
	}

//	@Command(description = "send a ping")
//	public void ping() {
//		if (core == null) {
//			log.warn("not connected!");
//			return;
//		}
//		log.debug("send a ping");
//		service.ping(core);
//	}

	public static void main(String[] args) throws IOException {
		log.debug("start client shell");

		ShellFactory.createConsoleShell("client shell", "client shell",
				new ServerShell()).commandLoop();
	}
}
