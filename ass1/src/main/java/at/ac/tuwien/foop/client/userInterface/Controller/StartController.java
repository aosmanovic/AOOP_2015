package at.ac.tuwien.foop.client.userInterface.Controller;




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asg.cliche.Command;
import asg.cliche.Param;
import at.ac.tuwien.foop.client.ClientHandler;
import at.ac.tuwien.foop.client.NettyClient;
import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.domain.Player;
import at.ac.tuwien.foop.client.events.ConnectListener;
import at.ac.tuwien.foop.client.service.GameService;
import at.ac.tuwien.foop.client.shell.ClientShell;
import at.ac.tuwien.foop.client.userInterface.Views.StartView;
import at.ac.tuwien.foop.server.NettyServer;

public class StartController implements ConnectListener {

	private static Logger log = LoggerFactory.getLogger(StartController.class);
	private Game game = new Game();
	private ClientHandler core = null;
//	private Thread server;
	private StartView start;

	public StartController() {
		
//		server = new Thread(new NettyServer());
		start = new StartView(game);
		connect("localhost", "20150");
		
		
		start.setStartControllerListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameStart();
			}
		});
	}

	public void connect(String host,String port) {
		/*if (core!= null) {
			log.warn("client already connected!");
			showAlreadyConnected();
			return;
		}*/
		log.debug("connect to {}:{}", host, port);
		game = new Game();
		NettyClient c = new NettyClient(game, host, Integer.parseInt(port));
		c.addConnectListener(this);
		new Thread(c, "Network-Layer-Thread").start();
	}
	
	
	
	public void gameStart() {
		start.showMaze();
	}
	
	public void onConnect(NettyClient client) {
		core = client.getClientHandler();
		//view shows success
		//start.showMaze();
	}
	
	
	public ClientHandler getCore() {
		return core;
	}

	public void setCore(ClientHandler core) {
		this.core = core;
	}

	@Override
	public void onConnecitonFailure() {
		start.showFailure();
	}
	
	public void showStart() {
		start.setVisible(true);
	}
	
	public void showAlreadyConnected() {
		start.showAlreadyConnected();
	}

}
