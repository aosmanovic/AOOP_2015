package at.ac.tuwien.foop.client.gamelogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asg.cliche.Command;
import asg.cliche.Param;
import at.ac.tuwien.foop.client.ClientHandler;
import at.ac.tuwien.foop.client.NettyClient;
import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.events.ConnectListener;
import at.ac.tuwien.foop.client.service.GameService;
import at.ac.tuwien.foop.client.shell.ClientShell;
import at.ac.tuwien.foop.client.shell.Start;
import at.ac.tuwien.foop.server.NettyServer;

public class StartController implements ConnectListener {

	private static Logger log = LoggerFactory.getLogger(StartController.class);
	private Game game = null;
	private ClientHandler core = null;
	private Thread server;
	private Thread client;
	private Start start = new Start();

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



	public void connect(String host,String port) {
		if (core!= null) {
			log.warn("client already connected!");
			return;
		}
		log.debug("connect to {}:{}", host, port);
		game = new Game();
		NettyClient c = new NettyClient(game, host, Integer.parseInt(port));
		c.addConnectListener(this);
		new Thread(c, "Network-Layer-Thread").start();
	}
	
	public void onConnect(NettyClient client) {
		core = client.getClientHandler();
		//view shows success
		start.showMaze();
	}
	
	
	public ClientHandler getCore() {
		return core;
	}

	public void setCore(ClientHandler core) {
		this.core = core;
	}

}
