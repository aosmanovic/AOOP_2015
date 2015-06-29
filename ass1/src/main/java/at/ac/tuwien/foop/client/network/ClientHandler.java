package at.ac.tuwien.foop.client.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.ClientPlayer;
import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.service.GameCore;
import at.ac.tuwien.foop.domain.Board;
import at.ac.tuwien.foop.domain.WindGust;
import at.ac.tuwien.foop.domain.message.Message;
import at.ac.tuwien.foop.domain.message.Message.Type;
import at.ac.tuwien.foop.domain.message.client.IntroduceMessage;
import at.ac.tuwien.foop.domain.message.client.WindMessage;
import at.ac.tuwien.foop.domain.message.server.BoardMessage;
import at.ac.tuwien.foop.domain.message.server.GameOverMessage;
import at.ac.tuwien.foop.domain.message.server.NewPlayerMessage;
import at.ac.tuwien.foop.domain.message.server.UnknownMessage;
import at.ac.tuwien.foop.domain.message.server.UpdateMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientHandler extends ChannelHandlerAdapter implements GameCore {
	private static Logger log = LoggerFactory.getLogger(ClientHandler.class);

	private Game game;
	private ObjectMapper mapper = new ObjectMapper();
	private Channel channel;

	private final CopyOnWriteArrayList<ChannelActiveListener> listeners = new CopyOnWriteArrayList<>();

	public ClientHandler(Game game) {
		this.game = game;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("send ping");
		ctx.writeAndFlush(new Message(Type.C_PING));
		channel = ctx.channel();
		listeners.forEach(e -> e.active());
	}

	// messages from the server

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String str = (String) msg;
		log.info(String.format("client received msg: %s", str));

		Message m = mapper.readValue(str, Message.class);

		if (m.type == Type.S_PONG) {
			log.info("yay, got a pong!");
		} else if (m.type == Type.S_NEWPLAYER) {
			game.addPlayer(new ClientPlayer(mapper.readValue(str,
					NewPlayerMessage.class).player));
		} else if (m.type == Type.S_REMOVEPLAYER) {
			// TODO: not needed, as update replaces players anyway, can be used
			// to show a message to the user...
			// game.removePlayer(mapper.readValue(str,
			// RemovePlayerMessage.class).player);
		} else if (m.type == Type.S_BOARD) {
			BoardMessage boardMessage = mapper.readValue(str,
					BoardMessage.class);
			Board board = Board.createBoard(boardMessage.fields,
					boardMessage.width);
			if ("".equals(game.winner())) {
				game.setBoard(board);
			} else {
				game.resetGame(board);
			}
		} else if (m.type == Type.S_UPDATE) {
			UpdateMessage updateMessage = mapper.readValue(str,
					UpdateMessage.class);
			game.update(
					updateMessage.players.stream()
							.map(p -> new ClientPlayer(p))
							.collect(Collectors.toList()), updateMessage.wind);
		} else if (m.type == Type.S_JOINED) {
			game.join();
			// TODO: remove additional data from joined message!

			// JoinedMessage joinedMessage = mapper.readValue(str,
			// JoinedMessage.class);
			// joinedMessage.players.forEach(p -> game.addPlayer(p));
		} else if (m.type == Type.S_ALREADY_FULL) {
			log.debug("game already full");
		} else if (m.type == Type.S_START) {
			game.start();
		} else if (m.type == Type.S_UNKNOWN) {
			log.warn("the server does not know the message '{}'",
					mapper.readValue(str, UnknownMessage.class).unknownType);
		} else if (m.type == Type.S_OVER) {
			GameOverMessage gameOverMessage = mapper.readValue(str,
					GameOverMessage.class);
			game.over(gameOverMessage.player.name());
			// game.start(); // TO DO Get board map from server, it is null !!!
			// BoardMessage boardMessage = mapper.readValue(str,
			// BoardMessage.class);
			// game.setBoard(Board.createBoard(boardMessage.fields,
			// boardMessage.width));

		} else {
			log.warn("unknown message");
		}
	}

	// client requests

	@Override
	public void introduce(String name) {
		System.out.println(channel);
		channel.writeAndFlush(new IntroduceMessage(name));

	}

	@Override
	public void join() {
		channel.writeAndFlush(new Message(Type.C_JOIN));
	}

	@Override
	public void leave() {
		channel.writeAndFlush(new Message(Type.C_LEAVE));
	}

	@Override
	public void disconnect() {
		channel.close();
	}

	@Override
	public void sendWind(WindGust wind) {
		channel.writeAndFlush(new WindMessage(wind));
	}

	@Override
	public void ping() {
		channel.writeAndFlush(new Message(Type.C_PING));
	}

	@Override
	public void start() {
		channel.writeAndFlush(new Message(Type.C_START));
	}

	public void addChannelActiveListener(ChannelActiveListener caListener) {
		listeners.add(caListener);
	}

	@Override
	public void newLevel() {
		channel.writeAndFlush(new Message(Type.C_JOIN));

	}

	@Override
	public void setGame(Game game) {
		this.game = game;
	}
}
