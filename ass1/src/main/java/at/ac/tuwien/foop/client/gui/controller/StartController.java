package at.ac.tuwien.foop.client.gui.controller;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.events.ConnectListener;
import at.ac.tuwien.foop.client.gui.utils.FontStore;
import at.ac.tuwien.foop.client.gui.view.StartFrame;
import at.ac.tuwien.foop.client.network.NettyClient;
import at.ac.tuwien.foop.server.event.ServerReadyListener;

public class StartController implements ConnectListener, ServerReadyListener {

	private static Logger log = LoggerFactory.getLogger(StartController.class);
	private static final int DEFAULT_PORT = 20150;

	private Game game;
	private StartFrame startFrame;
	private BoardController boardController;

	public StartController() {
		game = new Game();

		setUIFont(FontStore.getInstance().getGameFont());
		
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

	public void hideBoard() {
		System.exit(0);
	}
	
	private void setUIFont(Font font) {
		{
			Enumeration<Object> keys = UIManager.getDefaults().keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				Object value = UIManager.get(key);
				if (value instanceof FontUIResource) {
					UIManager.put(
							key,
							new FontUIResource(font.deriveFont(new Float(
									((FontUIResource) value).getSize()))));
				}
			}
		}
	}

	@Override
	public void onConnect(NettyClient client) {
		boardController = new BoardController(game, client.getClientHandler());
	}

	@Override
	public void onConnecitonFailure() {
		startFrame.showFailure();
	}

	@Override
	public void onReady() {
		startFrame.printMessage("Server started on your computer. Use 'localhost' to connect!");
	}

	@Override
	public void onFailure() {
		startFrame.printMessage("ATTENTION: Could not spawn a server, maybe there is already one running on your computer!?");
	}
}
