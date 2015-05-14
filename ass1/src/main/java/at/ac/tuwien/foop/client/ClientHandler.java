package at.ac.tuwien.foop.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.Board;
import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.domain.Player;
import at.ac.tuwien.foop.client.service.GameCore;
import at.ac.tuwien.foop.domain.Update;
import at.ac.tuwien.foop.domain.Wind;
import at.ac.tuwien.foop.domain.message.Message;
import at.ac.tuwien.foop.domain.message.Message.Type;
import at.ac.tuwien.foop.domain.message.client.JoinMessage;
import at.ac.tuwien.foop.domain.message.client.WindMessage;
import at.ac.tuwien.foop.domain.message.server.BoardMessage;
import at.ac.tuwien.foop.domain.message.server.NewPlayerMessage;
import at.ac.tuwien.foop.domain.message.server.UnknownMessage;
import at.ac.tuwien.foop.domain.message.server.UpdateMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientHandler extends ChannelHandlerAdapter implements
		GameCore {
	private static Logger log = LoggerFactory.getLogger(ClientHandler.class);

	private Game game;
	private ObjectMapper mapper = new ObjectMapper();
	private Channel channel;

	private List<ChannelActiveListener> listeners = new ArrayList<>();

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
			game.addPlayer(new Player(mapper.readValue(str,
					NewPlayerMessage.class).name, null)); // TODO, set coordinates
		} else if (m.type == Type.S_BOARD) {
			game.setBoard(Board.createBoard(mapper.readValue(str,
					BoardMessage.class).fields));
		} else if (m.type == Type.S_UPDATE) {
			game.update(Update.createUpdate(mapper.readValue(str,
					UpdateMessage.class)));
		} else if (m.type == Type.S_JOINED) {
			game.join();
		} else if (m.type == Type.S_ALREADY_FULL) {
			log.debug("game already full");
		} else if (m.type == Type.S_START) {
			game.start();
		} else if (m.type == Type.S_UNKNOWN) {
			log.warn("the server does not know the message '{}'", mapper.readValue(str,	UnknownMessage.class).unknownType);
		} else {
			log.warn("unknown message");
		}
	}

	// client requests

	@Override
	public void join(String name) {
		System.out.println(channel);
		channel.writeAndFlush(new JoinMessage(name));
		
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
	public void sendWind(Wind wind) {
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
}
