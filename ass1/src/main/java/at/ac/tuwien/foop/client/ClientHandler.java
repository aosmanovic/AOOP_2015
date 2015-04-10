package at.ac.tuwien.foop.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import at.ac.tuwien.foop.client.domain.Board;
import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.domain.Player;
import at.ac.tuwien.foop.client.domain.Update;
import at.ac.tuwien.foop.client.domain.Wind;
import at.ac.tuwien.foop.client.service.GameCore;
import at.ac.tuwien.foop.message.BoardMessage;
import at.ac.tuwien.foop.message.Message;
import at.ac.tuwien.foop.message.Message.Type;
import at.ac.tuwien.foop.message.NewPlayerMessage;
import at.ac.tuwien.foop.message.UpdateMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientHandler extends ChannelHandlerAdapter implements
		GameCore {
	private static Logger log = LoggerFactory.getLogger(ClientHandler.class);

	private Game game;
	private ObjectMapper mapper = new ObjectMapper();
	private Channel channel;

	public ClientHandler(Game game) {
		this.game = game;
//		game.addGameEventListener(this);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("send ping");
		ctx.writeAndFlush(new Message(Type.C_PING));
		channel = ctx.channel();
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
					NewPlayerMessage.class).name));
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
		} else {
			log.warn("unknown message");
		}
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
	public void sendWind(Wind wind) {
		throw new NotImplementedException();
	}

	@Override
	public void ping() {
		channel.writeAndFlush(new Message(Type.C_PING));
	}
}
