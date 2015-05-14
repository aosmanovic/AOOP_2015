package at.ac.tuwien.foop.client.userInterface.Controller;




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.ClientHandler;
import at.ac.tuwien.foop.client.NettyClient;
import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.events.ConnectListener;
import at.ac.tuwien.foop.client.events.GameEvent;
import at.ac.tuwien.foop.client.events.GameEventListener;
import at.ac.tuwien.foop.client.service.GameCore;
import at.ac.tuwien.foop.client.service.GameService;
import at.ac.tuwien.foop.client.userInterface.Views.Maze;
import at.ac.tuwien.foop.client.userInterface.Views.StartView;

public class StartController implements ConnectListener, GameEventListener {

	private static Logger log = LoggerFactory.getLogger(StartController.class);
	private Game game;
	private GameCore core = null;
//	private Thread server;
	private StartView start;
	private GameService service = new GameService();
	
	public StartController() {
		
//		server = new Thread(new NettyServer());
		start = new StartView();
		connect("localhost", "20150");
		game.addGameEventListener(this);
		
		
		start.setStartControllerListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameStart();
			}
		});
			
	}

	public void connect(String host,String port) {
		log.debug("connect to {}:{}", host, port);
		game = new Game();
		start.setGame(game);
		
		NettyClient c = new NettyClient(game, host, Integer.parseInt(port));
		c.addConnectListener(this);
		new Thread(c, "Network-Layer-Thread").start();
	}
	
	
	
	public void gameStart() {
		service.start(game, core);
	}
	
	public void onConnect(NettyClient client) {
		core = client.getClientHandler();
		service.join(game, core, "A");
		start.setStart();
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
	
	@Override
	public void onUpdate(GameEvent e) {
		// TODO Auto-generated method stub
		if (e.type == GameEvent.Type.NEW_PLAYER ) {
			start.printMessage();
		} else if (e.type == GameEvent.Type.START) {
			showMaze();
		} else if (e.type == GameEvent.Type.BOARD) {
			game.getBoard();
		}
	}
	
	public void showMaze() {
		new Maze();
	}

}
