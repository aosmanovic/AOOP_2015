package at.ac.tuwien.foop.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.model.Game;
import at.ac.tuwien.foop.client.model.Player;
import at.ac.tuwien.foop.message.Message;
import at.ac.tuwien.foop.message.NewPlayerMessage;
import at.ac.tuwien.foop.message.Message.Type;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientHandler extends ChannelHandlerAdapter {
	private static Logger log = LoggerFactory.getLogger(ClientHandler.class);

	private Game game;
	private ObjectMapper mapper = new ObjectMapper();

	public ClientHandler(Game game) {
		this.game = game;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("send ping");
		ctx.writeAndFlush(new Message(Type.C_PING));
	}

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
		} else if (m.type == Type.S_JOINED) {
			// TODO: check if needed?
		} else {
			log.warn("unknown message");
		}
	}
}
