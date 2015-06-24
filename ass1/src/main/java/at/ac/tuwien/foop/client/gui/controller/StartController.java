package at.ac.tuwien.foop.client.gui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.events.ConnectListener;
import at.ac.tuwien.foop.client.gui.view.StartFrame;
import at.ac.tuwien.foop.client.network.NettyClient;

public class StartController implements ConnectListener {

	private static Logger log = LoggerFactory.getLogger(StartController.class);
	private static final int DEFAULT_PORT = 20150;

	private Game game;
	private StartFrame startFrame;

	public StartController() {
		startFrame = new StartFrame();

		startFrame.addConnectButtonListener(e -> connect(
				startFrame.getServerAddress(), DEFAULT_PORT));
	}

	private void connect(String host, int port) {
		log.debug("connect to {}:{}", host, port);
		startFrame.printMessage(String.format("Connect to server '%s:%d'!",
				host, port));

		NettyClient c = new NettyClient(game, host, port);
		c.addConnectListener(this);
		new Thread(c, "Network-Layer-Thread").start();
	}

	public void showStartFrame() {
		startFrame.setVisible(true);
	}

	@Override
	public void onConnect(NettyClient client) {
		// TODO: we probably need a member of it so we can listen to it when the player left a game...
		new BoardController().showBoardFrame();
	}

	@Override
	public void onConnecitonFailure() {
		startFrame.showFailure();
	}

	public void hideBoard() {
		System.exit(0);
	}
}
