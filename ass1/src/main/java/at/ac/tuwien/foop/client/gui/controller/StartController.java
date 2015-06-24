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

public class StartController implements ConnectListener {

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

	@Override
	public void onConnect(NettyClient client) {
		boardController = new BoardController(game, client.getClientHandler());
	}

	@Override
	public void onConnecitonFailure() {
		startFrame.showFailure();
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
}
