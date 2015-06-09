package at.ac.tuwien.foop.server.shell;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asg.cliche.Command;
import asg.cliche.ShellFactory;
import at.ac.tuwien.foop.server.domain.Game;
import at.ac.tuwien.foop.server.network.NettyServer;
import at.ac.tuwien.foop.server.service.GameLogicService;

public class ServerShell {
	private static Logger log = LoggerFactory.getLogger(ServerShell.class);

	private NettyServer server;

	public ServerShell() {
		server = new NettyServer(new Game(
				new GameLogicService().loadBoard(GameLogicService.getBoardPath(0))));
		new Thread(server).start();
	}
	
	@Command(description = "disconnect all clients and ")
	public void disconnect() {
		server.shutDown();
	}

	
	@Command(description = "show some system information")
	public void show() {
		log.info("show some system information");
	}

	public static void main(String[] args) throws IOException {
		log.debug("start client shell");

		ShellFactory.createConsoleShell("client shell", "client shell",
				new ServerShell()).commandLoop();
	}
}
