package at.ac.tuwien.foop.client.gamelogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asg.cliche.Command;
import asg.cliche.Param;
import at.ac.tuwien.foop.client.ClientHandler;
import at.ac.tuwien.foop.client.NettyClient;
import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.service.GameService;
import at.ac.tuwien.foop.client.shell.ClientShell;
import at.ac.tuwien.foop.server.NettyServer;

public class StartController {

	private static Logger log = LoggerFactory.getLogger(StartController.class);
	private Game game = null;
	private ClientHandler core = null;
	private Thread server;
	private Thread client;

	public StartController() {
		server = new Thread(new NettyServer());
		client = new Thread(new NettyClient(new Game(), "localhost", 20150));
	}

	public void run() {
		try {
			server.start();
			Thread.sleep(500);

			client.run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Command(description = "conntect to a server")
	public void connect(@Param(name = "host") String host,
			@Param(name = "port") String port) {
		if (core!= null) {
			log.warn("client already connected!");
			return;
		}
		log.debug("connect to {}:{}", host, port);
		game = new Game();
		NettyClient c = new NettyClient(game, host, Integer.parseInt(port));
		c.addConnectListener(e -> core = e.getClientHandler());
		new Thread(c, "Network-Layer-Thread").start();
	}
	
	
	public ClientHandler getCore() {
		return core;
	}

	public void setCore(ClientHandler core) {
		this.core = core;
	}

}
